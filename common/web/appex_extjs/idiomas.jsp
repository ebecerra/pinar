<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_IDIOMAS" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appex_idiomasData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appex_idiomasData.superclass.constructor.call(this, Ext.apply({
            id: 'idIdiomasData',
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Idiomas.action',
            fields: [
                "logo", "nombre", "iso_2", "id_idioma", "activo", "iso_3", "abreviatura"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appex_idiomasGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    pageSize: 25,

    initComponent : function() {
        // DataStore
        this.appex_idiomasData = new Ext.com.befasoft.common.components.Appex_idiomasData();

        var config = {
            region: 'center',
            store: this.appex_idiomasData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='appex_idiomas.id_idioma'/>", sortable: true, dataIndex: 'id_idioma', width: 10 },
                { header:"<s:text name='appex_idiomas.nombre'/>", sortable: true, dataIndex: 'nombre', width: 40 },
                { header:"<s:text name='appex_idiomas.abreviatura'/>", sortable: true, dataIndex: 'abreviatura', width: 10 },
                { header:"<s:text name='appex_idiomas.iso_2'/>", sortable: true, dataIndex: 'iso_2', width: 10 },
                { header:"<s:text name='appex_idiomas.iso_3'/>", sortable: true, dataIndex: 'iso_3', width: 10 },
                { header:"<s:text name='appex_idiomas.activo'/>", sortable: true, dataIndex: 'activo', renderer: formatBoolean, width: 10 },
                { header:"<s:text name='appex_idiomas.logo'/>", sortable: true, dataIndex: 'logo', width: 10,
                    renderer: function (value, p, record) {
                        if (value != null && value != '')
                            return String.format("<img src='../images/{0}' width='20'/>", value);
                        else
                            return "";
                    }
                }
            ],
            bbar: new Ext.PagingToolbar({
                pageSize: this.pageSize,
                store: this.appex_idiomasData,
                displayInfo: true,
                displayMsg: "<s:text name="appex_idiomas.pg_info"/>",
                emptyMsg: "<s:text name="appex_idiomas.pg_none"/>"
            }),
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                scope: this,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                       this.linkedForm.getForm().loadRecord(record);
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appex_idiomasGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.appex_idiomasData.load({ params:{start:0, limit: this.pageSize}, callback: dataSourceLoad });
    }

});

Ext.com.befasoft.common.components.Appex_idiomasForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,

    initComponent : function() {

        var config = {
            region: 'south',
            height:200,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { name: 'id_idioma', xtype : 'hidden' },
                { fieldLabel:"<s:text name='appex_idiomas.nombre'/>", name: 'nombre', allowBlank:false, width:200 },
                { fieldLabel:"<s:text name='appex_idiomas.logo'/>", name: 'logo', allowBlank:false, width:100 },
                { fieldLabel:"<s:text name='appex_idiomas.abreviatura'/>", name: 'abreviatura', allowBlank:false, width:100 },
                { fieldLabel:"<s:text name='appex_idiomas.iso_2'/>", name: 'iso_2', allowBlank:false, width:100 },
                { fieldLabel:"<s:text name='appex_idiomas.iso_3'/>", name: 'iso_3', allowBlank:false, width:100 },
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_idiomas.activo'/>", name: 'activo'}
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: '../images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        mainDiv.mask("<s:text name='appbs.wait'/>");
                        var data = [];
                        data.push(this.form.getFieldValues());

                        Ext.Ajax.request({
                            url: appPath+'/Idiomas.action?action=save',
                            params: { dataToSave:Ext.encode(data) },
                            scope: this,
                            success: function(response) {
                                var params = getActionResponse(response);
                                if (params.success) {
                                    mainDiv.unmask();
                                    this.linkedGrid.updateData();
                                } else {
                                    showServerError(params);
                                }
                            },
                            failure: function() {
                                mainDiv.unmask();
                                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                            }
                        });
                    }
                },
                {
                    xtype: 'tbbutton',
                    icon: '../images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_idiomas.msg.delete"/>",
                            function (btn) {
                                if (btn == "yes") {
                                    mainDiv.mask("<s:text name='appbs.wait'/>");
                                    var data = [];
                                    data.push(this.form.getFieldValues());
                                    Ext.Ajax.request({
                                        url: appPath+'/Idiomas.action?action=save',
                                        params: { elementsToDelete: Ext.encode(data) },
                                        success: function(response) {
                                            var params = getActionResponse(response);
                                            if (params.success) {
                                                mainDiv.unmask();
                                                component.getForm().reset();
                                                component.linkedGrid.updateData();
                                            } else {
                                                showServerError(params);
                                            }
                                        },
                                        failure: function() {
                                            mainDiv.unmask();
                                            Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                        }
                                    });
                                }
                            }
                        );
                    }
                },
                {
                    text: "<s:text name='appbs.btn.reset.record'/>",
                    scope: this,
                    handler : function() {
                        this.getForm().reset();
                    }
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appex_idiomasForm.superclass.initComponent.call(this);
    }
});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appex_idiomas
//------------------------------------------------------------------------
function openTabIdiomas(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(title))) {
        var grid = new Ext.com.befasoft.common.components.Appex_idiomasGrid();
        var form = new Ext.com.befasoft.common.components.Appex_idiomasForm({ linkedGrid: grid });
        grid.linkedForm = form;
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:false,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[grid, form]
        });
        tabs.add(tab);
        grid.updateData();
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}

/*
 * Mantenimiento de "APPEX_IDIOMAS" (End)
 */


</script>
