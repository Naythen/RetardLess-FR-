package ro.tuiasi.ac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JsonBuilder.
 */
public class JsonBuilder {
    /**
     *
     */
    private JSONObject jsonArray;
    /**
     *
     */
    private JSONObject jsonDepou;

    /**
     *
     */
    private JSONObject jsonInMiscare;
    /**
     *
     */
    private JSONArray jsonArrayRouts;
    /**
     *
     */
    private JSONArray jsonArrayStops;
    /**
     *
     */
    private JSONArray jsonArrayStopsTime;
    /**
     *
     */
    private int numarDepou = 0;
    /**
     *
     */
    private int numarMiscare = 0;

    public JsonBuilder() {
    }

    /**
     * Constructor for the JsonBuilder class.
     *
     * @param filePath       The path to the file containing the vehicle data.
     * @param fileRouts      The path to the file containing the route data.
     * @param fileStops      The path to the file containing the stop data.
     * @param fileStopsTimes The path to the file containing the stop times data.
     * @throws Exception If an error occurs while reading the files or parsing the
     *                   JSON.
     */
    public JsonBuilder(final String filePath, final String fileRouts, final String fileStops,
            final String fileStopsTimes)
            throws Exception {
        // Read the content of the filePath file into a string
        final String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        // Read the content of the fileRouts file into a string
        final String fileContent1 = new String(Files.readAllBytes(Paths.get(fileRouts)));

        // Read the content of the fileStops file into a string
        final String fileContent2 = new String(Files.readAllBytes(Paths.get(fileStops)));

        // Read the content of the fileStopsTimes file into a string
        final String fileContent3 = new String(Files.readAllBytes(Paths.get(fileStopsTimes)));

        // Parse the JSON string from the filePath file into a JSONArray
        JSONArray temp = new JSONArray(fileContent);

        // Assign the parsed JSONArray to the jsonArray instance variable
        setJsonArray(temp);

        // Assign the parsed JSON string from the fileRouts file to the jsonArrayRouts
        // instance variable
        setJsonRouts(fileContent1);

        // Assign the parsed JSON string from the fileStops file to the jsonArrayStops
        // instance variable
        setJsonStops(fileContent2);

        // Assign the parsed JSON string from the fileStopsTimes file to the
        // jsonArrayStopsTime instance variable
        setJsonStopsTimes(fileContent3);

        // Create a new JSONObject and assign it to the jsonDepou instance variable
        jsonDepou = new JSONObject();

        // Create a new JSONObject and assign it to the jsonInMiscare instance variable
        jsonInMiscare = new JSONObject();
    }

    /**
     * This method sets the JSONArray for the vehicles.
     * It wraps the given JSONArray into a JSONObject with the key "vehicles".
     *
     * @param jsonArray The JSONArray containing the vehicle data.
     * @return void
     */
    public void setJsonArray(final JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonArray = temp;
    }

    /**
     * This method sets the JSONArray for the vehicles that are in depou.
     * It wraps the given JSONArray into a JSONObject with the key "vehicles".
     *
     * @param jsonArray The JSONArray containing the vehicle data that are in depou.
     * @return void
     */
    public void setJsonDeou(final JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonDepou = temp;
    }

    /**
     * This method sets the JSONArray for the vehicles that are in movement.
     * It wraps the given JSONArray into a JSONObject with the key "vehicles".
     *
     * @param jsonArray The JSONArray containing the vehicle data that are in
     *                  movement.
     * @return void
     */
    public void setJsonInMiscare(final JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonInMiscare = temp;
    }

    /**
     * This method sets the JSONArray for the vehicle routes.
     * It parses the given JSON string into a JSONArray and assigns it to the
     * 'jsonArrayRouts' instance variable.
     *
     * @param jsonArray A JSON string containing the vehicle route data.
     * @return void
     */
    public void setJsonRouts(final String jsonArray) {
        try {
            this.jsonArrayRouts = new JSONArray(jsonArray);
        } catch (JSONException e) {
            System.err.println("Failed to parse JSON array: " + e.getMessage());
        }
    }

