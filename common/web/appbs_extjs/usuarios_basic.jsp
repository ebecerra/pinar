<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * Mantenimiento de APPBS_USUARIOS [Basic Version] (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_usuariosBasicGrid = Ext.extend(Ext.grid.GridPanel, {

    int_param_1: null, int_param_2: null, int_param_3: null, int_param_4: null,
    param_1: null, param_2: null, param_3: null, param_4: null,
    tipo: null, perfil_tipo: null, linkedForm: null,
    pageBar: false, filterBar: false, exportBtn: false, pageSize: 25,

    initComponent : function() {

        this.usuariosData = new Ext.com.befasoft.common.components.Appbs_usuariosData();

        this.newButton = new Ext.Button({
            text: "<s:text name='appbs.btn.add.record'/>",
            icon: appBasicPath+'/images/add.png',
            scope: this,
            handler: function() {
                var win = new Ext.com.befasoft.common.components.Appbs_usuariosBasicWin({ linkedGrid: this });
                var data = {};
                data["int_param_1"] = this.int_param_1;
                data["int_param_2"] = this.int_param_2;
                data["int_param_3"] = this.int_param_3;
                data["int_param_4"] = this.int_param_4;
                data["param_1"] = this.param_1;
                data["param_3"] = this.param_2;
                data["param_2"] = this.param_3;
                data["param_4"] = this.param_4;
                data["tipo"] = this.tipo;
                data["clave"] = null;
                data["activo"] = null;
                data["telefono"] = null;
                data["fax"] = null;
                data["nombre"] = null;
                data["movil"] = null;
                data["fecha_creacion"] = null;
                data["log_level"] = null;
                data["id_usuario"] = null;
                data["email"] = null;
                data["cookie"] = null;
                data["fecha_conexion"] = null;
                data["login"] = null;
                data["activo"] = "S";

                var currentRecord = new Ext.data.Record(data);
                win.loadRecord(this.perfil_tipo, this.tipo, currentRecord);
                win.show();
            }
        });

        if (this.filterBar) {
            this.loginFilterText = new Ext.form.TextField({ name:'loginFilter', width: 80, value:'' });
            this.nameFilterText = new Ext.form.TextField({ name:'nameFilter', width: 80, value:'' });
            this.emailFilterText = new Ext.form.TextField({ name:'emailFilter', width: 80, value:'' });
            this.cbTiposFilter = new Ext.form.ComboBox({
                store: new Ext.com.befasoft.common.components.Appbs_Tipos_usuariosData({ autoLoad: true }),
                displayField:'nombre', valueField : 'id_tipo', hiddenName : 'tipo', width: 150,
                typeAhead: true, mode: 'local', triggerAction: 'all', selectOnFocus:true,
                fieldLabel:"<s:text name='appbs_usuarios.tipo'/>",
                emptyText:"<s:text name='appbs.form.combo.select'/>"
            });
            this.filterToolBar = new Ext.Toolbar({
                frame:true,
                height:30,
                region: 'north',
                items: [
                    '<s:text name="appbs_usuarios.login"/>:&nbsp;', this.loginFilterText,
                    '&nbsp;&nbsp;&nbsp;',
                    '<s:text name="appbs_usuarios.nombre"/>:&nbsp;', this.nameFilterText,
                    '&nbsp;&nbsp;&nbsp;',
                    '<s:text name="appbs_usuarios.email"/>:&nbsp;', this.emailFilterText,
                    '&nbsp;&nbsp;&nbsp;',
                    '<s:text name="appbs_usuarios.tipo"/>:&nbsp;', this.cbTiposFilter,
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/lupa.gif',
                        text: "<s:text name='appbs.form.filter'/>",
                        scope: this,
                        handler: this.doFilter
                    }, {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/excel.png',
                        text: "<s:text name='appbs.form.export'/>",
                        scope: this,
                        hidden: !this.exportBtn,
                        handler: function() {
                            this.doFilter();
                            var sortInfo = this.usuariosData.getSortState();
                            this.usuariosData.setBaseParam('login', this.loginFilterText.getValue());
                            this.usuariosData.setBaseParam('nombre', this.nameFilterText.getValue());
                            this.usuariosData.setBaseParam('email', this.emailFilterText.getValue());
                            this.usuariosData.setBaseParam('tipo', this.cbTiposFilter.getValue());
                            var params =
                                '&login='+this.loginFilterText.getValue()+'&nombre='+this.nameFilterText.getValue()+
                                '&email='+this.emailFilterText.getValue()+'&tipo='+this.cbTiposFilter.getValue();
                            if (sortInfo)
                                params += '&sort='+sortInfo.field+'&dir='+sortInfo.direction;
                            window.open(appPath + '/Usuarios.action?action=export'+params, "previewWindow", "width=750,height=500,scrollbars=YES");
                        }
                    }
                ]
            });
        } else {
            this.filterToolBar = null;
        }

        if (this.pageBar) {
            this.pagToolbar = new Ext.PagingToolbar({
                pageSize: this.pageSize,
                store: this.usuariosData,
                displayInfo: true,
                displayMsg: "<s:text name='appbs_usuarios.pg_info'/>",
                emptyMsg: "<s:text name='appbs_usuarios.pg_none'/>",
                items:[ this.newButton ]
            });

        } else {
            this.pagToolbar = new Ext.Toolbar({
                items:[ this.newButton ]
            });
        }
        var config = {
            region: 'center',
            store: this.usuariosData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_usuarios.id_usuario'/>", sortable: true, dataIndex: 'id_usuario', width: 7 },
                { header:"<s:text name='appbs_usuarios.login'/>", sortable: true, dataIndex: 'login', width: 10 },
                { header:"<s:text name='appbs_usuarios.nombre'/>", sortable: true, dataIndex: 'nombre', width: 25 },
                { header:"<s:text name='appbs_usuarios.email'/>", sortable: true, dataIndex: 'email', width: 25 },
                { header:"<s:text name='appbs_usuarios.telefono'/>", sortable: true, dataIndex: 'telefono', width: 20 },
                { header:"<s:text name='appbs_usuarios.tipo'/>", sortable: true, dataIndex: 'tipo_nombre', width: 13 }
            ],
            viewConfig: { forceFit: true },
            frame:true,
            tbar: this.filterToolBar,
            bbar: this.pagToolbar,
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),
            listeners: {
                scope: this,
                'rowdblclick': function(grid, rowIndex, e) {
                    var record = grid.getStore().getAt(rowIndex);
                    var win = new Ext.com.befasoft.common.components.Appbs_usuariosBasicWin({ linkedGrid: this });
                    win.loadRecord(this.perfil_tipo, this.tipo, record);
                    win.show();
                }
            }
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosBasicGrid.superclass.initComponent.call(this);
    },

    doFilter: function () {
        this.usuariosData.setBaseParam('login', this.loginFilterText.getValue());
        this.usuariosData.setBaseParam('nombre', this.nameFilterText.getValue());
        this.usuariosData.setBaseParam('email', this.emailFilterText.getValue());
        this.usuariosData.setBaseParam('tipo', this.cbTiposFilter.getValue());
        this.updateData();
    },

    updateData: function (perfil_tipo, tipo, int_param_1, int_param_2, int_param_3, int_param_4, param_1, param_2, param_3, param_4) {
        if (perfil_tipo) {
            this.perfil_tipo = perfil_tipo;
            this.tipo = tipo;
            this.int_param_1 = int_param_1;
            this.int_param_2 = int_param_2;
            this.int_param_3 = int_param_3;
            this.int_param_4 = int_param_4;
            this.param_1 = param_1;
            this.param_2 = param_2;
            this.param_3 = param_3;
            this.param_4 = param_4;
            this.usuariosData.setBaseParam('tipo', this.tipo);
            this.usuariosData.setBaseParam('int_param_1', this.int_param_1);
            this.usuariosData.setBaseParam('int_param_2', this.int_param_2);
            this.usuariosData.setBaseParam('int_param_3', this.int_param_3);
            this.usuariosData.setBaseParam('int_param_4', this.int_param_4);
            this.usuariosData.setBaseParam('param_1', this.param_1);
            this.usuariosData.setBaseParam('param_2', this.param_2);
            this.usuariosData.setBaseParam('param_3', this.param_3);
            this.usuariosData.setBaseParam('param_4', this.param_4);
        }
        if (this.pageBar)
            this.usuariosData.load({ params: { start: 0, limit: this.pageSize } });
        else
            this.usuariosData.load();
    }

});

