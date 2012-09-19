<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

MyDesktop = new Ext.app.App({
	init :function(){
		Ext.QuickTips.init();
	},

	getModules : function(){
		return [
			new MyDesktop.EstudiosWindow(),
            new MyDesktop.CalendarioWindow(),
            new MyDesktop.FacturacionWindow(),
            new MyDesktop.DescargasWindow(),
            new MyDesktop.SoporteWindow(),
            new MyDesktop.EstadisticasWindow(),
            new MyDesktop.MensajeWindow(),
            new MyDesktop.PlantillasWindow()
		];
	},

    // config for the start menu
    getStartConfig : function(){
        return {
            title: '<s:property value="#session.user.nombre"/>',
            iconCls: 'user',
            toolItems: [{
                text:'<s:text name="desktop.menu.password"/>',
                iconCls:'settings',
                scope:this,
                handler: function () { formPassword(); }
            },'-',{
                text:'<s:text name="appbs.form.logout"/>',
                iconCls:'logout',
                scope:this,
                handler: function () { loginOut(); }
            }]
        };
    }
});

//----------------------------------------------------------------
// Ventana de descargas
//----------------------------------------------------------------
MyDesktop.DescargasWindow = Ext.extend(Ext.app.Module, {

    id:'descargas-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.descargas"/>',
            iconCls:'descargas',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('descargas-win');
        if(!win){
            win = desktop.createWindow({
                id: 'descargas-win',
                title:'<s:text name="desktop.menu.descargas"/>',
                width:740,
                height:480,
                iconCls: 'descargas',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,

                layout: 'fit',
                items: [ { id: 'id-descargas-panel', xtype: 'panel', autoScroll: true } ]
            });
            var tpl = Ext.Template.from('dDescargas', {
                compiled:true,
                getBody : function(v, all) {
                    return Ext.util.Format.stripScripts(v || all.description);
                }
            });
            win.findById('id-descargas-panel').update(tpl.apply(null));
        }
        win.show();
    }
});

//----------------------------------------------------------------
// Ventana de soporte
//----------------------------------------------------------------
MyDesktop.SoporteWindow = Ext.extend(Ext.app.Module, {

    id:'soporte-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.soporte"/>',
            iconCls:'soporte',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('soporte-win');
        if(!win){
            win = desktop.createWindow({
                id: 'soporte-win',
                title:'<s:text name="desktop.menu.soporte"/>',
                width:740,
                height:480,
                iconCls: 'soporte',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,

                layout: 'fit',
                items: [ { id: 'id-soporte-panel', xtype: 'panel', autoScroll: true } ]
            });
            var tpl = Ext.Template.from('dSoporte', {
                compiled:true,
                getBody : function(v, all) {
                    return Ext.util.Format.stripScripts(v || all.description);
                }
            });
            win.findById('id-soporte-panel').update(tpl.apply(null));
        }
        win.show();
    }
});

//----------------------------------------------------------------
// Ventana de mensajes
//----------------------------------------------------------------
MyDesktop.MensajeWindow = Ext.extend(Ext.app.Module, {

    id:'mensajes-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.mensajes"/>',
            iconCls:'mensajes',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('mensajes-win');
        if(!win){
            var panel = new Ext.com.remedi.rmn.components.MensajesPanel();
            var grid = new Ext.com.remedi.rmn.components.MensajesGrid({ linkedPanel: panel });
            panel.linkedGrid = grid;
            win = desktop.createWindow({
                id: 'mensajes-win',
                title:'<s:text name="desktop.menu.mensajes"/>',
                width:800,
                height:540,
                iconCls: 'mensajes',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,
                layout: 'border',
                items: [ grid, panel ]
            });
            grid.updateData();
        }
        win.show();
    }
});