    public void setJsonStops(final String jsonArray) {
        try {
            this.jsonArrayStops = new JSONArray(jsonArray);
        } catch (JSONException e) {
            System.err.println("Failed to parse JSON array: " + e.getMessage());
        }
    }

    public void setJsonStopsTimes(final String jsonArray) {
        try {
            this.jsonArrayStopsTime = new JSONArray(jsonArray);
        } catch (JSONException e) {
            System.err.println("Failed to parse JSON array: " + e.getMessage());
        }
    }

    /**
     *
     * @return
     */
    public JSONObject getJsonArray() {
        return this.jsonArray;
    }

    /**
     *
     * @return
     */
    public JSONObject getJsonDeou() {
        return this.jsonDepou;
    }

    /**
     *
     * @return
     */
    public JSONObject getJsonInMiscare() {
        return this.jsonInMiscare;
    }

    /**
     *
     * @return
     */
    public JSONArray getJsonRouts() {
        return this.jsonArrayRouts;
    }

    public JSONArray getJsonStops() {
        return this.jsonArrayStops;
    }

    public JSONArray getJsonStopsTime() {
        return this.jsonArrayStopsTime;
    }

    /**
     *
     * @return
     */
    public int getDepouCount() {
        return this.numarDepou;
    }

    /**
     *
     * @return
     */
    public int getMiscareCount() {
        return this.numarMiscare;
    }

    /**
     *
     * @param x
     */
    public void setDepouCount(final int x) {
        this.numarDepou = x;
    }

    /**
     *
     * @param x
     */
    public void setMiscareCount(final int x) {
        this.numarMiscare = x;
    }

    /**
     * This method processes all the vehicle objects in the JSON array.
     * It categorizes the vehicles into those in depou and those in movement.
     * It also updates the count of vehicles in depou and those in movement.
     */
    public void processAllObjects() {
        JSONArray array = this.jsonArray.getJSONArray("vehicles");
        JSONArray jsonArrayFull = new JSONArray();
        JSONArray jsonArrayDepou = new JSONArray();
        JSONArray jsonArrayMiscare = new JSONArray();
        setDepouCount(0);
        setMiscareCount(0);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String curentTime = now.format(formatter);
        // String date = curentTime.substring(0, 10);
        String yearMonth = curentTime.substring(0, 8);
        int day = Integer.parseInt(curentTime.substring(8, 10));
        int ora = Integer.parseInt(curentTime.substring(11, 13));
        System.out.println("curent year: " + yearMonth);
        System.out.println("curent day: " + day);
        System.out.println("curent hour: " + ora);

        for (int i = 0; i < array.length(); i++) {
            JSONObject currentObject = array.getJSONObject(i);

            JSONObject obj = procesJsonObject(currentObject);
            if (obj == null) {
                continue;
            }
            jsonArrayFull.put(obj);
            if (obj.getString("time").substring(0, 8).equals(yearMonth)) {
                if (day - Integer.parseInt(obj.getString("time").substring(11, 13)) > 1) {
                    numarMiscare++;
                    jsonArrayMiscare.put(obj);
                    continue;
                }
            }
            jsonArrayDepou.put(obj);
            numarDepou++;
        }

        setJsonArray(jsonArrayFull);
        setJsonDeou(jsonArrayDepou);
        setJsonInMiscare(jsonArrayMiscare);
    }

    /**
     * This method finds the route long name and direction by routeId.
     *
     * @param routeId The routeId for which to find the route long name and
     *                direction.
     * @return An array of strings containing the route long name and direction.
     *         If the routeId is not found, it returns null.
     */
    private String[] findRouteLongNameByRouteId(final int routeId) {
        JSONArray routesArray = getJsonRouts();
        String[] r = new String[2];
        for (int i = 0; i < routesArray.length(); i++) {
            JSONObject routeObj = routesArray.getJSONObject(i);
            if (routeObj.getInt("route_id") == routeId) {
                r[0] = routeObj.getString("route_long_name");
                String[] words = routeObj.getString("route_desc").split("-+");
                r[1] = words[words.length - 1];
                return r;
            }
        }
        return null;
    }

