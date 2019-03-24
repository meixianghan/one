<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>RestAPI</title>
</head>
<body>
    <!-- 通过HiddenHttpMethodFilter过滤器的改写form支持其他请求方式 -->
    <form action="rest/users/4/8899" method="post">
        <input type="hidden" name="_method" value="PUT"><!--修改-->
        <input type="hidden" name="token" value="${token}" />
        <input type="submit" value="put">
    </form>

    <form action="rest/users/yaya/9999" method="post"><!--创建-->
        <input type="submit" value="post">
    </form>

    <form action="rest/users/1" method="get"><!--获取当个-->
        <input type="submit" value="get">
    </form>

    <form action="rest/users" method="get"><!--获取所有-->
        <input type="submit" value="get">
    </form>

    <form action="rest/users/1" method="post">
        <input type="hidden" name="_method" value="DELETE"><!--删除-->
        <input type="submit" value="delete">

    </form>
</body>
</html>
