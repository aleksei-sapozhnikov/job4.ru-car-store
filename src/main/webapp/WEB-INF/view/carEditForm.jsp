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
<%--Parameters for <select> options elements--%>
<%@ page import="carstore.util.PropertiesHolder" %>
<c:set var="select_manufacturer" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getManufacturer()%>"/>
<c:set var="select_newness" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getNewness()%>"/>
<c:set var="select_bodyType" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getBodyType()%>"/>
<c:set var="select_color" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getColor()%>"/>
<c:set var="select_engineFuel" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getEngineFuel()%>"/>
<c:set var="select_transmissionType" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getTransmissionType()%>"/>

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
    <c:import url="tools/headCommon.jsp"/>
    <c:import url="tools/ItemClass.html"/>

    <title>${title}</title>

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

    <style>
        .status-div {
            margin-top: 20px;
            margin-bottom: 20px;
            font-size: 1.5em;
            text-align: center;
        }

        .status-for-sale {
            background: #82ff5b;
            color: #000000;
            margin-left: 10px;
            margin-right: 10px;
            padding: 10px;
        }

        .status-sold {
            background: #960611;
            color: #ffffff;
            margin-left: 10px;
            margin-right: 10px;
            padding: 10px;
        }

        h4 {
            text-align: center;
        }
    </style>

</head>
<body>
<c:import url="tools/navbar.jsp"/>
<div align="center"><h3> ${title} </h3></div>
<c:import url="tools/alerts.jsp"/>

<div class="container">
    <form action="addCar" enctype="multipart/form-data" id="editCarForm" method="POST">
        <%--
        Hidden fields
        --%>
        <c:if test="${not empty editCar}">
            <input name=${prm_id} type="hidden" value="${editCar.id}">
        </c:if>
        <%--
        Required fields
        --%>
        <h4>Required parameters</h4>
        <%--Status--%>
        <div class="status-div">
            <div class="form-check form-check-inline status-for-sale">
                <input type="radio" id="radio-for-sale" name="${prm_available}" class="form-check-input"
                       value="true"
                       <c:if test="${editCar.available != false}">checked</c:if>>
                <label class="form-check-label" for="radio-for-sale">For sale</label>
            </div>
            <div class="form-check form-check-inline status-sold">
                <input type="radio" id="radio-sold" name="${prm_available}" class="form-check-input" value="false"
                       <c:if test="${editCar.available == false}">checked</c:if>>
                <label class="form-check-label" for="radio-sold">Sold</label>
            </div>
        </div>
        <div class="form-row">
            <%--Mark--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Mark</span>
                    </div>
                    <label for="manufacturer-input" hidden></label>
                    <input class="form-control" id="manufacturer-input" name="${prm_manufacturer}"
                           list="manufacturer-list" type="text" placeholder="Ford"
                    <c:if test="${not empty editCar}"> value="${editCar.manufacturer}"</c:if>>
                    <datalist id="manufacturer-list">
                        <c:forEach items="${select_manufacturer}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </datalist>
                </div>
            </div>
            <%--Model--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Model</span>
                    </div>
                    <input class="form-control" name="${prm_model}" placeholder="Transit FX-1300F" required
                           type="text" <c:if test="${not empty editCar}">value="${editCar.model}"</c:if>">
                </div>
            </div>
            <%--Price--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Price</span>
                    </div>
                    <input class="form-control" name="${prm_price}" placeholder="4000"
                           required type="number" <c:if test="${not empty editCar}">value="${editCar.price}"</c:if>">
                    <div class="input-group-append">
                        <span class="input-group-text" id="basic-addon2">$</span>
                    </div>
                </div>
            </div>
            <%--Newness--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Newness</span>
                    </div>
                    <label for="newness-input" hidden></label>
                    <select class="form-control" id="newness-input" name="${prm_newness}">
                        <c:forEach items="${select_newness}" var="item">
                            <option value="${item}"<c:if
                                    test="${editCar.newness == item}"> selected</c:if>>${item}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <%--Year of manufacture--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Year of manufacture</span>
                    </div>
                    <input class="form-control" name="${prm_yearManufactured}"
                           placeholder="2014"
                           required type="number"
                           <c:if test="${not empty editCar}">value="${editCar.yearManufactured}"</c:if>">
                </div>
            </div>
            <%--Mileage--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Mileage</span>
                    </div>
                    <input class="form-control" name="${prm_mileage}" placeholder="150000" required
                           type="number" <c:if test="${not empty editCar}">value="${editCar.mileage}"</c:if>">
                    <div class="input-group-prepend">
                        <span class="input-group-text">km</span>
                    </div>
                </div>
            </div>
            <!--Body type-->
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Body type</span>
                    </div>
                    <label for="bodyType-input" hidden></label>
                    <input class="form-control" id="bodyType-input" name="${prm_bodyType}" list="bodyType-list"
                           type="text" placeholder="sedan" <c:if test="${not empty editCar}">
                           value="${editCar.manufacturer}"</c:if>>
                    <datalist id="bodyType-list">
                        <c:forEach items="${select_bodyType}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </datalist>
                </div>
            </div>
            <%--Color--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Color</span>
                    </div>
                    <input class="form-control" id="color-input" name="${prm_color}" list="color-list"
                           type="text" placeholder="black" <c:if test="${not empty editCar}">
                           value="${editCar.color}"</c:if>>
                    <datalist id="color-list">
                        <c:forEach items="${select_color}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </datalist>
                </div>
            </div>
            <%--Fuel--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Fuel</span>
                    </div>
                    <input class="form-control" id="engineFuel-input" name="${prm_engineFuel}"
                           list="engineFuel-list"
                           type="text" placeholder="gasoline" <c:if test="${not empty editCar}">
                           value="${editCar.engineFuel}"</c:if>>
                    <datalist id="engineFuel-list">
                        <c:forEach items="${select_engineFuel}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </datalist>
                </div>
            </div>
            <%--Engine volume--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Engine volume</span>
                    </div>
                    <input class="form-control" name="${prm_engineVolume}" placeholder="1200"
                           required
                           type="number" <c:if test="${not empty editCar}">value="${editCar.engineVolume}"</c:if>>
                    <div class="input-group-append">
                        <span class="input-group-text">cm³</span>
                    </div>
                </div>
            </div>
            <%--Transmission type--%>
            <div class="col-lg-4 col-md-6 col-sm-12">
                <div class="input-group mb-2">
                    <div class="input-group-prepend">
                        <span class="input-group-text">Transmission type</span>
                    </div>
                    <label for="transmissionType-input" hidden></label>
                    <select class="form-control" id="transmissionType-input" name="${prm_transmissionType}">
                        <c:forEach items="${select_transmissionType}" var="item">
                            <option value="${item}"<c:if
                                    test="${editCar.transmissionType == item}"> selected</c:if>>${item}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <!--
        **** Optional fields
        -->
        <h4>Optional parameters</h4>
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