<%--
  Created by IntelliJ IDEA.
  User: 何炫烨
  Date: 2024/12/1
  Time: 18:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>用户记录</title>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
</head>
<body>
<div class="container-fluid">

    <!-- 通用头部 -->
    <jsp:include page="header.jsp"></jsp:include>

    <br>

    <!-- 消息提示 -->
    <c:if test="${!empty msg}">
        <div class="alert alert-success">${msg}</div>
    </c:if>
    <c:if test="${!empty failMsg}">
        <div class="alert alert-danger">${failMsg}</div>
    </c:if>
    <br/>

    <!-- 页面标题 -->
    <h3>用户浏览记录</h3>
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th width="5%">ID</th>
            <th width="20%">用户名</th>
            <th width="20%">商品名称</th>
            <th width="40%">浏览时间</th>
            <th width="15%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${historyPage.data != null && not empty historyPage.data}">
                <c:forEach items="${historyPage.data}" var="record">
                    <tr>
                        <td>${record.id}</td>
                        <td>${record.username}</td>
                        <td>${record.goodsname}</td>
                        <td>${record.datatime}</td>
                        <td>
                            <a class="btn btn-danger"
                               href="${pageContext.request.contextPath}/admin/history_delete?id=${record.id}">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="5" class="text-center">暂无记录</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <!-- 分页导航 -->
    <div class="text-center">
        <ul class="pagination">
            <c:if test="${historyPage.pageNumber > 1}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/history?id=${userId}&pageNumber=${historyPage.pageNumber - 1}">
                        上一页
                    </a>
                </li>
            </c:if>
            <li class="active">
                <a>当前页: ${historyPage.pageNumber}</a>
            </li>
            <c:if test="${historyPage.pageNumber < historyPage.totalPage}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/history?id=${userId}&pageNumber=${historyPage.pageNumber + 1}">
                        下一页
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
</body>
</html>
