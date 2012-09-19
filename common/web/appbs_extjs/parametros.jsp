<%@ page import="com.befasoft.common.tools.Constants" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

/*
 * Mantenimiento de APPBS_PARAMETROS (Start)
 */

Ext.ns('Ext.com.befasoft.common.components');

Ext.com.befasoft.common.components.Appbs_parametrosData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_parametrosData.superclass.constructor.call(this, Ext.apply({
            id: 'idParametrosData',
            remoteSort: false,
            autoDestroy: true,
            root:"elements",
            totalProperty: 'totalCount',
            url: appPath +'/Parametros.action',
            fields: [
                "editable2", "visible2", "checkable2", "id_aplicacion", "nombre", "id_parametro", "edit", "max_int",
                "min_int", "min_float", "check", "visible", "max_float", "valor", "valor2", "tipo",
                { name:"min_date", type: 'date', dateFormat: 'c' }, { name:"max_date", type: 'date', dateFormat: 'c' }
            ],

            listeners: { 'loadexception': dataSourceLoadException }
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_parametros_valoresData = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        Ext.com.befasoft.common.components.Appbs_parametros_valoresData.superclass.constructor.call(this, Ext.apply({
            id: 'idParametros_valoresData',
            remoteSort: false,
            autoDestroy: true,
            fields: [ "id_aplicacion", "id_parametro", "id_valor", "nombre" ]
        }, cfg));
    }
});

Ext.com.befasoft.common.components.Appbs_parametrosGrid = Ext.extend(Ext.grid.GridPanel, {

    linkedForm: null,
    pageSize: 25,

    initComponent : function() {
        // DataStore
        this.appbs_parametrosData = new Ext.com.befasoft.common.components.Appbs_parametrosData();
        this.appbs_parametros_valoresData = new Ext.com.befasoft.common.components.Appbs_parametros_valoresData();

        var config = {
            region: 'center',
            store: this.appbs_parametrosData,
            autoScroll:true,
            ddGroup: 'depGridDD',
            frame:true,
            viewConfig: { forceFit: true },
            columns: [
                { header:"<s:text name='appbs_parametros.id_parametro'/>", sortable: true, dataIndex: 'id_parametro', width: 20 },
                { header:"<s:text name='appbs_parametros.nombre'/>", sortable: true, dataIndex: 'nombre', width: 45 },
                { header:"<s:text name='appbs_parametros.valor'/>", sortable: true, dataIndex: 'valor2', width: 35 }
                <s:if test="#session.user.perfil_tipo == 'MANAGER'">
                    ,{ header:"<s:text name='appbs_parametros.visible'/>", sortable: true, dataIndex: 'visible2', width: 5, renderer:'Boolean' }
                    ,{ header:"<s:text name='appbs_parametros.edit'/>", sortable: true, dataIndex: 'editable2', width: 5, renderer:'Boolean' }
                    ,{ header:"<s:text name='appbs_parametros.check'/>", sortable: true, dataIndex: 'checkable2', width: 5, renderer:'Boolean' }
                </s:if>
            ],
            // Cuando se selecciona un elemento de la lista
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                scope: this,
                listeners: {
                    scope: this,
                    rowselect: function(smObj, rowIndex, record) {
                        this.linkedForm.getForm().loadRecord(record);
                        this.appbs_parametros_valoresData.removeAll();
                        var RecordType = this.appbs_parametros_valoresData.recordType;
                        for (var i = 0; i < record.json.valores.length; i++) {
                            var newRecord = new RecordType();
                            newRecord.data= record.json.valores[i];
                            this.appbs_parametros_valoresData.add(newRecord);
                        }
                        this.appbs_parametros_valoresData.modified = [];
                        this.linkedForm.setRangeCheck();
                    }
                }
            }) // End sm
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.befasoft.common.components.Appbs_parametrosGrid.superclass.initComponent.call(this);
    },

    updateData: function () {
        this.appbs_parametrosData.load({
            scope: this,
            callback: function () {
                if (this.store.getCount() == 0) {
                    this.linkedForm.newRecord();
                } else {
                    this.getSelectionModel().selectFirstRow();
                }
            }
        });
    }

});

