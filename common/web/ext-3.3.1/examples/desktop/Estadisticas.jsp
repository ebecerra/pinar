<%@ page import="com.befasoft.common.model.appbs.APPBS_USUARIOS_LOG" %>
<%@ page import="com.remedi.rmn.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * Estadisticas "RADIOLOGOS"
 */

Ext.ns('Ext.com.remedi.rmn.components');

Ext.com.remedi.rmn.components.StatisticPanel = Ext.extend(Ext.Panel, {
    year: null,
    month: null,
    month_name: null,
    id_cliente: null,
    centro: null,

    initComponent : function() {
        this.cbYears = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.EstudiosYearsData(),
            displayField:'nom', valueField : 'cod', hiddenName : 'year', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width: 90,
            emptyText:'<s:text name="appbs.form.combo.select"/>',
            listeners: {
                scope: this,
                'select': function () {
                    this.reloadData(this.cbYears.getValue());
                }
            }
        });

        this.tipoData = new Ext.com.remedi.rmn.components.StatisticData({ url: appPath+'/Estadisticas.action?action=listByTipo' } );
        this.chartTipo = new Ext.com.remedi.rmn.components.StatisticPieChart({ region: 'center', dataStore: this.tipoData });
        this.gridTipo = new Ext.com.remedi.rmn.components.StatisticGrid({
            region: 'west', width: 250, colcaption: "<s:text name='estadisticas.tipo'/>",
            linkedPanel: this, linkedId: "TIPO", linkedGraph: this.chartTipo, dataStore: this.tipoData
        });

        this.mesData = new Ext.com.remedi.rmn.components.StatisticData({ url: appPath+'/Estadisticas.action?action=listByMonth' } );
        this.chartMes = new Ext.com.remedi.rmn.components.StatisticColumnChart({ region: 'center', graphType: 'column', dataStore: this.mesData });
        this.gridMes = new Ext.com.remedi.rmn.components.StatisticGrid({
            region: 'west', width: 250, colcaption: "<s:text name='estadisticas.mes'/>",
            linkedPanel: this, linkedId: "MES", linkedGraph: this.chartMes, dataStore: this.mesData
        });

        this.timeData = new Ext.com.remedi.rmn.components.StatisticData({ url: appPath+'/Estadisticas.action?action=listByTime' } );
        this.chartTime = new Ext.com.remedi.rmn.components.StatisticPieChart({ region: 'center', dataStore: this.timeData });
        this.gridTime = new Ext.com.remedi.rmn.components.StatisticTiempoRangeGrid({ region: 'west', width: 250, linkedGraph: this.chartTime, dataStore: this.timeData });

        var config = {
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            layout:'form',
            items:[
                {
                    id: 'idPanelTipo', xtype: 'panel', layout: 'border', height: 350, title: "<s:text name="estadisticas.caption.tipos"/>",
                    items: [this.gridTipo, this.chartTipo]
                }, {
                    id: 'idPanelTipoMeses', xtype: 'panel', layout: 'border', height: 330, title: "<s:text name="estadisticas.caption.mes"/>",
                    items: [this.gridMes, this.chartMes]
                }, {
                    id: 'idPanelTiempo', xtype: 'panel', layout: 'border', height: 330, title: "<s:text name="estadisticas.caption.time_res"/>",
                    items: [this.chartTime, this.gridTime]
                }
            ],
            tbar: new Ext.Toolbar({
                items: [
                    "<s:text name='estudios.search.year'/>: ",
                    this.cbYears
                ]
            })
        };
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.StatisticPanel.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.cbYears.store.load({
            scope: this,
            callback: function(r) {
                if (r.length > 0)
                var year = r[0].data.cod;
                this.cbYears.setValue(year);
                this.reloadData(year);
            }
        });
    },

    reloadData: function (year) {
        writeUserLog(<%= Constants.USER_LOG_EST_RAD_ESTUDIOS %>, <%= APPBS_USUARIOS_LOG.LEVEL_INFO %>, year);
        this.year = year;
        this.month = null;
        this.gridTipo.updateData(year, this.month);
        this.gridMes.updateData(year, this.month, null, null, null, <s:property value="#session.user.id_radiologo"/>, null);
        this.findById("idPanelTipo").setTitle("<s:text name="estadisticas.caption.tipos"/>");
        this.findById("idPanelTipoMeses").setTitle("<s:text name="estadisticas.caption.mes"/>");
    },

    rowSelect: function (id, record) {
        if (id == 'MES') {
            this.month = record.data.id;
            this.month_name = record.data.name;
            this.gridMes.updateData(this.year, null, null, null, null, <s:property value="#session.user.id_radiologo"/>, null);
            this.gridTipo.updateData(this.year, this.month);
            this.timeData.load({ params: { year: this.year, month: this.month } });
            this.findById("idPanelTipoMeses").setTitle("<s:text name="estadisticas.caption.mes"/>");
            this.findById("idPanelTipo").setTitle("<s:text name="estadisticas.caption.tipos"/>"+" - "+record.data.name);
            this.findById("idPanelTiempo").setTitle("<s:text name="estadisticas.caption.time_res"/>"+" - "+record.data.name);
        } else if (id == 'TIPO') {
            this.gridMes.updateData(this.year, null, null, null, null, <s:property value="#session.user.id_radiologo"/>, record.data.id);
            this.gridTipo.updateData(this.year);
            this.findById("idPanelTipoMeses").setTitle("<s:text name="estadisticas.caption.mes"/>"+" - "+record.data.name);
            this.findById("idPanelTipo").setTitle("<s:text name="estadisticas.caption.tipos"/>");
        }
    }

});

</script>
