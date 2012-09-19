<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPBS_APLICACIONES" (Start)
 */

Ext.ns('Ext.com.befasoft.license.components');

Ext.com.befasoft.license.components.Appbs_aplicacionesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.license.components.Appbs_aplicacionesData.superclass.constructor.call(this, Ext.apply({
            id: 'idAplicacionesData',
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath +'/Aplicaciones.action',
            fields: [
                "id_aplicacion", "nombre"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.license.components.Appbs_aplicacionesGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    pageSize: 25,

    initComponent : function() {
        // DataStore
        this.appbs_aplicacionesData = new Ext.com.befasoft.license.components.Appbs_aplicacionesData();

        var config = {
            region: 'center',
            store: this.appbs_aplicacionesData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='appbs_aplicaciones.id_aplicacion'/>", sortable: true, dataIndex: 'id_aplicacion', width: 20 },
                { header:"<s:text name='appbs_aplicaciones.nombre'/>", sortable: true, dataIndex: 'nombre', width: 80 }

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
        Ext.com.befasoft.license.components.Appbs_aplicacionesGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.appbs_aplicacionesData.load({
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

Ext.com.befasoft.license.components.Appbs_aplicacionesForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,

    initComponent : function() {

        var config = {
            region: 'south',
            height:100,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { fieldLabel:"<s:text name='appbs_aplicaciones.id_aplicacion'/>", name: 'id_aplicacion', allowBlank:false, width:250 },
                { fieldLabel:"<s:text name='appbs_aplicaciones.nombre'/>", name: 'nombre', allowBlank:false, width:250 }
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/disk.png',
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
                            url: appPath+'/Aplicaciones.action?action=save',
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
                    icon: appBasicPath+'/images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appbs_aplicaciones.msg.delete"/>",
                                function (btn) {
                                    if (btn == "yes") {
                                        mainDiv.mask("<s:text name='appbs.wait'/>");
                                        var data = [];
                                        data.push(component.form.getFieldValues());
                                        Ext.Ajax.request({
                                            url: appPath+'/Aplicaciones.action?action=save',
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
                        this.newRecord();
                    }
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.license.components.Appbs_aplicacionesForm.superclass.initComponent.call(this);
    },

    newRecord: function () {
        var data = {};
        data["id_aplicacion"] = "";
        data["nombre"] = "";

        this.getForm().loadRecord(new Ext.data.Record(data));
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appbs_aplicaciones
//------------------------------------------------------------------------
function openTabAppbs_aplicaciones(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.license.components.Appbs_aplicacionesGrid();
        var form = new Ext.com.befasoft.license.components.Appbs_aplicacionesForm({ linkedGrid: grid });
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

</script>