<s:if test="#session.user.perfil_tipo == 'MANAGER'">

    Ext.com.befasoft.common.components.Appbs_parametrosEnumGrid = Ext.extend(Ext.grid.EditorGridPanel, {

        linkedGrid: null,
        elementsToDelete: [],

        initComponent : function() {
            var config = {
                store: Ext.StoreMgr.lookup('idParametros_valoresData'),
                enableDragDrop: true, autoScroll: true, hidden: true,
                ddGroup: 'depGridDD',
                columns: [
                    { header:"<s:text name='appbs_parametros.id_parametro'/>", sortable: true, dataIndex: 'id_valor', width:20, editor: new Ext.form.TextField({ allowBlank: false  }) },
                    { header:"<s:text name='appbs_parametros.nombre'/>", sortable: true, dataIndex: 'nombre', width:80, editor: new Ext.form.TextField({ allowBlank: false  }) }
                ],
                viewConfig: {
                    forceFit: true
                },
                height:170,
                frame:true,
                bbar: new Ext.Toolbar({
                    items:[
                        {
                            xtype: 'tbbutton', icon: 'images/add.png',
                            text: "<s:text name='appbs.btn.add.record'/>",
                            scope: this,
                            handler : function() {
                                gridAddRecord(this);
                            }
                        },
                        '-',
                        {
                            xtype: 'tbbutton', icon: 'images/delete.gif',
                            text: "<s:text name='appbs.btn.delete.record'/>",
                            scope: this,
                            handler: function() {
                                gridDeleteRecord(this, this.elementsToDelete, "id_valor");
                            }
                        },
                        '->',
                        {
                            xtype: 'tbbutton', icon: 'images/disk.png',
                            text: "<s:text name='appbs.btn.save.records'/>",
                            scope: this,
                            handler: function() {
                                mainDiv.mask("<s:text name='appbs.wait'/>");
                                this.updateParentData();
                                var data = gridGetModifiedRecords(this);
                                var id_aplicacion = Ext.getCmp("appbs_parametros_id_aplicacion").value;
                                var id_parametro = Ext.getCmp("appbs_parametros_id_parametro").value;
                                Ext.Ajax.request({
                                    url: appPath+'/Parametros.action?action=save_enum',
                                    scope: this,
                                    params: { dataToSave:Ext.encode(data), elementsToDelete: Ext.encode(this.elementsToDelete), id_parametro: id_parametro, id_aplicacion: id_aplicacion},
                                    success: function() {
                                        this.elementsToDelete = [];
                                        this.getStore().modified = [];
                                        mainDiv.unmask();
                                    },
                                    failure: function() {
                                        mainDiv.unmask();
                                        Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                    }
                                });
                            }
                        }
                    ]
                }),

                sm: new Ext.grid.RowSelectionModel({ singleSelect: true })
            };
            // apply config
            Ext.apply(this, Ext.apply(this.initialConfig, config));
            Ext.com.befasoft.common.components.Appbs_parametrosEnumGrid.superclass.initComponent.call(this);
        },

        updateParentData: function () {
            var record = this.linkedGrid.getSelectionModel().getSelected();
            if (record.json.tipo == 'E') {
                record.json.valores = [];
                for (var i = 0; i < this.store.data.items.length; i++) {
                    record.json.valores[i] = this.store.data.items[i].data;
                }
            }
        }

    });

    Ext.com.befasoft.common.components.Appbs_parametrosForm = Ext.extend(Ext.FormPanel, {

        linkedGrid: null,

        initComponent : function() {

            this.labelError = new Ext.form.Label({ text:'', cls: "appbs_error", hidden:true, align: 'left' });

            this.panelFechas = new Ext.Panel({
                frame:false,
                monitorValid:true,
                hidden: true,
                layout: 'form',
                items:[
                    { fieldLabel:"<s:text name='appbs_parametros.min_date'/>", xtype : 'datefield', name: 'min_date', allowBlank:true, width:100  },
                    { fieldLabel:"<s:text name='appbs_parametros.max_date'/>", xtype : 'datefield', name: 'max_date', allowBlank:true, width:100  }
                ]
            });

            this.panelInt = new Ext.Panel({
                frame:false,
                monitorValid:true,
                hidden: true,
                layout: 'form',
                items:[
                    { fieldLabel:"<s:text name='appbs_parametros.min_int'/>", xtype : 'numberfield', name: 'min_int', allowBlank:true, width:50 },
                    { fieldLabel:"<s:text name='appbs_parametros.max_int'/>", xtype : 'numberfield', name: 'max_int', allowBlank:true, width:50 }
                ]
            });

            this.panelFloat = new Ext.Panel({
                frame:false,
                monitorValid:true,
                hidden: true,
                layout: 'form',
                items:[
                    { fieldLabel:"<s:text name='appbs_parametros.min_float'/>", xtype : 'numberfield', name: 'min_float', allowBlank:true, width:50  },
                    { fieldLabel:"<s:text name='appbs_parametros.max_float'/>", xtype : 'numberfield', name: 'max_float', allowBlank:true, width:50  }
                ]
            });

            this.panelTipo = new Ext.form.ComboBox({
                width:150, maxHeight:200, fieldLabel:"<s:text name='appbs_parametros.tipo'/>",
                name:"tipo", typeAhead: true, editable: false, triggerAction: 'all', forceSelection:true,
                store:[ ['E', 'Enumerativo'], ['I', 'Entero'], ['F', 'Real'], ['S', 'Cadena'], ['M', 'Mail'], ['D', 'Fecha'], ['P', 'Clave'] ],
                listeners: {
                    scope: this,
                    'select': function (e, t) {
                        this.setRangeCheck();
                    }
                }
            });

            this.gridEnum = new Ext.com.befasoft.common.components.Appbs_parametrosEnumGrid();

            var config = {
                frame:true,
                defaultType:'textfield',
                monitorValid:true,
                region: 'south',
                height:245,
                items:[
                    {
                        xtype: 'fieldset', layout: 'column', border: true, defaults: { border: false },
                        items: [
                            {
                                xtype: 'fieldset', autoHeight: true, columnWidth: '.65',
                                items: [
                                    { xtype: 'hidden', name: 'id_aplicacion', value: "<%=Constants.APP_NAME%>", id: 'appbs_parametros_id_aplicacion' },
                                    { xtype: 'textfield', fieldLabel:"<s:text name='appbs_parametros.id_parametro'/>", name: 'id_parametro', allowBlank: false, width: 250, id: "appbs_parametros_id_parametro" },
                                    { xtype: 'textfield', fieldLabel:"<s:text name='appbs_parametros.nombre'/>", name: 'nombre', allowBlank:false, width: 500 },
                                    { xtype: 'textfield', fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor', allowBlank:false, width: 500 },
                                    this.panelTipo,
                                    { xtype: 'checkbox', boxLabel:"<s:text name='appbs_parametros.edit'/>", name: 'editable2'},
                                    { xtype: 'checkbox', boxLabel:"<s:text name='appbs_parametros.visible'/>", name: 'visible2'},
                                    { xtype: 'checkbox', boxLabel:"<s:text name='appbs_parametros.check'/>", name: 'checkable2', id: "appbs_parametros_check",
                                        listeners:{
                                            scope: this,
                                            'check': function (e, t) {
                                                this.setRangeCheck();
                                            }
                                        }
                                    }
                                ]
                            },
                            {
                                xtype: 'fieldset', autoHeight: true, columnWidth: '.35',
                                items: [ this.panelFechas, this.panelInt, this.panelFloat, this.gridEnum ]
                            }
                        ]
                    }
                ],
                buttons:[
                    this.labelError,
                    {
                        xtype: 'tbbutton', icon: 'images/disk.png',
                        text: "<s:text name='appbs.btn.save.records'/>",
                        scope: this,
                        handler: function() {

                            if (this.checkParametros()) {
                                mainDiv.mask("<s:text name='appbs.wait'/>");

                                var data = [];
                                data.push(this.form.getFieldValues());

                                Ext.Ajax.request({
                                    url: appPath+'/Parametros.action?action=save',
                                    params: { dataToSave:Ext.encode(data) },
                                    scope: this,
                                    success: function() {
                                        mainDiv.unmask();
                                        this.linkedGrid.updateData();
                                    },
                                    failure: function() {
                                        mainDiv.unmask();
                                        Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                    }
                                });
                            }
                        }
                    }, {
                        xtype: 'tbbutton', icon: 'images/delete.gif',
                        text: "<s:text name='appbs.btn.delete.record'/>",
                        scope: this,
                        handler: function() {
                            var component = this;
                            Ext.MessageBox.confirm("<s:text name="appbs.form.confirm.caption"/>", "<s:text name="appbs_parametros.msg.delete"/>",
                                    function (btn) {
                                        if (btn == "yes") {
                                            var data = [];
                                            data.push(component.form.getFieldValues());
                                            Ext.Ajax.request({
                                                url: appPath+'/Parametros.action?action=save',
                                                params: { elementsToDelete: Ext.encode(data) },
                                                scope: component,
                                                success: function() {
                                                    mainDiv.unmask();
                                                    this.linkedGrid.updateData();
                                                },
                                                failure: function() {
                                                    mainDiv.unmask();
                                                    Ext.Msg.alert("<s:text name='appbs.error.server.msg'/>");
                                                }
                                            });
                                        }
                                    }
                            );
                        }
                    }, {
                        xtype: 'tbbutton', icon: 'images/add.png',
                        text: "<s:text name='appbs.btn.reset.record'/>",
                        scope : this,
                        handler : function() {
                            this.newRecord();
                            this.setRangeCheck();
                        }
                    }
                ]
            };
            // apply config
            Ext.apply(this, Ext.apply(this.initialConfig, config));
            Ext.com.befasoft.common.components.Appbs_parametrosForm.superclass.initComponent.call(this);
        },

        newRecord: function () {
            var data = {};
            data["id_aplicacion"] = "<%=Constants.APP_NAME%>";
            data["id_parametro"] = "";
            data["nombre"] = "";
            data["valor"] = "";
            data["tipo"] = "S";
            data["editable2"] = true;
            data["visible2"] = true;
            data["checkable2"] = false;

            this.getForm().loadRecord(new Ext.data.Record(data));
        },

        setRangeCheck: function () {
            var tipo = this.panelTipo.value;
            var check = Ext.getCmp("appbs_parametros_check");
            this.panelFechas.hide();
            this.panelInt.hide();
            this.panelFloat.hide();
            this.gridEnum.hide();
            if (check.checked) {
                if ("I" == tipo)
                    this.panelInt.show();
                if ("D" == tipo)
                    this.panelFechas.show();
                if ("F" == tipo)
                    this.panelFloat.show();
            }
            if ("E" == tipo)
                this.gridEnum.show();
        },

        checkParametros: function () {
            this.labelError.hide();
            return true;
        }

    });

</s:if>
<s:else>

    Ext.com.befasoft.common.components.Appbs_parametrosForm = Ext.extend(Ext.FormPanel, {

        linkedGrid: null,

        initComponent : function() {
            this.labelError = new Ext.form.Label({ text:'', cls: "appbs_error", hidden:true, align: 'left' });
            this.editValueInt = new Ext.form.NumberField({ allowBlank: false, allowDecimals: false, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_int', id: 'appbs_parametros_valor_int', hidden:true });
            this.editValueFloat = new Ext.form.NumberField({ allowBlank: false, allowDecimals: true, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_float', id: 'appbs_parametros_valor_float', hidden:true });
            this.editValueView = new Ext.form.TextField({ allowBlank: false, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_text', id: 'appbs_parametros_valor_view', width:400, hidden:true, readOnly:true });
            this.editValueText = new Ext.form.TextField({ allowBlank: false, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_text', id: 'appbs_parametros_valor_text', width:400, hidden:true });
            this.editValueEmail = new Ext.form.TextField({ allowBlank: false, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_email', id: 'appbs_parametros_valor_email', width:400, hidden:true, vtype: 'email' });
            this.editValueDate = new Ext.form.DateField({ allowBlank: false, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_date', id: 'appbs_parametros_valor_date', hidden:true });
            this.editValuePassword = new Ext.form.TextField({ allowBlank: false, fieldLabel:"<s:text name='appbs_parametros.valor'/>",  name: 'valor_password', id: 'appbs_parametros_valor_password', hidden:true, inputType:'password' });
            this.editValueEnum = new Ext.form.ComboBox({
                store: Ext.StoreMgr.lookup('idParametros_valoresData'),
                displayField:'nombre', valueField: 'id_valor', hiddenName: 'valor_enum', id: 'appbs_parametros_valor_enum',
                typeAhead: true, mode: 'local', fieldLabel:"<s:text name='appbs_parametros.valor'/>",
                triggerAction: 'all', selectOnFocus:true, hidden:true
            });
            this.editRange = new Ext.form.Label({ text:'', cls: "appbs_params_range", hidden:true });
            this.editMin = "";
            this.editMax = "";

            var config = {
                frame:true,
                defaultType:'textfield',
                monitorValid:true,
                region: 'south',
                height:120,
                items:[
                    { xtype: 'hidden', name: 'id_aplicacion', value: "<%=Constants.APP_NAME%>", id: 'appbs_parametros_id_aplicacion' },
                    { xtype: 'hidden', name: 'id_parametro', id: 'appbs_parametros_id_parametro' },
                    { xtype: 'hidden', name: 'tipo', id: 'appbs_parametros_tipo' },
                    { xtype: 'hidden', name: 'valor', id: 'appbs_parametros_valor' },
                    { xtype: 'hidden', name: 'editable2', id: 'appbs_parametros_editable2' },
                    { xtype: 'hidden', name: 'visible2', id: 'appbs_parametros_visible2' },
                    { xtype: 'hidden', name: 'checkable2', id: 'appbs_parametros_check' },
                    { xtype: 'datefield', hidden: true, name: 'min_date', id: 'appbs_parametros_min_date' },
                    { xtype: 'datefield', hidden: true, name: 'max_date', id: 'appbs_parametros_max_date' },
                    { xtype: 'hidden', name: 'min_int', id: 'appbs_parametros_min_int' },
                    { xtype: 'hidden', name: 'max_int', id: 'appbs_parametros_max_int' },
                    { xtype: 'hidden', name: 'min_float', id: 'appbs_parametros_min_float' },
                    { xtype: 'hidden', name: 'max_float', id: 'appbs_parametros_max_float' },
                    { fieldLabel:"<s:text name='appbs_parametros.nombre'/>", name: 'nombre', readOnly: true, width:400 },
                    this.editValueInt,
                    this.editValueFloat,
                    this.editValueText,
                    this.editValueEmail,
                    this.editValueDate,
                    this.editValuePassword,
                    this.editValueEnum,
                    this.editValueView,
                    this.editRange
                ],
                buttons:[
                    this.labelError,
                    {
                        xtype: 'tbbutton', icon: 'images/disk.png',
                        text: "<s:text name='appbs.btn.save.records'/>",
                        scope: this,
                        handler: function() {

                            if (this.checkParametros()) {
                                mainDiv.mask("<s:text name='appbs.wait'/>");

                                var data = [];
                                data.push(this.form.getFieldValues());

                                Ext.Ajax.request({
                                    url: appPath+'/Parametros.action?action=save',
                                    params: { dataToSave:Ext.encode(data) },
                                    scope: this,
                                    success: function() {
                                        mainDiv.unmask();
                                        this.linkedGrid.updateData();
                                    },
                                    failure: function() {
                                        mainDiv.unmask();
                                        Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
                                    }
                                });
                            }

                        }// end handler

                    } // end button
                ]
            };
            // apply config
            Ext.apply(this, Ext.apply(this.initialConfig, config));
            Ext.com.befasoft.common.components.Appbs_parametrosForm.superclass.initComponent.call(this);
        },

        setRangeCheck: function () {
            this.editValueInt.hide();
            this.editValueFloat.hide();
            this.editValueText.hide();
            this.editValueEmail.hide();
            this.editValueDate.hide();
            this.editValuePassword.hide();
            this.editValueEnum.hide();
            this.editRange.hide();
            this.labelError.hide();
            this.editValueView.hide();
            var par_tipo = Ext.getCmp("appbs_parametros_tipo");
            var valor = Ext.getCmp("appbs_parametros_valor");
            var checkable = Ext.getCmp("appbs_parametros_check");
            var editable = Ext.getCmp("appbs_parametros_editable2");

            if (!editable.value) {
                this.editValueView.show();
                this.editValueView.setValue(valor.value);
            } else if (par_tipo.value == 'I') {
                this.editValueInt.show();
                this.editValueInt.setValue(valor.value);
                if (checkable.value)
                    this.showRange(par_tipo.value, Ext.getCmp("appbs_parametros_min_int").value, Ext.getCmp("appbs_parametros_max_int").value);
            } else if (par_tipo.value == 'F') {
                this.editValueFloat.show();
                this.editValueFloat.setValue(valor.value);
                if (checkable.value)
                    this.showRange(par_tipo.value, Ext.getCmp("appbs_parametros_min_float").value, Ext.getCmp("appbs_parametros_max_float").value);
            } else if (par_tipo.value == 'S') {
                this.editValueText.show();
                this.editValueText.setValue(valor.value);
            } else if (par_tipo.value == 'M') {
                this.editValueEmail.show();
                this.editValueEmail.setValue(valor.value);
            } else if (par_tipo.value == 'D') {
                this.editValueDate.show();
                this.editValueDate.setValue(valor.value);
                if (checkable.value)
                    this.showRange(par_tipo.value, Ext.getCmp("appbs_parametros_min_date").value, Ext.getCmp("appbs_parametros_max_date").value);
            } else if (par_tipo.value == 'P') {
                this.editValuePassword.show();
                this.editValuePassword.setValue(valor.value);
            } else if (par_tipo.value == 'E') {
                this.editValueEnum.show();
                this.editValueEnum.setValue(valor.value);
            }
        },

        showRange: function (tipo, min, max) {
            this.editMin = min;
            this.editMax = max;
            this.editRange.show();
            this.editRange.setText("<s:text name="appbs_parametros.range"/>: "+this.editMin+" - "+this.editMax);
        },

        checkParametros: function () {
            this.labelError.hide();
            var editable = Ext.getCmp("appbs_parametros_editable2");
            if (!editable.value)
                return false;
            var par_tipo = Ext.getCmp("appbs_parametros_tipo");
            var min, max, val;
            if (par_tipo.value == 'I') {
                Ext.getCmp("appbs_parametros_valor").setValue(this.editValueInt.value);
                val = parseInt(this.editValueInt.value); min = parseInt(this.editMin); max = parseInt(this.editMax);
            } else if (par_tipo.value == 'F') {
                Ext.getCmp("appbs_parametros_valor").setValue(this.editValueFloat.value);
                val = parseFloat(this.editValueFloat.value); min = parseFloat(this.editMin); max = parseFloat(this.editMax);
            } else if (par_tipo.value == 'S') {
                val = Ext.getCmp("appbs_parametros_valor_text").getValue();
                Ext.getCmp("appbs_parametros_valor").setValue(val);
            } else if (par_tipo.value == 'M') {
                val = Ext.getCmp("appbs_parametros_valor_email").getValue();
                Ext.getCmp("appbs_parametros_valor").setValue(val);
            } else if (par_tipo.value == 'D') {
                Ext.getCmp("appbs_parametros_valor").setValue(this.editValueDate.value);
                val = Date.parseDate(this.editValueDate.value, "d/m/Y");
                min = Date.parseDate(this.editMin, "d/m/Y");
                max = Date.parseDate(this.editMax, "d/m/Y");
            } else if (par_tipo.value == 'P') {
                val = Ext.getCmp("appbs_parametros_valor_password").getValue();
                Ext.getCmp("appbs_parametros_valor").setValue(val);
            } else if (par_tipo.value == 'E') {
                val = Ext.getCmp("appbs_parametros_valor_enum").getValue();
                Ext.getCmp("appbs_parametros_valor").setValue(val);
            }

            var checkable = Ext.getCmp("appbs_parametros_check");
            if (checkable.value) {
                if (val < min || max < val) {
                    this.labelError.show();
                    this.labelError.setText("<s:text name="appbs_parametros.err.range"/>");
                    return false;
                }
            }
            return true;
        }

    });

</s:else>

//------------------------------------------------------------------------
// Abre un Tab con el mantenimiento de parametros
//------------------------------------------------------------------------
function openTabParams(title, idMenu) {
    var tabs = Ext.getCmp("tabs");
    var tab;
    if (!(tab = tabs.getItem(title))) {
        var grid = new Ext.com.befasoft.common.components.Appbs_parametrosGrid();
        var form = new Ext.com.befasoft.common.components.Appbs_parametrosForm({ linkedGrid: grid });
        grid.linkedForm = form;
        <s:if test="#session.user.perfil_tipo == 'MANAGER'">
            form.gridEnum.linkedGrid = grid;
        </s:if>
        tab = new Ext.Panel({
            id: idMenu,
            title: title,
            closable:true,
            autoScroll:true,
            border:false,
            plain:true,
            layout: 'border',
            items:[ grid, form ]
        });
        tabs.add(tab);
        grid.updateData();
    }
    tabs.setActiveTab(tab);
    tabs.doLayout();
}

</script>
