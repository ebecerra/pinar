<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ page import="com.remedi.rmn.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "MENSAJES RADIOLOGOS"
 */

Ext.ns('Ext.com.remedi.rmn.components');

Ext.com.remedi.rmn.components.MensajesGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedPanel: null,
    pageSize: 25,

    initComponent : function() {
        // DataStore
        this.mensajesData = new Ext.com.remedi.rmn.components.MensajesData();

        this.cbEstado = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.MensajesEstadoData(),
            displayField:'description', valueField : 'code', hiddenName : 'estado', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 90,
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });

        this.pagingMsg = new Ext.PagingToolbar({
            pageSize: this.pageSize,
            store: this.mensajesData,
            displayInfo: true,
            displayMsg: "<s:text name="mensajes.pg_info"/>",
            emptyMsg: "<s:text name="mensajes.pg_none"/>",
            items:[
                {
                    text: "<s:text name='mensajes.btn.new'/>",
                    icon: 'iconos/msg_new.gif',
                    scope: this,
                    handler: function() {
                        var win = new Ext.com.remedi.rmn.components.MensajesWin({ linkedGrid: this });
                        var data = {};
                        data["org_tipo"] = "R";
                        data["origen"] = <s:property value="#session.user.id_radiologo"/>;
                        data["estado"] = null;
                        data["destinatario"] = null;
                        data["fecha"] = new Date();
                        data["id_mensaje"] = null;
                        data["asunto"] = null;
                        data["msg_respuesta"] = null;
                        data["texto"] = null;
                        data["dest_tipo"] = null;

                        var currentRecord = new Ext.data.Record(data);
                        win.loadRecord(currentRecord);
                        win.show();
                    }
                }, {
                    xtype: 'tbbutton',
                    icon: 'iconos/msg_resp.gif',
                    text: "<s:text name='mensajes.btn.resp'/>",
                    scope: this,
                    handler: function() {
                        var currentRecord = this.getSelectionModel().getSelected();
                        if (currentRecord) {
                            var win = new Ext.com.remedi.rmn.components.MensajesRespWin({ linkedGrid: this });
                            win.loadRecord(currentRecord);
                            win.show();
                        } else
                            Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="mensajes.msg.select"/>");
                    }
                }, {
                    xtype: 'tbbutton',
                    icon: 'iconos/msg_delete.gif',
                    text: "<s:text name='mensajes.btn.delete'/>",
                    scope: this,
                    handler: function() {
                        var currentRecord = this.getSelectionModel().getSelected();
                        if (currentRecord) {
                            var component = this;
                            Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="mensajes.msg.delete"/>",
                                function (btn) {
                                    if (btn == "yes") {
                                        Ext.Ajax.request({
                                            url: appPath+'/Mensajes.action?action=changeEstado',
                                            params: { id_mensaje: currentRecord.data.id_mensaje, estado: 'B' },
                                            success: function(response) {
                                                var params = getActionResponse(response);
                                                if (params.success) {
                                                    component.refreshPage();
                                                } else {
                                                    showServerError(params);
                                                }
                                            },
                                            failure: function() {
                                                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                            }
                                        });
                                    }
                                }
                            );
                        } else
                            Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="mensajes.msg.select"/>");
                    }
                }
            ]
        });

        var config = {
            region: 'center',
            store: this.mensajesData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"", sortable: true, dataIndex: 'msg_respuesta', width: 5, renderer: renderMsgEstado },
                { header:"<s:text name='mensajes.origen'/>", sortable: true, dataIndex: 'de', width: 30 },
                { header:"<s:text name='mensajes.asunto'/>", sortable: true, dataIndex: 'asunto', width: 50 },
                { header:"<s:text name='mensajes.fecha'/>", sortable: true, dataIndex: 'fecha', width: 15, renderer: formatDateTime3 }
            ],
            tbar: new Ext.Toolbar({
                frame:true,
                height:30,
                items: [
                    '&nbsp;&nbsp;<s:text name="mensajes.estado"/>:&nbsp;', this.cbEstado,
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: 'images/lupa.gif',
                        text: "<s:text name='appbs.form.filter'/>",
                        scope: this,
                        handler: function() {
                            this.store.setBaseParam('estado', this.cbEstado.getValue());
                            this.updateData();
                        }
                    }
                ]
            }),
            bbar: this.pagingMsg,
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                scope: this,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        this.linkedPanel.setData(record);
                        if (record.data.estado == 'N') {
                            // Marcar como leido
                            Ext.Ajax.request({
                                url: appPath+'/Mensajes.action?action=changeEstado',
                                scope: this,
                                params: { id_mensaje: record.data.id_mensaje, estado: 'L' },
                                success: function(response) {
                                    var params = getActionResponse(response);
                                    if (params.success) {
                                        this.refreshPage();
                                    }
                                }
                            });
                        }
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.MensajesGrid.superclass.initComponent.call(this);
        this.mensajesData.setDefaultSort("fecha", "desc");
    },

    refreshPage: function () {
        this.pagingMsg.doRefresh();
    },

    updateData: function () {
        this.mensajesData.load({ params:{start:0, limit: this.pageSize}, scope: this });
    }

});

