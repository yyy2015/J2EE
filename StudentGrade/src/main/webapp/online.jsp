<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<%@ page import="java.lang.reflect.Array" %>
<html>
<head>
    <title>welcome</title>
</head>
<body>
<h2>welcome to Student Grade Query System!</h2>
<%!
//    ServletContext context = this.getServletConfig().getServletContext();
//    int total = (Integer) this.getServletConfig().getServletContext().getAttribute("total");
//    ArrayList<String> list = (ArrayList<String>) this.getServletConfig().getServletContext().getAttribute("online");
%>

<span>在线总人数：&nbsp&nbsp</span>
<%=(Integer) this.getServletConfig().getServletContext().getAttribute("total") %>
<br>
<span>在线用户数：&nbsp&nbsp</span>
<%=((ArrayList<String>)this.getServletConfig().getServletContext().getAttribute("online")).size() %>
<br>
<span>游客数：&nbsp&nbsp</span>
<%=(Integer) this.getServletConfig().getServletContext().getAttribute("total")-((ArrayList<String>)this.getServletConfig().getServletContext().getAttribute("online")).size()
%>
<br>
<span>在线用户列表：</span><br>
<%
    for(String user:(ArrayList<String>)this.getServletConfig().getServletContext().getAttribute("online") ){
        out.print(user+"<br>");
    }
%>

<a href="login.html">login</a>


</body>
</html>
