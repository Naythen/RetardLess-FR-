package ro.tuiasi.ac;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JsonBuilder
 */
public class JsonBuilder {

    private JSONObject jsonArray;
    private JSONObject jsonDepou;
    private JSONObject jsonInMiscare;

    private int numar_depou = 0;
    private int numar_miscare = 0;

    public JsonBuilder(String filePath) throws Exception {
        // Read the file content into a String
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        JSONArray temp = new JSONArray(fileContent);
        // Parse the JSON string into a JSONObject
        setJsonArray(temp);
        jsonDepou = new JSONObject();
        jsonInMiscare = new JSONObject();
    }

    public void setJsonArray(JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonArray = temp;
    }

    public void setJsonDeou(JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonDepou = temp;
    }

    public void setJsonInMiscare(JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonInMiscare = temp;
    }

    public JSONObject getJsonArray() {
        return this.jsonArray;
    }

    public JSONObject getJsonDeou() {
        return this.jsonDepou;
    }

    public JSONObject getJsonInMiscare() {
        return this.jsonInMiscare;
    }

    public int getDepouCount() {
        return this.numar_depou;
    }

    public int getMiscareCount() {
        return this.numar_miscare;
    }

    public void processAllObjects() {
        JSONArray array = this.jsonArray.getJSONArray("vehicles");
        JSONArray jsonArray_full = new JSONArray();
        JSONArray jsonArray_depou = new JSONArray();
        JSONArray jsonArray_miscare = new JSONArray();
        numar_depou = 0;
        numar_miscare = 0;

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String curentTime = now.format(formatter);
        String date = curentTime.substring(0, 10);
        String year_month = curentTime.substring(0, 8);
        int day = Integer.parseInt(curentTime.substring(8, 10));
        int ora = Integer.parseInt(curentTime.substring(11, 13));
        System.out.println("curent year: " + year_month);
        System.out.println("curent day: " + day);
        System.out.println("curent hour: " + ora);

        for (int i = 0; i < array.length(); i++) {
            JSONObject currentObject = array.getJSONObject(i);
            JSONObject obj = procesJsonObject(currentObject);
            if (obj == null) {
                continue;
            }
            jsonArray_full.put(obj);
            if (obj.getString("time").substring(0, 8).equals(year_month)) {
                if (day - Integer.parseInt(obj.getString("time").substring(11, 13)) > 1) {
                    numar_miscare++;
                    jsonArray_miscare.put(obj);
                    continue;
                }
            }
            jsonArray_depou.put(obj);
            numar_depou++;
        }

        setJsonArray(jsonArray_full);
        setJsonDeou(jsonArray_depou);
        setJsonInMiscare(jsonArray_miscare);
    }

    private JSONObject procesJsonObject(JSONObject jsonObject) {
        JSONObject obj = new JSONObject();
        int id = jsonObject.optInt("id");
        int route_id = jsonObject.optInt("route_id");
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

        // route_id
        if (route_id == 0) {
            return null;
        }
        obj.put("route_id", route_id);

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
        if (Double.isFinite(lat) && Double.isFinite(lon)) {
            JSONObject position = new JSONObject();
            position.put("lat", lat);
            position.put("lng", lon);
            obj.put("positions", position);
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

            Request.timedrequest(1);
            String DirectoryPath = System.getProperty("user.dir") + "\\Request";
            String inputFilePath = DirectoryPath + "\\req0.json";
            String outputFilePath = DirectoryPath + "\\output.json";
            String depouFilePath = DirectoryPath + "\\depou.json";
            String miscareFilePath = DirectoryPath + "\\miscare.json";

            JsonBuilder builder = new JsonBuilder(inputFilePath);

            builder.processAllObjects();

            builder.saveToFile(outputFilePath, "full");
            builder.saveToFile(miscareFilePath, "miscare");
            builder.saveToFile(depouFilePath, "depou");

            System.out.println("numar depou : " + builder.getDepouCount());
            System.out.println("numar miscare : " + builder.getMiscareCount());

            System.out.println("JSON has been successfully modified and saved.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}