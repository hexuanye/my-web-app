<%--
  Created by IntelliJ IDEA.
  User: 何炫烨
  Date: 2024/12/1
  Time: 22:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>销售记录</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
</head>
<body>
<div class="container-fluid">

    <jsp:include page="header.jsp"></jsp:include>

    <br>

    <!-- 成功或失败消息 -->
    <c:if test="${!empty msg}">
        <div class="alert alert-success">${msg}</div>
    </c:if>
    <c:if test="${!empty failMsg}">
        <div class="alert alert-danger">${failMsg}</div>
    </c:if>
    <br/>

    <h3>销售记录</h3>
    <table class="table table-bordered table-hover">
        <tr>
            <th width="5%">ID</th>
            <th width="20%">商品 ID</th>
            <th width="20%">数量</th>
            <th width="20%">金额</th>
            <th width="15%">操作</th>
        </tr>

        <c:forEach items="${salesForms}" var="salesForm">
            <tr>
                <td>${salesForm.id}</td>
                <td>${salesForm.goodsId}</td>
                <td>${salesForm.number}</td>
                <td>${salesForm.money}</td>
                <td>
                    <a class="btn btn-danger" href="${pageContext.request.contextPath}/admin/salesform_delete?action=delete&id=${salesForm.id}">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>

