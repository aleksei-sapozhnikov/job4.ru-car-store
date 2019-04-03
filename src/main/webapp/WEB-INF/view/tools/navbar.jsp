<%@ page session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="prm_navbar_title" value="${param.navbar_title}"/>
<c:set var="loggedLogin" scope="page" value="${sessionScope.userLoggedInTheSessionLogin}"/>

<nav class="navbar navbar-expand-lg navbar-light bg-light" style="background-image: url(
<c:url value="/background.jpg"/>);">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target=".dual-collapse2">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="navbar-brand" href="<c:url value="/"/>">Car store</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/"/>">Main page</a>
            </li>
        </ul>
    </div>
    <div class="mx-auto order-0">
        <span class="navbar-brand mx-auto" style="font-size: 1.7em; font-weight: 500;">${prm_navbar_title}</span>
    </div>
    <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
        <ul class="navbar-nav ml-auto">
            <c:if test="${not empty loggedLogin}">
                <li class="nav-item">
                    <p class="navbar-text">Logged: <c:out value="${loggedLogin}"/></p>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value="/addCar"/>">Add car</a>
            </li>
            <c:if test="${empty loggedLogin}">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/login"/>">Login</a>
                </li>
            </c:if>
            <c:if test="${not empty loggedLogin}">
                <li class="nav-item">
                    <form class="form-inline" action="<c:url value="/logout"/>" method="POST">
                        <a class="nav-item nav-link" href="#" onclick="this.parentNode.submit();">Logout</a>
                    </form>
                </li>
            </c:if>
            <c:if test="${empty loggedLogin}">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/addUser"/>">Register</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>