    /**
     * This method processes a single JSONObject from the input data and transforms
     * it into a new JSONObject
     * with the required format.
     *
     * @param jsonObject The JSONObject to be processed.
     * @return A new JSONObject with the transformed data, or null if the input data
     *         is invalid.
     */
    private JSONObject procesJsonObject(final JSONObject jsonObject) {
        JSONObject obj = new JSONObject();
        int id = jsonObject.optInt("id");
        int routeId = jsonObject.optInt("route_id");
        int type = jsonObject.optInt("vehicle_type");
        String bike = jsonObject.optString("bike_accessible");
        String chair = jsonObject.optString("wheelchair_accessible");
        double lat = jsonObject.optDouble("latitude");
        double lon = jsonObject.optDouble("longitude");
        String time = jsonObject.optString("timestamp");

        // id
        // If the id is 0, return null to indicate invalid data.
        if (id == 0) {
            return null;
        }
        obj.put("id", id);

        // route_id
        // If the routeId is 0, return null to indicate invalid data.
        if (routeId == 0) {
            return null;
        }

        // Find the route long name and direction by routeId.
        String[] r = findRouteLongNameByRouteId(routeId);
        if (r == null) {
            return null;
        }
        obj.put("routeName", r[0]);
        obj.put("direction", r[1]);

        // type
        // Convert the vehicle type to a string representation.
        obj.put("vehicleType", type == 0 ? "Tram" : "Bus");

        // bike & chair
        // Build a string representation of the bike and chair accessibility features.
        String features = "";
        if (bike.equals("BIKE_ACCESSIBLE")) {
            features += "BIKE";
        }
        if (chair.equals("WHEELCHAIR_ACCESSIBLE")) {
            if (!features.equals("")) {
                features += " & ";
            }
            features += "WHEELCHAIR";
        }
        obj.put("features", features);

        // lat & lon
        // If the latitude or longitude is not finite, return null to indicate invalid
        // data.
        if (Double.isFinite(lat) && Double.isFinite(lon)) {
            JSONObject position = new JSONObject();
            position.put("lat", lat);
            position.put("lng", lon);
            obj.put("positions", position);
        } else {
            return null;
        }

        // opriri
        // Find the number of stop times by the stop ID obtained from the latitude and
        // longitude.
        int stops = getStopTimes(getStop(lat, lon));
        obj.put("opriri", stops);

        // time
        // If the time is null, return null to indicate invalid data.
        if (time == null) {
            return null;
        }
        obj.put("time", time);

        // Return the transformed JSONObject.
        return obj;
    }

    /**
     * This method retrieves the number of stop times for a given stop ID.
     *
     * @param stop_id The ID of the stop for which to retrieve the stop times.
     * @return The number of stop times for the given stop ID. If the stop ID is 0,
     *         it returns 0. If the stop ID is not found in the stop times array,
     *         it returns 0.
     */
    public int getStopTimes(int stop_id) {
        // If the stop ID is 0, return 0
        if (stop_id == 0) {
            return 0;
        }

        // Get the array of stop times from the JSON object
        JSONArray stops = getJsonStopsTime();

        // Iterate through each stop time in the array
        for (Object stop : stops) {
            JSONObject stopj = (JSONObject) stop;

            // If the current stop time's stop ID matches the given stop ID,
            // return the stop sequence
            if (stopj.getInt("stop_id") == stop_id) {
                return stopj.getInt("stop_sequence");
            }
        }

        // If the stop ID is not found in the stop times array, return 0
        return 0;
    }

