<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ page import="com.befasoft.common.model.manager.APPBS_USUARIOS_LOG_MANAGER" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_CONTACTOS" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.ContactosData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.ContactosData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            fields: [
                "fk", "id_contacto", "contacto.telefono", "contacto.fax", "contacto.observaciones", "contacto.email",
                "contacto.activo", "contacto.id_contacto", "contacto.movil", "contacto.nombre", "contacto.save"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.ContactosGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    pageSize: 25,
    urlDataStore : null,
    fk: null,
    userlog: false,
    logtabla: "APPEX_CONTACTOS",

    initComponent : function() {
        // DataStore
        this.contactosData = new Ext.com.befasoft.common.components.ContactosData({ url: this.urlDataStore });

        var config = {
            region: 'center',
            store: this.contactosData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            title: "<s:text name='appex_contactos.caption'/>",
            columns: [
                { header:"<s:text name='appex_contactos.id_contacto'/>", sortable: true, dataIndex: 'contacto.id_contacto', width: 10 },
                { header:"<s:text name='appex_contactos.nombre'/>", sortable: true, dataIndex: 'contacto.nombre', width: 30 },
                { header:"<s:text name='appex_contactos.movil'/>", sortable: true, dataIndex: 'contacto.movil', width: 20 },
                { header:"<s:text name='appex_contactos.email'/>", sortable: true, dataIndex: 'contacto.email', width: 30 },
                { header:"<s:text name='appex_contactos.activo'/>", sortable: true, dataIndex: 'contacto.activo', renderer: formatBoolean, width: 10 }
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
                                writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CONTACTOS_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, 0, "NUEVO", this.logtabla);
                            var win = new Ext.com.befasoft.common.components.ContactosWin({ linkedGrid: this, urlDataStore: this.urlDataStore });
                            var data = {};
                            data["fk"] = this.fk;
                            data["contacto.telefono"] = null;
                            data["contacto.fax"] = null;
                            data["contacto.observaciones"] = null;
                            data["contacto.email"] = null;
                            data["contacto.activo"] = null;
                            data["contacto.id_contacto"] = null;
                            data["contacto.movil"] = null;
                            data["contacto.nombre"] = null;
                            data["contacto.save"] = true;

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
        Ext.com.befasoft.common.components.ContactosGrid.superclass.initComponent.call(this);

        this.on('rowdblclick', function(grid, rowIndex, e) {
            var currentRecord = grid.getStore().getAt(rowIndex);
            if (this.userlog)
                writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CONTACTOS_LOAD %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, currentRecord.data.id_contacto, this.logtabla);
            var win = new Ext.com.befasoft.common.components.ContactosWin({ linkedGrid: this, urlDataStore: this.urlDataStore });
            win.loadRecord(currentRecord);
            win.show();
        });

    },

    updateData: function (fk) {
        if (fk != null)
            this.fk = fk;
        this.contactosData.load({ params:{fk: this.fk } });
    }

});

Ext.com.befasoft.common.components.ContactosForm = Ext.extend(Ext.FormPanel, {

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
                { xtype : 'hidden',  name: 'id_contacto' },
                { xtype : 'hidden',  name: 'contacto.id_contacto' },
                { xtype : 'hidden',  name: 'contacto.save' },
                { fieldLabel:"<s:text name='appex_contactos.nombre'/>", name: 'contacto.nombre', allowBlank:false, width: 300 },
                { fieldLabel:"<s:text name='appex_contactos.movil'/>", name: 'contacto.movil', allowBlank:false  },
                { fieldLabel:"<s:text name='appex_contactos.telefono'/>", name: 'contacto.telefono', allowBlank:true },
                { fieldLabel:"<s:text name='appex_contactos.fax'/>", name: 'contacto.fax', allowBlank:true  },
                { fieldLabel:"<s:text name='appex_contactos.email'/>", name: 'contacto.email', allowBlank:true, vtype: 'email', width: 300 },
                { fieldLabel:"<s:text name='appex_contactos.observaciones'/>", name: 'contacto.observaciones', allowBlank:true, width: 300  },
                { xtype: 'checkbox', boxLabel:"<s:text name='appex_contactos.activo'/>", name: 'contacto.activo' }
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
                                success: function(response) {
                                    var params = getActionResponse(response);
                                    if (params.success) {
 	                                    mainDiv.unmask();
                                        if (this.parentWin.linkedGrid.userlog)
                                            writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CONTACTOS_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, this.form.findField("id_contacto").getValue(), "GUARDAR", this.parentWin.linkedGrid.logtabla);
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
                        Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appex_contactos.msg.delete"/>",
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
                                                mainDiv.unmask();
                                                if (component.parentWin.linkedGrid.userlog)
                                                    writeUserLog(<%= APPBS_USUARIOS_LOG_MANAGER.ID_TIPO_CONTACTOS_SAVE %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, component.form.findField("id_contacto").getValue(), "BORRAR", component.parentWin.linkedGrid.logtabla);
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
        Ext.com.befasoft.common.components.ContactosForm.superclass.initComponent.call(this);
    }
});

Ext.com.befasoft.common.components.ContactosWin = Ext.extend(Ext.Window, {

    linkedGrid: null,

    initComponent : function() {
        this.editForm = new Ext.com.befasoft.common.components.ContactosForm({ parentWin : this, urlDataStore: this.urlDataStore });
        var config = {
            layout:'fit',
            frame:true,
            closable: true,
            closeAction: 'hide',
            width:450,
            modal:true,
            resizable: false,
            title:"<s:text name='appex_contactos.caption'/>",
            items: [this.editForm]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.ContactosWin.superclass.initComponent.call(this);
    },

    loadRecord: function (record) {
        this.editForm.getForm().loadRecord(record);
    },

    getValues: function () {
        return this.editForm.getValues();
    }
});

</script>
