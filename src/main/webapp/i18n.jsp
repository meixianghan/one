<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <script type="application/javascript" src="js/jquery-2.1.0.js"></script>
    <title>登录</title>
    <script type="text/javascript">
        $(function () {
            $('#language').find('a').click(function () {
                var language = $(this).data('value');
                document.cookie = "cookie_language=" + language +";expires=365";
                location.reload();
            });
        });
    </script>
</head>
<body>

<fmt:setBundle basename="mrerror_${system_language}"/>

<div id="footer">
    <div id="copyright">
        <span><fmt:message key="username_tip"/></span>
    </div>
    <div id="language">
        <span><fmt:message key="password_tip"/></span>
        <a href="#" data-value="zh_CN">中文</a>
        <span>|</span>
        <a href="#" data-value="en_US">English</a>
    </div>
</div>
</body>
</html>