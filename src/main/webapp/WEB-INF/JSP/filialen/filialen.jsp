<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<link rel='stylesheet'
	href='${pageContext.servletContext.contextPath}/styles/default.css'>
<title>Filialen</title>
</head>
<body>
	<a href="<c:url value='/'/>">Menu</a>
	<h1>Filialen</h1>
	<c:forEach items='${filialen}' var='filiaal'>
		<spring:url var='url' value='/filialen/{id}'>
			<spring:param name='id' value='${filiaal.id}' />
		</spring:url>
		<!-- Je maakt met de Spring tag url een URL met path variabelen. 
		Je definieert bij value de aan te maken URL als een URI template.
		Je maakt per path variabele een child element param. 
		Je vermeldt bij name de naam van de path variabele. 
		Je vermeldt bij value de waarde voor de path variabele. 
		Dit is in het voorbeeld de id van het huidig afgebeelde filiaal. 
		Als dit filiaal het id 1 heeft, bevat de variabele url de URL /filialen/1.-->

		<a href='${url}'>${filiaal.naam}</a>
		<p>${filiaal.adres.straat}
			${filiaal.adres.huisNr}<br> ${filiaal.adres.postcode}
			${filiaal.adres.gemeente}
		</p>
	</c:forEach>
</body>
</html>