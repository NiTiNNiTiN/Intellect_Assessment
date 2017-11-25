package com.controller;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.StartApplication;
import com.entities.Response;
import com.entities.User;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT,
		classes = StartApplication.class)
@Transactional
public class UserControllerTest{

	@Autowired
	UserController userController;

	private final static Logger Log = LoggerFactory.getLogger(UserControllerTest.class);

	@Before
	public void before() {}

	@After
	public void after() {}

	@Test
	public void testAdd() {
		User user = new User();
		user.setfName("John");
		user.setlName("Smith");
		user.setEmail("John.Smith@gmail.com");
		user.setPinCode(12345);
		user.setBirthDate(new Date(System.currentTimeMillis() - 100000));
		Log.info("Sending User Add: {}", user);
		Response response = userController.addUser(user);
		Log.info("Received test add response : {}", response);
		Assert.assertTrue( response.getValErrors().isEmpty());
		Assert.assertNotNull("Id generated", response.getUserId());
		Assert.assertEquals("User Added Successfully!!", response.getResMsg());
	}	

	@Test
	public void testUpdate() {
		User user = new User();
		user.setfName("John");
		user.setlName("Smith");
		user.setEmail("John.Smith@gmail.com");
		user.setPinCode(123456);
		user.setBirthDate(new Date(System.currentTimeMillis() - 100000));
		Log.info("Sending User Adding: {}", user);
		Response response = userController.addUser(user);
		Assert.assertNotNull("Id generated", response.getUserId());
		Assert.assertEquals("User Added Successfully!!", response.getResMsg());
		user.setId(response.getUserId());
		user.setPinCode(654321);
		user.setBirthDate(new Date(System.currentTimeMillis() - 700000));
		response = userController.updateUser(user);
		Log.info("Received test update response : {}", response);
		Assert.assertTrue( response.getValErrors().isEmpty());
		Assert.assertEquals("User Updated Successfully!!", response.getResMsg());
	}


	@Test
	public void testDelete() {
		User user = new User();
		user.setfName("John");
		user.setlName("Smith");
		user.setEmail("John.Smith@gmail.com");
		user.setPinCode(123456);
		user.setBirthDate(new Date(System.currentTimeMillis() - 700000));
		Log.info("Sending User Adding: {}", user);
		Response response = userController.addUser(user);
		Assert.assertNotNull("Id generated", response.getUserId());
		Assert.assertEquals("User Added Successfully!!", response.getResMsg());
		response = userController.deleteUser(response.getUserId());
		Log.info("Received test deletion response : {}", response);
		Assert.assertEquals("User Deleted Successfully!!", response.getResMsg());
	}
}
