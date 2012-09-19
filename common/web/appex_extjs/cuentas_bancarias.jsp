<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ page import="com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_CUENTAS_BANCARIAS" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Cuentas_bancariasData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Cuentas_bancariasData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            fields: [
                "fk", "id_cuenta", "cuenta.sucursal", "cuenta.cuenta", "cuenta.dc", "cuenta.iban", "cuenta.entidad", "cuenta.activa", "cuenta.id_cuenta", "cuenta.nombre"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Cuentas_bancariasGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    userlog: false,
    logtabla: "APPEX_CUENTAS_BANCARIAS",

    initComponent : function() {
        // DataStore
        this.cuentas_bancariasData = new Ext.com.befasoft.common.components.Cuentas_bancariasData({ url: this.urlDataStore });

        var config = {
            region: 'center',
            store: this.cuentas_bancariasData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            title: "<s:text name='appex_cuentas_bancarias.caption'/>",
            columns: [
                { header:"<s:text name='appex_cuentas_bancarias.id_cuenta'/>", sortable: true, dataIndex: 'cuenta.id_cuenta', width: 10 },
                { header:"<s:text name='appex_cuentas_bancarias.nombre'/>", sortable: true, dataIndex: 'cuenta.nombre', width: 50 },
                {
                    header:"<s:text name='appex_cuentas_bancarias.cuenta'/>", sortable: true, dataIndex: 'cuenta.cuenta', width: 30,
                    renderer: function (value, obj, record) {
                        return record.get("cuenta.entidad")+'-'+record.get("cuenta.sucursal")+'-'+record.get("cuenta.dc")+'-'+record.get("cuenta.cuenta")
                    }
                },
                { header:"<s:text name='appex_cuentas_bancarias.activa'/>", sortable: true, dataIndex: 'cuenta.activa', renderer: formatBoolean, width: 10 }
            ],
            viewConfig: {
                forceFit: true
            },
            frame:true,
            bbar: new Ext.Toolbar({
                items:[
                    {
                        text: "<s:text name='appbs.btn.add.record'/>",
                        icon: 'images/add.png',
                        scope: this,
                        handler: function() {
                            if (this.userlog)
                                writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CUENTABAN_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, 0, this.logtabla, "NUEVO");
                            var win = new Ext.com.befasoft.common.components.Cuentas_bancariasWin({ linkedGrid: this, urlDataStore: this.urlDataStore  });
                            var data = {};
                            data["fk"] = this.fk;
                            data["id_cuenta"] = null;
                            data["cuenta.sucursal"] = null;
                            data["cuenta.cuenta"] = null;
                            data["cuenta.dc"] = null;
                            data["cuenta.iban"] = null;
                            data["cuenta.entidad"] = null;
                            data["cuenta.activa"] = null;
                            data["cuenta.id_cuenta"] = null;
                            data["cuenta.nombre"] = null;
                            data["cuenta.save"] = true;

                            var currentRecord = new Ext.data.Record(data);
                            win.loadRecord(currentRecord);
                            win.show();
                        }
                    }
                ]
            }),
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Cuentas_bancariasGrid.superclass.initComponent.call(this);

        this.on('rowdblclick', function(grid, rowIndex, e) {
            var currentRecord = grid.getStore().getAt(rowIndex);
            if (this.userlog)
                writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CUENTABAN_LOAD %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, currentRecord.data.id_cuenta, this.logtabla);
            var win = new Ext.com.befasoft.common.components.Cuentas_bancariasWin({ linkedGrid: this, urlDataStore: this.urlDataStore  });
            win.loadRecord(currentRecord);
            win.show();
        });

    },

    updateData: function (fk) {
        if (fk != null)
            this.fk = fk;
        this.cuentas_bancariasData.load({ params:{fk: this.fk} });
    }

});

