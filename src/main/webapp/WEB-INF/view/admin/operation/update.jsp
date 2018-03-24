<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
<form name="editForm" id="editForm" action="${basePath}/operation/update" method="post">
<table class="mytable" align="center">
	<tr>
		<td>操作名:</td>
		<td><input type="hidden" name="opId" value="${operation.opId}" />
			<input type="hidden" name="cls" value="${operation.cls}" />
			<input name="name" value="${operation.name}"  class="easyui-validatebox" required="true"/></td>
	</tr>
	<tr>
		<td>操作URL:</td>
		<td><input name="opt" value="${operation.opt}"  class="easyui-validatebox" required="true"/></td>
	</tr>
</table>
</form>
</div>
