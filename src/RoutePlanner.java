import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RoutePlanner {
    private static final String API_KEY = "KEY";
    private static final String BASE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";
    private static final int MAX_POLYGON_POINTS = 4; // Adjust this value based on your requirements

    public static String getRoute(String origin, String destination, String[][] avoidAreas) throws Exception {
        OkHttpClient client = new OkHttpClient();
        String[] originCoords = origin.split(",");
        String[] destinationCoords = destination.split(",");

        JsonObject jsonRequest = new JsonObject();
        JsonArray coordinates = new JsonArray();

        // Add origin and destination coordinates
        JsonArray originArray = new JsonArray();
        originArray.add(Double.parseDouble(originCoords[1]));
        originArray.add(Double.parseDouble(originCoords[0]));
        coordinates.add(originArray);

        JsonArray destinationArray = new JsonArray();
        destinationArray.add(Double.parseDouble(destinationCoords[1]));
        destinationArray.add(Double.parseDouble(destinationCoords[0]));
        coordinates.add(destinationArray);

        jsonRequest.add("coordinates", coordinates);

        // Add avoid polygons (if any) within the options object
        if (avoidAreas != null && avoidAreas.length > 0) {
            JsonObject options = new JsonObject();
            JsonArray avoidPolygons = new JsonArray();

            for (String[] area : avoidAreas) {
                JsonArray polygon = new JsonArray();
                for (String point : area) {
                    String[] pointCoords = point.split(",");
                    JsonArray pointArray = new JsonArray();
                    pointArray.add(Double.parseDouble(pointCoords[1])); // Longitude
                    pointArray.add(Double.parseDouble(pointCoords[0])); // Latitude
                    polygon.add(pointArray);
                }
                // Ensure the polygon is closed by repeating the first point
                polygon.add(polygon.get(0));
                avoidPolygons.add(polygon);
            }

            JsonObject avoidPolygon = new JsonObject();
            avoidPolygon.addProperty("type", "Polygon");
            avoidPolygon.add("coordinates", avoidPolygons);

            options.add("avoid_polygons", avoidPolygon);
            jsonRequest.add("options", options);

            // Log the avoid polygons for debugging
            System.out.println("Avoid Polygons: " + avoidPolygons.toString());
        }

        Request request = new Request.Builder()
                .url(BASE_URL)
                .addHeader("Authorization", API_KEY)
                .post(okhttp3.RequestBody.create(jsonRequest.toString(), okhttp3.MediaType.parse("application/json")))
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            String jsonResponse = response.body().string();
            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            return jsonObject.toString();
        } else {
            String errorMessage = response.message();
            String responseBody = response.body() != null ? response.body().string() : "No response body";
            throw new Exception("Error: " + errorMessage + ". Response Body: " + responseBody);
        }
    }

    public static double getRouteDuration(String routeResponse) {
        JsonObject routeJson = JsonParser.parseString(routeResponse).getAsJsonObject();
        JsonArray routes = routeJson.getAsJsonArray("routes");
        if (routes.size() > 0) {
            JsonObject route = routes.get(0).getAsJsonObject();
            JsonObject summary = route.getAsJsonObject("summary");
            return summary.get("duration").getAsDouble();
        }
        return Double.MAX_VALUE; // Return a large number if no valid route is found
    }

    public static boolean isWithinAcceptableTime(double fastestRouteDuration, double routeDuration) {
        return routeDuration <= fastestRouteDuration * 1.25;
    }
}
