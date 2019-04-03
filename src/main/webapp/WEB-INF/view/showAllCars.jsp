<%--@elvariable id="loggedUser" type="carstore.model.User"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="tools/headCommon.jsp"/>
    <c:import url="tools/ItemClass.html"/>
    <c:import url="tools/showItemsScripts.jsp"/>
    <title>Car store</title>

    <script>
        $(function () {
            showItems($("#cars-table"));
        });
    </script>

</head>
<c:import url="tools/navbar.jsp">
    <c:param name="navbar_title" value="Current offers"/>
</c:import>
<div class="container">
    <c:import url="tools/alerts.jsp"/>

    <div class="row row-centered" id="cars-table">
        Loading items...
    </div>
</div>

</body>
</html>