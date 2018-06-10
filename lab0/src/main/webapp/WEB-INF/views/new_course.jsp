<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<html lang="${language}">
<head>
    <title><fmt:message key="teacher.h1.title" /></title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ua" ${language == 'ua' ? 'selected' : ''}>Ukrainian</option>
    </select>
</form>
    <h2><fmt:message key="teacher.h2.create_courses" /></h2>

    <form action="/api/newcourse" method="post">
        <fmt:message key="teacher.table.title" />: <input type="text" name="title" value="${title}"><br>
        <fmt:message key="teacher.table.program" />: <input type="text" name="program" value="${program}"><br>
        <input type="submit" value="Create">
    </form>
</body>
</html>
