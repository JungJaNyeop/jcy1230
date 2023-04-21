<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%	String msg="";
if(request.getAttribute("msg") != null){
	msg = (String)request.getAttribute("msg");
}
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>회원로그인</title>
        <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
        <style>

			
        </style>
        <script>
        $(document).ready(function(){
        	var msg = "<%=msg%>";
        	if(msg != ""){
        		alert(msg);
        	}
        });
        
            $(document).ready(function(){
                $("#btn1").click(function(){
                	if($("#memberId").val()==""){
        				alert("아이디를 입력하세요.")
        				$("#memberId").focus();
        				return;
        			}else if($("#memberPwd").val()==""){
        				alert("비밀번호를 입력하세요.")
        				$("#memberPwd").focus();
        				return;
        			}

             //폼태그 값 보내기 
             var fm = document.frm;
       			 //이 경로로 데이터를 감추어서 전송한다 //자바스크립트로 frm.속성부여하기 //2023.03.13 추가 가상주소
       			 fm.action ="<%=request.getContextPath()%>/member/memberLoginAction.do";  
       			 fm.method = "POST";
       			 fm.submit();
 
                });//$("#btn1").click(function(){-----end
            });//$(document).ready(function(){-----end
     
                	
        </script>
    </head>
    <body>
	    <div class="main_out">
	    <form id="frm" name="frm">
	        <table class="main">
	            <tr><td><h1>회원로그인</h1></td></tr>
	            <tr><td><h2><label for="memberId">아이디</label></h2></td></tr>
	            <tr>
	            	<td>
	            		<input id="memberId" name="memberId" class="btn2" type="text" placeholder="예)hong1234" value="">
	            	</td>
	            </tr>
	            <tr><td><h2><label for="memberPwd">비밀번호</label></h2></td></tr>
	            <tr><td><input id="memberPwd" name="memberPwd" class="btn2" type="password" placeholder="최소 5자리"></td></tr>
	            
	            <tr><td><input class="btn2" id="btn1" type="submit" value="확인"></td></tr>
	        </table>
	    </form>
	    </div>
    </body>
</html>