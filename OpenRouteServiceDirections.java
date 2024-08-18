import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OpenRouteServiceDirections {
    private static final String API_KEY = "KEY";
    private static final String BASE_URL = "https://api.openrouteservice.org/v2/directions/driving-car";

    public static String getFastestRoute(String origin, String destination, String[] avoidPoints) throws Exception {
        return RoutePlanner.getRoute(origin, destination, convertToAvoidAreas(avoidPoints));
    }

    private static String[][] convertToAvoidAreas(String[] avoidPoints) {
        if (avoidPoints == null || avoidPoints.length == 0) {
            return null;
        }
        String[][] avoidAreas = new String[1][avoidPoints.length];
        avoidAreas[0] = avoidPoints;
        return avoidAreas;
    }
}
