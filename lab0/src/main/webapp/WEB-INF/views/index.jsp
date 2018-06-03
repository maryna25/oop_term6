<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n.text" />
<html lang="${language}">
<head>lab0</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ua" ${language == 'ua' ? 'selected' : ''}>Ukrainian</option>
    </select>
</form>
<h1><fmt:message key="login.h1.title" /></h1>
<h2>${msg}</h2>

<form action="LoginServlet" method="post">
    <fmt:message key="login.label.login" /> <input type="text" name="login">
    <br>
    <fmt:message key="login.label.password" /> <input type="password" name="password">
    <br><br>
    <input type="submit" value="Login">
</form>

<a href="/api/sign_up"><fmt:message key="login.link.sign_up" /></a>

</body>
</html>
