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

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

public class TC_API_Login {
private String account;
private String password;
private Response response;
private ResponseBody responseBody;
private JsonPath jsonBody;

@BeforeClass

public void init() throws IOException {
	//init data
	String baseURL = PropertiesFileUtils.getProperty("baseurl");
	String loginPath = PropertiesFileUtils.getProperty("loginPath");
	account = PropertiesFileUtils.getProperty("account");
	password = PropertiesFileUtils.getProperty("password");
	
	RestAssured.baseURI = baseURL;

	
	//make body
	Map <String, Object> accinfo = new HashMap<String, Object>();
	accinfo.put("account",account);
	accinfo.put("password",password);
	
	//make request
	RequestSpecification req = RestAssured.given()
			.contentType(ContentType.JSON)
			.body(accinfo);
	

	//Get response
	response = req.post(loginPath);
	responseBody = response.body();
	jsonBody = responseBody.jsonPath();
	
	System.out.println(" " + responseBody.asPrettyString());
}
	
@Test(priority = 0)
public void TC01_Validate200OK() {
	//Verify the http response status returned. Check Status Code is 200 OK?
	 assertEquals(200,response.getStatusCode(),"Status Check Failed");
}

@Test(priority = 1)
public void TC02_ValidateMessage() {
	
	 //Check message field
	 assertTrue(responseBody.asString().contains("message"),"message field check Failed");
	 
	 //Check message content
	 String mes = jsonBody.getString("message");
	 assertEquals(mes,"Đăng nhập thành công","Message Check Failed");
	 
}

@Test(priority = 2)	
public void TC03_ValidateToken() throws IOException {
	//Check field Token
	String token = jsonBody.getString("token");
	
	PropertiesFileUtils propertiesFileUtils = new PropertiesFileUtils();
	//Save token
	propertiesFileUtils.saveToken("token", token);
}
	
@Test(priority = 3)	
public void TC04_ValidateAccount() {
	
	//Check message field
	assertTrue(responseBody.asString().contains("user"),"userinfo field check Failed");
	
	String resAccount = jsonBody.getString("user.account");
	assertEquals(resAccount,account,"AccountInfo Check Failed");
	
	String resPass = jsonBody.getString("user.password");
	assertEquals(resPass,password,"PasswordInfo Check Failed");
	
}

@Test(priority = 4)
public void TC05_ValidateUserType() {

	String resType = jsonBody.getString("user.type");
	assertEquals(resType,"UNGVIEN","Type field Check Failed");
}

}

