<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>

<a href="<%=request.getContextPath() %>/board/boardList.do">게시판 목록</a>

<%
	if (session.getAttribute("midx") != null) { //MemberController.java에 세션에 담은 값 가져오기
		int midx = Integer.parseInt(session.getAttribute("midx").toString()); //Object타입이라서 형변환을 해야한다
		String memberName = (String)session.getAttribute("memberName");
		out.println("회원번호: "+midx);
		out.println("회원이름: "+memberName);
		%>
			<a href="<%=request.getContextPath()%>/member/memberLogOut.do">회원로그아웃</a>
		<%			
	}else{
		%>
			<a href="<%=request.getContextPath() %>/member/MemberJoin.do">회원가입하기</a>
			<a href="<%=request.getContextPath()%>/member/memberLogin.do">회원로그인</a>
			<a href="<%=request.getContextPath()%>/member/memberList.do">회원목록</a>
	<% }%>






</body>
</html> 