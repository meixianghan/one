<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="y"  uri="http://www.mrerror.com/jsp/jstl/core" %>
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
    <y:TokenRepeat/>
    <input type="submit" value="put">
</form>
</form>
</body>
</html>
