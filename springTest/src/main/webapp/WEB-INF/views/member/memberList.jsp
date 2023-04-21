<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import = "com.myezen.myapp.domain.*" %>
<%@ page import = "java.util.*" %>
<%
	ArrayList<MemberVo> alist =(ArrayList<MemberVo>)request.getAttribute("alist");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원목록 UI</title>

<style>
	table{border:1px solid #000;}
	td{border:1px solid #000; height:20px; text-align:center;}
	tr td:nth-child(1){width:130px;}
	tr td:nth-child(2){width:65px;}
	tr td:nth-child(3){width:65px;}
	tr td:nth-child(4){width:130px;}
</style>
</head>
<body>
	<form>
		<table>
			<tr>
				<td>번호</td>
				<td>아이디</td>
				<td>이름</td>
				<td>탈퇴여부</td>
				<td>가입일</td>
			</tr>
			<% for (MemberVo mv : alist) {%>
			<tr>
				<td><%=mv.getMidx() %></td>
				<td><%=mv.getMemberid() %></td>
				<td><%=mv.getMembername() %></td>
				<td><%=mv.getDelyn() %></td>
				<td><%=mv.getWriteday().substring(0,10) %></td>
			</tr>
			<%} %>			
		</table>
	</form>
</body>
</html>