<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<script type="text/javascript">
	var opstree = $('#ops');
	opstree.tree({
		url : '${basePath}/role/getOpTree',
		checkbox:true,
		cascadeCheck:false
	});
</script>
<div>
<form name="addForm" id="addForm" action="${basePath}/role/add" method="post">
<table class="mytable" align="center">
	<tr>
		<td width="100">角色名:</td>
		<td align="left"><input name="name" size="40"  class="easyui-validatebox" required="true"/></td>
	</tr>
	<tr>
		<td width="100">排序:</td>
		<td align="left">
		
		
								<select name="orderId"  name="orderId" style="width:100px;">
											<c:forEach var="x" begin="10" end="30" step="1">
											               <option value="${x}">${x}</option>
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