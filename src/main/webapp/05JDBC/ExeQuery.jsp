<%@page import="java.sql.ResultSet"%>
<%@page import="common.JDBConnect"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>회원 목록 조회 테스트</h2>
	<%
	JDBConnect jdbc = new JDBConnect();
	//정적 쿼리 실행을 위한 statement객체 생성 및 쿼리 준비
	
	String sql = "SELECT id, pass, name, regidate FROM member";
	Statement stmt= jdbc.con.createStatement();
	//행에 영향이 없는 쿼리를 실행할 경우 executeQuery()fmf tkdydgksek.
	ResultSet rs = stmt.executeQuery(sql);
	
	/*
	select의 결과는 ResultSet객체를 통해 반환하고, 그 갯수만큼
	반복하여 출력한다.
	*/
	while(rs.next()){ 
		String id = rs.getString(1);
		String pw = rs.getString(2);
		String name = rs.getString("name");
		java.sql.Date regidate = rs.getDate("regidate");
		
		out.println(String.format("%s %s %s %s", id, pw, name, regidate) +"<br/>");
	}
	jdbc.close();
	%> 
</body>
</html>