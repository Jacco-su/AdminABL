function load(){
	$('#deptTree').tree({
		checkbox : false,
		url : '../dept/getDeptTree',
		checkbox:false,
		onlyLeafCheck:true
	});
	
	$('#selectDept').window({
		title:'选择部门',
		width:300,
		height:500,
		closed:true,
		modal:true
	});
}

function selectDept(){
	var id = "";
	var show = "";
	var selections = $('#deptTree').tree('getSelected');
	if(selections){
		id = selections.id ;
		show = selections.text;
	}
	$('#deptId').val(id);
	$('#deptName').val(show);
	$('#selectDept').window('close');
	$('#deptTree').tree('reload');
}
