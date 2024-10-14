package priceScraper;

public class EBAYProduct {
    private String url, name, location;
    private Double price, shipping;

    /** A product is created using the associated web URL. Fields are updated using set methods. **/
    public EBAYProduct(String url) { this.url = url; }

    /** Get the URL. **/
    public String getURL() { return url; }

    /** Return the name (listing title) of the product. **/
    public String getName() { return name; }
    /** Set the name (listing title) of the product. **/
    public void setName(String name) {this.name = name; }

    /** Return the location where the product is being shipped from. **/
    public String getLocation() { return location; }
    /** Set the location where the product is being shipped from. **/
    public void setLocation(String location) { this.location = location; }

    /** Return the price of the item (with shipping). **/
    public Double getPrice() { return price + getShipping(); }
    /** Sets the actual price of the item, since the format varies from listing to listing. Returns true
     * 	if the operations were successful. **/
    public boolean setPrice(String price) {
        // If the input includes the keyword 'to' or ',' then we know there is a complexity present.
        if(!(price.contains("to")) && !price.contains(",")) {
            this.price = Double.valueOf(price.substring(1));
            return true;
        } else {
            return false;
        }
    }

    /** Return the shipping cost of the item. **/
    public Double getShipping() { return shipping; }
    /** Set the shipping cost of the item, this can also be free shipping so there are complexities. **/
    public void setShipping(String shipping) {
        if(shipping.contains("Free shipping")) {
            this.shipping = 0.0;
        } else {
            this.shipping = Double.valueOf(shipping.substring(2, 6));
        }
    }

    @Override
    public String toString() {
        return this.name + "\n[$" + String.format("%.2f", (this.price + this.shipping)) + "] = $"
                + String.format("%.2f", this.price) + " + $" + String.format("%.2f", this.shipping)
                + "\n" + this.location + "\n";
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof EBAYProduct)) {
            return false;
        }
        EBAYProduct other_product = (EBAYProduct) other;
        return this.name.equals(other_product.getName());
    }
}