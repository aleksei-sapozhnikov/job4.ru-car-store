package carstore.constants;

import java.util.Optional;

public enum WebApp {
    BASEDIR(Optional.ofNullable(System.getProperty("catalina.base")).orElse("basedir")),
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
