<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_PAISES" (Start)
 */

Ext.ns('Ext.com.humanlike.web.components');

Ext.com.humanlike.web.components.Appex_paisesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.humanlike.web.components.Appex_paisesData.superclass.constructor.call(this, Ext.apply({
            id: 'idPaisesData',
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Paises.action',
            fields: [
                "id_pais", "nombre", "iso_code_2", "iso_code_3", "activo", "g_code", "geolocalizacion"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.humanlike.web.components.Appex_paisesGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,

    initComponent : function() {
        // DataStore
        this.appex_paisesData = new Ext.com.humanlike.web.components.Appex_paisesData();

        var config = {
            region: 'center',
            store: this.appex_paisesData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='appex_paises.id_pais'/>", sortable: true, dataIndex: 'id_pais', width: 10 },
                { header:"<s:text name='appex_paises.nombre'/>", sortable: true, dataIndex: 'nombre', width: 30 },
                { header:"<s:text name='appex_paises.iso_code_2'/>", sortable: true, dataIndex: 'iso_code_2', width: 10 },
                { header:"<s:text name='appex_paises.iso_code_3'/>", sortable: true, dataIndex: 'iso_code_3', width: 10 },
                { header:"<s:text name='appex_paises.activo'/>", sortable: true, dataIndex: 'activo', width: 10, renderer: formatBoolean },
                { header:"<s:text name='appex_paises.g_code'/>", sortable: true, dataIndex: 'g_code', width: 10 },
                { header:"<s:text name='appex_paises.geolocalizacion'/>", sortable: true, dataIndex: 'geolocalizacion', width: 30 }
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
        Ext.com.humanlike.web.components.Appex_paisesGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.appex_paisesData.load({
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

Ext.com.humanlike.web.components.Appex_paisesForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,

    initComponent : function() {

        var config = {
            region: 'south',
            height:220,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { name: 'id_pais', xtype : 'hidden' },
                { fieldLabel:"<s:text name='appex_paises.nombre'/>", name: 'nombre', allowBlank:false, width:200 },
                { fieldLabel:"<s:text name='appex_paises.iso_code_2'/>", name: 'iso_code_2', allowBlank:true, width: 50 },
                { fieldLabel:"<s:text name='appex_paises.iso_code_3'/>", name: 'iso_code_3', allowBlank:true, width: 50 },
                { fieldLabel:"<s:text name='appex_paises.activo'/>", name: 'activo', xtype: 'checkbox', allowBlank:false, width:100 },
                { fieldLabel:"<s:text name='appex_paises.g_code'/>", name: 'g_code', allowBlank:true, width:100 },
                { fieldLabel:"<s:text name='appex_paises.geolocalizacion'/>", name: 'geolocalizacion', allowBlank:true, width:200 }
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
                            url: appPath+'/Paises.action?action=save',
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
                    icon: 'images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_paises.msg.delete"/>",
                                function (btn) {
                                    if (btn == "yes") {
                                        mainDiv.mask("<s:text name='appbs.wait'/>");
                                        var data = [];
                                        data.push(this.form.getFieldValues());
                                        Ext.Ajax.request({
                                            url: appPath+'/Paises.action?action=save',
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
        Ext.com.humanlike.web.components.Appex_paisesForm.superclass.initComponent.call(this);
    },

    newRecord: function () {
        var data = {};
        data["id_pais"] = "";
        data["nombre"] = "";
        data["iso_code_2"] = "";
        data["iso_code_3"] = "";
        data["activo"] = "";
        data["g_code"] = "";
        data["geolocalizacion"] = "";

        this.getForm().loadRecord(new Ext.data.Record(data));
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appex_paises
//------------------------------------------------------------------------
function openTabAppex_paises(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.humanlike.web.components.Appex_paisesGrid();
        var form = new Ext.com.humanlike.web.components.Appex_paisesForm({ linkedGrid: grid });
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
