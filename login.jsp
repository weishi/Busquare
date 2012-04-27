<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Create your facebowl account</title>
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link href="css/style.register.css" rel="stylesheet" type="text/css" />
</head>

<body>
<jsp:include page="header.jsp" />
<div class="signup">
<c:forEach var="error" items="${errors}">
<h3 class="errorMsg"> ${error} </h3>
</c:forEach>
  <form id="form1" name="form1" method="post" action="login.do">
    <fieldset>
        <legend>Account</legend>
      <div class="entry">
        <label>Username</label>
        <input name="username" type="text" value="${form.username}"/>
      </div>
      <div class="entry">
        <label>Password</label>
        <input name="password" type="password" />
      </div>
      <div class="entry">
      <input class="btn" name="action" type="submit" value="Sign in" />
      <input class="btn" name="action" type="submit" value="Sign up" />
    </div>
    </fieldset>
  </form>
</div>
</body>
</html>
