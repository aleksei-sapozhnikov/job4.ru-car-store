package carstore.constants;

public enum ConstContext {
    SESSION_FACTORY("sessionFactory"),
    USER_STORE("userStore"),
    IMAGE_STORE("imageStore"),
    CAR_STORE("carStore"),
    TRANSFORMER("transformer");

    private final String value;

    ConstContext(String value) {
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