//----------------------------------------------------------------
// Ventana de Estudios
//----------------------------------------------------------------
MyDesktop.EstudiosWindow = Ext.extend(Ext.app.Module, {

    id:'estudios-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.estudios"/>',
            iconCls:'estudios',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('estudios-win');
        if(!win){
            var estudios = new Ext.com.remedi.rmn.components.EstudiosGrid();
            win = desktop.createWindow({
                id: 'estudios-win',
                title:'<s:text name="desktop.menu.estudios"/>',
                width:850,
                height:600,
                iconCls: 'estudios',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,
                layout: 'border',
                items: [ estudios  ]
            });
            win.estudios = estudios;
            estudios.initFilters();
            estudios.startAutoLoad();
        }
        win.show();
        win.on(
            'close',
            function () {
                win.estudios.stopAutoLoad();
            }
        );
    }
});

//----------------------------------------------------------------
// Ventana de Calendario
//----------------------------------------------------------------
MyDesktop.CalendarioWindow = Ext.extend(Ext.app.Module, {

    id:'calendario-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.calendario"/>',
            iconCls:'calendario',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('calendario-win');
        if(!win){
            var calendar = new Ext.com.remedi.rmn.components.CalendarioPanel();
            win = desktop.createWindow({
                id: 'calendario-win',
                title:'<s:text name="desktop.menu.calendario"/>',
                width:810,
                height:550,
                iconCls: 'calendario',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,

                layout: 'fit',
                items: [ calendar ]
            });
            calendar.cbYear.store.load({
                scope: this,
                callback: function () {
                    if (calendar.cbYear.store.getCount() > 0) {
                        calendar.cbYear.setValue(calendar.cbYear.store.getAt(0).get("cod"));
                        calendar.setCalendar();
                    }
                }
            });
        }
        win.show();
    }
});

//----------------------------------------------------------------
// Ventana de Facturacion
//----------------------------------------------------------------
MyDesktop.FacturacionWindow = Ext.extend(Ext.app.Module, {

    id:'facturacion-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.facturacion"/>',
            iconCls:'facturacion',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('facturacion-win');
        if(!win){
            var facturacion = new Ext.com.remedi.rmn.components.Radiologo_facturasGrid({ radioSelect: false });
            win = desktop.createWindow({
                id: 'facturacion-win',
                title:'<s:text name="desktop.menu.facturacion"/>',
                width:810,
                height:550,
                iconCls: 'facturacion',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,

                layout: 'fit',
                items: [ facturacion ]
            });
            facturacion.updateData();
        }
        win.show();
    }
});

//----------------------------------------------------------------
// Ventana de Estadisticas
//----------------------------------------------------------------
MyDesktop.EstadisticasWindow = Ext.extend(Ext.app.Module, {

    id:'estadisticas-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.estadisticas"/>',
            iconCls:'estadisticas',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('estadisticas-win');
        if(!win){
            var stats = new Ext.com.remedi.rmn.components.StatisticPanel();
            win = desktop.createWindow({
                id: 'estadisticas-win',
                title:'<s:text name="desktop.menu.estadisticas"/>',
                width:740,
                height:480,
                iconCls: 'estadisticas',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,

                layout: 'fit',
                items: [ stats ]
            });
            stats.updateData();
        }
        win.show();
    }
});

//----------------------------------------------------------------
// Ventana de plantillas
//----------------------------------------------------------------
MyDesktop.PlantillasWindow = Ext.extend(Ext.app.Module, {

    id:'plantillas-win',
    init : function(){
        this.launcher = {
            text: '<s:text name="desktop.menu.plantillas"/>',
            iconCls:'plantillas',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('plantillas-win');
        if(!win){
            var plantillas = new Ext.com.remedi.rmn.components.PlantillasMantPanel();
            win = desktop.createWindow({
                id: 'plantillas-win',
                title:'<s:text name="desktop.menu.plantillas"/>',
                width:740,
                height:480,
                iconCls: 'plantillas',
                shim:false,
                animCollapse:false,
                border:false,
                constrainHeader:true,

                layout: 'fit',
                items: [ plantillas ]
            });
            plantillas.updateData();
        }
        win.show();
    }
});

</script>
