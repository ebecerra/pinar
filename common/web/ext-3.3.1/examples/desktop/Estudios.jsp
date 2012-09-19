<%@ page import="com.remedi.rmn.Constants" %>
<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "ESTUDIOS" (Start)
 */

Ext.ns('Ext.com.remedi.rmn.components');

Ext.com.remedi.rmn.components.EstudiosGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    pageSize: 25,
    loaded: false,

    initComponent : function() {
        this.estudiosData = new Ext.com.remedi.rmn.components.EstudiosData();

        this.cbYears = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.EstudiosYearsData(),
            displayField:'nom', valueField : 'cod', hiddenName : 'year', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 90,
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });
        this.cbMes = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.MesesData(),
            displayField:'description', valueField : 'code', hiddenName : 'month', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 90,
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });
        this.cbEstado = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.EstadosEstudioData(),
            displayField:'description', valueField : 'code', hiddenName : 'estado', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 90,
            fieldLabel:"<s:text name='estudios.search.estado'/>",
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });
        this.textMedico = new Ext.form.TextField({ name: "medico", allowBlank: true });

        this.pgEstudios = new Ext.PagingToolbar({
            pageSize: this.pageSize,
            store: this.estudiosData,
            displayInfo: true,
            displayMsg: "<s:text name="estudios.pg_info"/>",
            emptyMsg: "<s:text name="estudios.pg_none"/>"
        });

        var config = {
            region: 'center',
            store: this.estudiosData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            plugins: new Ext.ux.grid.RowExpander({
                tpl : new Ext.XTemplate(
                    '<table border="0" cellpadding="0" cellspacing="2">'+
                        '<tr>'+
                            '<td class="estudios_text"><s:text name='estudios.id_estudio'/>:</td><td class="estudios_id">{id_estudio}</td>'+
                            '<td class="estudios_text"><s:text name='estudios.tec_comentarios2'/>:</td><td class="estudios_value"><tpl if="tec_comentarios">{tec_comentarios}</tpl></td>'+
                        '</tr><tr>'+
                            '<td class="estudios_text"><s:text name='estudios.fecha'/>:</td><td class="estudios_value">{fecha:date("d/m/Y g:i a")}</td>'+
                            '<td class="estudios_text"><s:text name='estudios.study_comments'/>:</td><td class="estudios_value"><tpl if="study_comments">{study_comments}</tpl></td>'+
                        '</tr><tr>'+
                            '<td class="estudios_text"><s:text name='estudios.study_date'/>:</td><td class="estudios_value">{study_date:date("d/m/Y")}</td>'+
                            '<td class="estudios_text"><s:text name='estudios.id_centro'/>:</td><td class="estudios_value">{centro}</td>'+
                        '</tr><tr>'+
                            '<td class="estudios_text"><s:text name='estudios.equipo'/>:</td><td class="estudios_value">{equipo}</td>'+
                            '<td class="estudios_text"><s:text name='estudios.operator_name'/>:</td><td class="estudios_value"><tpl if="operator_name">{operator_name}</tpl></td>'+
                        '<tr>'+
                    '</table>'
                )
            }),
            columns: [
                new Ext.ux.grid.RowExpander(),
                { header:"<s:text name='estudios.acciones'/>", sortable: false, dataIndex: 'stored_files', width: 10, renderer: renderEstudiosAcciones },
                { header:"<s:text name='estudios.id_estudio'/>", sortable: true, dataIndex: 'id_estudio', width: 10 },
                { header:"<s:text name='estudios.study_description'/>", sortable: true, dataIndex: 'study_description', width: 50 },
                { header:"<s:text name='estudios.estado'/>", sortable: true, dataIndex: 'estado', width: 10, renderer: renderEstudiosEstado },
                { header:"<s:text name='estudios.fecha'/>", sortable: true, dataIndex: 'fecha', renderer: formatDate, width: 10 },
                { header:" ", sortable: false, dataIndex: 'urgente', width: 10, renderer: renderEstudiosIconos }
            ],
            viewConfig: {
                forceFit: true
            },
            frame:false,
            tbar: new Ext.Toolbar({
                frame:true,
                height:30,
                items: [
                    '&nbsp;&nbsp;',
                    ' <s:text name="estudios.search.year"/>: ', this.cbYears,
                    '&nbsp;&nbsp;',
                    ' <s:text name="estudios.search.month"/>: ', this.cbMes,
                    '&nbsp;&nbsp;',
                    '<s:text name='estudios.search.estado'/>', this.cbEstado,
                    '&nbsp;&nbsp;',
                    '<s:text name='estudios.search.medico'/>', this.textMedico,
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: 'images/lupa.gif',
                        text: "<s:text name='appbs.form.filter'/>",
                        scope: this,
                        handler: function() {
                            this.doFilter();
                        }
                    }
                ]
            }),
            bbar:this.pgEstudios,
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),
            listeners: {
                scope: this,
                'rowdblclick': function(grid, rowIndex, e) {
                    var currentRecord = grid.getStore().getAt(rowIndex);
                    writeUserLog(<%= Constants.USER_LOG_ESTUDIOS_LOAD %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, currentRecord.data.id_estudio);
                    var win = new Ext.com.remedi.rmn.components.EstudiosWin({ linkedGrid: this });
                    win.loadRecord(currentRecord);
                    win.show();
                }
            }
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.EstudiosGrid.superclass.initComponent.call(this);
        this.estudiosData.setDefaultSort("id_estudio", "desc");
        this.estudiosData.setBaseParam("id_radiologo", <s:property value="#session.user.id_radiologo"/>);
    },

    initFilters: function () {
        this.cbYears.store.load({
            scope: this,
            callback: function (r) {
                if (r.length > 0)
                    this.cbYears.setValue(r[0].data.cod);
                var today = new Date();
                this.cbMes.setValue(today.getMonth()+1);
                this.doFilter();
            }
        });
    },

    doFilter: function () {
        this.estudiosData.setBaseParam("anyo", this.cbYears.getValue());
        this.estudiosData.setBaseParam("mes", this.cbMes.getValue());
        this.estudiosData.setBaseParam("estado", this.cbEstado.getValue());
        this.estudiosData.setBaseParam("medico", this.textMedico.getValue());
        this.updateData();
        this.loaded = true;
    },

    updateData: function () {
        this.estudiosData.load({ params:{start:0, limit: this.pageSize}, callback: dataSourceLoad });
    },

    startAutoLoad: function () {
        currentEstudiosGrid = this;
        this.task = {
            run: function() {
                if (currentEstudiosGrid.loaded)
                    currentEstudiosGrid.pgEstudios.doRefresh();
            },
            interval: <%= Constants.RADIO_RELOAD_ESTUDIOS %>
        };
        Ext.TaskMgr.start(this.task);
    },

    stopAutoLoad: function () {
        Ext.TaskMgr.stop(this.task);
    }
});


