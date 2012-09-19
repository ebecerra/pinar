<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_DISTRIBUIDORES" (Start)
 */

Ext.ns('Ext.com.befasoft.license.components');

Ext.com.befasoft.license.components.Appex_distribuidoresData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.license.components.Appex_distribuidoresData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Distribuidores.action',
            fields: [
                "id_distribuidor", "nombre", "descripcion", "activo"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.license.components.Appex_distribuidoresGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    changeCallback: null,
    scopeCallback: null,

    initComponent : function() {
        // DataStore
        this.appex_distribuidoresData = new Ext.com.befasoft.license.components.Appex_distribuidoresData();

        var config = {
            region: 'center',
            store: this.appex_distribuidoresData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='appex_distribuidores.id_distribuidor'/>", sortable: true, dataIndex: 'id_distribuidor', width: 10 },
                { header:"<s:text name='appex_distribuidores.nombre'/>", sortable: true, dataIndex: 'nombre', width: 40 },
                { header:"<s:text name='appex_distribuidores.descripcion'/>", sortable: true, dataIndex: 'descripcion', width: 40, allowBlank: true },
                { header:"<s:text name='appex_distribuidores.activo'/>", sortable: true, dataIndex: 'activo', width: 10, renderer: formatBoolean }
            ],
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                scope: this,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        this.linkedForm.getForm().loadRecord(record);
                        if (this.changeCallback)
                            this.changeCallback.call(this.scopeCallback, record);
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.license.components.Appex_distribuidoresGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.appex_distribuidoresData.load({
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

Ext.com.befasoft.license.components.Appex_distribuidoresForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,

    initComponent : function() {

        var config = {
            region: 'east',
            width: 300,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            labelWidth: 90,
            items:[
                { fieldLabel:"<s:text name='appex_distribuidores.id_distribuidor'/>", name: 'id_distribuidor', allowBlank:false, xtype : 'numberfield', width:60 },
                { fieldLabel:"<s:text name='appex_distribuidores.nombre'/>", name: 'nombre', allowBlank:false, width:180 },
                { fieldLabel:"<s:text name='appex_distribuidores.descripcion'/>", name: 'descripcion', allowBlank:false, width:180 },
                { boxLabel:"<s:text name='appex_distribuidores.activo'/>", name: 'activo', allowBlank:false, width:150, xtype: 'checkbox' }
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
                            url: appPath+'/Distribuidores.action?action=save',
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
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_distribuidores.msg.delete"/>",
                                function (btn) {
                                    if (btn == "yes") {
                                        mainDiv.mask("<s:text name='appbs.wait'/>");
                                        var data = [];
                                        data.push(this.form.getFieldValues());
                                        Ext.Ajax.request({
                                            url: appPath+'/Distribuidores.action?action=save',
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
        Ext.com.befasoft.license.components.Appex_distribuidoresForm.superclass.initComponent.call(this);
    },

    newRecord: function () {
        var id_distribuidor = 0;
        this.linkedGrid.store.each(function (record) {
            if (id_distribuidor < record.data.id_distribuidor)
                id_distribuidor = record.data.id_distribuidor;
        });
        var data = {};
        data["id_distribuidor"] = ++id_distribuidor;
        data["nombre"] = "";
        data["descripcion"] = "";
        data["activo"] = false;

        this.getForm().loadRecord(new Ext.data.Record(data));
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appex_distribuidores
//------------------------------------------------------------------------
function openTabAppex_distribuidores(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.license.components.Appex_distribuidoresGrid();
        var form = new Ext.com.befasoft.license.components.Appex_distribuidoresForm({ linkedGrid: grid });
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
