<%--@elvariable id="loggedUser" type="carstore.model.User"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%@ page import="carstore.constants.Attributes" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="context" scope="request" value="${pageContext.request.contextPath}"/>
<%--Filter cars parameters--%>
<c:set var="prm_filterBy" scope="page" value="<%=Attributes.PRM_FILTER_BY.v()%>"/>
<c:set var="prm_filterBy_createdToday" scope="page" value="<%=Attributes.PRM_FILTER_BY_CREATED_TODAY.v()%>"/>
<c:set var="prm_filterBy_manufacturer" scope="page" value="<%=Attributes.PRM_FILTER_BY_MANUFACTURER.v()%>"/>
<c:set var="prm_filterBy_WithImage" scope="page" value="<%=Attributes.PRM_FILTER_BY_WITH_IMAGE.v()%>"/>
<c:set var="prm_filterValue" scope="page" value="<%=Attributes.PRM_FILTER_VALUE.v()%>"/>
<%--Select car parameters--%>
<%@ page import="carstore.util.PropertiesHolder" %>
<c:set var="select_manufacturer" scope="page"
       value="<%=((PropertiesHolder) request.getServletContext().getAttribute(Attributes.ATR_SELECT_VALUES.v())).getManufacturer()%>"/>


<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="tools/headCommon.jsp"/>
    <c:import url="tools/ItemClass.html"/>
    <c:import url="tools/showItemsScripts.jsp"/>
    <title>Car store</title>

    <script>
        /** Defines type of filtering */
        let filterBy = "";
        /** Defines value of filtering */
        let filterValue = "";

        /** Listener on changing filterBy type. */
        $(document).on('change', $('[name=${prm_filterBy}]'), function () {
            reShowItems();
        });

        function reShowItems() {
            let urlParam = getFilterUrlParam();
            showItems($('#cars-table'), urlParam);
        }

        /** Returns url param defining filter value */
        function getFilterUrlParam() {
            let element = $('[name|=${prm_filterBy}]:checked');
            let dataValue = element.attr('data-value');
            filterBy = element.val();
            filterValue = dataValue !== ""
                ? $('input[type=text][data-value=' + dataValue + ']').val()
                : "";
            let urlParam = "";
            if (filterBy !== "") {
                urlParam += '?${prm_filterBy}=' + filterBy;
                if (filterValue !== '') {
                    urlParam += '&${prm_filterValue}=' + filterValue;
                }
            }
            return urlParam;
        }


        /**
         * Actions on document load.
         */
        $(function () {
            showItems($("#cars-table"), filterBy);
        });
    </script>

</head>
<c:import url="tools/navbar.jsp">
    <c:param name="navbar_title" value="Current offers"/>
</c:import>
<div class="container">
    <c:import url="tools/alerts.jsp"/>

    <div class="row">
        <div id="filter-choose" class="col col-sm-4 col-md-3 col-lg-2">
            <div>
                <label>
                    <input type="radio" name="${prm_filterBy}" value="" data-value="" checked/>
                    All cars
                </label>
            </div>
            <div>
                <label>
                    <input type="radio" name="${prm_filterBy}" value="${prm_filterBy_createdToday}" data-value=""/>
                    Created today
                </label>
            </div>
            <div>
                <label>
                    <input type="radio" name="${prm_filterBy}" value="${prm_filterBy_WithImage}" data-value=""/>
                    With image
                </label>
            </div>
            <div>
                <label>
                    <input type="radio" name="${prm_filterBy}" value="${prm_filterBy_manufacturer}"
                           data-value="manufacturer"/>
                    Having manufacturer:
                    <input name="${prm_filterValue}" data-value="manufacturer" list="manufacturer-list" type="text"
                           placeholder="e.g. Mercedes" oninput="reShowItems()"
                           onmousedown="$('[type=radio][data-value=manufacturer]').prop('checked', true); reShowItems();"/>
                    <datalist id="manufacturer-list">
                        <c:forEach items="${select_manufacturer}" var="item">
                            <option value="${item}">${item}</option>
                        </c:forEach>
                    </datalist>
                </label>
            </div>

        </div>
        <div class="col col-sm-8 col-md-9 col-lg-10">
            <div class="row row-centered" id="cars-table">
                Loading items...
            </div>
        </div>
    </div>
</div>

</body>
</html>