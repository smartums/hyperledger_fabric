package covid.vaccine.tracker.entity;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@DataType
public class VaccinationStatus {
	
	public VaccinationStatus(boolean takenFirstDose, boolean takenSecondDose, String vaccineName) {
		this.takenFirstDose = takenFirstDose;
		this.takenSecondDose = takenSecondDose;
		this.vaccineName = vaccineName;
	}

	@Property
	private boolean takenFirstDose;
	
	@Property
	private boolean takenSecondDose;
	
	@Property
	private String vaccineName;

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

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}
	
	
}
