<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<!doctype html>
<html lang='nl'>
<head>
<link rel='stylesheet'
	href='${pageContext.servletContext.contextPath}/styles/default.css'>
<title>Werknemers</title>
</head>
<body>
	<a href="<c:url value='/'/>">Menu</a>
	<h1>Werknemers</h1>
	<ul>
		<c:forEach items='${werknemers}' var='werknemer'>
			<li>${werknemer.naam} (${werknemer.filiaal.naam})</li>
		</c:forEach>
	</ul>
</body>
</html>