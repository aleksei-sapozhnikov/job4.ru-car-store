function getUrlParameter(paramName) {
    var pageUrl = window.location.search.substring(1);
    var variables = pageUrl.split('&');
    for (let i = 0; i < variables.length; i++) {
        let sParameterName = variables[i].split('=');
        if (sParameterName[0] === paramName) {
            return sParameterName[1];
        }
    }
}