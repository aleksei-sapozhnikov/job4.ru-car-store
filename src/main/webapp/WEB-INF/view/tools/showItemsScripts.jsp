<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
    function showItems(destination, urlParams) {
        $.ajax({
            type: 'GET',
            async: false,
            datatype: "application/json",
            url: "<c:url value="/getAllCarItems"/>" + urlParams,
            success: function (response) {
                if (response.error != null) {
                    alert(response.error);
                } else {
                    let items = JSON.parse(response);
                    drawItems(items, destination);
                }
            }
        });
    }

    function drawItems(items, destination) {
        let res = '';
        items.sort(compareItems);
        items.forEach(function (item) {
            Object.setPrototypeOf(item, Item.prototype);
            res += item.toHtml();
        });
        $(destination).html(res);
    }
</script>