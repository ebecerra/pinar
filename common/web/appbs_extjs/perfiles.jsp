<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * Mantenimiento de APPBS_PERFILES (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_perfilesGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    treePerfiles: null,

    initComponent : function() {

        var config = {
            store: new Ext.com.befasoft.common.components.Appbs_perfilesData(),
            autoScroll:true,
            ddGroup: 'depGridDD',
            region: 'north',
            height: 150,
            columns: [
                { header:"<s:text name='appbs_perfiles.id_perfil'/>", sortable: true, dataIndex: 'id_perfil', width: 10 },
                { header:"<s:text name='appbs_perfiles.nombre'/>", sortable: true, dataIndex: 'nombre', width: 50 },
                { header:"<s:text name='appbs_perfiles.tipo'/>", sortable: true, dataIndex: 'tipo', width: 40 }
            ],
            viewConfig: {
                forceFit: true
            },
            frame:true,

            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        this.selectRecord(record);
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_perfilesGrid.superclass.initComponent.call(this);
    },

    selectRecord: function (record) {
        this.treePerfiles.enable();
        this.treePerfiles.id_perfil= record.data.id_perfil;
        this.treePerfiles.getLoader().url = appPath + '/Perfiles.action?action=allMenusTree&id_perfil='+ record.data.id_perfil;
        this.treePerfiles.getLoader().load(this.treePerfiles.root);
        this.treePerfiles.getRootNode().expand(true);
        this.treePerfiles.expandAll();

        this.linkedForm.getForm().loadRecord(record);
    },

    updateData: function () {
        this.store.load({
            scope: this,
            callback: function (records) {
                if (records.length > 0)
                    this.getSelectionModel().selectFirstRow();
            }
        });
    }
});

Ext.com.befasoft.common.components.Appbs_perfilesForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,

    initComponent : function() {

        var config = {
            region: 'south',
            height: 130,
            frame: true,
            defaultType:'textfield',
            monitorValid:true,
            layout: 'form',
            items: [
                { fieldLabel:"<s:text name='appbs_perfiles.id_perfil'/>", xtype : 'numberfield', name: 'id_perfil', allowBlank:false, width:50 },
                { fieldLabel:"<s:text name='appbs_perfiles.nombre'/>", xtype : 'textfield', name: 'nombre', allowBlank:false, width:250 },
                { fieldLabel:"<s:text name='appbs_perfiles.tipo'/>", xtype : 'textfield', name: 'tipo', allowBlank:false, width:150 }
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        mainDiv.mask("<s:text name='appbs.wait'/>");

                        var menus= [];
                        var selNodes = this.treePerfiles.getChecked();
                        Ext.each(selNodes, function(node){
                            var item = {};
                            item["id_menu"] = node.id;
                            menus.push( item);
                        });
                        var data = [];
                        data.push(this.form.getFieldValues());

                        Ext.Ajax.request({
                            url: appPath+'/Perfiles.action?action=save',
                            params: { id_perfil: this.treePerfiles.id_perfil, menus: Ext.encode(menus), dataToSave: Ext.encode(data) },
                            scope: this,
                            success: function() {
                                mainDiv.unmask();
                                this.linkedGrid.updateData();
                            },
                            failure: function() {
                                mainDiv.unmask();
                                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                            }
                        });
                    }
                }, {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        mainDiv.mask("<s:text name='appbs.wait'/>");
                        var data = [];
                        data.push(this.form.getFieldValues());

                        Ext.Ajax.request({
                            url: appPath+'/Perfiles.action?action=save',
                            params: { elementsToDelete: Ext.encode(data)},
                            scope: this,
                            success: function() {
                                mainDiv.unmask();
                                this.linkedGrid.updateData();
                            },
                            failure: function() {
                                mainDiv.unmask();
                                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                            }
                        });
                    }
                }, {
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
        Ext.com.befasoft.common.components.Appbs_perfilesForm.superclass.initComponent.call(this);
    }
});

    //------------------------------------------------------------------------
    // Abre un Tab con el mantenimiento de perfiles
    //------------------------------------------------------------------------
    function openTabPerfiles(title, idMenu) {
        var tabs = Ext.getCmp("tabs");
        var tab;
        if (!(tab = tabs.getItem(title))) {
            var treePerfiles = new Ext.tree.TreePanel({
                region:'center',
                split: true,
                border: true,
                autoScroll:true,
                loader: new Ext.tree.TreeLoader({
                    url: appPath + '/Perfiles.action?action=allMenusTree',
                    listeners: {
                        'load': function() {
                            treePerfiles.expandAll();
                        }
                    }
                }),
                //loader: new Ext.tree.TreeLoader(),
                rootVisible:false,
                root: new Ext.tree.AsyncTreeNode({
                    text: 'Perfil Menus',
                    expanded:true,
                    id:'root'
                }),
                listeners: {
                    'click': function(node, e){
                    }
                }
            });
            var grid = new Ext.com.befasoft.common.components.Appbs_perfilesGrid({ treePerfiles: treePerfiles });
            var form = new Ext.com.befasoft.common.components.Appbs_perfilesForm({ linkedGrid: grid, treePerfiles: treePerfiles });
            grid.linkedForm = form;
            tab = new Ext.Panel({
                id: idMenu,
                title: title,
                closable: true,
                autoScroll: true,
                border: false,
                plain: true,
                layout: 'border',
                items:[ grid, treePerfiles, form ]
            });
            tabs.add(tab);
            grid.updateData();
        }
        tabs.setActiveTab(tab);
        tabs.doLayout();
    }

</script>
