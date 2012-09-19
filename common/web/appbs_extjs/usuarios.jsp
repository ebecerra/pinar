<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * Mantenimiento de APPBS_USUARIOS (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_usuariosGrid = Ext.extend(Ext.grid.GridPanel, {

    pageSize: 25,
    linkedPerfiles: null,
    linkedForm: null,

    initComponent : function() {

        this.usuariosData = new Ext.com.befasoft.common.components.Appbs_usuariosData();
        this.loginFilterText = new Ext.form.TextField({ name:'login', width: 80, value:'' });
        this.nameFilterText = new Ext.form.TextField({ name:'nameFilter', width: 80, value:'' });
        this.pageToolbar = new Ext.PagingToolbar({
            pageSize: this.pageSize,
            store: this.usuariosData,
            displayInfo: true,
            displayMsg: "<s:text name='appbs_usuarios.pg_info'/>",
            emptyMsg: "<s:text name='appbs_usuarios.pg_none'/>"
        });

        var config = {
            region: 'center',
            store: this.usuariosData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_usuarios.id_usuario'/>", sortable: true, dataIndex: 'id_usuario', width: 7 },
                { header:"<s:text name='appbs_usuarios.login'/>", sortable: true, dataIndex: 'login', width: 10 },
                { header:"<s:text name='appbs_usuarios.nombre'/>", sortable: true, dataIndex: 'nombre', width: 25 },
                { header:"<s:text name='appbs_usuarios.email'/>", sortable: true, dataIndex: 'email', width: 25, hidden: true },
                { header:"<s:text name='appbs_usuarios.telefono'/>", sortable: true, dataIndex: 'telefono', width: 20, hidden: true },
                { header:"<s:text name='appbs_usuarios.tipo'/>", sortable: true, dataIndex: 'tipo_nombre', width: 10 },
                { header:"<s:text name='appbs_usuarios.fecha_creacion'/>", sortable: true, dataIndex: 'fecha_creacion', width: 5, renderer: formatDate },
                { header:"<s:text name='appbs_usuarios.fecha_conexion'/>", sortable: true, dataIndex: 'fecha_conexion', width: 10, renderer: formatDateTime },
                { header:"<s:text name='appbs_usuarios.activo'/>", sortable: true, dataIndex: 'activo', width: 5 }
            ],
            tbar: new Ext.Toolbar({
                frame:true,
                height:30,
                region: 'north',
                items: [
                    '<s:text name="appbs_usuarios.login"/>: ', this.loginFilterText,
                    '    ',
                    '<s:text name="appbs_usuarios.nombre"/>: ', this.nameFilterText,
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/lupa.gif',
                        text: "<s:text name='appbs.form.filter'/>",
                        scope: this,
                        handler: function() {
                            this.usuariosData.setBaseParam('login', this.loginFilterText.getValue());
                            this.usuariosData.setBaseParam('nombre', this.nameFilterText.getValue());
                            this.updateData();
                        }
                    }
                ]
            }),
            bbar: this.pageToolbar,
            viewConfig: { forceFit: true },
            frame:true,

            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        this.linkedForm.getForm().loadRecord(record);
                        <s:if test="#session.user.perfil_tipo == 'MANAGER'">
                            this.linkedPerfiles.updateData(record.data.id_usuario);
                        </s:if>
                    }
                }
            }) // End sm
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosGrid.superclass.initComponent.call(this);
    },

    updateData: function() {
        this.usuariosData.load({ params:{start: 0, limit: this.pageSize }, callback: dataSourceLoad });
    },

    refreshPage: function () {
        this.pageToolbar.doRefresh();
    }
});

