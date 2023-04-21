<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<%//@ page import = "java.util.*"%>
<%//@ page import = "example1230.domain.*"%>
<%
//ArrayList<BoardVo> blist = (ArrayList<BoardVo>)request.getAttribute("blist");
//PageMaker pm = (PageMaker)request.getAttribute("pm");
%>       
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title>게시판 목록</title>
	
	<style>
		.inner{width:500px;}
		a{text-decoration:none;}
		
		#top{float:right;}
		
		#main{clear:both;}
		#main_table{border:1px solid #000;}
		#main_table td{border:1px solid #000; text-align:center;}
		#main_table tr td:nth-child(1){width:100px;}
		#main_table tr td:nth-child(2){width:100px;}
		#main_table tr td:nth-child(3){width:80px;}
		#main_table tr td:nth-child(4){width:130px;}
		#main_table tr td:nth-child(5){width:80px;}
		
		#page_num{margin-left:175px;}
		
		#write{border:1px solid #000; border-radius:3px; padding:3px; font-size:14px;}
		
	</style>
</head>
<body>
	<div>
		<h2>게시판 목록</h2>
	</div>
	<div class="inner">
		<form name="frm" action="${pageContext.request.contextPath }/board/boardList.do" method="post">
			<table id="top">
				<tr>
					<td>
						<select name="searchType">
							<option value="subject">제목</option>						
							<option value="writer">작성자</option>						
						</select>
					</td>
					<td><input type="text" name="keyword"></td>
					<td><input type="submit" name="submit" value="검색"></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="main" class="inner">
		<form>
			<table id="main_table">
				<tr>
					<td>게시물 번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>날짜</td>
				</tr> 
				<!-- 2023-03-16 -->
				<c:forEach var="bv" items="${blist}">
				<tr>
					<td>${bv.bidx}</td>
					<td>
					<c:forEach var = "i" begin="1" end="${bv.level_ }" step="1">
						&nbsp;&nbsp;
						<c:if test="${i == bv.level_ }">
							ㄴ						
						</c:if>
					</c:forEach>
					<a href="${pageContext.request.contextPath }/board/boardContents.do?bidx=${bv.bidx}">${bv.subject}</a></td>
					<td>${bv.writer}</td>
					<td>${bv.writeday.substring(0,10)}</td>
				</tr>
				</c:forEach>
			</table>
		</form>
	</div>
	<div id="page_num" class="inner">
		<table>
			<tr>
				<td>
					<c:if test="${pm.prev == true }">
						<a href="${pageContext.request.contextPath }/board/boardList.do?page=${pm.startPage-1}&searchType=${pm.scri.searchType}&keyword=${pm.encoding(pm.scri.keyword)}">◀</a>
					</c:if>
				</td>
				<td>
						<c:forEach var = "i" begin="${pm.startPage }" end = "${pm.endPage }" step = "1" >
							<a href="${pageContext.request.contextPath }/board/boardList.do?page=${ i }&searchType=${pm.scri.searchType} %>&keyword=${pm.encoding(pm.scri.keyword)}">${ i }</a>	
						</c:forEach>
				</td>
				<td>
					<c:if test="${pm.next && pm.endPage > 0 }">
						<a href="${pageContext.request.contextPath }/board/boardList.do?page=${pm.endPage+1}&searchType=${pm.scri.searchType}&keyword=${pm.encoding(pm.scri.keyword)}">▶</a>
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div>
		<a href="${pageContext.request.contextPath }/board/boardWrite.do" id="write">글쓰기</a>
	</div>
</body>
</html>