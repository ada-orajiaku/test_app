package com.andela.git.thegitapp.services;

import com.andela.git.thegitapp.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

/**
 * Created by adaobifrank on 3/7/17.
 */

public class UserService extends BaseService
{
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response, null if no response
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }
//
//    public static List<User> parseUsersFromJsonResponse (String forecastJsonStr) throws JSONException
//    {
//        JSONObject forecastJson = new JSONObject(forecastJsonStr);
//
////        /* Is there an error? */
////        if (forecastJson.has(OWM_MESSAGE_CODE))
////        {
////            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);
////
////            switch (errorCode)
////            {
////                case HttpURLConnection.HTTP_OK:
////                    break;
////                case HttpURLConnection.HTTP_NOT_FOUND:
////                    /* Location invalid */
////                    return null;
////                default:
////                    /* Server probably down */
////                    return null;
////            }
////        }
////
////        JSONArray jsonWeatherArray = forecastJson.getJSONArray(OWM_LIST);
////
////        JSONObject cityJson = forecastJson.getJSONObject(OWM_CITY);
////
////        JSONObject cityCoord = cityJson.getJSONObject(OWM_COORD);
////        double cityLatitude = cityCoord.getDouble(OWM_LATITUDE);
////        double cityLongitude = cityCoord.getDouble(OWM_LONGITUDE);
//    }
}