Ext.com.befasoft.common.components.Appbs_usuariosForm = Ext.extend(Ext.FormPanel, {

    linkedGrid: null,
    perfilesPanel: null,

    initComponent : function() {

        this.cbTipos = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_Tipos_usuariosData(),
            displayField:'nombre', valueField: 'id_tipo', hiddenName: 'tipo',
            typeAhead: true, mode: 'local', triggerAction: 'all', selectOnFocus:true,
            fieldLabel:"<s:text name='appbs_usuarios.tipo'/>",
            emptyText:"<s:text name='appbs.form.combo.select'/>"
        });
        this.cbLoglevel = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_LoglevelData(), width: 200,
            displayField: 'description', valueField: 'code', hiddenName: 'log_level', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection: true, selectOnFocus: true,
            fieldLabel:"<s:text name='appbs_usuarios.log_level'/>",
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });

        var config = {
            region: 'south',
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            height:270,
            items:[
                { name: 'id_usuario', allowBlank:false , xtype : 'hidden' },
                { name: 'contrasenya', allowBlank:false , xtype : 'hidden' },
                { name: 'cookie', allowBlank:false , xtype : 'hidden' },
                {
                    xtype: 'panel', layout: 'hbox',
                    items: [
                        {
                            xtype: 'panel', layout: 'form', labelAlign: 'right',
                            items: [
                                { fieldLabel:"<s:text name='appbs_usuarios.nombre'/>", name: 'nombre', allowBlank:true, xtype:'textfield', width: 200 },
                                { fieldLabel:"<s:text name='appbs_usuarios.email'/>", name: 'email', allowBlank:true, xtype:'textfield', vtype: 'email', width: 200 },
                                this.cbLoglevel,
                                { fieldLabel:"<s:text name='appbs_usuarios.param_1'/>", name: 'param_1', allowBlank:true, xtype:'textfield', width: 200 },
                                { fieldLabel:"<s:text name='appbs_usuarios.param_2'/>", name: 'param_2', allowBlank:true, xtype:'textfield', width: 200 },
                                { fieldLabel:"<s:text name='appbs_usuarios.param_3'/>", name: 'param_3', allowBlank:true, xtype:'textfield', width: 200 },
                                { fieldLabel:"<s:text name='appbs_usuarios.param_4'/>", name: 'param_4', allowBlank:true, xtype:'textfield', width: 200 }
                            ]
                        }, {
                            xtype: 'panel', layout: 'form', labelAlign: 'right',
                            items: [
                                { fieldLabel:"<s:text name='appbs_usuarios.login'/>", name: 'login', allowBlank:true, xtype:'textfield', width: 120 },
                                { fieldLabel:"<s:text name='appbs_usuarios.clave'/>", name: 'clave', allowBlank:true, xtype:'textfield', width: 120 },
                                { fieldLabel:"<s:text name='appbs_usuarios.activo'/>", name: 'activo', allowBlank:false, xtype:'textfield', width: 30 },
                                { fieldLabel:"<s:text name='appbs_usuarios.int_param_1'/>", name: 'int_param_1', allowBlank:true, xtype:'numberfield', width: 70 },
                                { fieldLabel:"<s:text name='appbs_usuarios.int_param_2'/>", name: 'int_param_2', allowBlank:true, xtype:'numberfield', width: 70 },
                                { fieldLabel:"<s:text name='appbs_usuarios.int_param_3'/>", name: 'int_param_3', allowBlank:true, xtype:'numberfield', width: 70 },
                                { fieldLabel:"<s:text name='appbs_usuarios.int_param_4'/>", name: 'int_param_4', allowBlank:true, xtype:'numberfield', width: 70 }

                            ]
                        }, {
                            xtype: 'panel', layout: 'form', labelAlign: 'right',
                            items: [
                                { fieldLabel:"<s:text name='appbs_usuarios.telefono'/>", name: 'telefono', allowBlank:true, xtype:'textfield', width: 120 },
                                { fieldLabel:"<s:text name='appbs_usuarios.movil'/>", name: 'movil', allowBlank:true, xtype:'textfield', width: 120 },
                                { fieldLabel:"<s:text name='appbs_usuarios.fax'/>", name: 'fax', allowBlank:true, xtype:'textfield', width: 120 },
                                { fieldLabel:"<s:text name='appbs_usuarios.fecha_creacion'/>", name: 'fecha_creacion', readOnly: true, xtype:'datefield', width: 80 },
                                { fieldLabel:"<s:text name='appbs_usuarios.fecha_conexion'/>", name: 'fecha_conexion', readOnly:true, xtype:'datefield', width: 80 }
                            ]
                        }
                    ]
                }
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        mainDiv.mask("<s:text name='appbs.wait'/>");
                        this.form.findField("contrasenya").setValue(this.form.findField("clave").getValue());
                        if (this.form.findField("contrasenya").getValue() == "")
                            this.form.findField("contrasenya").setValue("*");
                        var data = [];
                        data.push(this.form.getFieldValues());
                        Ext.Ajax.request({
                            url: appPath+'/Usuarios.action?action=save',
                            params: { dataToSave: Ext.encode(data) },
                            scope: this,
                            success: function() {
                                mainDiv.unmask();
                                this.linkedGrid.refreshPage();
                            },
                            failure: function() {
                                mainDiv.unmask();
                                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                            }
                        });
                    }
                }
                <s:if test="#session.user.perfil_tipo == 'MANAGER'">
                , {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appbs_usuarios.msg.delete"/>",
                            function (btn) {
                                if (btn == "yes") {
                                    var data = [];
                                    data.push(component.form.getFieldValues());

                                    Ext.Ajax.request({
                                        url: appPath+'/Usuarios.action?action=save',
                                        params: {elementsToDelete: Ext.encode(data)},
                                        success: function() {
                                            mainDiv.unmask();
                                            component.getForm().reset();
                                            component.linkedGrid.refreshPage();
                                            component.perfilesPanel.updateData(null);
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
                }, {
                    text: "<s:text name='appbs.btn.reset.record'/>",
                    scope: this,
                    handler : function() {
                        this.getForm().reset();
                        this.getForm().findField("fecha_creacion").setValue(new Date());
                        this.perfilesPanel.updateData(null);
                    }
                }
                </s:if>
            ]
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosForm.superclass.initComponent.call(this);
    },

    initLoad: function () {
        this.cbTipos.store.load();
    }

});


<s:if test="#session.user.perfil_tipo == 'MANAGER'">

Ext.com.befasoft.common.components.Appbs_UsuariosAppAvailData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_UsuariosAppAvailData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            url: appPath+'/Usuarios.action?action=app_avail',
            root:"elements",
            totalProperty: 'totalCount',
            fields: ["id_aplicacion", "nombre" ],
            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_UsuariosAppSelectData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_UsuariosAppSelectData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            url: appPath+'/Usuarios.action?action=app_select',
            root:"elements",
            totalProperty: 'totalCount',
            fields: ["id_aplicacion", "nombre", "id_perfil", "nombre_perfil" ],
            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_usuariosPerfilesPanel = Ext.extend(Ext.Panel, {

    parentPanel: null,
    appAvailGrid: null,
    appSelectGrid: null,

    initComponent : function() {
        this.cbPerfiles = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_perfilesData(),
            displayField:'nombre', valueField : 'id_perfil', hiddenName : 'id_perfil',
            typeAhead: true, mode: 'local', triggerAction: 'all', selectOnFocus:true,
            fieldLabel:"<s:text name='appbs_usuarios.perfil'/>",
            emptyText:"<s:text name='appbs.form.combo.select'/>"
        });

        var config = {
            labelAlign: 'top',
            frame:false,
            bodyStyle:'padding:15px 15px 0',
            layout:'form',
            width: "100%",
            items: [
                this.cbPerfiles,
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/add.png',
                    text: "<s:text name='appbs_usuarios.btn.add_app_perfil'/>",
                    scope: this,
                    handler: function() {
                        var currentRecord = this.appAvailGrid.getSelectionModel().getSelected();
                        var selectedPerfil = this.cbPerfiles.value;
                        if (this.parentPanel.id_usuario && currentRecord && selectedPerfil) {
                            Ext.Ajax.request({
                                url: appPath+'/Usuarios.action?action=add_app_per',
                                params: {id_aplicacion: currentRecord.get("id_aplicacion"), id_usuario: this.parentPanel.id_usuario, id_perfil: selectedPerfil},
                                scope: this,
                                success: function() {
                                    this.appAvailGrid.store.load({params: {id_usuario: this.parentPanel.id_usuario} });
                                    this.appSelectGrid.store.load({params: {id_usuario: this.parentPanel.id_usuario} });
                                },
                                failure: function() {
                                    Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                }
                            });
                        } else {
                            Ext.example.msg("<s:text name="appbs_usuarios.msg.caption"/>", "<s:text name="appbs_usuarios.msg.avail_app_perf"/>");
                        }
                    }// end handler
                },
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/delete.gif',
                    text: "<s:text name='appbs_usuarios.btn.del_app_perfil'/>",
                    scope: this,
                    handler: function() {
                        var currentRecord = this.appSelectGrid.getSelectionModel().getSelected();
                        if (this.parentPanel.id_usuario && currentRecord) {
                            Ext.Ajax.request({
                                url: appPath+'/Usuarios.action?action=del_app_per',
                                params: {id_aplicacion: currentRecord.get("id_aplicacion"), id_usuario: this.parentPanel.id_usuario, id_perfil: currentRecord.get("id_perfil")},
                                scope: this,
                                success: function() {
                                    this.appAvailGrid.store.load({params: {id_usuario: this.parentPanel.id_usuario} });
                                    this.appSelectGrid.store.load({params: {id_usuario: this.parentPanel.id_usuario} });
                                },
                                failure: function() {
                                    Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                }
                            });
                        } else {
                            Ext.example.msg("<s:text name="appbs_usuarios.msg.caption"/>", "<s:text name="appbs_usuarios.msg.sel_app_perf"/>");
                        }
                    }// end handler
                }
            ]
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosPerfilesPanel.superclass.initComponent.call(this);
    },

    initLoad: function () {
        this.cbPerfiles.store.load();
    }
});

