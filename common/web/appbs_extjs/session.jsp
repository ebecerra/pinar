<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.UserListData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.UserListData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Usuarios.action?action=getuserlist',
            fields: [
                "user_id", "user_login", "user_name", { name: "creation_time", type: "date", format: "c" }, { name: "last_access", type: "date", format: "c" },
                "sessionId", "user_agent", "mobile"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.UserListGrid = Ext.extend(Ext.grid.GridPanel, {

    fullInfo: false,

    initComponent : function() {
        // DataStore
        this.userData = new Ext.com.befasoft.common.components.UserListData();


        var config = {
            title: "<s:text name='session.info.caption'/>",
            region: 'center',
            store: this.userData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='session.info.user_login'/>", sortable: true, dataIndex: 'user_login', width: 15 },
                { header:"<s:text name='session.info.user_name'/>", sortable: true, dataIndex: 'user_name', width: 30 },
                { header:"<s:text name='session.info.creation_time'/>", sortable: true, dataIndex: 'creation_time', width: 20, renderer: formatDateTime },
                { header:"<s:text name='session.info.last_access'/>", sortable: true, dataIndex: 'last_access', width: 20, renderer: formatDateTime },
                {
                    header:"<s:text name='session.info.disp'/>", sortable: true, dataIndex: 'mobile', width: 15, hidden: !this.fullInfo,
                    renderer: function (value) {
                        return value ? "Mobile" : "Web";
                    }
                },
                { header:"<s:text name='session.info.user_agent'/>", sortable: true, dataIndex: 'user_agent', width: 40, hidden: !this.fullInfo }
            ],
            viewConfig: {
                forceFit: true
            },
            loadMask: true,
            bbar: {
                items: [
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/refresh2.gif',
                        text: "<s:text name='session.info.btn.update'/>",
                        scope: this,
                        handler: function() {
                            this.updateData();
                        }
                    },
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/disconnect.png',
                        text: "<s:text name='session.info.btn.logout'/>",
                        scope: this,
                        handler: function() {
                            var currentRecord = this.getSelectionModel().getSelected();
                            if (currentRecord) {
                                var component = this;
                                Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="session.info.msg.logout"/>",
                                        function (btn) {
                                            if (btn == "yes") {
                                                mainDiv.mask("<s:text name='appbs.wait'/>");
                                                Ext.Ajax.request({
                                                    url: appPath+'/Usuarios.action?action=removeuser',
                                                    params: { sessionId: currentRecord.data.sessionId },
                                                    success: function(response) {
                                                        mainDiv.unmask();
                                                        component.updateData();
                                                    },
                                                    failure: function() {
                                                        mainDiv.unmask();
                                                        Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                                    }
                                                });
                                            }
                                        }
                                );
                            } else
                                Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="session.info.msg.select"/>");
                        }
                    }
                ]
            },
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.UserListGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.userData.load();
    }

});

//------------------------------------------------------------------------
// Abre un Tab con la gestion de sesiones
//------------------------------------------------------------------------
function openTabSesiones(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.common.components.UserListGrid({ fullInfo: true });
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[ grid ]
        });
        tabs.add(tab);
        grid.updateData();
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}

</script>
