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

<h1><fmt:message key="sign_up.h1.title" /></h1>
<form action="SignUpServlet" method="post">
    <fmt:message key="sign_up.form.login" />: <input type="text" name="login">
    <br>
    <fmt:message key="sign_up.form.password" />: <input type="password" name="password">
    <br>
    <fmt:message key="sign_up.form.role" />: <input type="text" name="role">
    <br>
    <fmt:message key="sign_up.form.name" />: <input type="text" name="name">
    <br>
    <fmt:message key="sign_up.form.surname" />: <input type="text" name="surname">
    <br><br>
    <input type="submit" value="Sign up">
</form>

</body>
</html>
