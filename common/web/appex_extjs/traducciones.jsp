<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "APPEX_TRADUCCIONES" (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appex_traduccionesData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appex_traduccionesData.superclass.constructor.call(this, Ext.apply({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Traducciones.action?action=getTraducciones',
            fields: [
                "id_idioma", "idioma", "rowkey", "id_tabla", "texto"
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appex_traduccionesForm = Ext.extend(Ext.FormPanel, {

    parentWin: null,
    tabla: null,
    rowkey: null,
    mode: null,

    initComponent : function() {

        this.traduccionesData  = new Ext.com.befasoft.common.components.Appex_traduccionesData();

        this.saveButton = new Ext.Button({
            xtype: 'tbbutton',
            icon: '../images/disk.png',
            text: "<s:text name='appbs.btn.save.records'/>",
            scope: this,
            handler: function() {
                if (this.mode == 'FILE') {
                    this.getForm().submit({
                        url: appPath+'/Traducciones.action?action=save',
                        waitMsg: "<s:text name="appbs.upload.uploading"/>",

                        success: function(form, response) {
                            this.parentWin.close();
                            var params = getActionResponse(response);
                            Ext.example.msg("<s:text name="appbs.upload.msg.ok"/>", params.message);
                        },
                        failure: function(form, response) {
                            if (response.failureType == 'server') {
                                var params = getActionResponse(response);
                                Ext.example.msg("<s:text name="appbs.upload.msg.err"/>", params.message);
                            }
                        }
                    });
                } else {
                    if (this.getForm().isValid()) {
                        mainDiv.mask("<s:text name='appbs.wait'/>");
                        var data = [];
                        data.push(this.form.getFieldValues());

                        Ext.Ajax.request({
                            url: appPath+'/Traducciones.action?action=save',
                            params: { dataToSave:Ext.encode(data) },
                            scope: this,
                            success: function(response) {
                                var params = getActionResponse(response);
                                if (params.success) {
                                    mainDiv.unmask();
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
        });
        this.clearButton = new Ext.Button({
            text: "<s:text name='appbs.btn.reset.record'/>",
            scope: this,
            handler : function() {
                this.getForm().reset();
            }
        });

        var config = {
            autoHeight:true,
            width:450,
            frame:true,
            defaultType:'textfield',
            monitorValid:true,
            layout: 'form',
            buttons:[ this.saveButton, this.clearButton ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appex_traduccionesForm.superclass.initComponent.call(this);
    },

    loadTraduccion: function (tabla, rowkey, mode, path, contentType, parameters) {
        this.tabla = tabla;
        this.rowkey = rowkey;
        this.mode = mode;
        parameters = parameters ? "&"+parameters : "";
        if (mode == 'FILE') {
            this.saveButton.hide();
            this.clearButton.hide();
        } else {
            this.saveButton.show();
            this.clearButton.show();
        }
        this.traduccionesData.load({
            params: {tabla: tabla, rowkey: rowkey},
            scope: this,
            callback: function () {
                this.add({ xtype: 'hidden', name: 'tabla', value: tabla });
                this.add({ xtype: 'hidden', name: 'rowkey', value: rowkey });
                this.add({ xtype: 'hidden', name: 'mode', value: mode });
                this.add({ xtype: 'hidden', name: 'path', value: path });
                var tabs = null;
                if (mode == 'HTML') {
                    tabs = new Ext.TabPanel({ activeTab: 0, border: true, width: 523,  height:300, items: [] });
                }
                this.traduccionesData.each(
                    function (record) {
                        if (mode == 'FILE') {
                            this.add({ fieldLabel: record.data.idioma, xtype: 'textfield', readOnly: true, name: 'file_'+record.data.id_idioma, value: record.data.texto, width: 350,
                                listeners: {
                                    'focus': function(editor) {
                                        appbs_LoadFile(
                                            'Traducciones.action?action=upload&contentType='+contentType+'&path='+path+'&mode=FILE&tabla='+tabla+'&rowkey='+rowkey+'&id_idioma='+record.data.id_idioma+parameters,
                                            function (params) {
                                                if (params.success) {
                                                    editor.setValue(params.uploadFileName);
                                                }
                                            }
                                        );
                                    }
                                }
                            });
                        } else if (mode == 'HTML') {
                            tabs.add({ title: record.data.idioma, xtype: 'panel', layout: 'fit', items: [ { xtype: 'htmleditor', name: 'trad_'+record.data.id_idioma, value: record.data.texto } ] });
                        } else {
                            this.add({ fieldLabel: record.data.idioma, xtype: 'textfield', name: 'trad_'+record.data.id_idioma, value: record.data.texto, width: 350 });
                        }
                    },
                    this
                );
                if (mode == 'HTML') {
                    this.add(tabs);
                }
                this.doLayout();
            }
        });
    }
});

Ext.com.befasoft.common.components.Appex_traduccionesWin = Ext.extend(Ext.Window, {

    linkedGrid: null,

    initComponent : function() {
        this.editForm = new Ext.com.befasoft.common.components.Appex_traduccionesForm({ parentWin : this });
        var config = {
            layout:'fit',
            frame:true,
            closable: true,
            closeAction: 'hide',
            resizable: false,
            width: 550,
            modal: true,
            items: [this.editForm]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appex_traduccionesWin.superclass.initComponent.call(this);
    },

    loadRecord: function (record) {
        this.editForm.getForm().loadRecord(record);
    },

    getValues: function () {
        return this.editForm.getValues();
    },

    loadTraduccion: function (tabla, rowkey, mode, path, contentType, parameters) {
        this.editForm.loadTraduccion(tabla, rowkey, mode, path, contentType, parameters);
    }

});

</script>
