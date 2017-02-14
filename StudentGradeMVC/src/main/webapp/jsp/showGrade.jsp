<%--
  Created by IntelliJ IDEA.
  User: yyy
  Date: 2017/1/3
  Time: 0:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<%@ page import="edu.nju.student.grade.model.Score" %>
<%@ taglib prefix="MyTag" uri="/WEB-INF/tlds/checkSession.tld" %>
<html>
<head>
    <title>show grade</title>
</head>
<body>

<MyTag:checkSession/>


<h2>欢迎您！&nbsp&nbsp <%=session.getAttribute("studentId") %> </h2>
<h4>课程号：&nbsp&nbsp课程名：&nbsp&nbsp各次测验得分：</h4>

<jsp:useBean id="scoreList" type="edu.nju.student.grade.business.ScoreListBean"
scope="session" ></jsp:useBean>
<jsp:useBean id="item" class="edu.nju.student.grade.model.Score"
scope="page" ></jsp:useBean>

<%
    for(int i=0;i<scoreList.getScoreList().size();i++){
        pageContext.setAttribute("item",(Score)scoreList.getScoreList().get(i));

%>

<tr>
    <td> <jsp:getProperty name="item" property="courseId"></jsp:getProperty> </td>
    <td> <jsp:getProperty name="item" property="courseName"></jsp:getProperty> </td>
    <%
        for(Double score : ((Score)pageContext.getAttribute("item")).getScoreList()){
            if(score == -1.0){
                out.print("&nbsp&nbsp&nbsp&nbsp<td>未参加</td>");
            }else {
                out.print("&nbsp&nbsp&nbsp&nbsp<td>" + score + "</td>");
            }
        }
    %>
</tr><br>
<% } %>
<br>
<a href="<%=request.getContextPath() + "/jsp/online.jsp"%>">查看在线用户</a><br><br>

<span>Click <a href=<%=response.encodeURL(request.getRequestURI()) %> >here</a> to reload this page.</span><br><br>

<form method="GET" action="/StudentGrade/Grade">
    <input type="submit" name="logout" value="logout">
</form>





</body>
</html>
