<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<div>
<form name="addUserDept" id="addUserDept" action="${basePath}/user/updateUserDept" method="post">
<table class="mytable" align="center">
	<tr>
		<td>用户名:</td>
		<td>${user.username}</td>
	</tr>
	
	<tr>
		<td>负责部门:</td>
		<td>
					<c:forEach items="${deptList}" var="dept" varStatus="s">
						<span><input name="deptIdList" type="checkbox" value="${dept.id}"/>${dept.name }</span>
						<c:if test="${(s.index+1)%5==0}"><br/></c:if>
					</c:forEach>
		</td>
	</tr>
</table>
	</form>
</div>
<script type="text/javascript">
    var deptIds = "${deptIds}";
	$.each(deptIds.split(','), function(i, v) {
		$('input[type=checkbox][name=deptIdList][value='+v+']').attr("checked", true );
	});
</script>