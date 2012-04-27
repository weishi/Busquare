<!--Wei Shi, weishi, 15437, 2012/1/30 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Welcome to Busquare</title>
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link href="css/style.welcome.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/ico" href="favicon.ico">
</head>

<body>
<div class="container">
  <div id="welcome-content">
    <div id="home-top"> <img class="img-home" src="images/header.png"/>
      <div id="login">
        <form id="form1" name="form1" method="post" action="login.do">
          <div class="entry">
            <input class="textEntry" placeholder="Enter your username" name="username" type="text" />
          </div>
          <div class="entry">
            <input class="textEntry" placeholder="Enter your password" name="password" type="password" />
          </div>
          <div class="entry">
            <input class="btn" name="action" type="submit" value="Sign in" />
            <input class="btn" name="action" type="submit" value="Sign up" />
          </div>
        </form>
      </div>
    </div>
    <div id="home-bottom"> <img class="img-home" src="images/home.png"/>
      <div id="board">
        <div id="board-left"><a href="lookup.do">Check Bus Schedule</a></div>
        <div id="board-right"><a href="report.do">Report Bus Location</a></div>
      </div>
    </div>
  </div>
</div>
</body>
</html>
