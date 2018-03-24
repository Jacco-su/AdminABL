<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<script type="text/javascript">
	var opstree = $('#ops');
	opstree.tree({
		url : '${basePath}/role/getOpTree',
		checkbox:true,
		cascadeCheck:false,
		onLoadSuccess:function(node,data){
			var ms = "${role.modules}";
			var ops = "${opIds}";
			var ids = (ms+","+ops).split(',');
			for(var i=0;i<ids.length;i++){
				var n = opstree.tree('find',ids[i]);
				if(n){
					opstree.tree('check',n.target);
				}
			}
		}
	});
	
</script>
<div>
<form name="editForm" id="editForm" action="${basePath}/role/update" method="post">
<table class="mytable" align="center">
	<tr>
		<td width="100">角色名:</td>
		<td align="left"><input type="hidden" size="40" name="roId" value="${role.roId }"/><input name="name" class="easyui-validatebox" required="true" value="${role.name}"/></td>
	</tr>
	<tr>
		<td width="100">排序:</td>
		<td align="left">
		
								<select name="orderId"  name="orderId" style="width:100px;">
											<c:forEach var="x" begin="10" end="30" step="1">
											               <option value="${x}" <c:if test="${role.orderId==x}">selected</c:if>>${x}</option>
											</c:forEach>
								</select>
		</td>
	</tr>
	<tr>
		<td>模块权限:</td>
		<td>
			<input type="hidden" id="modules" name="modules" value=""/>
			<input type="hidden" id="opIdList" name="opIdList" value=""/>
			<input type="hidden" id="forms" name="forms" value=""/>
			<div style="width: 100%">
				<ul id="ops" style="margin-top: 10px;">
				</ul>
			</div>
		</td>
	</tr>
</table>
</form>
</div>
