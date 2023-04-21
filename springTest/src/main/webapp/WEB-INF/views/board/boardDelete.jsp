<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%//@page import="example1230.domain.BoardVo"%>    
<%// BoardVo bv = (BoardVo)request.getAttribute("bv"); %><!-- 가져오는게 오브젝트타입이라서 형변환을 시켜줘야한다 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시판 삭제</title>

	<style>
		table{border:1px solid #000;}
		td{border:1px solid #000;}
		
	</style>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script type="text/javascript">
	function check(){	
		
		var fm = document.frm;	
			
		if (fm.pwd.value == "" ){
			alert("비밀번호를 입력하세요");
			fm.pwd.focus();
			return;
		}
		
		var flag = confirm("삭제하시겠습니까?");
		if (flag == false) {
			return;
		}
		
		fm.action = "<%=request.getContextPath()%>/board/boardDeleteAction.do";
		fm.method="post";
		fm.submit();
		return;
		}
	</script>	
</head>
<body>
	<div>
		<p>게시판 삭제</p>
	</div>
	<div>
		<form name="frm" id="frm" method="post">
		<input type="hidden" name="bidx" value="${bv.bidx}"> <%-- <%=bv.getBidx()%> --%>
			<table class="main">			
				<tr>
					<td colspan="2">글 삭제</td>
				</tr>
				<tr>
					<td colspan="2" >글 제목:${bv.subject}</td>
				</tr>
				<tr>
					<td><label for="memberPwd">비밀번호</label></td>
					<td><input type="password" id="pwd" name="pwd" ></td>
				</tr>
				<tr>
					<td colspan="2" ><input id="btn1" type="submit" value="확인" onclick="check();"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>