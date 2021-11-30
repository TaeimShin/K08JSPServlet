<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" buffer="1kb" autoFlush="false"%>
    
<!-- 
JSP는 화면상에 내용을 출력할떄 항상 버퍼에 저장한 후 
buffer 속성을 통해 버퍼 사용여부를 설정할 수 있고 
autoFlush 속성을 통해 버퍼가 꽉 찬 경우 내보내기를 할지를 설정할수있다. -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page 지시어 - buffer, autoFlush 속성 </title>
</head>
<body>
	<%
	//아래는 10 byte의 문자열을 100번 반복하므로 결과적으로 1000byte가 된다.
	for(int i = 1 ; i <= 100; i++){
		out.println("abcde12345");
	}
	//위에서 출력된 문자열과 html태그가 합쳐져서 버퍼에 설정된 용량을 초과하게된다.
	//따라서 buffer의 용량을 늘리거나, autoFlush를 true로 변경해야 한다.
	%>
</body>
</html>