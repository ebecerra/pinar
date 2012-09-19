<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "FileManager" [Commons] (Start)
 */

Ext.ns('Ext.common.component');

Ext.common.component.FilemanagerGrid = Ext.extend(Ext.grid.GridPanel, {
    directory: null,
    contentType: null,
    maxSize: 5000, // 5 MB

    initComponent : function() {
        // DataStore
        this.filemanagerData = new Ext.data.JsonStore({
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath+'/Filemanager.action?directory='+this.directory,
            fields: [ "name", "path", "file", { name:"modified", type: 'date', dateFormat: 'c' }, "size" ],

            listeners: { 'loadexception': dataSourceLoadException }
        });

        var config = {
            store: this.filemanagerData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:false,
            region: 'center',
            columns: [
                { header:"<s:text name='appbs.filemanager.name'/>", sortable: true, dataIndex: 'name', width: 70 },
                { header:"<s:text name='appbs.filemanager.modified'/>", sortable: true, dataIndex: 'modified', renderer: formatDateTime, width: 20 },
                { header:"<s:text name='appbs.filemanager.size'/>", sortable: true, dataIndex: 'size', align: 'right', width: 10 }
            ],
            viewConfig: { forceFit: true },
            bbar: new Ext.Toolbar({
                items:[
                    {
                        text: "<s:text name='appbs.filemanager.btn.load'/>",
                        icon: appBasicPath+'/images/disk.png',
                        parentComponent: this,
                        handler : function() {
                            parentComponentFileManager = this.parentComponent;
                            appbs_LoadFile(
                                'Filemanager.action?action=upload',
                                function () { parentComponentFileManager.updateData(); },
                                this.parentComponent.directory,
                                this.parentComponent.contentType,
                                this.parentComponent.maxSize
                            );
                        }
                    },
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: appBasicPath+'/images/delete.gif',
                        text: "<s:text name='appbs.btn.delete.record'/>",
                        parentComponent: this,
                        handler: function() {
                            var currentRecord = this.parentComponent.getSelectionModel().getSelected();
                            if (currentRecord) {
                                parentComponentFileManager = this.parentComponent;
                                Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appbs.filemanager.msg.delete"/>",
                                    function (btn) {
                                        if (btn == "yes") {
                                            mainDiv.mask("<s:text name='appbs.wait'/>");
                                            Ext.Ajax.request({
                                                url: appPath+'/Filemanager.action?action=delete',
                                                params: { directory: parentComponentFileManager.directory, uploadFileName: currentRecord.data.name },
                                                success: function() {
                                                    mainDiv.unmask();
                                                    parentComponentFileManager.updateData();
                                                },
                                                failure: function() {
                                                    mainDiv.unmask();
                                                    Ext.Msg.alert("<s:text name='appbs.error.server.msg'/>");
                                                }
                                            });
                                        }
                                    }
                                );
                            } else
                                Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="appbs.filemanager.msg.select"/>");
                        }
                    }
                ]
            }),
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
        };

        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.common.component.FilemanagerGrid.superclass.initComponent.call(this);
    },

    updateData: function (id_paciente) {
        this.filemanagerData.load({ callback: dataSourceLoad });
    }
});

</script>