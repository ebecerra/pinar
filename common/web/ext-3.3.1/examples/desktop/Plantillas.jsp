<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de "PLANTILLAS"
 */

Ext.ns('Ext.com.remedi.rmn.components');

Ext.com.remedi.rmn.components.PlantillasNewWin = Ext.extend(Ext.Window, {

    linkedForm: null,
    id_radiologo: null,

    initComponent : function() {

        this.cbCategoria = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.Plantillas_categoriasData(), width:150,
            displayField:'nombre', valueField : 'id_categoria', hiddenName : 'id_categoria',
            typeAhead: true, triggerAction: 'all', mode: 'local', selectOnFocus:true, allowBlank:false,
            fieldLabel:"<s:text name='plantillas_categorias.categoria'/>",
            emptyText:"<s:text name='appbs.form.combo.select'/>"
        });
        this.editNombre = new Ext.form.TextField({ fieldLabel:"<s:text name='plantillas.nombre'/>", name: 'nombre', allowBlank:false, width:150 });

        var config = {
            layout:'form',
            frame:true,
            closable: true,
            closeAction: 'close',
            width:300,
            height: 150,
            padding: 10,
            resizable: true,
            modal: true,
            title:"<s:text name='plantillas.caption'/>",
            items: [
                this.cbCategoria,
                this.editNombre
            ],
            bbar: new Ext.Toolbar({
                items: [
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: 'images/tick.png',
                        text: "<s:text name='appbs.btn.ok'/>",
                        scope: this,
                        handler: function() {
                            if (this.cbCategoria.getValue() == '' || this.editNombre.getValue() == '') {
                                Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="appbs.form.msg.invalid"/>");
                                return;
                            }
                            var data = [];
                            data.push({
                                id_radiologo: this.id_radiologo,
                                id_categoria: this.cbCategoria.getValue(),
                                nombre: this.editNombre.getValue(),
                                inf_tecnica: this.inf_tecnica,
                                inf_informe: this.inf_informe,
                                inf_conclusion: this.inf_conclusion
                            });
                            Ext.Ajax.request({
                                url: appPath+'/Plantillas.action?action=save',
                                params: { dataToSave:Ext.encode(data) },
                                scope: this,
                                success: function(response) {
                                    mainDiv.unmask();
                                    var params = getActionResponse(response);
                                    if (params.success)
                                        this.close();
                                    else
                                        showServerError(params);
                                },
                                failure: function() {
                                    mainDiv.unmask();
                                    Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                }
                            });
                        }
                    }, {
                        xtype: 'tbbutton',
                        icon: 'images/cancel.png',
                        text: "<s:text name='appbs.btn.cancel'/>",
                        scope: this,
                        handler: function() {
                            this.close();
                        }
                    }
                ]
            })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.PlantillasNewWin.superclass.initComponent.call(this);
    },

    initForm: function (id_radiologo, inf_tecnica, inf_informe, inf_conclusion) {
        this.id_radiologo = id_radiologo;
        this.inf_tecnica = inf_tecnica;
        this.inf_informe = inf_informe;
        this.inf_conclusion = inf_conclusion;
        this.cbCategoria.store.load({
            params: { id_radiologo: id_radiologo }
        });
    }
});

Ext.com.remedi.rmn.components.PlantillasGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedPanel: null,

    initComponent : function() {
        // DataStore
        this.plantillasData = new Ext.com.remedi.rmn.components.PlantillasData();

        var config = {
            store: this.plantillasData,
            autoScroll:true,
            frame:false,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='plantillas.id_plantilla'/>", sortable: true, dataIndex: 'id_plantilla', width: 15 },
                { header:"<s:text name='plantillas.nombre'/>", sortable: true, dataIndex: 'nombre', width: 85 }
            ],
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                scope: this,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        this.linkedPanel.panelPlantilla.update(renderPlantilla(record));
                        this.linkedPanel.currentRecord = record;
                        this.linkedPanel.currentGrid = this;
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.PlantillasGrid.superclass.initComponent.call(this);
    },

    updateData: function (id_radiologo, id_categoria) {
        this.plantillasData.load({ params: { id_radiologo: id_radiologo, id_categoria: id_categoria} });
    }

});

