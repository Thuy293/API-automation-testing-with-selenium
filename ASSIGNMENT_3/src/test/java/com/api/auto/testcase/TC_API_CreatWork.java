package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;
import com.work.info.data.WorkInformation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_CreatWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	
	
//Create data
	private String myWork = "English Teacher";
	private String myExperience = "5 years";
	private String myEducation = "Bachelor's degree";
	
@BeforeClass
public void init() throws IOException {
	
	//init data
	String baseUrl = PropertiesFileUtils.getProperty("baseurl");
	String createWorkPath = PropertiesFileUtils.getProperty("createWorkPath");
	String token = PropertiesFileUtils.getToken("token");
	
	RestAssured.baseURI = baseUrl;
	
	
	
	//make body
//	Map<String, Object> body = new HashMap<String, Object>();
//	body.put("nameWork", myWork);
//	body.put("experience", myExperience);
//	body.put("education", myEducation);

	WorkInformation workInfo = new WorkInformation(myWork,myExperience,myEducation);
	
	//make request
	RequestSpecification request = RestAssured.given()
					.contentType(ContentType.JSON)
					.header("token",token)
					.body(workInfo);
	
	//Get response results
	response = request.post(createWorkPath);
	responseBody = response.body();
	jsonBody = responseBody.jsonPath();
	
	System.out.println(" " + responseBody.asPrettyString());
}

@Test(priority = 0)
	
public void TC01_Validate201Created() {
	//Verify the http response status returned. Check Status Code is 200 OK?
	 assertEquals(201,response.getStatusCode(),"Status Check Failed");
}
	
@Test(priority = 1)
public void TC02_ValidateWorkId() {
	
	 //Check id field
	 assertTrue(responseBody.asString().contains("id"),"id field check Failed");
	 	
}

@Test(priority = 2)
public void TC03_ValidateNameOfWorkMatched() {
	
	 //Check nameWork field
	 assertTrue(responseBody.asString().contains("nameWork"),"nameWork field check Failed");
	 
	 //Check message content
	 String name = jsonBody.getString("nameWork");
	 assertEquals(name,myWork,"Name Of Work Check Failed");
	 
}

@Test(priority = 3)
public void TC04_ValidateExperienceMatched() {
	
	 //Check message field
	 assertTrue(responseBody.asString().contains("experience"),"experience field check Failed");
	 
	 //Check message content
	 String exp = jsonBody.getString("experience");
	 assertEquals(exp,myExperience,"Experience content Check Failed");
	 
}

@Test(priority = 4)
public void TC05_ValidateEducationMatched() {
	
	 //Check message field
	 assertTrue(responseBody.asString().contains("education"),"education field check Failed");
	 
	 //Check message content
	 String edu = jsonBody.getString("education");
	 assertEquals(edu,myEducation,"Education content Check Failed");
	 
}

@AfterClass
public void afterTest() {
	 //Reset Values
	 RestAssured.baseURI = null;
	 RestAssured.basePath = null;
}


}
