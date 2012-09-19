<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_CLIENTES" (Start)
 */

Ext.ns('Ext.com.befasoft.license.components');

Ext.com.befasoft.license.components.Appex_clientesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.license.components.Appex_clientesData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Clientes.action',
            fields: [
                "id_cliente", "nombre", "razon_social", "cif", "email", "telefono", "movil", "activo",
                "int_param_1", "int_param_2", "int_param_3", "int_param_4", "str_param_1", "str_param_2", "str_param_3", "str_param_4"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.license.components.Appex_clientesGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    pageSize: 25,
    changeCallback: null,

    initComponent : function() {
        // DataStore
        this.appex_clientesData = new Ext.com.befasoft.license.components.Appex_clientesData();

        var config = {
            store: this.appex_clientesData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appex_clientes.id_cliente'/>", sortable: true, dataIndex: 'id_cliente', width: 10 },
                { header:"<s:text name='appex_clientes.nombre'/>", sortable: true, dataIndex: 'nombre', width: 20 },
                { header:"<s:text name='appex_clientes.razon_social'/>", sortable: true, dataIndex: 'razon_social', width: 20 },
                { header:"<s:text name='appex_clientes.cif'/>", sortable: true, dataIndex: 'cif', width: 10 },
                { header:"<s:text name='appex_clientes.email'/>", sortable: true, dataIndex: 'email', width: 10 },
                { header:"<s:text name='appex_clientes.telefono'/>", sortable: true, dataIndex: 'telefono', width: 10 },
                { header:"<s:text name='appex_clientes.movil'/>", sortable: true, dataIndex: 'movil', width: 10 },
                { header:"<s:text name='appex_clientes.activo'/>", sortable: true, dataIndex: 'activo', renderer: formatBoolean, width: 10}
            ],
            viewConfig: {
                forceFit: true
            },
            frame:true,
            bbar: new Ext.PagingToolbar({
                pageSize: this.pageSize,
                store: this.appex_clientesData,
                displayInfo: true,
                displayMsg: "<s:text name="appex_clientes.pg_info"/>",
                emptyMsg: "<s:text name="appex_clientes.pg_none"/>",
                items:[
                    {
                        text: "<s:text name='appbs.btn.add.record'/>",
                        icon: 'images/add.png',
                        scope: this,
                        handler: function() {
                            var win = new Ext.com.befasoft.license.components.Appex_clientesWin({ linkedGrid: this });
                            var data = {};
                            data["id_cliente"] = null;
                            data["nombre"] = null;
                            data["razon_social"] = null;
                            data["cif"] = null;
                            data["email"] = null;
                            data["telefono"] = null;
                            data["movil"] = null;
                            data["activo"] = null;
                            data["int_param_1"] = null;
                            data["int_param_2"] = null;
                            data["int_param_3"] = null;
                            data["int_param_4"] = null;
                            data["str_param_1"] = null;
                            data["str_param_2"] = null;
                            data["str_param_3"] = null;
                            data["str_param_4"] = null;

                            var currentRecord = new Ext.data.Record(data);
                            win.loadRecord(currentRecord);
                            win.show();
                        }
                    }
                ]
            }),
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                scope: this,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        if (this.changeCallback)
                            this.changeCallback.call(this.scopeCallback, record);
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.license.components.Appex_clientesGrid.superclass.initComponent.call(this);

        this.on('rowdblclick', function(grid, rowIndex, e) {
            var currentRecord = grid.getStore().getAt(rowIndex);
            var win = new Ext.com.befasoft.license.components.Appex_clientesWin({ linkedGrid: this });
            win.loadRecord(currentRecord);
            win.show();
        });

    },

    updateData: function () {
        this.appex_clientesData.load({ params:{start:0, limit: this.pageSize}, callback: dataSourceLoad });
    }

});

