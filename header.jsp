<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header">
  <div class="container"><img src="images/lcon.png"/>
    <div id="title">Busquare</div>
    <ul id="nav">
      <li><a href="home.do">Home</a></li>
      <li>::</li>
      <li><a href="lookup.do">Lookup Schedule</a></li>
      <li>::</li>
      <li><a href="report.do">Report Location</a></li>
    </ul>
    <c:if test="${not empty sessionScope.user}">
        <div id="logout"><a href="logout.do">Logout</a></div>
        <div id="uname">Hi ${user.username}, welcome back!</div>
    </c:if>
  </div>
</div>