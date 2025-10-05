package control;

import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URI;

/*
 - Used to validate that entered guess is valid English word
 - If not, guess it not accepted
 */

public class GuessValidator {
    private static final String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public static Boolean isValid(String word) throws Exception {
        URI uri = new URI(API_URL + word);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        return conn.getResponseCode() == 200;
    }

}
