<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>회원가입 UI 설계</title>
	<style>
		*{margin:0 auto;}
		input{height:20px;}
		table{border-spacing: 0 10px;}
		#memberIdCheck, #end{padding-bottom:21px;}
	</style>	
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			//자동실행영역
		});
		
		function check(){
			//alert("test");
			
			if($("#memberId").val()==""){
				alert("아이디를 입력하세요.")
				$("#memberId").focus();
				return;
			}else if($("#memberPwd").val()==""){
				alert("비밀번호를 입력하세요.")
				$("#memberPwd").focus();
				return;
			}else if($("#memberPwd2").val()==""){
				alert("비밀번호 확인을 입력하세요.")
				$("#memberPwd2").focus();
				return;
			}else if($("#memberName").val()==""){
				alert("이름을 입력하세요.")
				$("#memberName").focus();
				return;
			}else if($("#memberPhone").val()==""){
				alert("전화번호를 입력하세요.")
				$("#memberPhone").focus();
				return;
			}else if($.isNumeric($("#memberPhone").val())==false){
				alert("숫자만 입력하세요.")
				$("#memberPhone").val("");
				$("#memberPhone").focus();
				return;
			}else if($("#memberEmail").val()==""){
				alert("이메일을 입력하세요.")
				$("#memberEmail").focus();
				return;
			}else if($("#memberBirth").val()==""){
				alert("생년월일을 입력하세요.")
				$("#memberBirth").focus();
				return;
			}else if($.isNumeric($("#memberBirth").val())==false){
				alert("숫자만 입력하세요.")
				$("#memberBirth").val("");
				$("#memberBirth").focus();
				return;
			}
			else if($("#memberIdCheck").val() != "Y"){
		 	alert("아이디 중복체크를 하세요");
			 $("#memberId").focus();
			 return;
			}
			
			var fm = document.frm;
			//이 경로로 데이터를 감추어서 전송한다
			fm.action = "<%=request.getContextPath()%>/member/memberJoinAction.do";
			fm.method = "post";
			fm.submit();
			
		return;
		}
		
	 	function IdCheck(){
		//	alert("아이디 체크창입니다.");
			let memberId=$("#memberId").val();
			
			$.ajax({
				url: "<%=request.getContextPath()%>/member/memberIdCheck.do",		
				method: "POST",
				data: {"memberId": memberId },
				dataType: "json",
				success : function(data){
					if(data.value =="0"){
						alert("사용가능한 아이디입니다.");
						$("#memberIdCheck").val("Y");
					}else{
						alert("사용불가한 아이디입니다.");
					}
				},
				error : function(request,status,error){
					alert("다시 시도하시기 바랍니다.");			
				}		
			});
			return;
		} 
		
	</script>
</head>
<body>
	<div style="margin:20px 0 20px 0;">
		<h2 style="text-align:center">회원가입</h2>
	</div>
	<div>
		<form name="frm" id="frm">
			<table>
				<tr>
					<td>아이디</td>
					<td><input type="text" name="memberId" id="memberId" value=""></td>
					<td><input type="button" name="memberIdCheck" id="memberIdCheck" value="아이디 중복 확인" onclick="IdCheck()"
						style="margin-left:10px;"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="text" name="memberPwd" id="memberPwd" value=""></td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td><input type="text" name="memberPwd2" id="memberPwd2" value=""></td>
				</tr>
				<tr>
					<td>이름</td>
					<td><input type="text" name="memberName" id="memberName" value=""></td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><input type="text" name="memberPhone" id="memberPhone" placeholder="ex)01000000000" onfocus="this.placeholder = ''"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" name="memberEmail" id="memberEmail" value=""></td>
				</tr>
				<tr>
					<td>성별</td>
					<td><input type="radio" name="memberGender" id="memberMale" value="남성" checked>남성</td>
					<td><input type="radio" name="memberGender" id="memberFemale" value="여성">여성</td>
				</tr>
				<tr>
					<td>주소</td>
					<td>
						<select name="memberAddr" id="memberAddr">
							<option value="전주">전주</option>
							<option value="경기">경기</option>
							<option value="서울" selected>서울</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>생년월일</td>
					<td><input type="text" name="memberBirth" id="memberBirth" placeholder="ex)19001230" onfocus="this.placeholder = ''"></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="button" value="확인" name="end" id="end" onclick="check();"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>