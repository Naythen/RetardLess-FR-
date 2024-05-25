package ro.tuiasi.ac;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonBuilderTest {
    JsonBuilder jsonBuilder = new JsonBuilder();;

    /**
     * This method is used to set up the initial data for testing.
     * It creates JSON objects and arrays, populates them with data, and sets them
     * to the JsonBuilder instance.
     */
    @Before
    public void setUp() {
        JSONArray jsonArray = new JSONArray();
        JSONArray jsonArrayRouts = new JSONArray();
        JSONArray jsonArrayStops = new JSONArray();
        JSONArray jsonArrayStopsTimes = new JSONArray();

        // Create and populate JSON objects for vehicles
        JSONObject jsonObject1 = new JSONObject(
                "{\"id\": 1, \"route_id\": 10, \"vehicle_type\": 0, \"bike_accessible\": \"BIKE_ACCESSIBLE\", \"wheelchair_accessible\": \"WHEELCHAIR_ACCESSIBLE\", \"latitude\": 1, \"longitude\": 2, \"timestamp\": \"2022-01-01T10:00:00.000Z\"}");
        JSONObject jsonObject2 = new JSONObject(
                "{\"id\": 2, \"route_id\": 20, \"vehicle_type\": 3, \"bike_accessible\": \"BIKE_INACCESSIBLE\", \"wheelchair_accessible\": \"WHEELCHAIR_ACCESSIBLE\", \"latitude\": 3, \"longitude\": 4, \"timestamp\": \"2022-01-02T11:00:00.000Z\"}");

        // Create and populate JSON objects for routes
        JSONObject jsonObject3 = new JSONObject(
                "{\"agency_id\": 1,\"route_id\": 10,\"route_short_name\": 12,\"route_long_name\": \"nume1\",\"route_color\": \"#00adef\",\"route_type\": 0, \"route_desc\": \"Copou0 - Copou1\"}");
        JSONObject jsonObject4 = new JSONObject(
                "{\"agency_id\": 2,\"route_id\": 20,\"route_short_name\": 13,\"route_long_name\": \"nume2 \",\"route_color\": \"#00adef\",\"route_type\": 0, \"route_desc\": \"Copou2 - Copou3\"}");

        // Create and populate JSON objects for stops
        JSONObject jsonObject5 = new JSONObject(
                "{\"stop_id\": 1,\"stop_name\": \"Copou\",\"stop_lat\": 47.19052,\"stop_lon\": 27.55848,\"location_type\": 0,\"stop_code\": \"\"}");
        JSONObject jsonObject6 = new JSONObject(
                "{\"stop_id\": 2,\"stop_name\": \"Stadion\",\"stop_lat\": 47.18646,\"stop_lon\": 27.56235,\"location_type\": 0,\"stop_code\": \"\"}");

        // Create and populate JSON objects for stop times
        JSONObject jsonObject7 = new JSONObject(
                "{\"trip_id\": \"1_0\",\"stop_id\": 1,\"stop_sequence\": 3}");
        JSONObject jsonObject8 = new JSONObject(
                "{\"trip_id\": \"1_0\",\"stop_id\": 2,\"stop_sequence\": 5}");

        // Add JSON objects to respective arrays
        jsonArrayStopsTimes.put(jsonObject7);
        jsonArrayStopsTimes.put(jsonObject8);
        jsonArrayStops.put(jsonObject6);
        jsonArrayStops.put(jsonObject5);
        jsonArray.put(jsonObject1);
        jsonArray.put(jsonObject2);

        // Set JSON arrays to the JsonBuilder instance
        jsonBuilder.setJsonArray(jsonArray);
        jsonArrayRouts.put(jsonObject3);
        jsonArrayRouts.put(jsonObject4);
        jsonBuilder.setJsonStopsTimes(jsonArrayStopsTimes.toString());
        jsonBuilder.setJsonStops(jsonArrayStops.toString());
        jsonBuilder.setJsonRouts(jsonArrayRouts.toString());
    }

    @Test
    public void testProcessAllObjectsValidData() throws JSONException {

        // Act
        jsonBuilder.processAllObjects();

        // Assert
        JSONArray jsonArrayFull = jsonBuilder.getJsonArray().getJSONArray("vehicles");
        assertEquals(2, jsonArrayFull.length());
        JSONObject obj1 = jsonArrayFull.optJSONObject(0);
        assertEquals(1, obj1.getInt("id"));
        assertEquals("Tram", obj1.getString("vehicleType"));
        assertEquals("BIKE & WHEELCHAIR", obj1.getString("features"));
        assertEquals("2022-01-01T10:00:00.000Z", obj1.getString("time"));

        JSONObject obj2 = jsonArrayFull.getJSONObject(1);
        assertEquals(2, obj2.getInt("id"));
        assertEquals("Bus", obj2.getString("vehicleType"));
        assertEquals("WHEELCHAIR", obj2.getString("features"));
        assertEquals("2022-01-02T11:00:00.000Z", obj2.getString("time"));

    }

    @Test
    public void testProcessAllObjectsInvalidData() throws Exception {
        // Arrange
        JSONObject jsonObject1 = new JSONObject(
                "{\"id\": 0, \"route_id\": 7, \"vehicle_type\": 0, \"bike_accessible\": \"BIKE_ACCBLE\", \"wheelchair_accessible\": \"WHEELCHAIR_ACCESSIBLE\", \"latitude\": Nan, \"longitude\": 2, \"timestamp\": \"2022-01-01T10:00:00.000Z\"}");
        JSONObject jsonObject2 = new JSONObject(
                "{\"id\": 2, \"route_id\": 0, \"vehicle_type\": 3, \"bike_accessible\": \"BIKE_INACCESSIBLE\", \"wheelchair_accessible\": \"WHEELCHAIR_ACCESSIBLE\", \"latitude\": 3, \"longitude\": 4, \"timestamp\": \"2022-01-02T11:00:00.000Z\"}");
        JSONObject jsonObject3 = new JSONObject(
                "{\"id\": 1, \"route_id\": 10, \"vehicle_type\": 0, \"bike_accessible\": \"BIKE_ACIE\", \"wheelchair_accessible\": \"WHEELCHAIR_ACCESSIBLE\", \"latitude\": 1, \"longitude\": Nan, \"timestamp\": \"2022-01-01T10:00:00.000Z\"}");
        JSONObject jsonObject4 = new JSONObject(
                "{\"id\": 2, \"route_id\": 20, \"vehicle_type\": 3, \"bike_accessible\": \"BIKE_INACCESSIBLE\", \"wheelchair_accessible\": \"WHEECESSIBLE\", \"latitude\": Nan, \"longitude\": 4, \"timestamp\": \"\"}");

        JSONArray array = jsonBuilder.getJsonArray().getJSONArray("vehicles");

        int x = array.length();

        array.put(jsonObject1);
        array.put(jsonObject2);
        array.put(jsonObject3);
        array.put(jsonObject4);

        // Act
        jsonBuilder.setJsonArray(array);
        jsonBuilder.processAllObjects();

        array = jsonBuilder.getJsonArray().getJSONArray("vehicles");

        // Assert
        assertEquals(x, array.length());
    }

    @Test
    public void testFindRouteLongNameByRouteId() throws Exception {
        // Act
        String[] nume = jsonBuilder.findRouteLongNameByRouteId(10);
        String[] nume1 = jsonBuilder.findRouteLongNameByRouteId(30);

        // Assert
        assertEquals(nume[0], "nume1");
        assertEquals(nume[1], " Copou1");
        assertTrue(nume1 == null);
    }

    @Test
    public void testGetStopTimes() throws Exception {
        // Act
        int x = jsonBuilder.getStopTimes(2);
        int y = jsonBuilder.getStopTimes(7);
        int z = jsonBuilder.getStopTimes(0);

        // Assert
        assertEquals(x, 5);
        assertEquals(y, 0);
        assertEquals(z, 0);
    }

    @Test
    public void testGetStop() throws Exception {
        // Act
        int x = jsonBuilder.getStop(47.19052, 27.55848);
        int y = jsonBuilder.getStop(47.18646, 10);
        int z = jsonBuilder.getStop(0, 0);

        // Assert
        assertEquals(x, 1);
        assertEquals(y, 0);
        assertEquals(z, 0);
    }

    @Test
    public void testSaveToFile() throws Exception {
        // Act
        jsonBuilder.saveToFile("test.json", "full");
        jsonBuilder.saveToFile("test1.json", "none");

        File file = new File("test.json");
        File file_n = new File("test1.json");

        // Assert
        assertTrue(file.exists());
        assertFalse(file_n.exists());
        file.delete();
        file_n.delete();
    }
}