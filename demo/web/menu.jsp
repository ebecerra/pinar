<%@ taglib prefix="s" uri="/struts-tags" %>

<script type="text/javascript">

    var menusData=[];

    <s:iterator value="#session.user.menu" status="indx">
          var menuName= '<s:property value="nombre"/>';
          <s:iterator value="subMenus">
             var subMenu=['<s:property value="id_menu"/>', '<s:property value="nombre"/>', '<s:property value="enlace"/>', menuName, '<s:property value="orden"/>'];
             menusData.unshift(subMenu);
          </s:iterator>
    </s:iterator>

    var gridMenus = new Ext.grid.GridPanel({
        store: new Ext.data.GroupingStore({
            reader: new Ext.data.ArrayReader({}, [
               {name: 'id_menu'},
               {name: 'nombre'},
               {name: 'enlace'},
               {name: 'module'},
               {name: 'orden'}
            ]),
            data: menusData,
            sortInfo:{field: 'orden', direction: "ASC"},
            groupField:'module'
        }),
        columns: [
            {id:'module',header: "Module", width: 60, hidden: true, sortable: true, dataIndex: 'module'},
            {header: "Nombre", width: 20, sortable: true, dataIndex: 'nombre' }
        ],

        view: new Ext.grid.GroupingView({
            forceFit:true,
            groupTextTpl: '{[ values.rs[0].data["module"] ]}'
           // groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
        }),
        hideHeaders:true,
        frame:false,
        width: 180,
        height: 800,
        collapsible: true,
        animCollapse: false,
        autoScroll:true,
        border:false,
        title: '<s:text name="app.menu"/>',
        header : false, // ocultamos el titulo
        iconCls: 'icon-grid',
        listeners: {
           'rowclick':  function(grid, rowIndex, e) {
                    var record = grid.getStore().getAt(rowIndex);
                    var menuTitle = record.data.nombre;
                    var idMenu= record.data.id_menu;
                    var enlace= record.data.enlace;

                    abrirTab(menuTitle, idMenu, enlace);
           }
        }
    });
</script>