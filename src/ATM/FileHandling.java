package ATM;

import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileHandling {
    
    public static void writJSON(double amount) {
        try {
            // create a map
            Map<String, Object> map = new HashMap<>();
            map.put("amount", amount);

            // convert map to JSON File
            try (Writer writer = new FileWriter("account.json")) {
                // convert map to JSON File
                new Gson().toJson(map, writer);
                // close the writer
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static double readJSON() {
        double amount = 0.0;
        try {
            // create Gson instance
            Gson gson = new Gson();

            // convert JSON file to map
            try (Reader reader = Files.newBufferedReader(Paths.get("account.json"))) {
                // convert JSON file to map
                Map<?, ?> map = gson.fromJson(reader, Map.class);
                // print map entries
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    System.out.println(entry.getKey() + "=" + entry.getValue());
                }
                // close reader
                amount = Double.parseDouble(map.get("amount")+"");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return amount;
    }
}
