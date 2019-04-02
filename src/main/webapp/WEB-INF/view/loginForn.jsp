<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="carstore.constants.Attributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="prm_login" scope="page" value="<%=Attributes.PRM_USER_LOGIN.v()%>"/>
<c:set var="prm_password" scope="page" value="<%=Attributes.PRM_USER_PASSWORD.v()%>"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="tools/headCommon.jsp"/>
    <title>Login</title>
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
<div align="center"><h3>Login</h3></div>
<c:import url="tools/alerts.jsp"/>

<div class="container">
    <form action="login" id="editForm" method="POST">
        <!--Login-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Login</span>
            </div>
            <input class="form-control" name="${prm_login}" placeholder="Enter login:" required type="text" autofocus>
        </div>
        <!--Password-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Password</span>
            </div>
            <input class="form-control" name="${prm_password}"
                   placeholder="Enter password:" required
                   type="text">
        </div>
        <div style="text-align:center">
            <input class="btn btn-success submitBtn" name="submit" type="submit" value="LOGIN"/>
        </div>
    </form>
</div>

</body>
</html>