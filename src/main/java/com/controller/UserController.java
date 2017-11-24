package com.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Response;
import com.entities.User;
import com.repositories.UserRepository;
import com.utilities.UserUtility;

@RestController
@RequestMapping("/user")
public class UserController {

	private final static Logger Log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository 	userRepository;
	@Autowired
	UserUtility 	userUtility;

	Response 		response;

	@RequestMapping
	@ResponseBody
	public String defaultMethod(){
		Log.info("Default Method Invoked");
		return "Default Service. Available services are: add, update, delete. Use the required one by adding with current URl.";
	}

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod(){
		Log.info("Fallback Method invoked for because wrong service was called");		
		return "Service you are trying is not Valid. Available services are: add, update, delete. Use the required one by adding with current URl.";
	}

	@ResponseBody
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	public Response addUser(@RequestBody User user){
		response = new Response();
		Log.info("Adding User : {}", user);
		response = userUtility.validateUser(user, response);
		if((response.getValErrors()).isEmpty())
			if(userUtility.validateUserAddition(user)){
				user.setIsActive(true);
				user = userRepository.save(user);
				response.setUserId(user.getId());
				response.setResMsg("User Added Successfully!!");
			} else{
				response.setResMsg("User wasn't Added, There already exists an active User with this email ID.");
			}
		else{
			response.setResMsg("User wasn't Added, There exists validation errors.");
		}
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method=RequestMethod.POST)
	public Response updateUser(@RequestBody User user){
		response = new Response();
		Log.info("Updating User : {}", user);
		response = userUtility.validateUserUpdation(user, response);
		if((response.getValErrors()).isEmpty()){
			User dbUser = userRepository.findOne(user.getId());
			if(user.getPinCode() != null && user.getPinCode() != dbUser.getPinCode())
				dbUser.setPinCode(user.getPinCode());
			if(user.getBirthDate() != null && user.getBirthDate() != dbUser.getBirthDate())
				dbUser.setBirthDate(user.getBirthDate());
			userRepository.save(dbUser);
			response.setResMsg("User Updated Successfully!!");
		}
		else{
			response.setResMsg("User wasn't Updated, There exists abnormality in the User's data");
		}
		response.setUserId(user.getId());
		return response;
	}

	@ResponseBody
	@RequestMapping(value = "/delete", method=RequestMethod.DELETE)
	public Response deleteUser(@PathParam(value = "id") String id){
		response = new Response();
		Log.info("Deleting User with id : {}", id);
		response = userUtility.validateUserDeletion(id, response);
		if((response.getValErrors()).isEmpty()){
			User user = userRepository.findOne(id);
			user.setIsActive(false);
			userRepository.save(user);
			response.setResMsg("User Deleted Successfully!!");
		}
		else{
			response.setResMsg("User wasn't deleted, Problem with the sent ID");
		}
		response.setUserId(id);
		return response;
	}
}
