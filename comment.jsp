<!--Wei Shi, weishi, 15437, 2012/1/30 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Post your comment</title>
<link href="css/style.general.css" rel="stylesheet" type="text/css" />
<link href="css/style.comment.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div id="header">
  <div id="title">Facebowl</div>
  <div id="logout"><a href="logout.do">Logout</a></div>
	<div id="searchInput">
	<form action="search.do" method="get">
		<input name="username" type="text" /> <input name="action" type="submit"
			value="Search" />
	</form>
	</div>
</div>
<c:forEach var="error" items="${errors}">
<h3 style="color:red"> ${error} </h3>
</c:forEach>
<div class="comment">
  <form id="form1" name="form1" method="post" action="comment.do">
    <fieldset id="fs">
      <legend>Your comment</legend>
      <div class="entry">
        <label>Comment</label>
        <textarea name="comment" cols="" rows=""><c:out value="${ form.comment }" /></textarea>
        <input name="storyID" type="hidden" value="${param.storyID}"/>
      </div>
      <div class="entry">
        <input name="action" type="submit" value="Post the comment" />
      </div>
    </fieldset>
  </form>
</div>
</body>
</html>
