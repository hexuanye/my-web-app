<%--
  Created by IntelliJ IDEA.
  User: 何炫烨
  Date: 2024/10/29
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录入口</title>
</head>
<body>
<div class="amount">
    <div class="container">
        <div class="register" style="width: 300px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);">
            <c:if test="${!empty msg}">
                <div class="alert alert-success">${msg}</div>
            </c:if>
            <c:if test="${!empty failMsg}">
                <div class="alert alert-danger">${failMsg}</div>
            </c:if>
            <h2>用户登录</h2>
            <form action="${pageContext.request.contextPath}/user_login" method="post">
                <div class="input" style="margin-bottom: 15px;">
                    <label>用户名/邮箱 <span style="color: red;">*</span></label>
                    <input type="text" name="ue" placeholder="请输入用户名或邮箱" required>
                </div>
                <div class="input" style="margin-bottom: 15px;">
                    <label>密码 <span style="color: red;">*</span></label>
                    <input type="password" name="password" placeholder="请输入密码" required>
                </div>
                <div class="clearfix"></div>
                <div class="register-but text-center">
                    <input type="submit" value="提交">
                    <div class="clearfix"></div>
                </div>
            </form>
            <c:if test="${!empty msg}">
                <div class="alert alert-danger">${msg}</div>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
