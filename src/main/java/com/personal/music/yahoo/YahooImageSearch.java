package com.personal.music.yahoo;

import com.personal.music.util.Utils;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * Sample code to use Yahoo Search BOSS
 * <p/>
 * Please include the following libraries
 * 1. Apache Log4j
 * 2. oAuth Signpost
 *
 * @author xyz
 */
public class YahooImageSearch {

    private static final Logger log = Logger.getLogger(YahooImageSearch.class);
    /**
     * Encode Format
     */
    private static final String ENCODE_FORMAT = "UTF-8";
    /**
     * Call Type
     */
    private static final String callType = "images";
    private static final int HTTP_STATUS_OK = 200;
    protected static String yahooServer = "https://yboss.yahooapis.com/ysearch/";
    // Please provide your consumer key here
    private static String consumer_key = "dj0yJmk9VHV2RUlEVWd0ZGFlJmQ9WVdrOVlrWm1URFYwTkdFbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD02Yg--";
    // Please provide your consumer secret here
    private static String consumer_secret = "59d4ef78992b531dc027d9477b790f86cb9d3429";
    /**
     * The HTTPS request object used for the connection
     */
    private static StHttpRequest httpsRequest = new StHttpRequest();
    private String searchString;
    private XPath xPath = null;

    public YahooImageSearch() {
        super();
        xPath = XPathFactory.newInstance().newXPath();
    }

    /**
     * @return
     */
    public String getImageUrl() throws Exception {

        if (this.isConsumerKeyExists() && this.isConsumerSecretExists()) {
            // Start with call Type
            String params = callType;
            // Add query
            params = params.concat("?q=");
            // Encode Query string before concatenating
            params = params.concat(URLEncoder.encode(this.getSearchString(), "UTF-8"));
            // Create final URL
            String url = yahooServer + params + "&format=xml&count=1";
            // Create oAuth Consumer
            OAuthConsumer consumer = new DefaultOAuthConsumer(consumer_key, consumer_secret);

            // Set the HTTPS request correctly
            httpsRequest.setOAuthConsumer(consumer);

            try {
                log.info("sending get request to" + URLDecoder.decode(url, ENCODE_FORMAT));
                int responseCode = httpsRequest.sendGetRequest(url);

                // Send the request
                if (responseCode == HTTP_STATUS_OK) {
                    log.info("Response ");
                } else {
                    log.error("Error in response due to status code = " + responseCode);
                }
                String responseBody = httpsRequest.getResponseBody();
                if (responseBody != null && responseBody.trim().length() > 0) {
                    Document doc = Utils.getDocument(responseBody);
                    if (doc != null) {
                        String imageUrl = (String) xPath.evaluate("/bossresponse/images/results/result/url/text()", doc, XPathConstants.STRING);
                        if (imageUrl != null && imageUrl.trim().length() > 0)
                            return imageUrl;
                    }
                }

            } catch (UnsupportedEncodingException e) {
                log.error("Encoding/Decoding error");
            } catch (IOException e) {
                log.error("Error with HTTP IO", e);
            } catch (Exception e) {
                log.error(httpsRequest.getResponseBody(), e);
            }


        } else {
            log.error("Key/Secret does not exist");
        }
        return null;
    }

    private String getSearchString() {
        return this.searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    private boolean isConsumerKeyExists() {
        if (consumer_key.isEmpty()) {
            log.error("Consumer Key is missing. Please provide the key");
            return false;
        }
        return true;
    }

    private boolean isConsumerSecretExists() {
        if (consumer_secret.isEmpty()) {
            log.error("Consumer Secret is missing. Please provide the key");
            return false;
        }
        return true;
    }
}