<%--@elvariable id="loggedUser" type="carstore.model.User"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>

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

    <!-- Custom components -->
    <c:import url="tools/Item.html"/>
    <c:import url="tools/get-url-parameter.html"/>


    <script>
        function showItems() {
            $.ajax({
                type: 'GET',
                async: false,
                datatype: "application/json",
                url: "<c:url value="/getAllCarItems"/>",
                success: function (response) {
                    if (response.error != null) {
                        alert(response.error);
                    } else {
                        let items = JSON.parse(response);
                        drawItems(items);
                    }
                }
            });
        }

        /**
         *
         * @param items
         */
        function drawItems(items) {
            let res = '';
            items.forEach(function (item) {
                Object.setPrototypeOf(item, Item.prototype);
                res += '<div class="item-div col col-12 col-md-6 col-lg-4" style="background-color: bisque;">'
                    + item.toHtml()
                    + '</div>';
            });
            $("#cars-table").html(res);
        }
    </script>

    <script>
        $(function () {
            drawItems([
                {
                    "title": "manufacturer model",
                    "price": "1 $",
                    "seller": "Vasily",
                    "contacts": "tel: 564-345-23",
                    "descriptions": {
                        "Body": "bodyType; color",
                        "Age": "newness; 2 y.m.; 3 km",
                        "Engine": "engineFuel; 4 cm³",
                        "Transmission": "transmissionType"
                    },
                    "imageIds": [0]
                }
            ]);
        });
    </script>

    <title>Car store</title>
    <link rel="shortcut icon" href="${context}/favicon.ico"/>
</head>
<body>

<div align="center" class="container-fluid">
    <h3>Car store</h3>
</div>

<div class="container">


    <p class="statusMsg">
        <c:if test="${not empty param.success}">
            <span style="color:green"><b>Success: </b>${param.success}</span>
        </c:if>
        <c:if test="${not empty param.error}">
            <span style="color:red"><b>Error: </b>${param.error}</span>
        </c:if>
        <c:if test="${error != null}">
            <span style="color:red"><b>Error: </b>${error}</span>
        </c:if>
    </p>

    <div class="row">
        <a href="addCar">
            <button class="btn btn-primary" id="button_add_item">Add car</button>
        </a>
        <a href="addUser">
            <button class="btn btn-primary" id="button_add_user">Add user</button>
        </a>
        <a href="login">
            <button class="btn btn-primary" id="button_login">Login</button>
        </a>
        <form action="logout" method="POST">
            <input type="submit" class="btn btn-primary" id="button_logout" value="Logout"/>
        </form>
    </div>


    <div class="row row-centered" id="cars-table">
        Loading items...
    </div>
</div>

</body>
</html>