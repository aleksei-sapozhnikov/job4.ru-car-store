package carstore.constants;

import java.util.Optional;

public enum WebApp {
    BASEDIR(Optional.ofNullable(System.getProperty("catalina.base")).orElse("basedir")),

    MSG_ERROR("error"),
    MSG_SUCCESS("success"),

    SRV_LOGIN("loginServlet"),

    VIEW_ROOT("/WEB-INF/view"),
    PG_CREATE_USER("createUser.jsp"),
    PG_EDIT_CAR("editCar.jsp");

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
