package carstore.constants;

public enum Attributes {
    ATR_HB_FACTORY("hibernateSessionFactory"),
    ATR_HB_SESSION("hibernateSession"),

    ATR_LOGGED_USER_ID("userLoggedInTheSessionId"),

    ATR_USER_STORE("userStore"),
    ATR_IMAGE_STORE("imageStore"),
    ATR_CAR_STORE("carStore"),
    ATR_TRANSFORMER("transformer"),

    PRM_USER_LOGIN("user_login"),
    PRM_USER_PASSWORD("user_password"),
    PRM_USER_PHONE("user_phone"),

    PRM_CAR_STORE_ID("storeId");

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
