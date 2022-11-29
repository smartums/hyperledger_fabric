package covid.vaccine.tracker.contract;


import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;

import com.owlike.genson.Genson;

import covid.vaccine.tracker.entity.Person;
import covid.vaccine.tracker.entity.VaccinationStatus;

@Contract(
		name = "covid-vaccine-tracker",
		info = @Info(
					title = "COVID Vaccination Tracker",
					description = "Tracking the number doses taken by the persons",
	                version = "0.0.1-SNAPSHOT"
				)
		)
@Default
public final class CovidVaccinationContract implements ContractInterface{
	
	private final Genson genson = new Genson();
	private enum PersonErrors {
        PERSON_NOT_FOUND,
        PERSON_ALREADY_TAKEN_FIRST_DOSE,
        PERSON_ALREADY_TAKEN_BOTH_DOSES,
        FIRST_DOSE_NOT_TAKEN,
        MISMATCHED_VACCINE_FROM_FIRST_DOSE
    }
	
	/**
     * Add some initial properties to the ledger
     *
     * @param ctx the transaction context
     */
    @Transaction
    public void initLedger(final Context ctx) {
    	
        
    }
    
    @Transaction
    public Person addFirstDosePerson(
    		final Context ctx, 
    		final String name, 
    		final String age,
            final String gender, 
            final String idProof,
            final String vaccinationDate,
            final String vaccineName) {
    	
    	ChaincodeStub stub = ctx.getStub();
    	String personState = stub.getStringState(idProof);
    	
    	Person person = null;
    	if(!personState.isEmpty()) {
    		person = genson.deserialize(personState, Person.class);
    		if(person.isTakenFirstDose()) {
    			String msg = String.format("Person %s already taken their FIRST dose on %s!", idProof, person.getFirstVaccinationDate());
    			System.out.println(msg);
    			throw new ChaincodeException(msg, PersonErrors.PERSON_ALREADY_TAKEN_FIRST_DOSE.toString());
    		}
    		
    		if(person.isTakenSecondDose()) {
    			String msg = String.format("Person %s already taken their BOTH doses on %s and %s respectively!", idProof, person.getFirstVaccinationDate(), person.getSecondVaccinationDate());
    			System.out.println(msg);
    			throw new ChaincodeException(msg, PersonErrors.PERSON_ALREADY_TAKEN_BOTH_DOSES.toString());
    		}
    	} else {
    		person = new Person();
    	}
    	
    	
    	person.setName(name);
    	person.setAge(Integer.parseInt(age));
    	person.setIdProof(idProof);
    	person.setGender(gender);
    	person.setTakenFirstDose(true);
    	person.setFirstVaccinationDate(vaccinationDate);
    	person.setVaccineName(vaccineName);
    	
    	personState = genson.serialize(person);
    	stub.putStringState(idProof, personState); 
    	
    	return person;
    }
    
    @Transaction
    public VaccinationStatus getVaccinationStatus(final Context ctx, final String idProof) {
    	ChaincodeStub stub = ctx.getStub();
    	String personState = stub.getStringState(idProof);
    	
    	if(!personState.isEmpty()) {
    		Person person = genson.deserialize(personState, Person.class);
    		return new VaccinationStatus(
    				person.isTakenFirstDose(), 
    				person.isTakenSecondDose(), 
    				person.getVaccineName()
    			);
    	} else {
    		String msg = String.format("Person %s doesn't exist!", idProof);
			System.out.println(msg);
			throw new ChaincodeException(msg, PersonErrors.PERSON_NOT_FOUND.toString());
    	}
    }
    
    @Transaction
    public Person updatePesonSecondDose(
    		final Context ctx, 
    		final String idProof, 
    		final String vaccinationDate, 
    		final String vaccineName) {
    	ChaincodeStub stub = ctx.getStub();
    	String personState = stub.getStringState(idProof);
    	
    	if(personState != null && !personState.isEmpty()) {
    		Person person = genson.deserialize(personState, Person.class);
    		
    		if(!person.isTakenFirstDose()) {
    			String msg = String.format("Person %s should have taken first dose before taking second!", idProof);
    			System.out.println(msg);
    			throw new ChaincodeException(msg, PersonErrors.FIRST_DOSE_NOT_TAKEN.toString());
    		}
    		
    		if(person.isTakenSecondDose()) {
    			String msg = String.format("Person %s already taken their BOTH doses on %s and %s respectively!", idProof, person.getFirstVaccinationDate(), person.getSecondVaccinationDate());
    			System.out.println(msg);
    			throw new ChaincodeException(msg, PersonErrors.PERSON_ALREADY_TAKEN_BOTH_DOSES.toString());
    		}
    		
    		if(person.isTakenFirstDose() && !person.getVaccineName().equalsIgnoreCase(vaccineName)) {
    			String msg = String.format("Person %s should take same vaccine as taken in FIRST dose %s!", idProof, person.getVaccineName());
    			System.out.println(msg);
    			throw new ChaincodeException(msg, PersonErrors.MISMATCHED_VACCINE_FROM_FIRST_DOSE.toString());
    		}
    		
    		person.setTakenSecondDose(true);
    		person.setSecondVaccinationDate(vaccinationDate);
    		
    		String personNewState = genson.serialize(person);
        	stub.putStringState(idProof, personNewState); 
    		return person;
    	} else {
    		String msg = String.format("Person %s doesn't exist!", idProof);
			System.out.println(msg);
			throw new ChaincodeException(msg, PersonErrors.PERSON_NOT_FOUND.toString());
    	}
    }

}
