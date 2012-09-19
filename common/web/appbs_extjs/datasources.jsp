<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * DataSources del COMMON
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_usuariosData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_usuariosData.superclass.constructor.call(this, Ext.apply({
            remoteSort: true,
            autoDestroy: true,
            url: appPath+'/Usuarios.action',
            root:"elements",
            totalProperty: 'totalCount',
            fields: [
                "id_usuario", "clave", "fax", "login", {type:'date', name:"fecha_creacion"}, {type:'date', name:"fecha_conexion"},
                "activo", "nombre", "email", "tipo", "cookie", "telefono", "movil", "log_level", "id_perfil", "tipo_nombre",
                "param_1",  "param_2", "param_3", "param_4", "int_param_1", "int_param_2", "int_param_3", "int_param_4"
            ],
            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_Tipos_usuariosData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_Tipos_usuariosData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            url: appPath+'/Usuarios_tipos.action',
            root:"elements",
            totalProperty: 'totalCount',
            fields: ["nombre", "id_tipo"],
            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_LoglevelData = Ext.extend(Ext.data.SimpleStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_LoglevelData.superclass.constructor.call(this, Ext.apply({
            fields: ['code', 'description'],
            data : [
                [1, "<s:text name='appbs_usuarios.log_level.1'/>"], [2, "<s:text name='appbs_usuarios.log_level.2'/>"],
                [3, "<s:text name='appbs_usuarios.log_level.3'/>"], [4, "<s:text name='appbs_usuarios.log_level.4'/>"],
                [5, "<s:text name='appbs_usuarios.log_level.5'/>"]
            ]
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_perfilesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_perfilesData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            url: appPath+'/Perfiles.action',
            root:"elements",
            totalProperty: 'totalCount',
            fields: [
                "nombre", "tipo", "id_perfil"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

</script>