Ext.com.befasoft.common.components.Appbs_usuariosBasicForm = Ext.extend(Ext.FormPanel, {

    parentWin: null,

    initComponent : function() {
        this.cbTipos = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_Tipos_usuariosData(),
            displayField:'nombre', valueField : 'id_tipo', hiddenName : 'tipo', width: 150,
            typeAhead: true, mode: 'local', triggerAction: 'all', selectOnFocus:true,
            fieldLabel:"<s:text name='appbs_usuarios.tipo'/>",
            emptyText:"<s:text name='appbs.form.combo.select'/>"
        });
        this.cbPerfiles = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_perfilesData(),
            displayField:'nombre', valueField : 'id_perfil', hiddenName : 'id_perfil', width: 150,
            typeAhead: true, mode: 'local', triggerAction: 'all', selectOnFocus:true,
            fieldLabel:"<s:text name='appbs_usuarios.perfil'/>",
            emptyText:"<s:text name='appbs.form.combo.select'/>"
        });

        var config = {
            layout:'form',
            frame:true,
            defaultType:'textfield',
            items: [
                { name: 'id_usuario', xtype : 'hidden' },
                { name: 'clave', xtype : 'hidden' },
                { name: 'login_old', xtype : 'hidden' },
                { name: 'cookie', xtype : 'hidden' },
                { name: 'int_param_1', xtype : 'hidden' },
                { name: 'int_param_2', xtype : 'hidden' },
                { name: 'int_param_3', xtype : 'hidden' },
                { name: 'int_param_4', xtype : 'hidden' },
                { name: 'param_1', xtype : 'hidden' },
                { name: 'param_2', xtype : 'hidden' },
                { name: 'param_3', xtype : 'hidden' },
                { name: 'param_4', xtype : 'hidden' },
                { fieldLabel:"<s:text name='appbs_usuarios.nombre'/>", name: 'nombre', allowBlank: false, width: 300 },
                { fieldLabel:"<s:text name='appbs_usuarios.email'/>", name: 'email', allowBlank: true, width: 300 },
                { fieldLabel:"<s:text name='appbs_usuarios.login'/>", name: 'login', allowBlank: false, width: 150 },
                { fieldLabel:"<s:text name='appbs_usuarios.clave'/>", name: 'contrasenya', allowBlank: true, width: 150, inputType: 'password' },
                { fieldLabel:"<s:text name='appbs_usuarios.telefono'/>", name: 'telefono', allowBlank: true, width: 150 },
                { fieldLabel:"<s:text name='appbs_usuarios.movil'/>", name: 'movil', allowBlank: true, width: 150 },
                { fieldLabel:"<s:text name='appbs_usuarios.fax'/>", name: 'fax', allowBlank: true, width: 150 },
                { fieldLabel:"<s:text name='appbs_usuarios.activo'/>", name: 'activo', allowBlank:false, width: 20  },
                this.cbTipos,
                this.cbPerfiles
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        if (this.getForm().isValid()) {
                            mainDiv.mask("<s:text name='appbs.wait'/>");
                            if (this.form.findField("contrasenya").getValue() == "")
                                this.form.findField("contrasenya").setValue("*");
                            else
                                this.form.findField("clave").setValue(this.form.findField("contrasenya").getValue());
                            var data = [];
                            data.push(this.form.getFieldValues());

                            Ext.Ajax.request({
                                url: appPath+'/Usuarios.action?action=save',
                                params: { dataToSave:Ext.encode(data) },
                                scope: this,
                                success: function(response) {
                                    mainDiv.unmask();
                                    var params = getActionResponse(response);
                                    if (params.success) {
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
                }, {
                    xtype: 'tbbutton',
                    icon: appBasicPath+'/images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appbs_usuarios.msg.delete"/>",
                            function (btn) {
                                if (btn == "yes") {
                                    mainDiv.mask("<s:text name='appbs.wait'/>");
                                    var data = [];
                                    data.push(component.form.getFieldValues());
                                    Ext.Ajax.request({
                                        url: appPath+'/Usuarios.action?action=save',
                                        params: { elementsToDelete: Ext.encode(data) },
                                        success: function(response) {
                                            mainDiv.unmask();
                                            var params = getActionResponse(response);
                                            if (params.success) {
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
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosBasicForm.superclass.initComponent.call(this);
    },

    loadRecord: function (perfil_tipo, tipo, record) {
        this.cbPerfiles.store.load({
            params: { tipo: perfil_tipo },
            scope: this,
            callback: function (r) {
                if (r.length > 0 && record.data.id_perfil == null)
                    record.data.id_perfil = r[0].data.id_perfil;
                if (tipo != null) {
                    record.data.login_old = record.data.login;
                    this.getForm().loadRecord(record);
                    this.cbTipos.hide();
                } else {
                    this.cbTipos.show();
                    this.cbTipos.store.load({
                        params: { tipo: perfil_tipo },
                        scope: this,
                        callback: function (r) {
                            if (r.length > 0 && record.data.tipo == null)
                                record.data.tipo = r[0].data.id_tipo;
                            this.getForm().loadRecord(record);
                        }
                    });
                }
            }
        });
    }
});

Ext.com.befasoft.common.components.Appbs_usuariosBasicWin = Ext.extend(Ext.Window, {

    linkedGrid: null,

    initComponent : function() {
        this.editForm = new Ext.com.befasoft.common.components.Appbs_usuariosBasicForm({ parentWin : this });
        var config = {
            layout:'fit',
            frame:true,
            closable: true,
            closeAction: 'hide',
            width:450,
            height: 335,
            modal: true,
            resizable: false,
            title:"<s:text name='appbs_usuarios.caption'/>",
            items: [this.editForm]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuariosBasicWin.superclass.initComponent.call(this);
    },

    loadRecord: function (perfil_tipo, tipo, record) {
        this.editForm.loadRecord(perfil_tipo, tipo, record);
    }
});

</script>
