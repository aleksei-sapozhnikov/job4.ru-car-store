package carstore.constants;

public enum Attributes {
    HB_FACTORY("hibernateSessionFactory"),
    HB_SESSION("hibernateSession"),
    LOGGED_USER_ID("userLoggedInTheSessionId"),
    USER_STORE("userStore"),
    IMAGE_STORE("imageStore"),
    CAR_STORE("carStore"),
    TRANSFORMER("transformer"),


    CAR_STORE_ID("storeId");

    private final String value;

    Attributes(String value) {
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
