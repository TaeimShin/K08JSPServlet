<%@ page import="model1.board.BoardDAO"%> 
<%@ page import="model1.board.BoardDTO"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 글쓰기 처리전 로그인확인 -->
 <%@ include file="./IsLoggedIn.jsp"%> 
 
 <% 
 String title = request.getParameter("title"); 
 String content = request.getParameter("content"); 
 
 //사용자가 입력한 폼값을 저장하기 위해 DTO객체 생성
BoardDTO dto = new BoardDTO(); 
dto.setTitle(title); 
dto.setContent(content); 
dto.setId(session.getAttribute("UserId").toString()); 

//DAO객체 생성 및DB연결
BoardDAO dao = new BoardDAO(application); 
//dto객체를 매개변수로 전달하여 레코드insert처리
int iResult = dao.insertWrite(dto); 
dao.close(); 

if (iResult == 1) {
	response.sendRedirect("List.jsp"); 	
}
else { 
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out); 
}


%>