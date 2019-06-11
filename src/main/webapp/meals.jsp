<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals list</h2>
<table border="1">
<c:forEach var="meal" items="${meals}">
    <tr>
        <td>
                    <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
        </td>
        <td>${meal.description}</td>
        <td>
            <c:if test="${meal.excess eq true}">
                <font color="red"> ${meal.calories}</>
            </c:if>
            <c:if test="${meal.excess eq false}">
                <font color="green"> ${meal.calories}</>
            </c:if>
        </td>
    </tr>
</c:forEach>
</table>
</body>
</html>