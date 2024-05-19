package PR.Pip.ro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JsonBuilder
 */
public class JsonBuilder {

    private JSONArray jsonArray;
    private JSONArray jsonDepou;
    private JSONArray jsonInMiscare;

    public JsonBuilder(String filePath) throws Exception {
        // Read the file content into a String
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        // Parse the JSON string into a JSONObject
        jsonArray = new JSONArray(fileContent);
        jsonDepou = new JSONArray();
        jsonInMiscare = new JSONArray();
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public void setJsonDeou(JSONArray jsonArray) {
        this.jsonDepou = jsonArray;
    }

    public void setJsonInMiscare(JSONArray jsonArray) {
        this.jsonInMiscare = jsonArray;
    }

    public JSONArray getJsonArray() {
        return this.jsonArray;
    }

    public JSONArray getJsonDeou() {
        return this.jsonDepou;
    }

    public JSONArray getJsonInMiscare() {
        return this.jsonInMiscare;
    }

    public void processAllObjects() {
        JSONArray array = getJsonArray();
        JSONArray jsonArray_full = new JSONArray();
        JSONArray jsonArray_depou = new JSONArray();
        JSONArray jsonArray_miscare = new JSONArray();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String curentTime = now.format(formatter);
        String date = curentTime.substring(0, 10);
        int ora = Integer.parseInt(curentTime.substring(11, 13));

        System.out.println(date);
        System.out.println(ora);

        for (int i = 0; i < array.length(); i++) {
            JSONObject currentObject = array.getJSONObject(i);
            JSONObject obj = procesJsonObject(currentObject);
            if (obj == null) {
                continue;
            }
            jsonArray_full.put(obj);
            if (!obj.getString("time").substring(0, 10).equals(date)) {
                jsonArray_depou.put(obj);
                continue;
            }
            if (ora - Integer.parseInt(obj.getString("time").substring(11, 13)) > 2) {
                jsonArray_depou.put(obj);
                continue;
            }
            jsonArray_miscare.put(obj);
        }

        setJsonArray(jsonArray_full);
        setJsonDeou(jsonArray_depou);
        setJsonInMiscare(jsonArray_miscare);
    }

    private JSONObject procesJsonObject(JSONObject jsonObject) {
        JSONObject obj = new JSONObject();
        int id = jsonObject.optInt("route_id");
        int type = jsonObject.optInt("vehicle_type");
        String bike = jsonObject.optString("bike_accessible");
        String chair = jsonObject.optString("wheelchair_accessible");
        double lat = jsonObject.optDouble("latitude");
        double lon = jsonObject.optDouble("longitude");
        String time = jsonObject.optString("timestamp");

        // id
        if (id == 0) {
            return null;
        }
        obj.put("id", id);
        // type{
        obj.put("type", type == 0 ? "Tram" : "Bus");
        // bike & chair
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
        // lat
        // lon
        System.out.println(Double.isFinite(lat));
        System.out.println(Double.isFinite(lon));
        if (Double.isFinite(lat) && Double.isFinite(lon)) {
            JSONObject position = new JSONObject();
            position.put("lat", lat);
            position.put("lng", lon);
            obj.put("position", position);
        } else {
            return null;
        }
        // time
        if (time == null) {
            return null;
        }
        obj.put("time", time);
        // return jsonObject;
        return obj;
    }

    public void saveToFile(String outputFilePath, String index_json_array) throws IOException {
        // Convert the JSONObject to a string
        String jsonString;

        switch (index_json_array) {
            case "full":
                jsonString = getJsonArray().toString();
                break;
            case "depou":
                jsonString = getJsonDeou().toString();
                break;
            case "miscare":
                jsonString = getJsonInMiscare().toString();
                break;
            default:
                System.out.println("err");
                return;
        }
        ;

        // Write the string to a file
        Files.write(Paths.get(outputFilePath), jsonString.getBytes());
    }

    public static void main(String[] args) {
        try {
            String DirectoryPath = System.getProperty("user.dir") + "\\Request";
            String inputFilePath = DirectoryPath + "\\input.json";
            String outputFilePath = DirectoryPath + "\\output.json";
            String depouFilePath = DirectoryPath + "\\depou.json";
            String miscareFilePath = DirectoryPath + "\\miscare.json";

            JsonBuilder builder = new JsonBuilder(inputFilePath);

            builder.processAllObjects();

            builder.saveToFile(outputFilePath, "full");
            builder.saveToFile(miscareFilePath, "miscare");
            builder.saveToFile(depouFilePath, "depou");

            System.out.println("JSON has been successfully modified and saved.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}