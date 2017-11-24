package com.services;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Response;
import com.entities.User;
import com.repositories.UserRepository;

import utilities.UserUtility;

@RestController
@RequestMapping("/user")
public class UserService {

	private final static Logger Log = LoggerFactory.getLogger(UserService.class);

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
	@RequestMapping(value = "/add", method=RequestMethod.POST, consumes = "application/json")
	public Response addUser(User user){
		response = new Response();
		Log.info("Adding User : {}", user);
		response = userUtility.validateUser(user, response);
		if((response.getValErrors()).isEmpty())
			if(userUtility.validateUserAddition(user)){
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
	@RequestMapping(value = "/update", method=RequestMethod.POST, consumes = "application/json")
	public Response updateUser(User user){
		response = new Response();
		Log.info("Updating User : {}", user);
		response = userUtility.validateUserUpdation(user, response);
		if((response.getValErrors()).isEmpty()){
			userRepository.save(user);
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
