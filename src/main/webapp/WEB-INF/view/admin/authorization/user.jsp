
    <meta charset="UTF-8">
    <title>Basic ComboGrid - jQuery EasyUI Demo</title>
    <%--<link rel="stylesheet" type="text/css" href="../../themes/default/easyui.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="../../themes/icon.css">--%>
    <%--<link rel="stylesheet" type="text/css" href="../demo.css">--%>
    <%--<script type="text/javascript" src="../../jquery.min.js"></script>--%>
    <%--<script type="text/javascript" src="../../jquery.easyui.min.js"></script>--%>


    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="${basePath}/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/jquery.easyui.min.1.2.2.js"></script>
    <script type="text/javascript" src="${basePath}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/windowControl.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui/toolbar.js"></script>


    <div id="selectUsers" style="margin:20px 0"></div>
<select class="easyui-combogrid" style="width:250px" data-options="
			panelWidth: 500,
    idField: 'itemid',
    textField: 'productname',
    url: 'datagrid_data1.json',
    method: 'get',
    columns: [[
    {field:'itemid',title:'Item ID',width:80},
    {field:'productname',title:'Product',width:120},
    {field:'listprice',title:'List Price',width:80,align:'right'},
    {field:'unitcost',title:'Unit Cost',width:80,align:'right'},
    {field:'attr1',title:'Attribute',width:200},
    {field:'status',title:'Status',width:60,align:'center'}
    ]],
    fitColumns: true
    ">
</select>
