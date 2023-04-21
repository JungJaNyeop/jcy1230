<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%//@ page import = "com.myezen.myapp.domain.BoardVo" %>
<%//BoardVo bv = (BoardVo)request.getAttribute("bv"); %>    
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>게시판 내용</title>
	
	<style>
		ul{list-style:none;}
		li{display:inline-block;}
		a{text-decoration:none;}
		.btn1{border:1px solid #000; border-radius:3px; padding:3px; font-size:14px;}
		
		table{border:1px solid #000;}
		td{border:1px solid #000;}
		tr td:nth-child(1){width:80px;}
		tr td:nth-child(2){width:450px;}
		img{width:400px; height:100px;}
	</style>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script type="text/javascript">
		/*파일 다운로드*/
		$(document).ready(function(){
			
			var originalFileName= getOriginalFileName("${bv.filename}");
			
			var str = "";
			var str2 = getImageLink("${bv.filename}");
						
			str = "<div><a href='${pageContext.request.contextPath}/board/displayFile.do?fileName=" + str2 + "&down=1'>" + originalFileName + "</a></div>";
			
			$("#download").html(str);
			
			$.boardCommentList(0);//함수호출  2023-04-19 //리스트꺼내오기 
			
			//댓글달기 2023-04-19
			$("#save").click(function(){
				var bidx = ${bv.bidx};
				var cwriter = $("#cwriter").val();
				var ccontents = $("#ccontents").val();
				var nextBlock = $("#nextBlock").val();
				var midx = ${sessionScope.midx};
				
				$.ajax({
						
					type: "post",
					url: "${pageContext.request.contextPath}/comment/commentWrite.do",
					dataType: "json",
					data: {
						"bidx": bidx,
						"cwriter": cwriter,
						"ccontents": ccontents,
						"nextBlock": nextBlock,
						"midx": midx
						
					},
					cache: false,
					success: function(data) {
						//alert("등록성공");
						$.boardCommentList(0);//함수호출  2023-04-19 //리스트꺼내오기 
						$("#cwriter").val("");//빈칸으로 만들어주기  
						$("#ccontents").val("");
					},
					error: function() {
						alert("댓글 등록실패");
					}
				});//ajax-end

			});//#save-end
		$("#more").click(function(){
			alert("더보기");
			var nextBlock = $("#nextBlock").val();
			
			 $.ajax({
				
				type: "get",
				url : "${pageContext.request.contextPath}/comment/${bv.bidx}/"+nextBlock+"/more.do",
				dataType : "json",
				cache : false,
				success : function(data){
				//	alert("성공");
				//	console.log(data);
				//	alert(JSON.stringify(data));
				$("#nextBlock").val(data.nextBlock);			
				$.boardCommentList(nextBlock);
				
				},
				error : function(){
					alert("nextblock실패");
				}
				
				
			});
			
		});
		
		});//ready-end
		
		
		//제이쿼리 함수 
		//댓글리스트 뽑아오기 2023-04-19
		$.boardCommentList = function(nb){
	
			var nextBlock;
			if (nb ==0){
			 nextBlock = 1;
			}else{
			 nextBlock = nb;	
			}
			
			$.ajax({			
				type: "get",
				url: "${pageContext.request.contextPath}/comment/${bv.bidx}/"+nextBlock+"/commentList.do",
				dataType: "json",		
				cache: false,
				success: function(data){
				//	alert("성공");
				//	console.log(data);			
				//	alert(JSON.stringify(data));
					commentList(data.alist);
					
					if(data.moreView =="N"){
						$("#morebtn").css("display","none");
					}else{
						$("#morebtn").css("display","block");
					}
					
				} ,
				error:function(){
					alert("리스트뽑아오기 실패");				
				}			
			});		
		}
		
		/*파일이름 원본 추출*/
		function getOriginalFileName(fileName){
			
			var idx = fileName.lastIndexOf("_")+1;
		
			return fileName.substr(idx);
		}
		
		//파일이 이미지일 경우
		function getImageLink(fileName){
			
			if(!checkImageType(fileName)){
				return fileName;
			}
						
			var front = fileName.substr(0,12); //위치 폴더 뽑기
			
			var end = fileName.substr(14); //파일 이름 뽑기
			
			return front+end;
		}
		
		/*파일이 이미지인지 확인*/
		function checkImageType(fileName){
			
			var pattern = /jpg$|gif$|png$|jpeg$/i;
			
			return fileName.match(pattern);
			
		}
		
		function commentList(data){
			var str = "";	
			str ="<tr><td>이름</td><td>내용</td></tr>"
			 
			$(data).each(function(){		
			str= str+ "<tr><td>"+this.cwriter+"</td><td>"+this.ccontents+"</td><tr>"
			});	
			
			$("#tbl").html("<table>"+str+"</table>"); 
			   
			return;
		}
	</script>
</head>
<body>
	<div>
		<h2>내용 보기</h2>
	</div>
	<div>
		
			<table>
				<tr>
					<td >제목</td>
					<td>${bv.subject} &nbsp; 조회수(${bv.viewcnt})</td>
				</tr>
				<tr>
					<td>파일 다운로드</td>
					<td>
						<div id="download"></div>
					</td>
				</tr>
				<tr>
					<td>이미지</td>
					<td>
						<c:choose>
							<c:when test="${bv.filename == null }"></c:when>
							<c:otherwise>
								<c:set var="exp"  value="${bv.filename.substring(bv.filename.length()-3, bv.filename.length()) }"></c:set>
								
								<c:if test="${exp.equals('jpg') || exp.equals('gif') || exp.equals('png') }"></c:if>
								<img src="${pageContext.request.contextPath}/board/displayFile.do?fileName=${bv.filename}">
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				<tr>
					<td style="height:200px;">내용</td>
					<td>${bv.contents}</td>
				</tr>
				<tr>
					<td>작성자</td>
					<td>${bv.writer}</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:right;">
						<a href="${pageContext.request.contextPath}/board/boardModify.do?bidx=${bv.bidx }">수정</a>
						<a href="${pageContext.request.contextPath}/board/boardDelete.do?bidx=${bv.bidx }">삭제</a>
						<a href="${pageContext.request.contextPath}/board/boardReply.do?bidx=${bv.bidx }&originbidx=${bv.originbidx }&depth=${bv.depth}&level_=${bv.level_}">답변</a>
						<a href="${pageContext.request.contextPath}/board/boardList.do">목록</a>							
					</td>
				</tr>
			</table>
			<table>
				<tr>
					<td>이름</td>
					<td><input type="text" name="cwriter" id="cwriter" size="10"></td>
					<td></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="ccontents" id="ccontents" cols=50 rows=3 placeholder="내용을입력하세요"></textarea></td>
					<td><input type="button" name="save" id="save" value="확인"></td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td><a href="${pageContext.request.contextPath}/commun/communDeleteAction.do?bidx=${bv.bidx }" onclick="">삭제</a></td>
				</tr>
			</table>			
		
	</div>
		<input id="nextBlock" type="text" value="2" />			
			<div id="tbl"></div>
			<div id="morebtn">
				<button id="more">더보기</button>
			</div>
</body>
</html>