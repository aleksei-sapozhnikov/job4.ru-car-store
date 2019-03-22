package carstore.constants;

public enum AppPaths {
    VIEW_ROOT("/WEB-INF/view"),
    EDIT_CAR(String.join("/", VIEW_ROOT.v(), "editCar.jsp"));

    private final String value;

    AppPaths(String value) {
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
