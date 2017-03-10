<%--
  Created by IntelliJ IDEA.
  User: yyy
  Date: 2017/3/7
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>login</title>
</head>
<body>

    <form action="/Grade/" method="post">
        账号：<input type="text" name="studentId"><br>
        密码：<input type="password" name="password"><br>
        <input type="submit" value="登录">
    </form>
    <br>
    <a href="online.jsp">查看在线用户</a>

</body>
</html>
