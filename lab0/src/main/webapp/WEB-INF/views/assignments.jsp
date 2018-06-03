<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ct" uri="WEB-INF/custom_tags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<html lang="${language}">
<head>
    <title><fmt:message key="assignments.h1.assignments" /></title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ua" ${language == 'ua' ? 'selected' : ''}>Ukrainian</option>
    </select>
</form>
<br><br>

<h2><fmt:message key="assignments.h2.assignments" />:</h2>
<table border="1">
    <thead>
    <tr>
        <th><fmt:message key="assignments.table.course" /></th>
        <th><fmt:message key="assignments.table.student" /></th>
        <th><fmt:message key="assignments.table.mark" /></th>
        <th><fmt:message key="assignments.table.response" /></th>
        <th><fmt:message key="assignments.table.status" /></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ass" items="${assignments}">
        <tr><ct:assignment item="${ass}" /></tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