Ext.com.remedi.rmn.components.MensajesForm = Ext.extend(Ext.FormPanel, {

    parentWin: null,

    initComponent : function() {

        this.cbDest_tipo = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.MensajesTipoRadioData(),
            displayField:'description', valueField : 'code', hiddenName : 'dest_tipo', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 120, allowBlank: false,
            emptyText:'<s:text name="appbs.form.combo.select"/>',
            fieldLabel:"<s:text name='mensajes.dest_tipo'/>",
            listeners: {
                scope: this,
                'select': function (combo, record, index) {
                    if (record.data.code == "C") {
                        this.adminGrid.hide();
                        this.clientesGrid.show();
                    } else {
                        this.clientesGrid.hide();
                        this.adminGrid.show();
                    }
                }
            }
        });

        this.adminGrid = new Ext.grid.GridPanel({
            store: new Ext.com.remedi.rmn.components.UsuariosAdminData(),
            height: 150, autoScroll:true, frame:false,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='radiologos.nombre'/>", sortable: true, dataIndex: 'nombre' }
            ],
            sm: new Ext.grid.RowSelectionModel({ singleSelect: false })
        });
        this.clientesGrid = new Ext.grid.GridPanel({
            store: new Ext.com.remedi.rmn.components.ClientesData(),
            height: 150, autoScroll:true, frame:false, hidden: true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='clientes.nombre'/>", sortable: true, dataIndex: 'nombre' }
            ],
            sm: new Ext.grid.RowSelectionModel({ singleSelect: false })
        });

        var config = {
            region: 'south',
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            items:[
                { xtype: 'hidden', name: 'org_tipo' },
                { xtype: 'hidden', name: 'origen' },
                {
                    xtype:'fieldset', title: "<s:text name='mensajes.panel.dest'/>", collapsible: false, autoHeight:true,
                    items :[
                        this.cbDest_tipo,
                        this.clientesGrid,
                        this.adminGrid
                    ]
                },
                {
                    xtype:'fieldset', title: "<s:text name='mensajes.panel.msg'/>", collapsible: false, autoHeight:true, labelWidth: 50,
                    items :[
                        { fieldLabel:"<s:text name='mensajes.asunto'/>", name: 'asunto', allowBlank:false, xtype: 'textfield', width: 540 },
                        { fieldLabel:"<s:text name='mensajes.texto'/>", name: 'texto', allowBlank:false, xtype: 'htmleditor', width: 540, height: 135  }
                    ]
                }
            ],
            buttons:[
                {
                    xtype: 'tbbutton',
                    icon: 'images/email_go.png',
                    text: "<s:text name='mensajes.btn.send'/>",
                    scope: this,
                    handler: function() {
                        if (this.getForm().isValid()) {
                            var texto = this.form.findField("texto").getValue();
                            if (!texto || texto == '<br>') {
                                Ext.example.msg("<s:text name='app.form.err.title'/>", "<s:text name='mensajes.msg.texto'/>");
                                this.form.findField("texto").markInvalid();
                                return;
                            }
                            var dest_tipo =  this.cbDest_tipo.getValue();
                            var selection, i;
                            var destinatarios = "";
                            if (dest_tipo == "A") {
                                selection = this.adminGrid.getSelectionModel().getSelections();
                                for (i = 0; i < selection.length; i++)
                                    destinatarios += selection[i].data.id_usuario+",";
                            } else {
                                selection = this.clientesGrid.getSelectionModel().getSelections();
                                for (i = 0; i < selection.length; i++)
                                    destinatarios += selection[i].data.id_cliente+",";
                            }
                            if (destinatarios == "") {
                                Ext.example.msg("<s:text name='app.form.err.title'/>", "<s:text name='mensajes.msg.dest'/>");
                                return;
                            }
                            mainDiv.mask("<s:text name='appbs.wait'/>");
                            Ext.Ajax.request({
                                url: appPath+'/Mensajes.action?action=save',
                                params: {
                                    origen: this.form.findField("origen").getValue(),
                                    org_tipo: this.form.findField("org_tipo").getValue(),
                                    dest_tipo: dest_tipo,
                                    asunto: this.form.findField("asunto").getValue(),
                                    texto: texto,
                                    destinatarios: destinatarios,
                                    dataToSave: 'send'
                                },
                                scope: this,
                                success: function(response) {
                                    var params = getActionResponse(response);
                                    if (params.success) {
 	                                    mainDiv.unmask();
                                        writeUserLog(<%= Constants.USER_LOG_MESG_NEW %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, destinatarios, dest_tipo);
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
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.MensajesForm.superclass.initComponent.call(this);
        this.adminGrid.store.load();
        this.clientesGrid.store.load();
        this.cbDest_tipo.setValue("A");
    }
});

</script>
