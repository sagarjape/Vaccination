package vaccination;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Center {
	String name;
	int center_id;
	String address;

	List<Session> sessions = new ArrayList<>();

	private long capacity;

	long getCapacity() {
		return capacity;
	}

	void setCapacity(long capacity) {
		this.capacity = capacity;
	}

	String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public Center toPartnerDomain(JSONObject object, PersonData person) throws java.text.ParseException, JSONException {
		Center c = new Center();
		List<Session> sessionsList = new ArrayList<>();
		c.setName(object.get("name").toString());
		JSONArray sessions = (JSONArray) object.get("sessions");
		// check if vaccine center is close
		if (this.isHospitalNear(c.getName())) {
			for (int i = 0; i < sessions.size(); i++) {
				JSONObject session = (JSONObject) sessions.get(i);
				long available_capacity = (long) session.get("available_capacity");
				String date = (String) session.get("date");
				long minAge = (long) session.get("min_age_limit");
				String vaccine = (String) session.get("vaccine");

				// check if center has vaccine available
				if (available_capacity > 0) {
					Session sessionObj = new Session();
					sessionObj.setDate(date);
					sessionObj.setCapacity(available_capacity);
					sessionObj.setMinAge(minAge);
					sessionObj.setVaccine(vaccine);

					// check if appointment matches person's requirement
					if (canScheduleAppointment(c.getName(), sessionObj, person)) {
						sessionsList.add(sessionObj);
					}
				}
			}
			c.setSessions(sessionsList);
			if (c.getSessions() != null && !c.getSessions().isEmpty()) {
				return c;
			}
			return null;
		}
		return null;
	}

	private boolean isHospitalNear(String name) {
		String[] hospitals = VaccineApplication.hospitalsNearby;
		for (String hosp : hospitals)
			if (name.contains(hosp)) {
				return true;
			}
		return false;
	}

	private boolean canScheduleAppointment(String name, Session session, PersonData person) {
		if (!person.getExcludeHospitals().contains(name) && session.getMinAge() == person.minAge
				&& (person.getVaccineTaken() == null
						|| session.getVaccine().equalsIgnoreCase(person.getVaccineTaken()))) {
			return true;
		}
		return false;

	}
}