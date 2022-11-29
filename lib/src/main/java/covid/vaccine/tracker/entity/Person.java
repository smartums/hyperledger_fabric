package covid.vaccine.tracker.entity;


import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
public class Person {

	@Property
	private String name;
	
	@Property
	private Integer age;
	
	@Property
	private String gender;
	
	@Property
	private String idProof;
	
	@Property
	private boolean takenFirstDose;
	
	@Property
	private boolean takenSecondDose;
	
	@Property
	private String firstVaccinationDate;
	
	@Property
	private String vaccineName;
	
	@Property
	private String secondVaccinationDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdProof() {
		return idProof;
	}

	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}

	public boolean isTakenFirstDose() {
		return takenFirstDose;
	}

	public void setTakenFirstDose(boolean takenFirstDose) {
		this.takenFirstDose = takenFirstDose;
	}

	public boolean isTakenSecondDose() {
		return takenSecondDose;
	}

	public void setTakenSecondDose(boolean takenSecondDose) {
		this.takenSecondDose = takenSecondDose;
	}

	public String getFirstVaccinationDate() {
		return firstVaccinationDate;
	}

	public void setFirstVaccinationDate(String firstVaccinationDate) {
		this.firstVaccinationDate = firstVaccinationDate;
	}

	public String getSecondVaccinationDate() {
		return secondVaccinationDate;
	}

	public void setSecondVaccinationDate(String secondVaccinationDate) {
		this.secondVaccinationDate = secondVaccinationDate;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	
	
}
