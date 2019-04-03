<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="tools/headCommon.jsp"/>

    <title>Register</title>
    <link rel="shortcut icon" href="<c:url value="/favicon.ico"/>"/>

    <style>
        .input-group-prepend {
            width: 100px;
        }

        .input-group-text {
            width: 100%;
        }
    </style>
</head>
<body>
<c:import url="tools/navbar.jsp">
    <c:param name="navbar_title" value="Register"/>
</c:import>
<div class="container">
    <c:import url="tools/alerts.jsp"/>

    <form action="addUser" id="editForm" method="POST">
        <!--Login-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Login</span>
            </div>
            <input class="form-control" name="user_login"
                   placeholder="Login: at least 3 chars, may use small latin letters and numbers (e.g. john2013)"
                   required pattern="[A-Za-z0-9\s]{3,}"
                   type="text">
        </div>
        <!--Password-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Password</span>
            </div>
            <input class="form-control" name="user_password"
                   placeholder="Password: at least 3 chars, may use small and capital latin letters and numbers (e.g. qwERy12R34)"
                   required pattern="[a-zA-Z0-9]{3,}"
                   type="text">
        </div>
        <!--Phone-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Phone</span>
            </div>
            <input class="form-control" name="user_phone"
                   placeholder="Phone: from 7 to 30 chars, numbers only (e.g 1234567)" required
                   type="text" pattern="[0-9]{7,30}">
        </div>
        <div style="text-align:center">
            <input class="btn btn-success submitBtn" name="submit" type="submit" value="SAVE"/>
        </div>
    </form>
</div>

</body>
</html>