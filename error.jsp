<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Busquare Error</title>
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/ico" href="favicon.ico">
</head>
    
	<body>
	<jsp:include page="header.jsp" />
		<h2>Error...Oops!</h2>

		<c:forEach var="error" items="${errors}">
			<h3 class="errorMsg"> ${error} </h3>
		</c:forEach>
		
		<c:choose>
			<c:when test="${ (empty user) }">
				Click <a href="login.do">here</a> to login.
			</c:when>
			<c:otherwise>
				Click <a href="home.do">here</a> to return to Busquare.
			</c:otherwise>
		</c:choose>

	</body>
</html>