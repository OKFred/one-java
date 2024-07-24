package java_modules.globalThis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetch {
    public static String _fetch(String str, String method) {
        try {
            URL url = new URL(str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            int responseCode = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
				return response.toString();
            // if (responseCode == HttpURLConnection.HTTP_OK) {
				// System.out.println("OK");
            // } else {
                // System.out.println("请求失败" + responseCode);
            // }
        } catch (Exception e) {
			return e.getMessage();
        }
    }
}
