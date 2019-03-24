<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>登录</title>
</head>
<body>
<form action="/ssm/user/login" method="post">
    <fmt:message key="username_tip"/>:<input name="account"/><br>
    <fmt:message key="password_tip"/>:<input name="password" type="password"/><br>
        <input type="submit" value="<fmt:message key="login_button"/>"/>
    </form>
</body>
</html>