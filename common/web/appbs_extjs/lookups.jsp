<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPBS_LOOKUPS" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_lookupsData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_lookupsData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Lookups.action',
            fields: [
                "nombre", "activo", "id_lookup", "editable"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_lookups_valuesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_lookups_valuesData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Lookups_values.action',
            fields: [
                "id_lookup", "id_valor", "activo", "nombre"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

<s:if test="#session.user.perfil_tipo == 'MANAGER'">
    Ext.com.befasoft.common.components.Appbs_lookupsGrid = Ext.extend(Ext.grid.EditorGridPanel, {

        appbs_lookupsElementsToDelete: [],
        linkedGrid: null,

        initComponent : function() {
            // DataStore
            this.appbs_lookupsData = new Ext.com.befasoft.common.components.Appbs_lookupsData();

            var config = {
                region: 'center',
                store: this.appbs_lookupsData,
                autoScroll:true,
                ddGroup: 'depGridDD',
                columns: [
                    { header:"<s:text name='appbs_lookups.id_lookup'/>", sortable: true, dataIndex: 'id_lookup',  editor: new Ext.form.TextField({ allowBlank: false  }), width: 30 },
                    { header:"<s:text name='appbs_lookups.nombre'/>", sortable: true, dataIndex: 'nombre',  editor: new Ext.form.TextField({ allowBlank: false }), width: 50},
                    { header:"<s:text name='appbs_lookups.editable'/>", sortable: true, dataIndex: 'editable', renderer: formatBoolean, editor: new Ext.form.TextField({ allowBlank: false  }), width: 10 },
                    { header:"<s:text name='appbs_lookups.activo'/>", sortable: true, dataIndex: 'activo', renderer: formatBoolean, editor: new Ext.form.TextField({ allowBlank: false  }), width: 10 }
                ],
                viewConfig: {
                    forceFit: true
                },
                frame:true,
                bbar: new Ext.Toolbar({
                    items:[
                        {
                            text: "<s:text name='appbs.btn.add.record'/>",
                            icon: appBasicPath+'/images/add.png',
                            scope: this,
                            handler : function() {
                                gridAddRecord(this);
                            }
                        },
                        '-',
                        {
                            xtype: 'tbbutton',
                            icon: appBasicPath+'/images/delete.gif',
                            text: "<s:text name='appbs.btn.delete.record'/>",
                            scope: this,
                            handler: function() {
                                gridAddRecord(this, this.appbs_lookupsElementsToDelete, "");
                            }
                        },
                        '->',
                        {
                            xtype: 'tbbutton',
                            icon: appBasicPath+'/images/disk.png',
                            text: "<s:text name='appbs.btn.save.records'/>",
                            scope: this,
                            handler: function() {
                                mainDiv.mask("<s:text name='appbs.wait'/>");
                                this.stopEditing();

                                var data = [];
                                Ext.each(this.getStore().getModifiedRecords(), function(record) {
                                    data.push(record.data);
                                });

                                Ext.Ajax.request({
                                    url: appPath+'/Lookups.action?action=save',
                                    params: {dataToSave:Ext.encode(data), elementsToDelete:Ext.encode(this.appbs_lookupsElementsToDelete)},
                                    scope: this,
                                    success: function(response) {
                                        var params = getActionResponse(response);
                                        if (params.success) {
                                            this.appex_lookupsElementsToDelete = [];
                                            this.getStore().modified = [];
                                            mainDiv.unmask();
                                            this.updateData();
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
                    ]
                }),
                // Cuando se selecciona un elemento de la lista
                sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    scope: this,
                    listeners: {
                        scope: this,
                        rowselect: function(smObj, rowIndex, record) {
                            this.linkedGrid.store.setBaseParam("id_lookup", record.data.id_lookup);
                            this.linkedGrid.store.load();
                        }
                    }
                })
            };
            // apply config
            Ext.apply(this, Ext.apply(this.initialConfig, config));
            Ext.com.befasoft.common.components.Appbs_lookupsGrid.superclass.initComponent.call(this);
        },

        updateData: function () {
            this.appbs_lookupsData.load({ callback: dataSourceLoad });
        }

    });
</s:if>
<s:else>
    Ext.com.befasoft.common.components.Appbs_lookupsGrid = Ext.extend(Ext.grid.GridPanel, {

        linkedGrid: null,

        initComponent : function() {
            // DataStore
            this.appbs_lookupsData = new Ext.com.befasoft.common.components.Appbs_lookupsData();

            var config = {
                region: 'center',
                store: this.appbs_lookupsData,
                autoScroll:true,
                ddGroup: 'depGridDD',
                columns: [
                    { header:"<s:text name='appbs_lookups.id_lookup'/>", sortable: true, dataIndex: 'id_lookup', width: 30 },
                    { header:"<s:text name='appbs_lookups.nombre'/>", sortable: true, dataIndex: 'nombre', width: 50 },
                    { header:"<s:text name='appbs_lookups.activo'/>", sortable: true, dataIndex: 'activo', renderer: formatBoolean, width: 10  },
                    { header:"<s:text name='appbs_lookups.editable'/>", sortable: true, dataIndex: 'editable', renderer: formatBoolean, width: 10 }
                ],
                viewConfig: {
                    forceFit: true
                },
                frame:true,
                // Cuando se selecciona un elemento de la lista
                sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    scope: this,
                    listeners: {
                        scope: this,
                        rowselect: function(smObj, rowIndex, record) {
                            this.linkedGrid.store.setBaseParam("id_lookup", record.data.id_lookup);
                            this.linkedGrid.store.load();
                            if (record.data.editable == 'S')
                                this.linkedGrid.toolbar.show();
                            else
                                this.linkedGrid.toolbar.hide();
                        }
                    }
                }) // End sm
            };
            // apply config
            Ext.apply(this, Ext.apply(this.initialConfig, config));
            Ext.com.befasoft.common.components.Appbs_lookupsGrid.superclass.initComponent.call(this);
        },

        updateData: function () {
            this.appbs_lookupsData.load({ callback: dataSourceLoad });
        }

    });
</s:else>

Ext.com.befasoft.common.components.Appbs_lookups_valuesGrid = Ext.extend(Ext.grid.EditorGridPanel, {

    appex_lookups_valuesElementsToDelete: [],

    initComponent : function() {
        // DataStore
        this.appbs_lookups_valuesData = new Ext.com.befasoft.common.components.Appbs_lookups_valuesData();

        this.toolbar = new Ext.Toolbar({
            items:[
                {
                    text: "<s:text name='appbs.btn.add.record'/>",
                    icon: appBasicPath+'/images/add.png',
                    scope: this,
                    handler : function() {
                        gridAddRecord(this);
                    }
                },
                '-',
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        gridAddRecord(this, this.appex_lookups_valuesElementsToDelete, "");
                    }
                },
                '->',
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        mainDiv.mask("<s:text name='appbs.wait'/>");
                        this.stopEditing();

                        var data = [];
                        Ext.each(this.getStore().getModifiedRecords(), function(record) {
                            data.push(record.data);
                        });

                        Ext.Ajax.request({
                            url: appPath+'/Lookups_values.action?action=save',
                            params: {dataToSave:Ext.encode(data), elementsToDelete:Ext.encode(this.appex_lookups_valuesElementsToDelete)},
                            scope: this,
                            success: function(response) {
                                var params = getActionResponse(response);
                                if (params.success) {
                                    this.appex_lookups_valuesElementsToDelete = [];
                                    this.getStore().modified = [];
                                    mainDiv.unmask();
                                    this.updateData();
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
            ]
        });

        var config = {
            region: 'south',
            height:300,
            store: this.appbs_lookups_valuesData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_lookups_values.id_valor'/>", sortable: true, dataIndex: 'id_valor',  editor: new Ext.form.TextField({ allowBlank: false  }), width: 35 },
                { header:"<s:text name='appbs_lookups_values.nombre'/>", sortable: true, dataIndex: 'nombre',  editor: new Ext.form.TextField({ allowBlank: false  }), width: 60 },
                { header:"<s:text name='appbs_lookups_values.activo'/>", sortable: true, dataIndex: 'activo',  editor: new Ext.form.TextField({ allowBlank: false  }), width: 5, renderer: formatBoolean }
            ],
            viewConfig: {
                forceFit: true
            },
            frame:true,
            bbar: this.toolbar,
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_lookups_valuesGrid.superclass.initComponent.call(this);

    },

    updateData: function () {
        this.appbs_lookups_valuesData.load({ callback: dataSourceLoad });
    }

});



//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appex_lookups
//------------------------------------------------------------------------
function openTabAppbs_lookups(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(title))) {
        var gridValues = new Ext.com.befasoft.common.components.Appbs_lookups_valuesGrid();
        var grid = new Ext.com.befasoft.common.components.Appbs_lookupsGrid({ linkedGrid: gridValues });
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[grid, gridValues]
        });
        tabs.add(tab);
        grid.updateData();
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}

</script>
