import java.util.HashMap;
import java.util.Random;

public class URLShortener {
    private HashMap<String, String> keyMap; // key-url map
    private HashMap<String, String> valueMap; // url-key map to quickly check whether an url is already entered in our system
    private String domain; // Use this attribute to generate urls for a custom domain name defaults to http://fkt.in
    private char myChars[]; // This array is used for character to number mapping
    private Random myRand; // Random object used to generate random integers
    private int keyLength; // the key length in URL defaults to 8

    // Default Constructor
    public URLShortener() {
        keyMap = new HashMap<String, String>();
        valueMap = new HashMap<String, String>();
        myRand = new Random();
        keyLength = 8;
        myChars = new char[62];
        for (int i = 0; i < 62; i++) {
            int j = 0;
            if (i < 10) {
                j = i + 48;
            } else if (i > 9 && i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            myChars[i] = (char) j;
        }
    }

    // Function to shorten a URL
    public String shortenURL(String url) {
        if (valueMap.containsKey(url)) {
            return domain + "/" + valueMap.get(url);
        } else {
            String key;
            do {
                key = generateKey();
            } while (keyMap.containsKey(key));
            keyMap.put(key, url);
            valueMap.put(url, key);
            return domain + "/" + key;
        }
    }

    // Function to expand a short URL
    public String expandURL(String shortURL) {
        String key = shortURL.replace(domain + "/", "");
        return keyMap.get(key);
    }

    // Function to generate a random key
    private String generateKey() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyLength; i++) {
            sb.append(myChars[myRand.nextInt(62)]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        URLShortener shortener = new URLShortener();
        String longURL = "https://www.geeksforgeeks.org/count-sum-of-digits-in-numbers-from-1-to-n/";
        String shortURL = shortener.shortenURL(longURL);
        System.out.println("Short URL: " + shortURL);
        System.out.println("Original URL: " + shortener.expandURL(shortURL));
    }
}