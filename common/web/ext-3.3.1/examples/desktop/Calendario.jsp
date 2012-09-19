<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">
/*
 * Mantenimiento de "CALENDARIO"
 */

Ext.ns('Ext.com.remedi.rmn.components');

Ext.com.remedi.rmn.components.CalendarioPanel = Ext.extend(Ext.Panel, {

    initComponent : function() {

        this.calendar = new Ext.com.remedi.rmn.components.RadiologosCalendarioPanel({ columns: 4});
        this.cbYear = new Ext.form.ComboBox({
            store: new Ext.com.remedi.rmn.components.DisponibleYearsData(), width:70,
            displayField:'nom', valueField : 'cod', hiddenName : 'anyo',
            typeAhead: true, triggerAction: 'all', mode: 'local', selectOnFocus:true,
            fieldLabel:"<s:text name='radiologos.disp.year'/>",
            emptyText:"<s:text name='appbs.form.combo.select'/>",
            listeners: {
                scope: this,
                'select': function (e, t) {
                    this.setCalendar();
                }
            }
        });

        var config = {
            layuot: 'border',
            items: [ this.calendar ],
            tbar: new Ext.Toolbar({
                items: [
                    "<s:text name='estudios.search.year'/>: ",
                    this.cbYear
                ]
            })
        };
        // apply config
        Ext.apply(this, Ext.apply(this.initialConfig, config));
        Ext.com.remedi.rmn.components.CalendarioPanel.superclass.initComponent.call(this);
    },

    setCalendar: function () {
        var id_radiologo = <s:property value="#session.user.id_radiologo"/>;
        Ext.Ajax.request({
            url: appPath+'/Radiologo_disp.action?action=loadDisp',
            scope: this,
            params: { id_radiologo: id_radiologo, anyo: this.cbYear.getValue() },
            success: function(response) {
                mainDiv.unmask();
                var params = getActionResponse(response);
                if (params.success) {
                    this.calendar.setCalendar(id_radiologo, params.properties.calendar, this.cbYear.getValue());
                }
            },
            failure: function() {
                mainDiv.unmask();
                Ext.Msg.alert("<s:text name='appbs.error.server.title'/>", "<s:text name='appbs.error.server.msg'/>");
            }
        });
    }

});

</script>

