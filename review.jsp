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
	curRep=new Object();
	curRep.lat=${curRep.report.latitude};
	curRep.lng=${curRep.report.longitude};
	curRep.userName='${curRep.user.username}';
	curRep.routeName='${curRep.route.routeName}';

	var busStops=new Array();
	<c:forEach var="stop" items="${stopList}">
		stopInfo=new Object();
		stopInfo.lat=${stop.latitude};
		stopInfo.lng=${stop.longitude};
		stopInfo.stopName='${stop.stopName}';
		busStops.push(stopInfo);
	</c:forEach>
</script>
<script type="text/javascript" src="javascript/review.js"></script>
<link href="css/style.review.css" rel="stylesheet" type="text/css" />
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/ico" href="favicon.ico">
</head>

<body onload="load()">
<jsp:include page="header.jsp" />
<div id="content">
  <div class="container">
    <div id="sidebar"> 
      
      <!-- All action BEGIN -->
      <div class="mySection">
      <div class="listHeader">Actions</div>
        <div class="listEntry">
          <div class="itemName">Route ${curRep.route.routeName}</div>
          <form action="favRoute.do" method="post">
            <input name="routeID" type="hidden" value="${curRep.route.id}" />
            <input name="action" type="hidden" value="bookmark" />
            <c:if test="${hasBookmarked==0}">
            	<input class="itemAction btn" name="submit" type="submit" value="Bookmark" />
            </c:if>
            <c:if test="${hasBookmarked==1}">
            	<input class="itemAction btn" name="submit" type="submit" value="Bookmarked" disabled="disabled" />
            </c:if>
          </form>
        </div>
        <div class="listEntry">
        	<div class="itemName">Report</div>
	        <form action="report.do" method="post">
	          <input name="reportID" type="hidden" value="${curRep.report.id}" />
	          <c:if test="${user.id==curRep.user.id}">
	          	<input name="action" type="hidden" value="remove" />
	          	<input class="itemAction btn" name="submit" type="submit" value="Remove" />
	          </c:if>
	          <c:if test="${user.id!=curRep.user.id}">
	          	<c:if test="${hasFlagged==0}">
	          	<input name="action" type="hidden" value="flag" />
	        	<input class="itemAction btn" name="submit" type="submit" value="Flag" />
	        	</c:if>
	        	<c:if test="${hasFlagged==1}">
	        	<input class="itemAction btn" name="submit" type="button" value="Flagged" disabled="disabled" />
	        	</c:if>
	        </c:if>
	        </form>
        </div>
     </div>
    <!--All action END --> 
    </div>
    <!-- End Side Bar--> 
    <!-- Begin Main Bar-->
    <div id="mainbar">
      <div id="mapcanvas" >Google Map Placeholder</div>
      <jsp:include page="legend.jsp" />
      <p>Reports shown in map</p>
      <ul id="reportList">
	      <li><a href="review.do?reportID=${curRep.report.id}">
	      <span class="routeName">${curRep.route.routeName}</span>
	      <span class="location">is near location (${curRep.report.longitude},${curRep.report.latitude})</span>
	      <span class="comment">Comment: ${curRep.report.comment}</span>
	      <span class="timestamp"><fmt:formatDate value="${curRep.report.timestamp}" type="DATE" pattern="MM/dd/yyyy HH:mm:ss"/></span>
	      <span class="userName">Posted by ${curRep.user.username}</span>
	      </a></li>
      </ul>
    </div>
    <!-- End Main Bar--> 
  </div>
</div>
</body>
</html>
