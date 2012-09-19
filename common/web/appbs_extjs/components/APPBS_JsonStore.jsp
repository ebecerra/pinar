<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

    APPBS_JsonStore = Ext.extend(Ext.data.JsonStore, {
    constructor: function(cfg) {
        cfg = cfg || {};
        APPBS_JsonStore.superclass.constructor.call(this, Ext.apply(
        {
            remoteSort: false,
            autoDestroy: true,
            listeners: {
                'loadexception': function() {
                    Ext.Msg.show(
                    {
                        title:"<s:text name='appbs.error.server.title'/>",
                        msg:"<s:text name='appbs.error.server.msg'/>",
                        buttons: Ext.Msg.OK,
                        icon: Ext.MessageBox.ERROR
                    });
                }
            }
        }, cfg));
    }
});

</script>
