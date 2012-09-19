<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Ventana de carga de ficheros (Start)
 */

Ext.ns('Ext.common.component');

Ext.common.component.FileloadForm = Ext.extend(Ext.FormPanel, {

    directory: null,
    contentType: null,
    callback: null,
    urlAction: null,
    maxSize: null,

    initComponent : function() {
        var config = {
            fileUpload: true,
            frame: true,
            autoHeight: true,
            bodyStyle: 'padding: 10px 10px 0 10px;',
            labelWidth: 50,
            defaults: {
                anchor: '95%',
                allowBlank: false,
                msgTarget: 'side'
            },
            items: [
                { xtype: 'hidden', name: 'directory', value: this.directory },
                { xtype: 'hidden', name: 'contentType', value: this.contentType },
                { xtype: 'hidden', name: 'maxSize', value: this.maxSize },
                {
                    xtype: 'fileuploadfield',
                    emptyText: "<s:text name="appbs.upload.select"/>",
                    fieldLabel: "<s:text name="appbs.upload.file"/>",
                    name: 'upload',
                    buttonText: ' ',
                    buttonCfg: {
                        iconCls: 'upload-icon'
                    }
                }
            ],

            buttons: [{
                text: "<s:text name="appbs.upload.btn"/>",
                formBind: true,
                scope: this,
                handler: function(){
                    if (this.getForm().isValid()) {
                        this.getForm().submit({
                            url: appPath + '/' + this.urlAction,
                            waitMsg: "<s:text name="appbs.upload.uploading"/>",
                            scope: this,
                            success: function(form, action) {
                                this.ownerCt.hide();
                                var params = Ext.decode(action.response.responseText);
                                Ext.example.msg("<s:text name="appbs.upload.msg.ok"/>", params.msg+": "+params.uploadFileName);
                                if (this.callback)
                                    this.callback(params);
                            },
                            failure: function(form, action) {
                                if (action.failureType == 'server') {
                                    var params = Ext.decode(action.response.responseText);
                                    Ext.example.msg("<s:text name="appbs.upload.msg.err"/>", params.msg);
                                    if (this.callback)
                                        this.callback(params);
                                } else {
                                    Ext.Msg.alert("<s:text name="appbs.error.server.title"/>: " + action.response.responseText);
                                }
                            }
                        });
                    }
                }
            }]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.common.component.FileloadForm.superclass.initComponent.call(this);
    }
});

Ext.common.component.FileloadWin = Ext.extend(Ext.Window, {

    maxSize: null,
    directory: null,
    contentType: null,
    callback: null,
    urlAction: null,

    initComponent : function() {
        if (!this.maxSize)
            this.maxSize = 5000; // 5 MB
        var fp = new Ext.common.component.FileloadForm({ directory: this.directory, contentType: this.contentType, callback: this.callback, urlAction: this.urlAction, maxSize: this.maxSize });
        var config = {
            title: "<s:text name="appbs.upload.caption"/>",
            layout:'fit',
            closable: true,
            resizable: false,
            closeAction: 'hide',
            plain: true,
            border: false,
            modal: true,
            width: 500,
            items: [fp]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.common.component.FileloadWin.superclass.initComponent.call(this);
    }
});

function appbs_LoadFile(urlAction, callback, directory, contentType, maxSize) {
    var uploadFormWin = new Ext.common.component.FileloadWin({ callback: callback, urlAction: urlAction, directory: directory, contentType: contentType, maxSize: maxSize });
    uploadFormWin.show();
}

</script>
