<%@ taglib prefix="s" uri="/struts-tags" %>

<s:include value="menu.jsp"/>

<script type="text/javascript">

    var panelMain;

    Ext.onReady(function() {
        var viewport;

        <s:if test="#session.user != null">
            panelMain = new Ext.Panel({
                region: 'center', // a center region is ALWAYS required for border layout
                deferredRender: false,
                activeTab: 0,     // first tab initially active
                margins: '0 5 0 0',
                items: [{
                    id: 'panelMainCaption',
                    contentEl: 'panelMainContent',
                    title: '--',
                    autoScroll: true
                }]
            });

            viewport = new Ext.Viewport({
                layout: 'border',
                items: [
                    // create instance immediately
                    {
                        // lazily created panel (xtype:'panel' is default)
                        region: 'north',
                        contentEl: 'north',
                        height: 55,
                        margins: '0 5 0 5',
                        border: false
                    },  {
                        region: 'west',
                        id: 'west-panel',
                        split: false,
                        width: 180,
                        minSize: 175,
                        maxSize: 600,
                        border:false,
                        collapsible: true,
                        margins: '0 0 0 5',
                        items: [
                           gridMenus
                        ]
                    },
                    {
                        region: 'center',
                        activeTab: 0,
                        xtype: 'tabpanel',
                        id: 'tabs',
                        maxTabWidth: 15,
                        margins: '0 5 0 0',
                        listeners: {activate: function activateTabType(tab) { tab.doLayout(); } } ,
                        enableTabScroll:true,
                        plain:true
                    }
                ]
            });
        </s:if>
        <s:else>
            viewport = new Ext.Viewport({
                layout: 'border',
                items: [
                    // create instance immediately
                    {
                        region: 'center',
                        margins: '0 5 0 5',
                        contentEl: 'center_empty',
                        border: false
                    }
                ]
            });
        </s:else>
    });
</script>
