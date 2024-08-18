import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoogleMapsDirections {
    private static final String API_KEY = "Google Key";
    private static final String BASE_URL = "https://routes.googleapis.com/directions/v2:computeRoutes";

    public static String getFastestRoute(String origin, String destination) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String requestBody = String.format(
                "{\"origin\":{\"location\":{\"latLng\":{\"latitude\":%s,\"longitude\":%s}}}," +
                        "\"destination\":{\"location\":{\"latLng\":{\"latitude\":%s,\"longitude\":%s}}}," +
                        "\"travelMode\":\"DRIVE\"," +
                        "\"routingPreference\":\"TRAFFIC_AWARE\"," +
                        "\"computeAlternativeRoutes\":false," +
                        "\"routeModifiers\":{\"avoidTolls\":false,\"avoidHighways\":false,\"avoidFerries\":false}," +
                        "\"languageCode\":\"en-US\"}",
                origin.split(",")[0], origin.split(",")[1],
                destination.split(",")[0], destination.split(",")[1]
        );

        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Goog-Api-Key", API_KEY)
                .post(okhttp3.RequestBody.create(requestBody, okhttp3.MediaType.parse("application/json")))
                .build();

        System.out.println("Request URL: " + request.url()); // Log the request URL
        System.out.println("Request Body: " + requestBody); // Log the request body

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();
            System.out.println("Response: " + jsonResponse); // Log the response
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return jsonObject.toString();
        } else {
            System.err.println("Error: " + response.message()); // Log the error message
            throw new Exception("Error: " + response.message());
        }
    }
}
