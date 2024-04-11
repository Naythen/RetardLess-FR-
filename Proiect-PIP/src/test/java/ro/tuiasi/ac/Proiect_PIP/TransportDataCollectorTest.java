package ro.tuiasi.ac.Proiect_PIP;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransportDataCollectorTest {

    private TransportDataCollector collector;

    @Before
    public void setUp() {
        collector = new TransportDataCollector();
    }

    @Test
    public void testIsValidResponse() {
        try {
        JSONObject validResponse = new JSONObject("{\"key\": \"value\"}");
        JSONObject invalidResponse = new JSONObject("{\"invalidKey\": \"invalidValue\"}");

        assertTrue(collector.isValidResponse(validResponse));
        assertFalse(collector.isValidResponse(invalidResponse));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testFilterData() {
        try {
        JSONObject data = new JSONObject("{\"key1\": \"value1\", \"key2\": \"value2\"}");
        JSONObject expectedFilteredData = new JSONObject("{\"key1\": \"value1\"}");

        JSONObject filteredData = collector.filterData(data);
        assertEquals(expectedFilteredData.toString(), filteredData.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}