Ext.com.befasoft.common.components.Appbs_usuariosPerfilesForm = Ext.extend(Ext.FormPanel, {

    id_usuario: null,

    initComponent : function() {

        this.gridAppAvail = new Ext.grid.GridPanel({
            height: 200,
            store: new Ext.com.befasoft.common.components.Appbs_UsuariosAppAvailData(),
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_usuarios.app.available'/>", sortable: false, dataIndex: 'nombre'}
            ],
            viewConfig: { forceFit: true },
            frame:true
        });

        this.gridAppSelect = new Ext.grid.GridPanel({
            height: 200,
            store: new Ext.com.befasoft.common.components.Appbs_UsuariosAppSelectData(),
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_usuarios.aplicacion'/>", sortable: false, dataIndex: 'nombre'},
                { header:"<s:text name='appbs_usuarios.perfil'/>", sortable: false, dataIndex: 'nombre_perfil'}
            ],
            viewConfig: { forceFit: true },
            frame:true
        });
        this.panelPerfiles = new Ext.com.befasoft.common.components.Appbs_usuariosPerfilesPanel({
            parentPanel: this,
            appAvailGrid: this.gridAppAvail,
            appSelectGrid: this.gridAppSelect
        });

        var config = {
            labelAlign: 'top',
            frame:true,
            bodyStyle:'padding:5px 5px 0 0',
            layout:'form',
            width: "100%",
            items: [{
                layout:'column',
                items:[
                    { columnWidth:.33, layout: 'form', items: [ this.gridAppAvail ] },
                    { columnWidth:.33, layout: 'form', items: [ this.panelPerfiles ] },
                    { columnWidth:.33, layout: 'form', items: [ this.gridAppSelect ] }
                ]
            }]
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosPerfilesForm.superclass.initComponent.call(this);
    },

    initLoad: function () {
        this.panelPerfiles.cbPerfiles.store.load();
    },

    updateData: function (id_usuario) {
        this.id_usuario = id_usuario;
        if (id_usuario == null)
            id_usuario = 0;
        this.gridAppAvail.store.load({ params: {id_usuario: id_usuario} });
        this.gridAppSelect.store.load({ params: {id_usuario: id_usuario} });
    }
});
</s:if>

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de usuarios
//------------------------------------------------------------------------
function openTabUsuarios(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var gridUsuarios = new Ext.com.befasoft.common.components.Appbs_usuariosGrid();
        gridUsuarios.updateData();
        var formUsuarios = new Ext.com.befasoft.common.components.Appbs_usuariosForm({ linkedGrid: gridUsuarios});
        formUsuarios.initLoad();
        gridUsuarios.linkedForm = formUsuarios;
        <s:if test="#session.user.perfil_tipo == 'MANAGER'">
            var formUsuariosPerfiles = new Ext.com.befasoft.common.components.Appbs_usuariosPerfilesForm();
            formUsuariosPerfiles.initLoad();
            gridUsuarios.linkedPerfiles = formUsuariosPerfiles;
            formUsuarios.perfilesPanel = formUsuariosPerfiles;
        </s:if>
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable: true,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[
                gridUsuarios,
                <s:if test="#session.user.perfil_tipo == 'MANAGER'">
                    {
                        region: 'south', height:270, xtype: 'tabpanel', activeTab: 0,
                        items:[
                            {
                                title: "<s:text name='appbs_usuarios.tab.main'/>",
                                layout: 'fit',
                                items:[ formUsuarios ]
                            }, {
                                title: "<s:text name='appbs_usuarios.tab.perfiles'/>",
                                layout: 'fit',
                                items:[ formUsuariosPerfiles ]
                            }
                        ]
                    }
                </s:if>
                <s:else>
                    formUsuarios
                </s:else>
            ]
        });
        tabs.add(tab);
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}
</script>