    /**
     * This method retrieves the stop ID based on the given latitude and longitude.
     * It checks if the latitude and longitude are valid, then iterates through the
     * array of stops
     * to find a match within a certain radius.
     *
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @return The stop ID if a match is found within the radius, otherwise returns
     *         0.
     */
    public int getStop(double lat, double lon) {
        // Check if latitude and longitude are valid
        if (lat <= 0 || lon <= 0) {
            return 0;
        }

        // Get the array of stops from the JSON object
        JSONArray array = this.getJsonStops();

        // Iterate through each stop in the array
        for (int i = 0; i < array.length(); i++) {
            JSONObject stop = array.getJSONObject(i);

            // Get the latitude and longitude of the current stop
            int stopLat = stop.getInt("stop_lat");
            int stopLon = stop.getInt("stop_lon");

            // Check if the current stop is within a certain radius of the given latitude
            // and longitude
            if (Math.abs(stopLat - lat) <= 0.00002 && Math.abs(stopLon - lon) <= 0.00002) {
                // Return the stop ID if a match is found
                return stop.getInt("stop_id");
            }
        }

        // Return 0 if no stop is found within the radius
        return 0;
    }

    /**
     * This method saves the processed JSON data to a file.
     *
     * @param outputFilePath The path where the output file will be saved.
     * @param indexJsonArray The index of the JSON array to be saved. It can be one
     *                       of the following:
     *                       - "full": Saves the full processed JSON data.
     *                       - "depou": Saves the JSON data of vehicles in depou.
     *                       - "miscare": Saves the JSON data of vehicles in
     *                       movement.
     * @throws IOException If an error occurs while writing the file.
     */
    public void saveToFile(final String outputFilePath, final String indexJsonArray) throws IOException {
        // Convert the JSONObject to a string
        String jsonString;

        // Determine which JSON array to save based on the indexJsonArray parameter
        switch (indexJsonArray) {
            case "full":
                // Save the full processed JSON data
                jsonString = getJsonArray().toString();
                break;
            case "depou":
                // Save the JSON data of vehicles in depou
                jsonString = getJsonDeou().toString();
                break;
            case "miscare":
                // Save the JSON data of vehicles in movement
                jsonString = getJsonInMiscare().toString();
                break;
            default:
                // Invalid indexJsonArray parameter
                System.out.println("err");
                return;
        }

        // Write the string to a file
        Files.write(Paths.get(outputFilePath), jsonString.getBytes());
    }

    /**
     * This is the main method for running the program.
     * It initializes the necessary variables, processes the data, and saves the
     * results to output files.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(final String[] args) {
        try {

            // Make a timed request to retrieve data
            Request.timedrequest(1);

            // Define the directory path for input and output files
            String directoryPath = System.getProperty("user.dir") + "\\Request";

            // Define the file paths for input and output files
            String inputFilePath = directoryPath + "\\reqVehicule0.json";
            String inputFilePath1 = directoryPath + "\\reqRouts0.json";
            String inputFilePath2 = directoryPath + "\\reqStops0.json";
            String inputFilePath3 = directoryPath + "\\reqStopTimes0.json";
            String outputFilePath = directoryPath + "\\output.json";
            String depouFilePath = directoryPath + "\\depou.json";
            String miscareFilePath = directoryPath + "\\miscare.json";

            // Make a timed request to retrieve data
            Request.timedrequest(0);

            // Create a new JsonBuilder object with the input file paths
            JsonBuilder builder = new JsonBuilder(inputFilePath, inputFilePath1, inputFilePath2, inputFilePath3);

            // Process the data in the JsonBuilder object
            builder.processAllObjects();

            // Save the processed data to output files
            builder.saveToFile(outputFilePath, "full");
            builder.saveToFile(miscareFilePath, "miscare");
            builder.saveToFile(depouFilePath, "depou");

            // Print the counts of vehicles in depou and in movement
            System.out.println("numar depou : " + builder.getDepouCount());
            System.out.println("numar miscare : " + builder.getMiscareCount());

            // Print a success message
            System.out.println("JSON has been successfully modified and saved.");
        } catch (Exception e) {
            // Print the error message if an exception occurs
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
