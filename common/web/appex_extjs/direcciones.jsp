<%@ page import="com.befasoft.common.tools.Constants" %>
<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ page import="com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "DIRECCIONES" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.DireccionesForm = Ext.extend(Ext.FormPanel, {

    direccionesElementsToDelete: [],
    urlDataStore: null,
    parentDataGUI: null,

    initComponent : function() {
        // DataStore
        this.direccionesPaisesFKData = new Ext.data.JsonStore({
            remoteSort: false,
            autoDestroy: true,
            autoLoad: true,
            url: appPath+'/Paises.action',
            root:"elements",
            totalProperty: 'totalCount',
            fields: ["nombre", "id_pais"],
            listeners: { 'loadexception': dataSourceLoadException }
        });

        this.direccionesProvinciasFKData = new Ext.data.JsonStore({
            remoteSort: false,
            autoDestroy: true,
            url: appPath+'/Provincias.action',
            root:"elements",
            totalProperty: 'totalCount',
            fields: ["nombre", "id_provincia", "id_pais"],
            listeners: { 'loadexception': dataSourceLoadException }
        });

        this.cbDireccionesPaises = new Ext.form.ComboBox({
            store: this.direccionesPaisesFKData,
            displayField:'nombre', valueField : 'id_pais', hiddenName : 'direccion.id_pais',
            typeAhead: true, mode: 'local', allowBlank:false,
            fieldLabel:"<s:text name='appex_direcciones.id_pais'/>",
            triggerAction: 'all',
            emptyText:"<s:text name='appbs.form.combo.select'/>",
            selectOnFocus:true,
            storeProvincias: this.direccionesProvinciasFKData,
            listeners:{
                'select': function (combo, record, index) {
                    this.storeProvincias.load({ params: {id_pais: record.get("id_pais") } });
                }
            }
        });

        this.cbDireccionesProvincias = new Ext.form.ComboBox({
            store: this.direccionesProvinciasFKData,
            displayField:'nombre', valueField : 'id_provincia', hiddenName : 'direccion.id_provincia',
            typeAhead: true, allowBlank:false, mode: 'local',
            fieldLabel:"<s:text name='appex_direcciones.id_provincia'/>",
            triggerAction: 'all',
            emptyText:"<s:text name='appbs.form.combo.select'/>",
            selectOnFocus:true
        });

        var config = {
            frame:true,
            autoHeight:true,
            autoWidth:true,
            defaultType:'textfield',
            monitorValid:true,
            //title:"<s:text name='appex_direcciones.caption'/>",
            items:[
                { xtype : 'hidden',  name: 'fk' },
                { xtype : 'hidden',  name: 'id_direccion' },
                { xtype : 'hidden',  name: 'direccion.id_direccion' },
                { xtype : 'hidden',  name: 'direccion.save' },
                { fieldLabel:"<s:text name='appex_direcciones.direccion'/>",  name: 'direccion.direccion', allowBlank:false, width: 300 },
                { fieldLabel:"<s:text name='appex_direcciones.cp'/>",  name: 'direccion.cp', allowBlank:false },
                { fieldLabel:"<s:text name='appex_direcciones.poblacion'/>",  name: 'direccion.poblacion', allowBlank:false },
                this.cbDireccionesPaises,
                this.cbDireccionesProvincias,
                { fieldLabel:"<s:text name='appex_direcciones.observaciones'/>",  name: 'direccion.observaciones', allowBlank:true, width: 300},
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_direcciones.fiscal'/>", name: 'direccion.fiscal' },
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_direcciones.facturacion'/>", name: 'direccion.facturacion'},
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_direcciones.envio'/>", name: 'direccion.envio' },
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_direcciones.principal'/>", name: 'direccion.principal' },
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_direcciones.activa'/>", name: 'direccion.activa' }
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
                                url: this.urlDataStore+'?action=save',
                                params: { dataToSave:Ext.encode(data) },
                                scope: this,
                                success: function() {
                                    if (this.parentDataGUI.userlog)
                                        writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_DIRECCIONES_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, this.getForm().findField("id_direccion").getValue(), this.parentDataGUI.logtabla, "GUARDAR");
                                    mainDiv.unmask();
                                    //actualizamos el grid
                                    this.winFormDirecciones.hide();
                                    if (this.parentDataGUI)
                                        this.parentDataGUI.updateDireccionesData(null);
                                },
                                failure: function() {
                                    mainDiv.unmask();
                                    Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                }
                            });
                        } else
                            Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="appbs.form.msg.invalid"/>");
                    }// end handler
                }, // end button
                {
                    xtype: 'tbbutton',
                    icon: 'images/delete.gif',
                    text: "<s:text name='appbs.btn.delete.record'/>",
                    scope: this,
                    handler: function() {
                        var component = this;
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_direcciones.msg.delete"/>",
                            function (btn) {
                                if (btn == "yes") {
                                    mainDiv.mask("<s:text name='appbs.wait'/>");
                                    var data = [];
                                    data.push(component.form.getFieldValues());
                                    Ext.Ajax.request({
                                        url: component.urlDataStore+'?action=save',
                                        params: { elementsToDelete: Ext.encode(data) },
                                        success: function(response) {
                                            var params = getActionResponse(response);
                                            if (params.success) {
                                                if (component.parentDataGUI.userlog)
                                                    writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_DIRECCIONES_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, component.getForm().findField("id_direccion").getValue(), component.parentDataGUI.logtabla, "BORRAR");
                                                mainDiv.unmask();
                                                component.winFormDirecciones.hide();
                                                if (component.parentDataGUI)
                                                    component.parentDataGUI.updateDireccionesData(null);
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
                }// End boton
            ]
        };

        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.DireccionesForm.superclass.initComponent.call(this);
    },

    updateDireccionesData: function (fk) {
        if (fk == null)
            fk = this.direccionesFk;
        else
            this.direccionesFk = fk;
    },

    // Formulario de edicion
    showEditForm: function (record) {
        if (this.winFormDirecciones == null) {
            this.winFormDirecciones = new Ext.Window({
                layout:'fit',
                frame:true,
                closable: true,
                closeAction: 'hide',
                resizable: true,
                width: 450,
                modal: true,
                title:"<s:text name='appex_direcciones.caption'/>",
                items: [this]
            });
        }
        var id_pais;
        if (record) {
            if (record.data.isNew) {
                this.setFk(record.data.fk);
            } else {
                this.getForm().loadRecord(record);
                id_pais = record.get("direccion.id_pais");
            }
        }
        if (!id_pais)
            id_pais = <%=Constants.ID_PAIS_ESPANA%>;
        this.direccionesProvinciasFKData.load({ params: {id_pais: id_pais }, scope: this,
            callback: function () {
                this.winFormDirecciones.show();
            }
        });
    },

    setFk: function (fk) {
        this.getForm().findField("fk").setValue(fk);
        this.getForm().findField("id_direccion").setValue(0);
        this.getForm().findField("direccion.id_direccion").setValue(0);
        this.getForm().findField("direccion.direccion").setValue("");
        this.getForm().findField("direccion.cp").setValue("");
        this.getForm().findField("direccion.poblacion").setValue("");
        this.getForm().findField("direccion.id_pais").setValue(<%=Constants.ID_PAIS_ESPANA%>);
        this.getForm().findField("direccion.id_provincia").setValue("");
        this.getForm().findField("direccion.observaciones").setValue("");
        this.getForm().findField("direccion.fiscal").setValue(true);
        this.getForm().findField("direccion.facturacion").setValue(true);
        this.getForm().findField("direccion.envio").setValue(true);
        this.getForm().findField("direccion.principal").setValue(true);
        this.getForm().findField("direccion.activa").setValue(true);
        this.getForm().findField("direccion.save").setValue(true);
    }

});

Ext.com.befasoft.common.components.DireccionesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.DireccionesData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            fields: [
                "fk", "id_direccion", "direccion.id_direccion", "direccion.id_provincia", "direccion.id_pais", "direccion.observaciones", "direccion.poblacion", "direccion.cp",
                "direccion.envio", "direccion.facturacion", "direccion.direccion", "direccion.id_direccion", "direccion.principal", "direccion.activa", "direccion.fiscal",
                "direccion.provincia.nombre", "direccion.provincia.pais.nombre"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.DireccionesGrid = Ext.extend(Ext.grid.GridPanel, {

    direccionesElementsToDelete: [],
    direccionesFk: null,
    urlDataStore: null,
    userlog: false,
    logtabla: "APPEX_DIRECCIONES",

    initComponent : function() {
        // DataStore
        this.direccionesData = new Ext.com.befasoft.common.components.DireccionesData({ url: this.urlDataStore });

        // Grid
        var config = {
            region: 'center',
            store: this.direccionesData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            title: "<s:text name='appex_direcciones.caption'/>",
            columns: [
                { header:"<s:text name='appex_direcciones.id_direccion'/>", sortable: true, dataIndex: 'direccion.id_direccion', width: 5 },
                { header:"<s:text name='appex_direcciones.direccion'/>", sortable: true, dataIndex: 'direccion.direccion', width: 20 },
                { header:"<s:text name='appex_direcciones.cp'/>", sortable: true, dataIndex: 'direccion.cp', width: 15 },
                { header:"<s:text name='appex_direcciones.poblacion'/>", sortable: true, dataIndex: 'direccion.poblacion', width: 20 },
                { header:"<s:text name='appex_direcciones.id_provincia'/>", sortable: true, dataIndex: 'direccion.provincia.nombre', width: 20 },
                { header:"<s:text name='appex_direcciones.id_pais'/>", sortable: true, dataIndex: 'direccion.provincia.pais.nombre', hidden:true },
                { header:"<s:text name='appex_direcciones.envio'/>", sortable: true, dataIndex: 'direccion.envio', hidden:true, renderer:'Boolean' },
                { header:"<s:text name='appex_direcciones.facturacion'/>", sortable: true, dataIndex: 'direccion.facturacion', width: 10, renderer:'Boolean' },
                { header:"<s:text name='appex_direcciones.principal'/>", sortable: true, dataIndex: 'direccion.principal', width: 10, renderer:'Boolean' },
                { header:"<s:text name='appex_direcciones.fiscal'/>", sortable: true, dataIndex: 'direccion.fiscal', hidden:true, renderer:'Boolean' },
                { header:"<s:text name='appex_direcciones.activa'/>", sortable: true, dataIndex: 'direccion.activa', hidden:true, renderer:'Boolean' },
                { header:"<s:text name='appex_direcciones.observaciones'/>", sortable: true, dataIndex: 'direccion.observaciones', hidden:true }
            ],
            viewConfig: {
                forceFit: true
            },
            frame:true,

            sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),

            listeners: {
                rowdblclick: function(grid, rowIndex, e) {
                    var record = grid.getStore().getAt(rowIndex);
                    if (this.userlog)
                        writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_DIRECCIONES_LOAD %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, record.data.id_direccion, this.logtabla);
                    this.showEditForm(record);
                }
            },

            bbar: new Ext.Toolbar({
                items:[
                    {
                        text: "<s:text name='appbs.btn.add.record'/>",
                        icon: 'images/add.png',
                        scope: this,
                        handler : function() {
                            if (this.userlog)
                                writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_DIRECCIONES_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, 0, this.logtabla, "NUEVO");
                            var record = gridNotEditAddRecord(this);
                            record.set("fk", this.direccionesFk);
                            record.set("isNew", true);
                            this.showEditForm(record);
                        }
                    }
                ]
            })
        };

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.DireccionesGrid.superclass.initComponent.call(this);

    }, //end function InitComponent

    updateDireccionesData: function (fk) {
        if (fk == null)
            fk = this.direccionesFk;
        else
            this.direccionesFk = fk;
        this.direccionesData.load({ params: {fk: fk}, callback: dataSourceLoad });
    },

    // Formulario de edicion
    showEditForm: function (record) {
        if (this.formDirecciones == null) {
            this.formDirecciones = new Ext.com.befasoft.common.components.DireccionesForm({ urlDataStore: this.urlDataStore, parentDataGUI: this});
            this.formDirecciones.updateDireccionesData();
        }
        this.formDirecciones.showEditForm(record);
    }

});

/*
 * Mantenimiento de "DIRECCIONES" (End)
 */

</script>
