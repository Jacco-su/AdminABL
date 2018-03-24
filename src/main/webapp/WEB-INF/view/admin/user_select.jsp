<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="selectUser" >
			<ul id="depttree" style="margin-top: 10px;"></ul>
</div>
<script type="text/javascript">
$(function() {
   $('#depttree').tree({
		checkbox : false,
		url : '${basePath}/userselect/getDeptUserTree',
		checkbox:true,
		onBeforeExpand:function(node,param){
		}
	});	
});
</script>