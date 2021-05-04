package vaccination;

public class Session {

	long capacity;
	String date;
	long minAge;
	String vaccine;

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	public long getMinAge() {
		return minAge;
	}

	public void setMinAge(long minAge) {
		this.minAge = minAge;
	}

	public long getCapacity() {
		return capacity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCapacity(long capacity) {
		this.capacity = capacity;
	}
}