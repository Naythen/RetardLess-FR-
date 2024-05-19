package PR.Pip.ro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;

/**
 * JsonBuilder
 */
public class JsonBuilder {

    JSONObject jsonObject;

    public JsonBuilder(String filePath) throws Exception {
        // Read the file content into a String
        String fileContent = new String(Files.readAllBytes(Paths.get(filePath)));

        // Parse the JSON string into a JSONObject
        jsonObject = new JSONObject(fileContent);
    }

    public void saveToFile(String outputFilePath) throws IOException {
        // Convert the JSONObject to a string
        String jsonString = jsonObject.toString();

        // Write the string to a file
        Files.write(Paths.get(outputFilePath), jsonString.getBytes());
    }

    public static void main(String[] args) {
        try {
            String DirectoryPath = System.getProperty("user.dir") + "\\requestfolder";
            String inputFilePath = DirectoryPath + "\\input.json";
            String outputFilePath = DirectoryPath + "\\output.json";

            JsonBuilder builder = new JsonBuilder(inputFilePath);
            builder.saveToFile(outputFilePath);

            System.out.println("JSON has been successfully modified and saved.");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}