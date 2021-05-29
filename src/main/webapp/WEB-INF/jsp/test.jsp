<%@page import="java.util.Iterator"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>

</head>
<body>
	<%
	String user_id = (String)session.getAttribute("id");
	 Map<String, String[]> map = request.getParameterMap();
     Set<String> keySet=  map.keySet();
     Iterator<String> itr = keySet.iterator();
     while(itr.hasNext()) {
         String key = itr.next();
         String[] values = map.get(key);
         System.out.print(user_id);
         System.out.print(key);
         System.out.print(" : ");
         System.out.println( Arrays.toString(values)  );
     }

	%>
</body>
</html>