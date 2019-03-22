package carstore.constants;

public enum RequestParameters {
    STORE_ID("storeId");

    private final String value;

    RequestParameters(String value) {
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
