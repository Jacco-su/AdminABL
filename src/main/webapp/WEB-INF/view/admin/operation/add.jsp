<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
<form name="addForm" id="addForm" action="${basePath}/operation/add" method="post">
<table class="mytable" align="center">
	<tr>
		<td>操作名:</td>
		<td><input name="cls" type="hidden" value="${cls}" />
			<input name="name"  class="easyui-validatebox" required="true"/></td>
	</tr>
	<tr>
		<td>操作URL:</td>
		<td><input name="opt"  class="easyui-validatebox" required="true"/></td>
	</tr>
</table>
</form>
</div>
