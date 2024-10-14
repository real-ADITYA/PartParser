package priceScraper;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EBAYDriver {

    private ArrayList<EBAYProduct> products;
    private static final String EBAY_URL_BASE = "https://www.ebay.com/sch/i.html?_from=R40&_nkw=";
    private static final String EBAY_URL_END = "&_sacat=0&LH_BIN=1&_sop=15&rt=nc&LH_ItemCondition=";

    /** This function will build the eBay URL based on the search query. By default, we use the buy-it-now purchase
     * option and choose between new (0), used/open box (1), or not-working (2) conditions for the item. (New
     * condition will be chosen if an invalid option is entered) */
    private static String buildURL(String searchQuery, int condition) {
    	String condURLPart;
    	if(condition == 1) {
    		condURLPart = "1500%7C3000";
    	} else if(condition == 2) {
    		condURLPart = "7000";
    	} else {
    		condURLPart = "1000";
    	}
        String formattedSearchQuery = searchQuery.replace(' ', '+');
        return EBAY_URL_BASE + formattedSearchQuery + EBAY_URL_END + condURLPart;
    }

    /** Runs the eBay scraper with the provided search query and condition. **/
    public void runScraper(String userIn, int condition) {
        String searchURL = buildURL(userIn, condition);
        Document doc = connectToSite(searchURL);

        if (doc == null) {
            System.out.println("Failed to connect to eBay.");
            return;
        }
        System.out.println("Connected to eBay successfully: " + searchURL);

        products = new ArrayList<>();
        Elements elementList = doc.select(".s-item");

        for (Element currentElement : elementList) {
            // Ignore certain irrelevant items in the list
            if (currentElement.selectFirst(".s-item__title").text().equals("Shop on eBay")) {
                continue;
            }

            EBAYProduct product = new EBAYProduct(currentElement.selectFirst(".s-item__link").attr("href"));
            product.setName(currentElement.selectFirst(".s-item__title").text());

            // Parse price
            if (!product.setPrice(currentElement.selectFirst(".s-item__price").text())) {
                continue; // Skip if price is invalid
            }

            Element shippingElement = currentElement.selectFirst(".s-item__shipping");
            product.setShipping(shippingElement != null ? shippingElement.text() : "$0.00 shipping");

            Element locationElement = currentElement.selectFirst(".s-item__itemLocation");
            product.setLocation(locationElement != null ? locationElement.text() : "from US");

            // Filtering out irrelevant listings by name
            String productName = product.getName().toUpperCase();
            if (!productName.contains("BOX ONLY") && !productName.contains("FAN ONLY") &&
                    !productName.contains("W/O") && !productName.contains("READ") &&
                    !productName.contains("PLEASE READ") && products.size() <= 60) {

                products.add(product);
            }
        }
    }

    /** Connects to the given URL using Jsoup and returns the Document. **/
    private Document connectToSite(String url) {
        try {
            return Jsoup.connect(url).userAgent("Mozilla/5.0").header("Accept-Language", "*").get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /** Returns the list of scraped products. */
    public ArrayList<EBAYProduct> getProducts() {
        return products;
    }

    /** Returns a list of all prices from the scraped products. **/
    public ArrayList<Double> getAllPrices() {
        ArrayList<Double> prices = new ArrayList<>();
        for (EBAYProduct product : products) {
            prices.add(product.getPrice());
        }
        return prices;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (EBAYProduct product : products) {
            result.append(product.toString());
        }
        return result.toString();
    }
}
