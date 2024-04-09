package ATM;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

public class WebAPI {

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final String GET_URL = "http://localhost:8080/pin/hello";
    
    public final static String VALID_PIN = "Valid pin";
    public final static String INVALID_PIN = "Invalid pin!!";
    public final static String ERROR = "PIN request is faild!";
    public final static String CONNECTION_ERROR = "Not connected to server!";
    
    public static void sendGETRequest() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }

        for (int i = 1; i <= 8; i++) {
            System.out.println(httpURLConnection.getHeaderFieldKey(i) + " = " + httpURLConnection.getHeaderField(i));
        }

    }

    private static final String POST_URL = "http://localhost:8080/pin/verify";

    public static String sendPOST(String data) {
        try {
            URL obj = new URL(POST_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestProperty("Method", "POST");
            httpURLConnection.addRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("User-Agent", USER_AGENT);
            httpURLConnection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzExNTQzODcwLCJleHAiOjE3MTE1NTI4NzB9.QC97ZgdMNzcyRqjHOtgrNMJDO8qouOXuANMbOEIp4z08dRGA58ivEwod0YuxnxJ8zE7YZiVRlTlGgkkc0g5YYA");

            // For POST only - START
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(data.getBytes("UTF-8"));
            //os.flush();
            os.close();
            // For POST only - END

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("POST Response Code :: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                return response.toString();
            } else {
                return ERROR;
            }
        } catch (IOException e) {
            return CONNECTION_ERROR;
        }
    }

}
