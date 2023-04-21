<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%//@ page import = "example1230.domain.BoardVo" %>
<%// BoardVo bv = (BoardVo)request.getAttribute("bv"); %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>게시판 답변</title>
	
	<style>
		table{border:1px solid #000;}
		td{border:1px solid #000;}
		tr td:nth-child(2){width:400px;}
		
	</style>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//자동실행영역
		});
		
		function check(){
			//alert("test");
			
			if($("#subject").val()==""){
				alert("제목을 입력하세요.")
				$("#subject").focus();
				return;
			}else if($("#det").val()==""){
				alert("내용을 입력하세요.")
				$("#det").focus();
				return;
			}else if($("#writer").val()==""){
				alert("작성자를 입력하세요.")
				$("#writer").focus();
				return;
			}else if ($("#pwd").val()==""){//"---2023-03-17----jsp 게시판-삭제-비밀번호추가---" 
				alert("비밀번호를 입력하세요");
				fm.pwd.focus();
				return;
			}
			var fm = document.frm;
			fm.action = "<%=request.getContextPath()%>/board/boardReplyAction.do";
			fm.enctype="multipart/form-data";
			fm.method = "post";
			fm.submit();
			return;
		}
	</script>
</head>
<body>
	<div>
		<h2>게시판 답변</h2>
	</div>
	<div>
		<form name="frm">
		<input type="hidden" name="bidx" value="${bv.bidx }">
		<input type="hidden" name="originbidx" value="${bv.originbidx }">
		<input type="hidden" name="depth" value="${bv.depth }">
		<input type="hidden" name="level_" value="${bv.level_ }">
			<table>
				<tr>
					<td>제목</td>
					<td>
						<input type="text" id="subject" name="subject">
					</td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="contents" cols="50" rows="5"
					 id="contents"></textarea></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" id="writer" name = "writer"maxlength='5'></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input id="pwd" name="pwd" type="password" maxlength="20" placeholder="비밀번호를 입력해 주세요."></td>
				</tr>
				<tr>
					<td>파일첨부</td>
					<td><input type="file" name="filename"></td>
				</tr>
				<tr>
					<td colspan='2'>
					<input type="button" value="확인" onclick="check()">
					<input type="reset" value="리셋">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body> 
</html>