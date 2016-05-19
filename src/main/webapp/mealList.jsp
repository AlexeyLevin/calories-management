<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<h4>Add new meal</h4>
<table border="1" width="60%" cellpadding="4">
    <tr>
        <th>date</th>
        <th>time</th>
        <th>description</th>
        <th>calories</th>
    </tr>
    <form method="post" action="${pageContext.request.contextPath}/meals">
        <tr>
            <td><label><input required type="date" name="mealDate"/></label></td>
            <td><label><input required type="time" name="mealTime"/></label></td>
            <td><label><input required type="text" name="mealDescription"/></label></td>
            <td><label><input required type="number" name="mealCalories"/></label></td>
            <td colspan="2"><input type="submit" value="Add new meal"/></td>
        </tr>
    </form>
    <%--@elvariable id="error" type="java.lang.Boolean"--%>
    <c:if test="${error}">
        Incorrect added data!
    </c:if>
</table>

<h4>Remove meal</h4>
<table border="1" width="10%" cellpadding="0">
    <form method="get" action="${pageContext.request.contextPath}/meals">
        <tr>
            <%--@elvariable id="maxId" type="java.lang.Integer"--%>
            <td><label><input type="number" max="${maxId}" min="1" name="removeId"/></label></td>
            <td colspan="2"><input type="submit" value="Remove meal by ID"/></td>
        </tr>
    </form>
</table>

<h1>Meal list</h1>
<table border="1" width="60%" cellpadding="3">
    <tr>
        <th>id</th>
        <th>date</th>
        <th>time</th>
        <th>description</th>
        <th>calories</th>
        <th>exceed</th>
    </tr>
    <c:forEach items="${requestScope.userMealWithExceeds}" var="meal">
        <c:set var="color" scope="page">"green"</c:set>
        <c:if test="${meal.exceed}">
            <c:set var="color" scope="page">"red"</c:set>
        </c:if>
        <form method="post" action="${pageContext.request.contextPath}/meals">
            <tr bgcolor=${pageScope.color}>
                <td><label><input readonly type="number" name="mealId" value="${meal.id}"/></label></td>
                <td><label><input required type="date" name="mealDate" value="${meal.dateTime.toLocalDate()}"/></label></td>
                <td><label><input required type="time" name="mealTime" value="${meal.dateTime.toLocalTime()}"/></label></td>
                <td><label><input required type="text" name="mealDescription" value="${meal.description}"/></label></td>
                <td><label><input required type="number" name="mealCalories" value="${meal.calories}"/></label></td>
                <td> ${meal.exceed} </td>
                <td colspan="2"><input type="submit" value="Edit meal"/></td>
            </tr>
        </form>
    </c:forEach>
</table>

</body>
</html>