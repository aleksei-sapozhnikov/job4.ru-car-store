let defaultCarImage = 'components/item-default-image.jpg';

/**
 * Item - main item to sale.
 */
class Item {
    /** Store id */
    storeId;
    /** Title */
    title;
    /** Price */
    price;
    /** Descriptions */
    descriptions;
    /** Ids of images for this item */
    imagesIds;

    /**
     * Constructs new car object.
     * @param {number} storeId
     * @param {string} title
     * @param {number} price
     * @param descriptions
     * @param imagesIds
     */
    constructor(storeId, title, price, descriptions, imagesIds) {
        this.storeId = storeId;
        this.title = title;
        this.price = price;
        this.descriptions = descriptions;
        this.imagesIds = imagesIds;
    }

    /**
     * Formats item to html.
     * @return {string} html value
     */
    toHtml() {
        let res = ''
            + '<div class="item-image-div">'
            + '<img class="item-image-img"'
            + ` src="${this.imagesIds.length > 0 ? `image?id=${this.imagesIds[0]}` : defaultCarImage}"`
            + ` alt="${this.title}">`
            + '</div>';
        res += `<p class="item-title">${this.title}</>`;
        res += `<p class="item-price">${this.price.toLocaleString()}</>`;
        for (const [key, value] of Object.entries(this.descriptions)) {
            res += '<p class="item-description-text">'
                + `<span class = "item-description-title">${key}: </span>`
                + value
                + '</p>';
        }
        return res;
    }
}