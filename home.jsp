<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Home</title>
<link href="css/style.home.css" rel="stylesheet" type="text/css" />
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/ico" href="favicon.ico">
</head>

<body>
<jsp:include page="header.jsp" />
<div id="content">
  <div class="container">
    <div id="sidebar"> 
     
      <!--My fav buses -->
      <div class="mySection">
        <div class="listHeader">My favorite buses</div>
          <c:forEach var="fr" items="${favRoutes}">
          <div class="listEntry">
            <div class="itemName">Route ${fr.routeName}</div>
            <form action="favRoute.do" method="post">
              <input name="routeID" type="hidden" value="${fr.id}" />
              <input name="action" type="hidden" value="remove" />
              <input class="itemAction btn" name="submit" type="submit" value="Remove" />
            </form>
          </div>
          </c:forEach>
       </div>
      <!--My recent reports -->
      <div class="mySection">
        <div class="listHeader">My recent reports</div>
        <c:forEach var="rep" items="${mine}">
        <div class="listEntry">
          <div class="itemName"><fmt:formatDate value="${rep.report.timestamp}" type="DATE" pattern="MM/dd/yyyy HH:mm:ss"/>
          <b>${rep.route.routeName}</b></div>
          <form action="report.do" method="post">
          	<input name="reportID" type="hidden" value="${rep.report.id}" />
          	<input name="action" type="hidden" value="remove" />
            <input class="itemAction btn" name="submit" type="submit" value="Remove" />
          </form>
        </div>
        </c:forEach>
       </div>
       
    </div>
    <div id="mainbar">
      <div id="newsHeader">Reports updates</div>
      <ul id="newsFeed">
      <c:forEach var="rep" items="${feed}">
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
  </div>
</div>
</body>
</html>
