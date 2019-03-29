package carstore.constants;

public enum Attributes {
    ATR_HB_FACTORY("hibernateSessionFactory"),
    ATR_HB_SESSION("hibernateSession"),

    ATR_LOGGED_USER_ID("userLoggedInTheSessionId"),

    ATR_JSON_PARSER("gson"),
    ATR_USER_STORE("userStore"),
    ATR_IMAGE_STORE("imageStore"),
    ATR_CAR_STORE("carStore"),
    ATR_ITEM_FACTORY("frontItemFactory"),

    PRM_USER_LOGIN("user_login"),
    PRM_USER_PASSWORD("user_password"),
    PRM_USER_PHONE("user_phone"),

    PRM_IMAGE_ID("id"),

    PRM_CAR_STORE_ID("carId");

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
