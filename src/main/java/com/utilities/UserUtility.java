package com.utilities;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entities.Response;
import com.entities.User;
import com.entities.ValidationError;
import com.repositories.UserRepository;

@Component
public class UserUtility {

	private final static Logger Log = LoggerFactory.getLogger(UserUtility.class);

	@Autowired
	UserRepository userRepository;

	ValidationError validationError;
	
	// 1 - No First name found
	// 2 - No Last name found
	// 3 - No Email Found
	// 4 - Invalid Email
	// 5 - No Pin Code Found
	// 6 - No Birth Date FOund	
	// 7 - Invalid Birth Date
	// 8 - ID missing from data
	// 9 - User sent dosen't exists
	
	public Boolean validateUserAddition(User user){
		Log.info("Checking User's existance : {}", user);
		Collection<User> data = userRepository.findByEmail(user.getEmail());
		if(!data.isEmpty()){
			for(User entry: data){
				if(entry.getIsActive()){
					return false;
				}
			}
			return true;
		}
		return true;
	}	

	public Response validateUserUpdation(User user, Response response){
		Log.info("Checking User Updation : {}", user);
		if(user.getId() == null){
			validationError = new ValidationError();
			validationError.setCode("8");
			validationError.setField("Id");
			validationError.setMessage("ID missing from data");
			response.setValErrors(validationError);
		}
		else{
			if(!userRepository.exists(user.getId())){
				validationError = new ValidationError();
				validationError.setCode("9");
				validationError.setField("Id");
				validationError.setMessage("User sent dosen't exists");
				response.setValErrors(validationError);
			}
		}
		return response;
	}
	
	public Response validateUserDeletion(String id, Response response){
		Log.info("Checking User's deletion : {}", id);
		if(id == null){
			validationError = new ValidationError();
			validationError.setCode("8");
			validationError.setField("Id");
			validationError.setMessage("ID missing from data");
			response.setValErrors(validationError);
		}
		else{
			if(!userRepository.exists(id)){
				validationError = new ValidationError();
				validationError.setCode("9");
				validationError.setField("Id");
				validationError.setMessage("User sent dosen't exists");
				response.setValErrors(validationError);
			}
		}
		return response;
	}
	
	public Response validateUser(User user, Response response){
		Log.info("Validating User Received : {}", user);
		if(user.getfName() == null){
			validationError = new ValidationError();
			validationError.setCode("1");
			validationError.setField("fname");
			validationError.setMessage("No First Name Found");
			response.setValErrors(validationError);
		}
		if(user.getlName() == null){
			validationError = new ValidationError();
			validationError.setCode("2");
			validationError.setField("lname");
			validationError.setMessage("No Last Name Found");
			response.setValErrors(validationError);
		}
		if(user.getEmail() == null){
			validationError = new ValidationError();
			validationError.setCode("3");
			validationError.setField("email");
			validationError.setMessage("No Email Found");
			response.setValErrors(validationError);
		}
		else{
			if(!user.getEmail().contains("@")){
				validationError = new ValidationError();
				validationError.setCode("4");
				validationError.setField("email");
				validationError.setMessage("Invalid Email");
				response.setValErrors(validationError);
			}
		}
		if(user.getPinCode() == null){
			validationError = new ValidationError();
			validationError.setCode("5");
			validationError.setField("pinCode");
			validationError.setMessage("No Pin Code Found");
			response.setValErrors(validationError);
		}
		if(user.getBirthDate() == null){
			validationError = new ValidationError();
			validationError.setCode("6");
			validationError.setField("birthDate");
			validationError.setMessage("No Birth Date Found");
			response.setValErrors(validationError);
		}
		else{
			if(user.getBirthDate().getTime() >= new Date().getTime()){
				validationError = new ValidationError();
				validationError.setCode("7");
				validationError.setField("birthDate");
				validationError.setMessage("Invalid Birth Date");
				response.setValErrors(validationError);
			}
		}
		return response;
	}

}
