<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de APPBS_USUARIOS [Basic Version] (Start)
 */

Ext.ns('Ext.common.component');

Ext.common.component.UsuariosGrid = Ext.extend(Ext.grid.GridPanel, {

    appbs_usuarios_basicPageSize: 25,
    appbs_usuarios_basicElementsToDelete: [],
    appbs_usuarios_basicData: null,
    searchAppbs_usuarios_basic: null,
    formAppbs_usuarios_basic:null,

    initComponent : function() {

        this.appbs_usuarios_basicData = new Ext.data.JsonStore({
            remoteSort: true,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            idProperty: 'pg',
            proxy: new Ext.data.HttpProxy({url: appPath+'/Usuarios.action'}),
            fields: [
                "nombre", "email", "movil", "telefono", "tipo", "clave", "fax", "login", "id_usuario", "usuario_tipo.nombre",
                "param_1", "param_2", "param_3", "param_4", "int_param_1", "int_param_2", "int_param_3", "int_param_4"
            ],
            listeners: { 'loadexception': dataSourceLoadException }
        });

        var loginBasicFilterText = new Ext.form.TextField({ name:'login', width: 80, value:'' });
        var nameBasicFilterText = new Ext.form.TextField({ name:'nameFilter', width: 80, value:'' });

        // Se crea el toolbar
        this.searchAppbs_usuarios_basic = new Ext.Toolbar({
           frame:true,
           height:35,
           region: 'north',
            items: [
                '<s:text name="appbs_usuarios.login"/>: ', loginBasicFilterText,
                '    ',
                '<s:text name="appbs_usuarios.nombre"/>: ', nameBasicFilterText,
                '->',
                {
                    xtype: 'tbbutton',
                    icon: 'images/lupa.gif',
                    text: "<s:text name='appbs.form.filter'/>",
                    storeToFilter: this.appbs_usuarios_basicData,
                    handler: function() {
                        this.storeToFilter.setBaseParam('login', loginBasicFilterText.getValue());
                        this.storeToFilter.setBaseParam('nombre', nameBasicFilterText.getValue());
                        this.storeToFilter.load();
                    }
                }
            ]
        });

        this.appbs_usuarios_basic_Tipos_usuariosFKData = new Ext.data.JsonStore({
            remoteSort: false,
            autoDestroy: true,
            url: appPath+'/Usuarios_tipos.action',
            root:"elements",
            totalProperty: 'totalCount',
            fields: ["nombre", "id_tipo"],
            listeners: { 'loadexception': dataSourceLoadException }
        });

        this.cbAppbs_usuarios_basic_tipos = new Ext.form.ComboBox({
            store: this.appbs_usuarios_basic_Tipos_usuariosFKData,
            displayField:'nombre',
            valueField : 'id_tipo',
            hiddenName : 'tipo',
            typeAhead: true,
            mode: 'local',
            fieldLabel:"<s:text name='appbs_usuarios.tipo'/>",
            triggerAction: 'all',
            emptyText:"<s:text name='appbs.form.combo.select'/>",
            selectOnFocus:true
        });

        var config = {

            region: 'center',
            store: this.appbs_usuarios_basicData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                { header:"<s:text name='appbs_usuarios.id_usuario'/>", sortable: true, dataIndex: 'id_usuario', width: 7 },
                { header:"<s:text name='appbs_usuarios.login'/>", sortable: true, dataIndex: 'login', width: 10 },
                { header:"<s:text name='appbs_usuarios.nombre'/>", sortable: true, dataIndex: 'nombre', width: 25 },
                { header:"<s:text name='appbs_usuarios.email'/>", sortable: true, dataIndex: 'email', width: 25 },
                { header:"<s:text name='appbs_usuarios.telefono'/>", sortable: true, dataIndex: 'telefono', width: 20 },
                { header:"<s:text name='appbs_usuarios.tipo'/>", sortable: true, dataIndex: 'usuario_tipo.nombre', width: 13 }
            ],
            tbar:this.searchAppbs_usuarios_basic,
            bbar: new Ext.PagingToolbar({
                pageSize: this.appbs_usuarios_basicPageSize,
                store: this.appbs_usuarios_basicData,
                displayInfo: true,
                displayMsg: "<s:text name='appbs_usuarios.pg_info'/>",
                emptyMsg: "<s:text name='appbs_usuarios.pg_none'/>"
            }),
            viewConfig: {
                forceFit: true
            },
            frame:false,

            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(smObj, rowIndex, record) {
                        // formAppbs_usuarios_basic.getForm().loadRecord(record);
                    }
                }
            }),


            listeners: {
                rowselect: function(smObj, rowIndex, record) {
                    //this.formAppbs_usuarios_basic.getForm().loadRecord(record);
                },
                rowdblclick: function(grid, rowIndex, e) {
                    var record = grid.getStore().getAt(rowIndex);
                    this.showEditForm(record);
                }
            }


        }; // end config

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.common.component.UsuariosGrid.superclass.initComponent.call(this);

    }, //end function InitComponen

    //------------------------------------------------------------------------
    // Funciones para actualizacion de datos
    //------------------------------------------------------------------------
    updateAppbs_usuarios_basicData: function (int_param_1, int_param_2, int_param_3, int_param_4, param_1, param_2, param_3, param_4) {
        if (int_param_1 != null)
            this.appbs_usuarios_basicData.setBaseParam('int_param_1', int_param_1);
        if (int_param_2 != null)
            this.appbs_usuarios_basicData.setBaseParam('int_param_2', int_param_2);
        if (int_param_3 != null)
            this.appbs_usuarios_basicData.setBaseParam('int_param_3', int_param_3);
        if (int_param_4 != null)
            this.appbs_usuarios_basicData.setBaseParam('int_param_4', int_param_4);
        if (param_1 != null)
            this.appbs_usuarios_basicData.setBaseParam('param_1', param_1);
        if (param_2 != null)
            this.appbs_usuarios_basicData.setBaseParam('param_2', param_2);
        if (param_3 != null)
            this.appbs_usuarios_basicData.setBaseParam('param_3', param_3);
        if (param_4 != null)
            this.appbs_usuarios_basicData.setBaseParam('param_4', param_4);

        this.appbs_usuarios_basicData.load({ params:{start:0, limit:this.appbs_usuarios_basicPageSize}, callback: dataSourceLoad });
        this.appbs_usuarios_basic_Tipos_usuariosFKData.load();
    },

    showEditForm: function( record ) {

        this.formAppbs_usuarios_basic = new Ext.FormPanel({
            frame:true,
            autoHeight:true,
            autoWidth:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { name: 'id_usuario', allowBlank:false , xtype : 'hidden' },
                { name: 'clave', allowBlank:false , xtype : 'hidden' },
                { name: 'int_param_1', allowBlank:false , xtype : 'hidden' },
                { name: 'int_param_2', allowBlank:false , xtype : 'hidden' },
                { name: 'int_param_3', allowBlank:false , xtype : 'hidden' },
                { name: 'int_param_4', allowBlank:false , xtype : 'hidden' },
                { name: 'param_1', allowBlank:false , xtype : 'hidden' },
                { name: 'param_2', allowBlank:false , xtype : 'hidden' },
                { name: 'param_3', allowBlank:false , xtype : 'hidden' },
                { name: 'param_4', allowBlank:false , xtype : 'hidden' },
                { fieldLabel:"<s:text name='appbs_usuarios.login'/>", name: 'login', allowBlank:true },
                { fieldLabel:"<s:text name='appbs_usuarios.nombre'/>", name: 'nombre', allowBlank:true },
                { fieldLabel:"<s:text name='appbs_usuarios.email'/>", name: 'email', allowBlank:true },
                { fieldLabel:"<s:text name='appbs_usuarios.clave'/>", name: 'contrasenya', allowBlank:true },
                { fieldLabel:"<s:text name='appbs_usuarios.telefono'/>", name: 'telefono', allowBlank:true },
                { fieldLabel:"<s:text name='appbs_usuarios.movil'/>", name: 'movil', allowBlank:true },
                { fieldLabel:"<s:text name='appbs_usuarios.fax'/>", name: 'fax', allowBlank:true },
                this.cbAppbs_usuarios_basic_tipos
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: '../ext-3.3.1/examples/layout-browser/images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    usuariosGrid:this,
                    handler: function() {

                        mainDiv.mask("<s:text name='appbs.wait'/>");

                        var data = [];
                        data.push(this.usuariosGrid.formAppbs_usuarios_basic.form.getFieldValues());

                        usuariosGrid=this.usuariosGrid;

                        Ext.Ajax.request({
                            url: appPath+'/Usuarios.action?action=save',
                            params: {dataToSave:Ext.encode(data), elementsToDelete:Ext.encode(this.usuariosGrid.appbs_usuarios_basicElementsToDelete)},
                            success: function() {

                                // ya no hay elementos por borrar
                                usuariosGrid.appbs_usuarios_basicElementsToDelete = [];

                                mainDiv.unmask();

                                //actualizamos el grid
                                usuariosGrid.updateAppbs_usuarios_basicData();
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

        this.formAppbs_usuarios_basic.getForm().loadRecord(record);


        this.winFormAppbs_usuarios_basic = new Ext.Window({
           layout:'fit',
           frame:true,
           closable: true,
           closeAction: 'hide',
           resizable: true,
           title:"<s:text name='appbs_usuarios.caption'/>",
           items: [this.formAppbs_usuarios_basic]
        });

        this.winFormAppbs_usuarios_basic.show();
    }

}); //End Componente

</script>
