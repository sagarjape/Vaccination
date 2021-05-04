package vaccination;

import java.util.ArrayList;
import java.util.List;

public enum Person {
	SAGAR, DAD, MOM
}

class PersonData{
	List<String> excludeHospitals;
	int minAge;
	String vaccineTaken;
	Person person;
	
	public List<String> getExcludeHospitals() {
		return excludeHospitals;
	}

	public void setExcludeHospitals(List<String> excludeHospitals) {
		this.excludeHospitals = excludeHospitals;
	}

	public int getMinAge() {
		return minAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public String getVaccineTaken() {
		return vaccineTaken;
	}

	public void setVaccineTaken(String vaccineTaken) {
		this.vaccineTaken = vaccineTaken;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	PersonData(ArrayList<String> hospitals, int minAge, String vaccineTaken, Person person){
		excludeHospitals = new ArrayList<>();
		excludeHospitals.addAll(hospitals);
		this.minAge=minAge;
		this.vaccineTaken=vaccineTaken;
		this.person=person;
	}
	
	public void excludeHospitals(ArrayList<String> hospitals) {
		excludeHospitals.addAll(hospitals);
	}
}
