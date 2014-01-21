<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<!doctype html>
<html lang='nl'>
<head>
<title>Filiaal ${filiaal.naam} wijzigen</title>
<link rel='stylesheet'
	href='${pageContext.servletContext.contextPath}/styles/default.css'>
</head>
<body>
	<a href="<c:url value='/'/>">Menu</a>
	<h1>Filiaal ${filiaal.naam} wijzigen</h1>
	<spring:url value='/filialen/{id}' var='url'>
		<spring:param name='id' value='${filiaal.id}' />
	</spring:url>
	<form:form action='${url}' method='post' commandName='filiaal'>
		<jsp:include page='filiaalformfields.jsp' />
		<input type='submit' value='Wijzigen'>
	</form:form>
</body>
</html>