Ext.com.remedi.rmn.components.EstudiosForm = Ext.extend(Ext.FormPanel, {

    parentWin: null,
    id_estudio: null,

    initComponent : function() {

        this.panelInfo = new Ext.Panel({
            tpl: new Ext.XTemplate(
                '<table border="0" cellpadding="0" cellspacing="2" width="100%">'+
                        '<tpl if="urgente || urgente2">'+
                            '<tr>'+
                                '<td class="estudios_text"><tpl if="urgente"><img src="iconos/urgent.png" width="20"></tpl><tpl if="urgente2"><img src="iconos/urgent2.png" width="20"></tpl></td><td colspan="3" class="estudios_urgente">{urg_motivo}</td>'+
                            '</tr>'+
                        '</tpl>'+
                    '<tr>'+
                        '<td class="estudios_text"><s:text name='estudios.id_estudio'/>:</td><td class="estudios_id">{id_estudio}</td>'+
                        '<td class="estudios_text"><s:text name='estudios.study_instanceuid'/>:</td><td class="estudios_value">{study_instanceuid}</td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text"><s:text name='estudios.fecha'/>:</td><td class="estudios_value">{fecha:date("d/m/Y g:i a")}</td>'+
                        '<td class="estudios_text"><s:text name='estudios.id_centro'/>:</td><td class="estudios_value">{centro}</td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text"><s:text name='estudios.study_date'/>:</td><td class="estudios_value">{study_date:date("d/m/Y")}</td>'+
                        '<td class="estudios_text"><s:text name='estudios.referring_physician_name'/>:</td><td class="estudios_value"><tpl if="referring_physician_name">{referring_physician_name}</tpl></td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text"><s:text name='estudios.fec_inf_maxima'/>:</td><td class="estudios_value"><tpl if="fec_inf_maxima">{fec_inf_maxima:date("d/m/Y")}</tpl></td>'+
                        '<td class="estudios_text"><s:text name='estudios.paciente'/>:</td><td class="estudios_value">{id_paciente}</td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text"><s:text name='estudios.estado'/>:</td>'+
                        '<td>'+
                            '<tpl if="estado == 0"><span style="color:#f00"><s:text name="estudios.estado.re.0"/></span></tpl>'+
                            '<tpl if="estado == 1"><span style="color:#008080"><s:text name="estudios.estado.re.1"/></span></tpl>'+
                            '<tpl if="estado == 2"><span style="color:#008000"><s:text name="estudios.estado.re.2"/></span></tpl>'+
                            '<tpl if="estado == 3"><span style="color:#000080"><s:text name="estudios.estado.re.3"/></span></tpl>'+
                        '</td>'+
                        '<td class="estudios_text"><s:text name='estudios.radiologo'/>:</td><td class="estudios_value"><tpl if="radiologo">{radiologo}</tpl></td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text"><s:text name='estudios.equipo'/>:</td><td class="estudios_value">{equipo}</td>'+
                        '<td class="estudios_text"><s:text name='estudios.operator_name'/>:</td><td class="estudios_value"><tpl if="operator_name">{operator_name}</tpl></td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text" valign="middle"><s:text name='estudios.study_description'/>:</td>'+
                        '<td colspan="3" class="estudios_value"><tpl if="study_description">{study_description}</tpl></td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text" valign="middle"><s:text name='estudios.tec_comentarios2'/>:</td>'+
                        '<td colspan="3" class="estudios_value"><tpl if="tec_comentarios">{tec_comentarios}</tpl></td>'+
                    '</tr><tr>'+
                        '<td class="estudios_text" valign="middle"><s:text name='estudios.study_clinica'/>:</td>'+
                        '<td colspan="3" class="estudios_value"><tpl if="study_clinica">{study_clinica}</tpl></td>'+
                    '<tr>'+
                '</table>'
            )
        });
        this.cbFact = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.FacturacionEstudioData(),
            displayField:'description', valueField : 'code', hiddenName : 'facturacion', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 90,
            fieldLabel:"<s:text name='estudios.facturacion'/>",
            emptyText:'<s:text name="appbs.form.combo.select"/>',
            listeners: {
                scope: this,
                'select': function (combo, record, index) {
                    var component = this;
                    Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="estudios.msg.fact.confirm"/>",
                        function (btn) {
                            if (btn == "yes") {
                                Ext.Ajax.request({
                                    url: appPath+'/Estudios.action?action=changeFact',
                                    params: { id_estudio: component.id_estudio, facturacion: component.cbFact.getValue() },
                                    success: function(response) {
                                        var params = getActionResponse(response);
                                        if (params.success) {
                                            component.parentWin.linkedGrid.pgEstudios.doRefresh();
                                        } else
                                            showServerError(params);
                                    },
                                    failure: function() {
                                        Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                    }
                                });
                            }
                        }
                    );
                }
            }
        });

        var config = {
            region: 'south',
            frame:true,
            monitorValid:true,
            title:"<s:text name='estudios.tabs.detail'/>",
            items:[
                {
                    xtype:'fieldset', title: "<s:text name='estudios.tabs.detail.info'/>",
                    collapsible: false, autoHeight:true,
                    items :[ this.panelInfo ]
                }
            ],
            buttons:[
                "<s:text name='estudios.facturacion'/>",
                this.cbFact,
                {
                    text: "<s:text name='estudios.btn.com_tecnico'/>",
                    icon: 'images/information3.gif',
                    scope: this,
                    handler : function() {
                        var component = this;
                        Ext.MessageBox.show({
                            title: "<s:text name='estudios.btn.com_tecnico'/>",
                            msg: "<s:text name="estudios.comments"/>",
                            width:300,
                            buttons: Ext.MessageBox.OKCANCEL,
                            multiline: true,
                            fn: function (btn, text) {
                                if (btn == 'ok')
                                    sendComment(component.id_estudio, text, "com_tecnico", "R");
                            }
                        });
                    }
                },
                {
                    text: "<s:text name='estudios.btn.com_remedi'/>",
                    icon: 'images/information.gif',
                    scope: this,
                    handler : function() {
                        var component = this;
                        Ext.MessageBox.show({
                            title: "<s:text name='estudios.btn.com_remedi'/>",
                            msg: "<s:text name="estudios.comments"/>",
                            width:300,
                            buttons: Ext.MessageBox.OKCANCEL,
                            multiline: true,
                            fn: function (btn, text) {
                                if (btn == 'ok')
                                    sendComment(component.id_estudio, text, "com_remedi", "R");
                            }
                        });
                    }
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.EstudiosForm.superclass.initComponent.call(this);
    },

    sendComment: function (text, dest) {
        mainDiv.mask("<s:text name='appbs.wait'/>");
        Ext.Ajax.request({
            url: appPath+'/Estudios.action?action=sendComments',
            params: { id_estudio: this.id_estudio, nombre: "<s:property value="#session.user.nombre"/>", origen: "R", dest: dest, texto: text },
            success: function(response) {
                mainDiv.unmask();
                var params = getActionResponse(response);
                if (!params.success)
                    showServerError(params);
            },
            failure: function() {
                mainDiv.unmask();
                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
            }
        });
    }
});

Ext.com.remedi.rmn.components.EstudiosWin = Ext.extend(Ext.Window, {

    linkedGrid: null,
    record: null,

    initComponent : function() {
        this.editForm = new Ext.com.remedi.rmn.components.EstudiosForm({ parentWin : this });
        this.seriesPanel = new Ext.com.remedi.rmn.components.St_seriesPanel();
        this.informesGrid = new Ext.com.remedi.rmn.components.Estudio_informesGrid({ dicomViewer: false });

        var config = {
            layout:'fit',
            frame:true,
            closable: true,
            closeAction: 'hide',
            width:720,
            resizable: false,
            modal: true,
            title:"<s:text name='estudios.caption'/>",
            items: [
                {
                    xtype: 'tabpanel', activeTab: 0, enableTabScroll: true, resizeTabs: true, minTabWidth: 50, border: false, height:420,
                    items: [
                        this.informesGrid,
                        this.editForm,
                        this.seriesPanel
                    ]
                }
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.EstudiosWin.superclass.initComponent.call(this);
    },

    loadRecord: function (record) {
        this.record = record;
        this.editForm.getForm().loadRecord(record);
        this.editForm.cbFact.setValue(record.data.facturacion);
        this.editForm.id_estudio = record.data.id_estudio;
        this.editForm.panelInfo.data = record.data;
        this.informesGrid.updateData(record);
        this.seriesPanel.updateData(record.data.id_estudio);
    },

    getValues: function () {
        return this.editForm.getValues();
    }
});

//----------------------------------------------------------------
// Descarga el estudio
//----------------------------------------------------------------
function downloadEstudio(id_estudio) {
    var url = "<%= request.getContextPath() %>/Estudios.action?action=downloadEstudio&id_estudio="+id_estudio;
    window.open(url);
}

//----------------------------------------------------------------
// Muestra el ultimo informe del estudio
//----------------------------------------------------------------
function showLastInforme(id_estudio) {
    var win = new Ext.com.remedi.rmn.components.Estudio_informesWin({ linkedGrid: this });
    win.loadLast(id_estudio);
    win.show();
}

//----------------------------------------------------------------
// Abre el visor Dicom
//----------------------------------------------------------------
function openViewer(id_estudio) {
    var url = appPath + '/Dicom_files.action?action=open&id_estudio='+id_estudio;
    window.open(url, "dicom_viewer_"+id_estudio, "channelmode=1,fullscreen=1,location=0,menubar=0,scrollbars=0,status=0");
}

</script>
