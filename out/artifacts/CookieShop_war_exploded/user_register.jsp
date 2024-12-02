<%--
  Created by IntelliJ IDEA.
  User: 何炫烨
  Date: 2024/10/29
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>购物商城账号注册</title>
</head>
<body>
<div class="amount">
    <div class="container">
        <div class="register">
            <c:if test="${!empty msg}">
                <div class="alert alert-danger">${msg}</div>
            </c:if>
            <h2>用户注册</h2>
            <form action="${pageContext.request.contextPath}/user_register" method="post">
                <div class="input">
                    <label>用户名: <span class="required">*</span></label>
                    <input type="text" name="username" placeholder="请输入用户名" required>
                </div>

                <div class="input">
                    <label>邮箱: <span class="required">*</span></label>
                    <input type="email" name="email" placeholder="请输入邮箱" required>
                </div>

                <div class="input">
                    <label>密码: <span class="required">*</span></label>
                    <input type="password" name="password" placeholder="请输入密码" required>
                </div>

                <div class="input">
                    <label>收货人姓名:</label>
                    <input type="text" name="name" placeholder="请输入收货人姓名">
                </div>

                <div class="input">
                    <label>收货电话:</label>
                    <input type="tel" name="phone" placeholder="请输入收货电话">
                </div>

                <div class="input">
                    <label>收货地址:</label>
                    <input type="text" name="address" placeholder="请输入收货地址">
                </div>

                <div class="clearfix"></div>
                <div class="register-but text-center">
                    <input type="submit" value="提交">
                    <div class="clearfix"></div>
                </div>
            </form>
            <div class="login-link text-center">
                <p>已有账号？ <a href="${pageContext.request.contextPath}/user_login.jsp">直接登录</a></p>
            </div>
        </div>
    </div>
</div>

</body>
</html>
