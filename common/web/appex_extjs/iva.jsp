<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ page import="com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_IVA" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appex_ivaData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appex_ivaData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Iva.action',
            fields: [
                "id_iva", "nombre", "recargo", "valor"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appex_ivaGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    userlog: false,

    initComponent : function() {
        // DataStore
        this.appex_ivaData = new Ext.com.befasoft.common.components.Appex_ivaData();

        var config = {
            region: 'center',
            store: this.appex_ivaData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='appex_iva.id_iva'/>", sortable: true, dataIndex: 'id_iva', width: 10 },
                { header:"<s:text name='appex_iva.nombre'/>", sortable: true, dataIndex: 'nombre', width: 50 },
                { header:"<s:text name='appex_iva.valor'/>", sortable: true, dataIndex: 'valor', width: 10 },
                { header:"<s:text name='appex_iva.recargo'/>", sortable: true, dataIndex: 'recargo', width: 10 }
            ],
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
        Ext.com.befasoft.common.components.Appex_ivaGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        if (this.userlog)
            writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_IVA_LIST %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>);
        this.appex_ivaData.load({
            scope: this,
            callback: function () {
                if (this.store.getCount() == 0) {
                    this.linkedForm.newRecord();
                } else {
                    this.getSelectionModel().selectFirstRow();
                }
            }
        });
    }

});

Ext.com.befasoft.common.components.Appex_ivaForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,
    userlog: false,

    initComponent : function() {

        var config = {
            region: 'south',
            height:150,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { name: 'id_iva', xtype : 'hidden' },
                { fieldLabel:"<s:text name='appex_iva.nombre'/>", name: 'nombre', allowBlank:false, width:300 },
                { fieldLabel:"<s:text name='appex_iva.recargo'/>", name: 'recargo', allowBlank:false, xtype : 'numberfield', width:100 },
                { fieldLabel:"<s:text name='appex_iva.valor'/>", name: 'valor', allowBlank:false, xtype : 'numberfield', width:100 }
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: 'images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        if (!this.getForm().isValid()) {
                            Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="appbs.form.msg.invalid"/>");
                            return;
                        }
                        mainDiv.mask("<s:text name='appbs.wait'/>");
                        var data = [];
                        data.push(this.form.getFieldValues());

                        Ext.Ajax.request({
                            url: appPath+'/Iva.action?action=save',
                            params: { dataToSave:Ext.encode(data) },
                            scope: this,
                            success: function(response) {
                                var params = getActionResponse(response);
                                if (params.success) {
                                    mainDiv.unmask();
                                    if (this.userlog)
                                        writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_IVA_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, this.getForm().findField("id_iva").getValue(), "GUARDAR");
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
                    icon: 'images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_iva.msg.delete"/>",
                            function (btn) {
                                if (btn == "yes") {
                                    mainDiv.mask("<s:text name='appbs.wait'/>");
                                    var data = [];
                                    data.push(component.form.getFieldValues());
                                    Ext.Ajax.request({
                                        url: appPath+'/Iva.action?action=save',
                                        params: { elementsToDelete: Ext.encode(data) },
                                        success: function(response) {
                                            var params = getActionResponse(response);
                                            if (params.success) {
                                                mainDiv.unmask();
                                                if (component.userlog)
                                                    writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_IVA_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, component.getForm().findField("id_iva").getValue(), "BORRAR");
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
                        this.newRecord();
                    }
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appex_ivaForm.superclass.initComponent.call(this);
    },

    newRecord: function () {
        var data = {};
        data["id_iva"] = 0;
        data["nombre"] = "";
        data["recargo"] = 0;
        data["valor"] = 0;

        this.getForm().loadRecord(new Ext.data.Record(data));
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appex_iva
//------------------------------------------------------------------------
function openTabAppex_iva(title, idMenu, userlog) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.common.components.Appex_ivaGrid({ userlog: userlog });
        var form = new Ext.com.befasoft.common.components.Appex_ivaForm({ linkedGrid: grid, userlog: userlog });
        grid.linkedForm = form;
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:true,
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
 * Mantenimiento de "APPEX_IVA" (End)
 */


</script>
