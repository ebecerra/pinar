<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPBS_USUARIOS_LOG" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_usuarios_logData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_usuarios_logData.superclass.constructor.call(this, Ext.apply({
            remoteSort: true,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Usuarios_log.action',
            fields: [
                "id_usuario", "nivel", "id_tipo", "descripcion", "usuario",
                { name:"fecha", type: 'date', dateFormat: 'c' }
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_usuarios_logLevelData = Ext.extend(Ext.data.SimpleStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_usuarios_logLevelData.superclass.constructor.call(this, Ext.apply({
            fields: ['code', 'description'],
            data : [
                [1, "<s:text name='appbs_usuarios.log_level.1'/>"], [2, "<s:text name='appbs_usuarios.log_level.2'/>"],
                [3, "<s:text name='appbs_usuarios.log_level.3'/>"], [4, "<s:text name='appbs_usuarios.log_level.4'/>"],
                [5, "<s:text name='appbs_usuarios.log_level.5'/>"]
            ]
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_usuarios_logGrid = Ext.extend(Ext.grid.GridPanel, {

    pageSize: 25,

    initComponent : function() {
        // DataStore
        this.appbs_usuarios_logData = new Ext.com.befasoft.common.components.Appbs_usuarios_logData();

        this.id_usuarioFilter = new Ext.form.TextField({ name:'id_usuario', width: 80, value:'' });
        this.cbNivelFilter = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_usuarios_logLevelData(),
            displayField:'description', valueField : 'code', hiddenName : 'nivel', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true, width:140,
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });
        this.cbTipoFilter = new Ext.form.ComboBox({
            store: new Ext.com.befasoft.common.components.Appbs_usuarios_log_legendData(),
            displayField:'nombre', valueField : 'id_tipo', hiddenName : 'id_tipo', typeAhead: true, mode: 'local',
            triggerAction: 'all', forceSelection:true, selectOnFocus:true,
            emptyText:'<s:text name="appbs.form.combo.select"/>'
        });

        this.fec_fromFilter = new Ext.form.DateField({ name:'fec_from', width: 80, value:'' });
        this.fec_toFilter = new Ext.form.DateField({ name:'fec_from', width: 80, value:'' });

        var config = {
            region: 'center',
            store: this.appbs_usuarios_logData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                {
                    header:"", sortable: true, dataIndex: 'nivel', width:5,
                    renderer: function (value) {
                        switch (value) {
                            case 1: return '<img src="'+appBasicPath+'/images/stop.gif" title="<s:text name="appbs_usuarios.log_level.1"/>">';
                            case 2: return '<img src="'+appBasicPath+'/images/warning2.gif" title="<s:text name="appbs_usuarios.log_level.2"/>">';
                            case 3: return '<img src="'+appBasicPath+'/images/information2.gif" title="<s:text name="appbs_usuarios.log_level.3"/>">';
                            case 4: return '<img src="'+appBasicPath+'/images/information.gif" title="<s:text name="appbs_usuarios.log_level.4"/>">';
                            case 5: return '<img src="'+appBasicPath+'/images/topic.gif" title="<s:text name="appbs_usuarios.log_level.5"/>">';
                        }
                        return value;
                    }
                },
                { header:"<s:text name='appbs_usuarios_log.fecha'/>", sortable: true, dataIndex: 'fecha', width: 15, renderer: formatDateTime },
                { header:"<s:text name='appbs_usuarios_log.id_usuario'/>", sortable: true, dataIndex: 'id_usuario', width: 10 },
                { header:"<s:text name='appbs_usuarios_log.usuario'/>", sortable: true, dataIndex: 'usuario', width: 15 },
                { header:"<s:text name='appbs_usuarios_log.log'/>", sortable: true, dataIndex: 'descripcion', width: 55 }
            ],
            tbar: new Ext.Toolbar({
                frame:true,
                height:30,
                items: [
                    '&nbsp;&nbsp;',
                    ' <s:text name="appbs_usuarios_log.nivel"/>: ', this.cbNivelFilter,
                    '&nbsp;&nbsp;',
                    ' <s:text name="appbs_usuarios_log.id_tipo"/>: ', this.cbTipoFilter,
                    '&nbsp;&nbsp;',
                    ' <s:text name="appbs_usuarios_log.id_usuario"/>: ', this.id_usuarioFilter,
                    '&nbsp;&nbsp;',
                    ' <s:text name="appbs_usuarios_log.fecha"/>: ', this.fec_fromFilter,
                    '&nbsp;-&nbsp;', this.fec_toFilter,

                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/lupa.gif',
                        text: "<s:text name='appbs.form.filter'/>",
                        scope: this,
                        handler: function() {
                            this.store.setBaseParam('id_usuario', this.id_usuarioFilter.getValue());
                            this.store.setBaseParam('nivel', this.cbNivelFilter.getValue());
                            this.store.setBaseParam('id_tipo', this.cbTipoFilter.getValue());
                            this.store.setBaseParam('fec_from', this.fec_fromFilter.getValue());
                            this.store.setBaseParam('fec_to', this.fec_toFilter.getValue());
                            this.store.load();
                        }
                    }
                ]
            }),
            bbar: new Ext.PagingToolbar({
                pageSize: this.pageSize,
                store: this.appbs_usuarios_logData,
                displayInfo: true,
                displayMsg: "<s:text name="appbs_usuarios_log.pg_info"/>",
                emptyMsg: "<s:text name="appbs_usuarios_log.pg_none"/>"
            }),
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_usuarios_logGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.cbTipoFilter.store.load();
        this.appbs_usuarios_logData.setDefaultSort("fecha", "asc");
        this.appbs_usuarios_logData.load({ params:{start:0, limit: this.pageSize} });
    }

});

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de Appbs_usuarios_log
//------------------------------------------------------------------------
function openTabAppbs_usuarios_log(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(idMenu))) {
        var grid = new Ext.com.befasoft.common.components.Appbs_usuarios_logGrid();
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
        grid.updateData();
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}
</script>
