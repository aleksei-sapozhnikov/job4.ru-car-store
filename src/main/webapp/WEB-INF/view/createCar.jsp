<%--@elvariable id="error" type="java.lang.String"--%>
<%--@elvariable id="editCar" type="carstore.model.Car"--%>
<%--@elvariable id="carImageIds" type="java.util.List"--%>
<%@ page import="carstore.constants.Attributes" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--Car parameters--%>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>
<c:set var="prm_id" scope="page" value="<%=Attributes.PRM_CAR_ID.v()%>"/>
<c:set var="prm_available" scope="page" value="<%=Attributes.PRM_CAR_AVAILABLE.v()%>"/>
<c:set var="prm_manufacturer" scope="page" value="<%=Attributes.PRM_CAR_MANUFACTURER.v()%>"/>
<c:set var="prm_model" scope="page" value="<%=Attributes.PRM_CAR_MODEL.v()%>"/>
<c:set var="prm_newness" scope="page" value="<%=Attributes.PRM_CAR_NEWNESS.v()%>"/>
<c:set var="prm_bodyType" scope="page" value="<%=Attributes.PRM_CAR_BODY_TYPE.v()%>"/>
<c:set var="prm_color" scope="page" value="<%=Attributes.PRM_CAR_COLOR.v()%>"/>
<c:set var="prm_engineFuel" scope="page" value="<%=Attributes.PRM_CAR_ENGINE_FUEL.v()%>"/>
<c:set var="prm_transmissionType" scope="page" value="<%=Attributes.PRM_CAR_TRANSMISSION_TYPE.v()%>"/>
<c:set var="prm_price" scope="page" value="<%=Attributes.PRM_CAR_PRICE.v()%>"/>
<c:set var="prm_yearManufactured" scope="page" value="<%=Attributes.PRM_CAR_YEAR_MANUFACTURED.v()%>"/>
<c:set var="prm_mileage" scope="page" value="<%=Attributes.PRM_CAR_MILEAGE.v()%>"/>
<c:set var="prm_engineVolume" scope="page" value="<%=Attributes.PRM_CAR_ENGINE_VOLUME.v()%>"/>
<c:set var="prm_imageKey" scope="page" value="<%=Attributes.PRM_IMAGE_KEY_START.v()%>"/>
<%--Image parameters--%>
<c:set var="prm_imageId" scope="page" value="<%=Attributes.PRM_IMAGE_ID.v()%>"/>

<c:choose>
    <c:when test="${not empty editCar}">
        <c:set var="title" scope="page" value="Edit car"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" scope="page" value="Add car"/>
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

    <!-- Custom components -->
    <c:import url="tools/Item.html"/>

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

    <script>
        $(function () {
            $('input[type="file"]').change(function (e) {
                let file = e.target.files[0];
                let labelSelector = '#' + e.target.id + '_label';
                let fileName = file !== undefined
                    ? e.target.files[0].name
                    : 'Choose file';
                $(labelSelector).text(fileName);
            });
        });
    </script>

    <script>
        function showLoadedImage(input) {
            if (input.files && input.files[0]) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    $('#img_current').attr('src', e.target.result);
                };
                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>

</head>
<body>

<div class="container">
    <div align="center"><h3> ${title} </h3></div>

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
        <a href="<c:url value="/"/>">
            <button class="btn btn-primary" id="button_add_item">To main page</button>
        </a>
    </div>

    <form action="addCar" enctype="multipart/form-data" id="editCarForm" method="POST">
        <%--
        Hidden fields
        --%>
        <c:if test="${not empty editCar}">
            <input name=${prm_id} type="hidden" value="${editCar.id}">
        </c:if>
        <!--
        **** Required fields
        -->
        <h4>Required</h4>
        <!--Status-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Car status</span>
            </div>
            <label for="status" hidden>Car status</label>
            <select id="status" name="${prm_available}">
                <option value="true">For sale</option>
                <option value="false">Sold</option>
            </select>
        </div>
        <!--Mark-->
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Mark</span>
            </div>
            <input class="form-control" name="${prm_manufacturer}" placeholder="Manufacturer (e.g. Ford)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.manufacturer}"</c:if>>
            <input class="form-control" name="${prm_model}" placeholder="Model (e.g. Transit FX-1300)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.model}"</c:if>">
        </div>
        <!--Price-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Price</span>
            </div>
            <input class="form-control" name="${prm_price}" placeholder="Price, $ (e.g. 4000)"
                   required type="number" <c:if test="${not empty editCar}">value="${editCar.price}"</c:if>">
        </div>
        <!--Age-->
        <div class="input-group mb-4">
            <div class="input-group-prepend">
                <span class="input-group-text">Age</span>
            </div>
            <input class="form-control" name="${prm_newness}" placeholder="Newness (e.g. new, old)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.newness}"</c:if>>
            <input class="form-control" name="${prm_yearManufactured}" placeholder="Year of manufacturing (e.g. 2014)"
                   required type="number"
                   <c:if test="${not empty editCar}">value="${editCar.yearManufactured}"</c:if>">
            <input class="form-control" name="${prm_mileage}" placeholder="Mileage, km (e.g. 150000)" required
                   type="number" <c:if test="${not empty editCar}">value="${editCar.mileage}"</c:if>">
        </div>
        <!--Body-->
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Body</span>
            </div>
            <input class="form-control" name="${prm_bodyType}" placeholder="Type (e.g. Sedan, Pickup)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.bodyType}"</c:if>>
            <input class="form-control" name="${prm_color}" placeholder="Color (e.g. black, white)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.color}"</c:if>>
        </div>
        <!--Engine-->
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Engine</span>
            </div>
            <input class="form-control" name="${prm_engineFuel}" placeholder="Type (e.g. gasoline, kerosene)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.engineFuel}"</c:if>>
            <input class="form-control" name="${prm_engineVolume}" placeholder="Volume, mm³ (e.g. 1200)" required
                   type="number" <c:if test="${not empty editCar}">value="${editCar.engineVolume}"</c:if>>
        </div>
        <!--Chassis-->
        <div class="input-group mb-2">
            <div class="input-group-prepend">
                <span class="input-group-text">Chassis</span>
            </div>
            <input class="form-control" name="${prm_transmissionType}"
                   placeholder="Transmission type (e.g. automatic, manual)" required
                   type="text" <c:if test="${not empty editCar}">value="${editCar.transmissionType}"</c:if>>
        </div>
        <!--
        **** Optional fields
        -->
        <h4>Optional</h4>
        <!--Photo-->
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text">Photo</span>
            </div>
            <div class="item-image-div">
                <img id="img_current" alt="current image" class="item-image-img"
                        <c:choose>
                            <c:when test="${not empty carImageIds && carImageIds.size() > 0}">
                                src="image?${prm_imageId}=${carImageIds.get(0)}"
                            </c:when>
                            <c:otherwise>
                                src="<c:url value="/item-default-image.jpg"/>"
                            </c:otherwise>
                        </c:choose>
                />
            </div>
            <div class="custom-file">
                <input class="custom-file-input" id="car_image" name="${prm_imageKey}1" type="file"
                       onchange="showLoadedImage(this)">
                <label class="custom-file-label" for="car_image" id="car_image_label">Choose file</label>
            </div>
        </div>
        <input class="btn btn-success submitBtn" name="submit" type="submit" value="SAVE"/>
    </form>
</div>

</body>
</html>