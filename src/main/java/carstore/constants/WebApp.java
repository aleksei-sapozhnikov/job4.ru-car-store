package carstore.constants;

public enum WebApp {
    BASEDIR(System.getProperty("catalina.base")),
    VIEW_ROOT("/WEB-INF/view"),
    ERROR_MSG("error"),
    SRV_LOGIN("loginServlet"),
    EDIT_CAR(String.join("/", VIEW_ROOT.v(), "editCar.jsp"));

    private final String value;

    WebApp(String value) {
        this.value = value;
    }

    /**
     * Returns value.
     *
     * @return Value of value field.
     */
    public String v() {
        return this.value;
    }
}
