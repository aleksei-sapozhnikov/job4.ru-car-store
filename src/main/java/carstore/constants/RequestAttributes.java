package carstore.constants;

public enum RequestAttributes {
    EDIT_CAR("editCar");


    private final String value;

    RequestAttributes(String value) {
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
