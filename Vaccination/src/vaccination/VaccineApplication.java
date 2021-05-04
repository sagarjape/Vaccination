package vaccination;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.embed.swing.JFXPanel;

public class VaccineApplication {
	final static String DISTRICT_CODE="392";
	final static String[] hospitalsNearby = { "pollo", "edant", "orizon", "aushal", "Highway Hospital TMC Thane", "THANE Global Hub",
	"MILLENNIUM HOSP NAVI MUMBAI","Labourward Civil Hospital","THANE CIVIL HOSPITAL" };
	
	public static void main(String[] args) {
		while (true) {
			try {
				ArrayList<String> hospitals = new ArrayList<>();
//				hospitals.add("Apollo Hospital Navi Mumbai");
				PersonData sagar = new PersonData(hospitals,18,null,Person.SAGAR);
				hospitals = new ArrayList<>();
				PersonData mom = new PersonData(hospitals,45,"COVISHIELD",Person.MOM);
				hospitals = new ArrayList<>();
				hospitals.add("MILLENNIUM HOSP NAVI MUMBAI");
				PersonData dad = new PersonData(hospitals,45,"COVISHIELD",Person.DAD);
				
				LocalDateTime currentTime = LocalDateTime.now();
				setAppointmentsAlert(sagar,currentTime);
				LocalDateTime momStartDate=currentTime.plusDays(20-LocalDateTime.now().getDayOfMonth());
				setAppointmentsAlert(mom,momStartDate);
				LocalDateTime dadStartDate=currentTime.plusDays(20-LocalDateTime.now().getDayOfMonth());
				setAppointmentsAlert(dad,dadStartDate);

				java.util.concurrent.TimeUnit.MINUTES.sleep(2);

			} catch (IOException | ParseException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private static void setAppointmentsAlert(PersonData person, LocalDateTime startDate)
			throws IOException, ParseException, java.text.ParseException, JSONException, InterruptedException {
		List<Center> centersList = new ArrayList<>();
		for (int i = 0; i <= 7; i++) {
				LocalDateTime date = startDate.plusDays(i);
				JSONObject apiResponse = getDataforDate(date);
				centersList = getCentersForPerson(date, apiResponse, person);
				if (centersList!=null && !centersList.isEmpty()) {
					printCenters(person, date, centersList);
					playSound();
					ArrayList<String> hospitals = new ArrayList<>();
					for(Center center: centersList) {
						hospitals.add(center.getName());
					}
					person.excludeHospitals(hospitals);
				}
		}
	}

	private static List<Center> getCentersForPerson(LocalDateTime day, JSONObject apiResponse, PersonData person)
			throws java.text.ParseException, JSONException, InterruptedException {
		JSONArray centers = new JSONArray();
		List<Center> centersList = new ArrayList<>();
		try {
		centers = (JSONArray) apiResponse.get("centers");


		for (int i = 0; i < centers.size(); i++) {
			JSONObject center = (JSONObject) centers.get(i);
			Center centerObject = new Center();
			centerObject = centerObject.toPartnerDomain(center, person);
			if (centerObject != null)
				centersList.add(centerObject);
		}}
		catch(NullPointerException e) {
			return null;
		}
		
		return centersList;
		
	}

	private static void playSound() throws InterruptedException {
		final JFXPanel fxPanel = new JFXPanel();
		String bip = "notification.mp3";
		Media hit = new Media(new File(bip).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		java.util.concurrent.TimeUnit.SECONDS.sleep(2);
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		java.util.concurrent.TimeUnit.SECONDS.sleep(2);
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
		java.util.concurrent.TimeUnit.SECONDS.sleep(2);
	}

	private static void printCenters(PersonData person, LocalDateTime day, List<Center> centers) {
		for (Center c : centers) {
			System.out.println("Book appointment for: "+person.getPerson());
			System.out.println("Center: "+c.getName());
			System.out.println("Date: "+c.getSessions().get(0).date);
			System.out.println("Date used in API request: "+day.getDayOfMonth());
			System.out.println();
			System.out.println();

		}
	}

	private static JSONObject getDataforDate(LocalDateTime date) throws IOException, ParseException {
		APIHelper helper = APIHelper.getInstance();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		JSONObject apiResponse = helper.myGETRequest(DISTRICT_CODE,dtf.format(date));
		return apiResponse;
	}
}
