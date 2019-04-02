<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="carstore.constants.Attributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="prm_login" scope="page" value="<%=Attributes.PRM_USER_LOGIN.v()%>"/>
<c:set var="prm_password" scope="page" value="<%=Attributes.PRM_USER_PASSWORD.v()%>"/>
<c:choose>
    <c:when test="${not empty param.id}">
        <c:set var="title" scope="page" value="Edit user"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" scope="page" value="Login"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1, shrink-to-fit=no" name="viewport">
    <!-- Bootstrap CSS -->
    <link crossorigin="anonymous" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" rel="stylesheet">
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script crossorigin="anonymous"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <script crossorigin="anonymous" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
    <script crossorigin="anonymous" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <!-- Jquery-UI -->
    <link href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css" rel="stylesheet">
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

    <title>${title}</title>
    <link rel="shortcut icon" href="<c:url value="/favicon.ico"/>"/>

    <style>
        .input-group-prepend {
            width: 75px;
        }

        .input-group-text {
            width: 100%;
        }
    </style>
</head>
<body>

<div align="center" class="container-fluid">
    <h3>${title}</h3>
</div>

<div class="container">


    <%--
      Alerts
      --%>
    <c:if test="${not empty param.success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <strong>Success!</strong> ${param.success}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${not empty param.error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> ${param.error}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>
    <c:if test="${error != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <strong>Error!</strong> ${error}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:if>


    <form action="login" id="editForm" method="POST">
        <!--Login-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Login</span>
            </div>
            <input class="form-control" name="${prm_login}" placeholder="Login (e.g. john2013)"
                   required
                   type="text">
        </div>
        <!--Password-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Password</span>
            </div>
            <input class="form-control" name="${prm_password}"
                   placeholder="Password (e.g. qwerty1234)" required
                   type="text">
        </div>
        <input class="btn btn-success submitBtn" name="submit" type="submit" value="SAVE"/>
    </form>
</div>

</body>
</html>