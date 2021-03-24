<%--
  Created by IntelliJ IDEA.
  User: Dmytro
  Date: 20/03/2021
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach var="navMessage" items="${navMessages}">
    <p><c:out value="${navMessage}"/></p>
</c:forEach>
<form method="post" action="./answer" modelAttribute="answer">
    Action: <input name="answer"/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>