Ext.com.befasoft.common.components.Cuentas_bancariasForm = Ext.extend(Ext.FormPanel, {

    parentWin: null,
    urlDataStore: null,

    initComponent : function() {

        var config = {
            region: 'south',
            height:230,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { xtype : 'hidden',  name: 'fk' },
                { xtype : 'hidden',  name: 'id_cuenta' },
                { xtype : 'hidden',  name: 'cuenta.id_cuenta' },
                { xtype : 'hidden',  name: 'cuenta.save' },
                { fieldLabel:"<s:text name='appex_cuentas_bancarias.nombre'/>", name: 'cuenta.nombre', allowBlank:false, width: 250  },
                { fieldLabel:"<s:text name='appex_cuentas_bancarias.entidad'/>", name: 'cuenta.entidad', allowBlank:false, width: 50  },
                { fieldLabel:"<s:text name='appex_cuentas_bancarias.sucursal'/>", name: 'cuenta.sucursal', allowBlank:false, width: 50 },
                { fieldLabel:"<s:text name='appex_cuentas_bancarias.dc'/>", name: 'cuenta.dc', allowBlank:false, width: 50  },
                { fieldLabel:"<s:text name='appex_cuentas_bancarias.cuenta'/>", name: 'cuenta.cuenta', allowBlank:false, width: 150  },
                { fieldLabel:"<s:text name='appex_cuentas_bancarias.iban'/>", name: 'cuenta.iban', allowBlank:true  },
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_cuentas_bancarias.activa'/>", name: 'cuenta.activa' }
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: 'images/disk.png',
                    text: "<s:text name='appbs.btn.save.records'/>",
                    scope: this,
                    handler: function() {
                        if (this.getForm().isValid()) {
                            var ok = checkBankAccount(
                                    this.getForm().findField("cuenta.entidad").getValue(),
                                    this.getForm().findField("cuenta.sucursal").getValue(),
                                    this.getForm().findField("cuenta.dc").getValue(),
                                    this.getForm().findField("cuenta.cuenta").getValue()
                            );
                            if (!ok) {
                                Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="appex_cuentas_bancarias.msg.invalid"/>");
                                return;
                            }
                            mainDiv.mask("<s:text name='appbs.wait'/>");
                            var data = [];
                            data.push(this.form.getFieldValues());

                            Ext.Ajax.request({
                                url: this.urlDataStore+'?action=save',
                                params: { dataToSave:Ext.encode(data) },
                                scope: this,
                                success: function(response) {
                                    var params = getActionResponse(response);
                                    if (params.success) {
                                        if (this.parentWin.linkedGrid.userlog)
                                            writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CUENTABAN_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, this.getForm().findField("id_cuenta").getValue(), this.parentWin.linkedGrid.logtabla, "GUARDAR");
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
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_cuentas_bancarias.msg.delete"/>",
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
                                                if (component.parentWin.linkedGrid.userlog)
                                                    writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CUENTABAN_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, component.getForm().findField("id_cuenta").getValue(), component.parentWin.linkedGrid.logtabla, "BORRAR");
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
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Cuentas_bancariasForm.superclass.initComponent.call(this);
    }
});

Ext.com.befasoft.common.components.Cuentas_bancariasWin = Ext.extend(Ext.Window, {

    linkedGrid: null,

    initComponent : function() {
        this.editForm = new Ext.com.befasoft.common.components.Cuentas_bancariasForm({ parentWin : this, urlDataStore: this.urlDataStore  });
        var config = {
            layout:'fit',
            frame:true,
            closable: true,
            closeAction: 'hide',
            width:400,
            resizable: false,
            title:"<s:text name='appex_cuentas_bancarias.caption'/>",
            items: [this.editForm]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Cuentas_bancariasWin.superclass.initComponent.call(this);
    },

    loadRecord: function (record) {
        this.editForm.getForm().loadRecord(record);
    },

    getValues: function () {
        return this.editForm.getValues();
    }
});


/*
 * Mantenimiento de "APPEX_CUENTAS_BANCARIAS" (End)
 */

</script>