Ext.com.remedi.rmn.components.PlantillasLoadWin = Ext.extend(Ext.Window, {

    linkedForm: null,
    currentRecord: null,
    currentGrid: null,

    initComponent : function() {

        this.categoriasData = new Ext.com.remedi.rmn.components.Plantillas_categoriasData();
        this.accordion = new Ext.Panel({
            region:'west',
            margins:'5 0 5 5',
            split:true,
            width: 300,
            layout:'accordion',
            items: []
        });
        this.panelPlantilla = new Ext.Panel({
            region: 'center', padding: 5, autoScroll: true,
            bbar: new Ext.Toolbar({
                items: [
                    '->',
                    {
                        xtype: 'tbbutton',
                        icon: 'iconos/plant.gif',
                        text: "<s:text name='estudios.btn.load_plant'/>",
                        scope: this,
                        handler: function() {
                            if (!this.currentRecord) {
                                Ext.example.msg("<s:text name='appbs.please.title'/>", "<s:text name="plantillas.msg.select"/>");
                                return;
                            }
                            var component = this;
                            Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="estudio_informes.msg.load"/>",
                                function (btn) {
                                    if (btn == "yes") {
                                        var form = component.linkedForm;
                                        form.fieldInf_tecnica.setValue(component.currentRecord.data.inf_tecnica);
                                        form.fieldInf_informe.setValue(component.currentRecord.data.inf_informe);
                                        form.fieldInf_conclusion.setValue(component.currentRecord.data.inf_conclusion);
                                        component.close();
                                    }
                                }
                            );
                        }
                    }
                ]
            })
        });

        var config = {
            layout:'border',
            frame:true,
            closable: true,
            closeAction: 'close',
            width:750,
            height: 600,
            resizable: true,
            modal: true,
            title:"<s:text name='plantillas.caption'/>",
            items: [ this.accordion, this.panelPlantilla ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.PlantillasLoadWin.superclass.initComponent.call(this);
    },

    loadData: function(id_radiologo) {
        this.categoriasData.load({
            params: { id_radiologo: id_radiologo}, scope: this,
            callback: function (records) {
                for (var i = 0; i < records.length; i++) {
                    var grid = new Ext.com.remedi.rmn.components.PlantillasGrid({ linkedPanel: this });
                    grid.updateData(records[i].data.id_radiologo, records[i].data.id_categoria);
                    this.accordion.add({ title: records[i].data.nombre, xtype: 'panel', layout: 'fit', items: [ grid ]});
                }
                this.accordion.doLayout();
            }
        });
    },

    loadPlantilla: function (grid, record) {
        this.panelPlantilla.update(renderPlantilla(record));
        this.currentGrid = grid;
        this.currentRecord = record;
    }
});

Ext.com.remedi.rmn.components.PlantillasMantPanel = Ext.extend(Ext.Panel, {

    linkedForm: null,
    id_radiologo: null,

    initComponent : function() {

        this.plantPanel = new Ext.Panel({ region: 'center', autoScroll: true });
        this.plantillasGrid = new Ext.com.remedi.rmn.components.Plant_PlantillasGrid({ linkedPanel: this.plantPanel });
        this.categoriasGrid = new Ext.com.remedi.rmn.components.Plant_categoriasGrid({ linkedPlantillas: this.plantillasGrid });

        var config = {
            layout:'border',
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            items: [
                {
                    xtype: 'panel', region: 'north', height: 200, layout: 'border',
                    items: [
                        { title: "<s:text name="plantillas_categorias.caption"/>", layout: 'fit', xtype: 'panel', region: 'west', width: 330, items: [ this.categoriasGrid ]  },
                        { title: "<s:text name="plantillas.caption"/>", layout: 'fit', xtype: 'panel', region: 'center', items: [ this.plantillasGrid ]  }
                    ]
                },
                this.plantPanel
            ]
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.PlantillasMantPanel.superclass.initComponent.call(this);
    },

    updateData: function() {
        this.categoriasGrid.updateData(<s:property value="#session.user.id_radiologo"/>);
    }
});

</script>
