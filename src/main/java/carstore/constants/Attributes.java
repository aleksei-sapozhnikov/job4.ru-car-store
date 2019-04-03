package carstore.constants;

public enum Attributes {
    ATR_CONTEXT_PATH("contextPath"),

    ATR_SELECT_VALUES("selectValues"),

    ATR_HB_FACTORY("hibernateSessionFactory"),
    ATR_HB_SESSION("hibernateSession"),

    ATR_LOGGED_USER_ID("userLoggedInTheSessionId"),
    ATR_LOGGED_USER_LOGIN("userLoggedInTheSessionLogin"),

    ATR_CAR_TO_EDIT("editCar"),
    ATR_CAR_IMAGE_IDS("carImageIds"),

    ATR_JSON_PARSER("jsonParser"),
    ATR_USER_STORE("userStore"),
    ATR_IMAGE_STORE("imageStore"),
    ATR_CAR_STORE("carStore"),

    ATR_ITEM_FACTORY("frontItemFactory"),
    ATR_CAR_FACTORY("carFactory"),
    ATR_IMAGE_FACTORY("imageFactory"),

    PRM_USER_LOGIN("user_login"),
    PRM_USER_PASSWORD("user_password"),
    PRM_USER_PHONE("user_phone"),

    PRM_CAR_ID("carId"),
    PRM_CAR_AVAILABLE("carAvailable"),

    PRM_CAR_MANUFACTURER("carManufacturer"),
    PRM_CAR_MODEL("carModel"),
    PRM_CAR_NEWNESS("carNewness"),
    PRM_CAR_BODY_TYPE("carBodyType"),
    PRM_CAR_COLOR("carColor"),
    PRM_CAR_ENGINE_FUEL("carEngineFuel"),
    PRM_CAR_TRANSMISSION_TYPE("carTransmissionType"),

    PRM_CAR_PRICE("carPrice"),
    PRM_CAR_YEAR_MANUFACTURED("carYearManufactured"),
    PRM_CAR_MILEAGE("mileage"),
    PRM_CAR_ENGINE_VOLUME("carEngineVolume"),

    PRM_IMAGE_ID("imageId"),
    PRM_IMAGE_KEY_START("image");

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
