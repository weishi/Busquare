<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Home</title>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyAjYacuGm5n7wkYPCEBOY34OTDabHfRqGI&sensor=true"></script>
<script type="text/javascript">
	var points=new Array();
	<c:forEach var="rep" items="${nearReports}">
		pointInfo=new Object();
		pointInfo.lat=${rep.report.latitude};
		pointInfo.lng=${rep.report.longitude};
		pointInfo.userName='${rep.user.username}';
		pointInfo.routeName='${rep.route.routeName}';
		points.push(pointInfo);
	</c:forEach>
	
	var busStops=new Array();
	<c:forEach var="stop" items="${stopList}">
		<c:if test="${stop.id==curStop}">
			var curStop= new Object();
			curStop.lat=${stop.latitude};
			curStop.lng=${stop.longitude};
			curStop.stopName='${stop.stopName}';
		</c:if>
		<c:if test="${stop.id!=curStop}">
			stopInfo=new Object();
			stopInfo.lat=${stop.latitude};
			stopInfo.lng=${stop.longitude};
			stopInfo.stopName='${stop.stopName}';
			busStops.push(stopInfo);
		</c:if>
	</c:forEach>
</script>
<script type="text/javascript" src="javascript/lookup.js"></script>
<script type="text/javascript" src="javascript/lookupSelect.js"></script>
<link href="css/style.lookup.css" rel="stylesheet" type="text/css" />
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/ico" href="favicon.ico">
</head>

<body onload="load()">
<jsp:include page="header.jsp" />
<div id="content">
  <div class="container">
    <div id="sidebar"> 
      
      <!-- All bus list BEGIN -->
      <div id="routeList">
          <select name="routeList" onchange="javascript:handleSelect(this)">
        	  <c:forEach var="route" items="${routeList}">
              <option value="${route.id}"
              	<c:if test="${route.id==routeID}">selected="selected"</c:if>
              >${route.routeName}</option>
              </c:forEach>
          </select>
        <form id="locateForm" action="lookup.do" method="post" name="location">
          <input id="routeID" name="routeID" type="hidden" value="${routeID}"/>
          <input id="geolong" name="geolong" type="hidden" value=""/>
          <input id="geolat" name="geolat" type="hidden" value=""/>
          <input id="geoaccu"  name="geoaccu" type="hidden" value=""/>
          <input id="locateBtn" class="btn" name="action" type="submit" value="Locating..." disabled="disabled" />
        </form>
        <ul id="busStops">
        	<c:forEach var="stop" items="${stopList}">
        	<li>
        		<a href="lookup.do?routeID=${routeID}&stopID=${stop.id}"
        				<c:if test="${stop.id==curStop}">class="curStop"</c:if>
        		>${stop.stopName}</a>
        	</li>
        	</c:forEach>
        </ul>
        
      </div>
      <!--All bus list END --> 
    </div>
    <!-- End Side Bar--> 
    <!-- Begin Main Bar-->
    <div id="mainbar">
      <div id="mapcanvas" >Google Map Placeholder</div>
      <jsp:include page="legend.jsp" />
      <p>Reports shown in map</p>
      <ul id="reportList">
      	  <c:if test="${empty nearReports}">
	      	  <li><a href="report.do">
		      <span class="location">No reports for this route yet...</span>
		      <span class="timestamp">Click here to report! (Sign in required)</span>
		      </a></li>
          </c:if>
	      <c:forEach var="rep" items="${nearReports}">
		      <li><a href="review.do?reportID=${rep.report.id}">
		      <span class="routeName">${rep.route.routeName}</span>
		      <span class="location">is near location (${rep.report.longitude},${rep.report.latitude})</span>
		      <span class="comment">Comment: ${rep.report.comment}</span>
		      <span class="timestamp"><fmt:formatDate value="${rep.report.timestamp}" type="DATE" pattern="MM/dd/yyyy HH:mm:ss"/></span>
		      <span class="userName">Posted by ${rep.user.username}</span>
		      </a></li>
	      </c:forEach>
      </ul>
    </div>
    <!-- End Main Bar--> 
  </div>
</div>
</body>
</html>
