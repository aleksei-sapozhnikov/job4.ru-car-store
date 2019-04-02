<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="tools/headCommon.jsp"/>

    <title>Add user</title>
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
<c:import url="tools/navbar.jsp"/>
<div align="center"><h3>Add user</h3></div>
<c:import url="tools/alerts.jsp"/>

<div class="container">
    <form action="addUser" id="editForm" method="POST">
        <!--Login-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Login</span>
            </div>
            <input class="form-control" name="user_login" placeholder="Login (e.g. john2013)" required
                   type="text">
        </div>
        <!--Password-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Password</span>
            </div>
            <input class="form-control" name="user_password" placeholder="Password (e.g. qwerty1234)" required
                   type="text">
        </div>
        <!--Phone-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Phone</span>
            </div>
            <input class="form-control" name="user_phone" placeholder="Phone (e.g. 1234567)" required
                   type="text">
        </div>
        <input class="btn btn-success submitBtn" name="submit" type="submit" value="SAVE"/>
    </form>
</div>

</body>
</html>