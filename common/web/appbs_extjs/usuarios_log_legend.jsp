<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPBS_USUARIOS_LOG_LEGEND" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_usuarios_log_legendData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_usuarios_log_legendData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Usuarios_log_legend.action',
            fields: [
                "nombre", "id_tipo", "texto"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_usuarios_log_legendGrid = Ext.extend(Ext.grid.EditorGridPanel, {

    appbs_usuarios_log_legendElementsToDelete: [],

    initComponent : function() {
        // DataStore
        this.appbs_usuarios_log_legendData = new Ext.com.befasoft.common.components.Appbs_usuarios_log_legendData();

        var config = {
            region: 'center',
            store: this.appbs_usuarios_log_legendData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_usuarios_log_legend.id_tipo'/>", sortable: true, dataIndex: 'id_tipo', width: 10, editor: new Ext.form.TextField({ allowBlank: false  }) },
                { header:"<s:text name='appbs_usuarios_log_legend.nombre'/>", sortable: true, dataIndex: 'nombre', width: 30, editor: new Ext.form.TextField({ allowBlank: false })},
                { header:"<s:text name='appbs_usuarios_log_legend.texto'/>", sortable: true, dataIndex: 'texto', width: 60, editor: new Ext.form.TextField({ allowBlank: false  }) }
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
                            gridAddRecord(this, this.appbs_usuarios_log_legendElementsToDelete, "");
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
                                url: appPath+'/Usuarios_log_legend.action?action=save',
                                params: {dataToSave:Ext.encode(data), elementsToDelete:Ext.encode(this.appbs_usuarios_log_legendElementsToDelete)},
                                scope: this,
                                success: function(response) {
                                    var params = getActionResponse(response);
                                    if (params.success) {
                                        this.appbs_usuarios_log_legendElementsToDelete = [];
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
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuarios_log_legendGrid.superclass.initComponent.call(this);

    },

    updateData: function () {
        this.appbs_usuarios_log_legendData.load();
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appbs_usuarios_log_legend
//------------------------------------------------------------------------
function openTabAppbs_usuarios_log_legend(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(title))) {
        var grid = new Ext.com.befasoft.common.components.Appbs_usuarios_log_legendGrid();
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
