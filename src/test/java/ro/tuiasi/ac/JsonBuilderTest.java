package ro.tuiasi.ac;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonBuilderTest {

    @Test
    public void testProcessAllObjects() throws JSONException {
        // Arrange
        JsonBuilder jsonBuilder = new JsonBuilder();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject(
                "{\"id\": 1, \"route_id\": 10, \"vehicle_type\": 0, \"bike_accessible\": \"BIKE_ACCESSIBLE\", \"wheelchair_accessible\": \"WHEELCHAIR_ACCESSIBLE\", \"latitude\": 45.7678, \"longitude\": 21.2345, \"timestamp\": \"2022-01-01T10:00:00.000Z\"}");
        JSONObject jsonObject2 = new JSONObject(
                "{\"id\": 2, \"route_id\": 20, \"vehicle_type\": 1, \"bike_accessible\": \"NO_BIKE_ACCESSIBLE\", \"wheelchair_accessible\": \"NO_WHEELCHAIR_ACCESSIBLE\", \"latitude\": 46.7678, \"longitude\": 22.2345, \"timestamp\": \"2022-01-02T11:00:00.000Z\"}");
        JSONObject jsonObject3 = new JSONObject(
                "{\"agency_id\": 1,\"route_id\": 1,\"route_short_name\": 13,\"route_long_name\": \" \",\"route_color\": \"#00adef\",\"route_type\": 0, \"route_desc\": \"Copou - Targu Cucu - Tatarasi - Baza 3 - Podu Ros - Copou\"}");
        JSONObject jsonObject4 = new JSONObject(
                "{\"agency_id\": 1,\"route_id\": 1,\"route_short_name\": 13,\"route_long_name\": \" \",\"route_color\": \"#00adef\",\"route_type\": 0, \"route_desc\": \"Copou - Targu Cucu - Tatarasi - Baza 3 - Podu Ros - Copou\"}");
        ;
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);
        jsonBuilder.setJsonArray(jsonArray);
        String jsonArrayString = "[" + jsonObject3.toString() + "," + jsonObject4.toString() + "]";
        jsonBuilder.setJsonRouts(jsonArrayString);
        // Act
        jsonBuilder.processAllObjects();

        // Assert
        JSONArray jsonArrayFull = jsonBuilder.getJsonArray().getJSONArray("vehicles");
        assertEquals(0, jsonArrayFull.length());
        JSONObject obj1 = jsonArrayFull.optJSONObject(0);
        assertEquals(1, obj1.getInt("id"));
        assertEquals("Tram", obj1.getString("vehicleType"));
        assertEquals("BIKE", obj1.getString("features"));
        assertEquals("2022-01-01T10:00:00.000Z", obj1.getString("time"));

        JSONObject obj2 = jsonArrayFull.getJSONObject(1);
        assertEquals(2, obj2.getInt("id"));
        assertEquals("Bus", obj2.getString("vehicleType"));
        assertEquals("", obj2.getString("features"));
        assertEquals("2022-01-02T11:00:00.000Z", obj2.getString("time"));
    }

}