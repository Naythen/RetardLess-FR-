package ro.tuiasi.ac;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
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
    private int numarDepou = 0;
    /**
     *
     */
    private int numarMiscare = 0;
    /**
     *
     * @param filePath
     * @param fileRouts
     * @throws Exception
     */
    public JsonBuilder(final String filePath, final String fileRouts)
            throws Exception {
        // Read the file content into a String
       final String fileContent =
               new String(Files.readAllBytes(Paths.get(filePath)));
       final String fileContent1 =
               new String(Files.readAllBytes(Paths.get(fileRouts)));
        JSONArray temp = new JSONArray(fileContent);
        // Parse the JSON string into a JSONObject
        setJsonArray(temp);
        setJsonRouts(fileContent1);
        jsonDepou = new JSONObject();
        jsonInMiscare = new JSONObject();
    }

    /**
     *
     * @param jsonArray
     */
    public void setJsonArray(final JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonArray = temp;
    }

    /**
     *
     * @param jsonArray
     */
    public void setJsonDeou(final JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonDepou = temp;
    }

    /**
     *
     * @param jsonArray
     */
    public void setJsonInMiscare(final JSONArray jsonArray) {
        JSONObject temp = new JSONObject();
        temp.put("vehicles", jsonArray);
        this.jsonInMiscare = temp;
    }

    /**
     *
     * @param jsonArray
     */
    public void setJsonRouts(final String jsonArray) {
        this.jsonArrayRouts = new JSONArray(jsonArray);
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
     *
     */
    public void processAllObjects() {
        JSONArray array = this.jsonArray.getJSONArray("vehicles");
        JSONArray jsonArrayFull = new JSONArray();
        JSONArray jsonArrayDepou = new JSONArray();
        JSONArray jsonArrayMiscare = new JSONArray();
        setDepouCount(0);
        setMiscareCount(0);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
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
     *
     * @param routeId
     * @return
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
     *
     * @param jsonObject
     * @return
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
        if (id == 0) {
            return null;
        }
        obj.put("id", id);

        // route_id
        if (routeId == 0) {
            return null;
        }

        String[] r = findRouteLongNameByRouteId(routeId);
        if (r == null) {
            return null;
        }
        obj.put("routeName", r[0]);
        obj.put("direction", r[1]);

        // type{
        obj.put("vehicleType", type == 0 ? "Tram" : "Bus");
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

    /**
     *
     * @param outputFilePath
     * @param indexJsonArray
     * @throws IOException
     */
    public void saveToFile(final String outputFilePath, final String indexJsonArray) throws IOException {
        // Convert the JSONObject to a string
        String jsonString;

        switch (indexJsonArray) {
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
        // Write the string to a file
        Files.write(Paths.get(outputFilePath), jsonString.getBytes());
    }

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        try {

            Request.timedrequest(1);
            String directoryPath = System.getProperty("user.dir") + "\\Request";
            String inputFilePath = directoryPath + "\\req0.json";
            String inputFilePath1 = directoryPath + "\\reqRouts0.json";
            String outputFilePath = directoryPath + "\\output.json";
            String depouFilePath = directoryPath + "\\depou.json";
            String miscareFilePath = directoryPath + "\\miscare.json";

            JsonBuilder builder = new JsonBuilder(inputFilePath, inputFilePath1);

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