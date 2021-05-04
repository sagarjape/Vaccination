package vaccination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class APIHelper {
	public static APIHelper getInstance() {
		return new APIHelper();
	}

	public JSONObject myGETRequest(String districtCode, String date) throws IOException, ParseException {
		URL url = new URL(
				"https://cdn-api.co-vin.in/api/v2/appointment/sessions/calendarByDistrict?district_id="+districtCode+"&date="
						+ date);
		HttpURLConnection conection = (HttpURLConnection) url.openConnection();
		conection.setRequestMethod("GET");
		int responseCode = conection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			String readLine = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
			StringBuffer resp = new StringBuffer();
			while ((readLine = in.readLine()) != null) {
				resp.append(readLine);
			}
			in.close();
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(resp.toString());
			return json;
		} else {
//			System.out.println("GET did not work");
		}
		return null;
	}
	//
//		public void myPOSTResponse(String payload) throws IOException, ParseException {
//			try {
//				URL urlForGetRequest = new URL(
//						"https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=075340c3a4d37d8eaea0cf00da74");
//				HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
//				connection.setRequestMethod("POST");
//				connection.setRequestProperty("Accept", "application/json");
//				connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//				connection.setDoOutput(true);
	//
//				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
//				writer.write(payload);
//				writer.close();
//				int responseCode = connection.getResponseCode();
//				if (responseCode == HttpURLConnection.HTTP_CREATED) {
//					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//					StringBuffer jsonString = new StringBuffer();
//					String line;
//					while ((line = br.readLine()) != null) {
//						jsonString.append(line);
//					}
//					br.close();
//				}
//				connection.disconnect();
//			} catch (Exception e) {
//				throw new RuntimeException(e.getMessage());
//			}
//		}

}
