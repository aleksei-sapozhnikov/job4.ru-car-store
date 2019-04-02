<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div style="text-align:center">
    <a href="<c:url value="/"/>">
        <button class="btn btn-primary" id="button_main_page">Main page</button>
    </a>
    <a href="<c:url value="/addCar"/>">
        <button class="btn btn-primary" id="button_add_item">Add car</button>
    </a>
    <a href="<c:url value="/addUser"/>">
        <button class="btn btn-primary" id="button_add_user">Add user</button>
    </a>
    <a href="<c:url value="/login"/>">
        <button class="btn btn-primary" id="button_login">Login</button>
    </a>
    <form action="logout" method="POST">
        <input type="submit" class="btn btn-primary" id="button_logout" value="Logout"/>
    </form>
</div>