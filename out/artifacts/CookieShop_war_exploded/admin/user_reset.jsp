<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>重置密码</title>
	<meta charset="utf-8"/>
	<!-- 使用上下文路径来确保路径正确 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
</head>
<body>
<div class="container-fluid">

	<!-- 使用上下文路径来确保路径正确 -->
	<jsp:include page="header.jsp"></jsp:include>

	<br><br>

	<form class="form-horizontal" action="${pageContext.request.contextPath}/admin/user_reset" method="post">
		<input type="hidden" name="id" value="${param.id }">
		<div class="form-group">
			<label for="input_name_username_${param.id}" class="col-sm-1 control-label">用户名</label>
			<div class="col-sm-5">${param.username }</div>
		</div>
		<div class="form-group">
			<label for="input_name_email_${param.id}" class="col-sm-1 control-label">邮箱</label>
			<div class="col-sm-5">${param.email }</div>
		</div>
		<div class="form-group">
			<label for="input_name_password" class="col-sm-1 control-label">密码</label>
			<div class="col-sm-6">
				<input type="text" class="form-control" id="input_name_password" name="password" value="" required="required">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-1 col-sm-10">
				<button type="submit" class="btn btn-success">提交修改</button>
			</div>
		</div>
	</form>

	<span style="color:red;"></span>

</div>
</body>
</html>
