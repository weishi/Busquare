<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Busquare - Report Location</title>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAjYacuGm5n7wkYPCEBOY34OTDabHfRqGI&sensor=true"></script>
<script type="text/javascript" src="javascript/report.js"></script>
<link href="css/style.report.css" rel="stylesheet" type="text/css" />
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/ico" href="favicon.ico">
</head>

<body>
<jsp:include page="header.jsp" />
<div id="content">
  <div class="container">
    <div id="sidebar"> </div>
    <!-- End Side Bar--> 
    <!-- Begin Main Bar-->
    <div id="mainbar">
    
    <div id="mapcanvas">Google Map Placeholder</div>
    <p><span id="status"><img class="loading" src="images/loading.gif"/>Finding your locationg...</span></p>
    <c:forEach var="error" items="${errors}">
    <h3 class="errorMsg"> ${error} </h3>
    </c:forEach>
      <form id="locationForm" action="report.do" method="post" name="location">
      <label>Route: </label>
        <select name="routeID">
      	  <c:forEach var="route" items="${routeList}">
          <option value="${route.id}">${route.routeName}</option>
          </c:forEach>
        </select>
        <input id="geolong" name="geolong" type="hidden" value=""/>
        <input id="geolat" name="geolat" type="hidden" value=""/>
        <input id="geoaccu"  name="geoaccu" type="hidden" value=""/>
        <label>Comment: </label>  
        <input name="comment" type="text" maxlength="140" />
        <input name="action" type="hidden" value="report" />
        <input id="reportBtn" class="btn" name="submit" type="submit" value="Report location" visibility="hidden" />
      </form>
    </div>
    <!-- End Main Bar--> 
  </div>
</div>
</body>
</html>
