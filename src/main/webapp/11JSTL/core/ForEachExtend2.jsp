<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedList"%>
<%@page import="common.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h4>List컬렉션 사용하기</h4>
	<%
	LinkedList<Person> lists = new LinkedList<Person>();
	lists.add(new Person("맹사성",34));
	lists.add(new Person("장영실", 44));
	lists.add(new Person("신숙주", 54));	
	%>
	<!-- set태그로 변수 설정(영역에저장) -->
	<c:set var="lists" value="<%= lists %>" />
	<c:forEach items='${lists }' var="list"> 
	<li>
		이름 : ${ list.name }, 나이 : ${list.age }
	</li>
	</c:forEach>
	<h4>Java코드를 통한 출력</h4>
	<%
	for(Person p : lists){
		out.println("이름:" +p.getName()+", 나이: "+ p.getAge() +"<br>");
	}
	
	%>
	
	<h4>Map 컬렉션 사용하기</h4>
	<%
	Map<String,Person> maps = new HashMap<String,Person>();
	maps.put("1st", new Person("맹사성",34));
	maps.put("2nd", new Person("장영실", 44));
	maps.put("3rd", new Person("신숙주", 54));
	%>
	<c:set var="maps" value="<%= maps %>" />
	<!-- 
	map컬렉션사용시 key값을 별도로 얻어올 필요없이
	객체.key혹은 객체.value를 통해 값을 출력할 수 있다.
	 -->
	<c:forEach items='${maps }' var="map">
	<li> Key=> ${map.key } <br />
		Value => 이름 : ${ map.value.name }, 나이 : ${ map.value.age }
	</li>
	</c:forEach>
	
	<h4>Java코드를 통한 출력</h4>
	<%
	//Map컬렉션은 항상 key값을 먼저 얻어와야 한다.
	Set<String> keys = maps.keySet();
	for(String k : keys){
		Person p = maps.get(k);
		out.println(p.getName()+" "+p.getAge()+"<br>");
		out.println("Key =>" +k +"<br>");
		out.println("Value =>" +p.getName() +", "+p.getAge()+"<br>");
		
	}
	/*
	JSTL의 확장for문을 사용하면 key값을 별도로 얻어와야 하는 번거로움이 없으므로 훨씬 편리하다.
	*/
	
	%>
	
</body>
</html>