Ext.com.befasoft.license.components.Appex_clientesForm = Ext.extend(Ext.FormPanel, {

    parentWin: null,

    initComponent : function() {

        var config = {
            region: 'south',
            height:460,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            labelWidth: 120,
            items:[
                { fieldLabel:"<s:text name='appex_clientes.id_cliente'/>", name: 'id_cliente', allowBlank:false, width: 50 },
                { fieldLabel:"<s:text name='appex_clientes.nombre'/>", name: 'nombre', allowBlank:false  },
                { fieldLabel:"<s:text name='appex_clientes.razon_social'/>", name: 'razon_social', allowBlank:true  },
                { fieldLabel:"<s:text name='appex_clientes.cif'/>", name: 'cif', allowBlank:false  },
                { fieldLabel:"<s:text name='appex_clientes.email'/>", name: 'email', allowBlank:false  },
                { fieldLabel:"<s:text name='appex_clientes.telefono'/>", name: 'telefono', allowBlank:false  },
                { fieldLabel:"<s:text name='appex_clientes.movil'/>", name: 'movil', allowBlank:true  },
                { fieldLabel:"<s:text name='appex_clientes.activo'/>", name: 'activo', allowBlank:true, xtype: 'checkbox'  },
                { fieldLabel:"<s:text name='appex_clientes.int_param_1'/>", name: 'int_param_1', allowBlank:true , xtype : 'numberfield', width: 50 },
                { fieldLabel:"<s:text name='appex_clientes.int_param_2'/>", name: 'int_param_2', allowBlank:true , xtype : 'numberfield', width: 50 },
                { fieldLabel:"<s:text name='appex_clientes.int_param_3'/>", name: 'int_param_3', allowBlank:true , xtype : 'numberfield', width: 50 },
                { fieldLabel:"<s:text name='appex_clientes.int_param_4'/>", name: 'int_param_4', allowBlank:true , xtype : 'numberfield', width: 50 },
                { fieldLabel:"<s:text name='appex_clientes.str_param_1'/>", name: 'str_param_1', allowBlank:true  },
                { fieldLabel:"<s:text name='appex_clientes.str_param_2'/>", name: 'str_param_2', allowBlank:true  },
                { fieldLabel:"<s:text name='appex_clientes.str_param_3'/>", name: 'str_param_3', allowBlank:true  },
                { fieldLabel:"<s:text name='appex_clientes.str_param_4'/>", name: 'str_param_4', allowBlank:true  }

            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: 'images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        if (this.getForm().isValid()) {
                            mainDiv.mask("<s:text name='appbs.wait'/>");
                            var data = [];
                            data.push(this.form.getFieldValues());

                            Ext.Ajax.request({
                                url: appPath+'/Clientes.action?action=save',
                                params: { dataToSave:Ext.encode(data) },
                                scope: this,
                                success: function(response) {
                                    var params = getActionResponse(response);
                                    if (params.success) {
                                        mainDiv.unmask();
                                        this.parentWin.linkedGrid.updateData();
                                        this.parentWin.close();
                                    } else {
                                        showServerError(params);
                                    }
                                },
                                failure: function() {
                                    mainDiv.unmask();
                                    Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                }
                            });
                        } else
                            Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="appbs.form.msg.invalid"/>");
                    }
                },
                {
                    xtype: 'tbbutton',
                    icon: 'images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_clientes.msg.delete"/>",
                                function (btn) {
                                    if (btn == "yes") {
                                        mainDiv.mask("<s:text name='appbs.wait'/>");
                                        var data = [];
                                        data.push(component.form.getFieldValues());
                                        Ext.Ajax.request({
                                            url: appPath+'/Clientes.action?action=save',
                                            params: { elementsToDelete: Ext.encode(data) },
                                            success: function(response) {
                                                var params = getActionResponse(response);
                                                if (params.success) {
                                                    mainDiv.unmask();
                                                    component.parentWin.linkedGrid.updateData();
                                                    component.parentWin.close();
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
        Ext.com.befasoft.license.components.Appex_clientesForm.superclass.initComponent.call(this);
    }
});

Ext.com.befasoft.license.components.Appex_clientesWin = Ext.extend(Ext.Window, {

    linkedGrid: null,

    initComponent : function() {
        this.editForm = new Ext.com.befasoft.license.components.Appex_clientesForm({ parentWin : this });
        var config = {
            layout:'fit',
            frame:true,
            closable: true,
            closeAction: 'hide',
            width:310,
            resizable: false,
            title:"<s:text name='appex_clientes.caption'/>",
            items: [this.editForm]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.license.components.Appex_clientesWin.superclass.initComponent.call(this);
    },

    loadRecord: function (record) {
        this.editForm.getForm().loadRecord(record);
    },

    getValues: function () {
        return this.editForm.getValues();
    }
});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appex_clientes
//------------------------------------------------------------------------
function openTabAppex_clientes(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.license.components.Appex_clientesGrid();
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[grid]
        });
        tabs.add(tab);
        grid.updateData();
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}

</script>
