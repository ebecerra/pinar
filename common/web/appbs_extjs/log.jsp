<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * Mantenimiento de "APPBS_LOG"
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_logData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_logData.superclass.constructor.call(this, Ext.apply({
            remoteSort: true,
            autoDestroy: true,
            url: appPath+'/Log.action',
            root:"elements",
            totalProperty: 'totalCount',
            idProperty: 'pg',
            fields: [
                "log", "nivel", "id_aplicacion", "tipo", { name:"fecha", type: 'date', dateFormat: 'c' }
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_logGrid = Ext.extend(Ext.grid.EditorGridPanel, {

    pageSize: 25,

    initComponent : function() {
        // DataStore
        this.appbs_logData = new Ext.com.befasoft.common.components.Appbs_logData();
        this.filterLogNivel = new Ext.form.ComboBox({
            width:100, maxHeight:200,
            name:"tipo", typeAhead: true, editable: false, triggerAction: 'all', forceSelection:true,
            store:[ [0, '<s:text name="appbs_log.nivel.0"/>'], [1, '<s:text name="appbs_log.nivel.1"/>'], [2, '<s:text name="appbs_log.nivel.2"/>'], [3, '<s:text name="appbs_log.nivel.3"/>'], [4, '<s:text name="appbs_log.nivel.4"/>'] ]
        });
        this.filterLogTipo = new Ext.form.TextField({ name:'tipo', width: 120, value:'' });
        this.filterLogFecha = new Ext.form.DateField({ name:'fecha', width: 90, value:'' });

        var config = {
            region: 'center',
            store: this.appbs_logData,
            enableDragDrop   : true,
            autoScroll:true,
            ddGroup: 'depGridDD',
            columns: [
                {
                    header:"", sortable: true, dataIndex: 'nivel', width:5,
                    renderer: function (value) {
                        switch (value) {
                            case 1: return '<img src="'+appBasicPath+'/images/cancel.png" title="<s:text name="appbs_log.nivel.1"/>">';
                            case 2: return '<img src="'+appBasicPath+'/images/warning.gif" title="<s:text name="appbs_log.nivel.2"/>">';
                            case 3: return '<img src="'+appBasicPath+'/images/information.png" title="<s:text name="appbs_log.nivel.3"/>">';
                            case 4: return '<img src="'+appBasicPath+'/images/topic.gif" title="<s:text name="appbs_log.nivel.4"/>">';
                        }
                        return value;
                    }
                },
                { header:"<s:text name='appbs_log.fecha'/>", sortable: true, dataIndex: 'fecha', width:20, renderer: formatDateTime },
                { header:"<s:text name='appbs_log.tipo'/>", sortable: true, dataIndex: 'tipo', width:20 },
                { header:"<s:text name='appbs_log.datos'/>", sortable: true, dataIndex: 'log', width:55 }
            ],
            viewConfig: {
                forceFit: true
            },
            frame:false,

            bbar: new Ext.PagingToolbar({
                pageSize: this.pageSize,
                store: this.appbs_logData,
                displayInfo: true,
                displayMsg: "<s:text name='appbs_log.pg_info'/>",
                emptyMsg: "<s:text name='appbs_log.pg_none'/>"
            }),

            sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),

            tbar: new Ext.Toolbar({
                frame:true,
                height:35,
                items: [
                    '&nbsp;&nbsp;<s:text name="appbs_log.nivel"/>:&nbsp;', this.filterLogNivel,
                    '&nbsp;&nbsp;<s:text name="appbs_log.tipo"/>:&nbsp;', this.filterLogTipo,
                    '&nbsp;&nbsp;<s:text name="appbs_log.fecha"/>:&nbsp;', this.filterLogFecha,
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/lupa.gif',
                        text: '<s:text name="appbs.form.filter"/>',
                        scope: this,
                        handler: function() {
                            this.appbs_logData.setBaseParam('nivel', this.filterLogNivel.getValue());
                            this.appbs_logData.setBaseParam('tipo', this.filterLogTipo.getValue());
                            this.appbs_logData.setBaseParam('fecha', this.filterLogFecha.getValue());
                            this.updateData();
                        }
                    }
                ]
            })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_logGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.appbs_logData.load({ params: { start: 0, limit: this.pageSize }, callback: dataSourceLoad });
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appbs_log
//------------------------------------------------------------------------
function openTabLog(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.common.components.Appbs_logGrid();
        grid.updateData();
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[grid]
        });
        tabs.add(tab);
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}

</script>
