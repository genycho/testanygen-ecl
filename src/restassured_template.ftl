package ${testSuiteVO.targetPackage?lower_case};

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import ${testSuiteVO.targetPackage?lower_case}.MyCustomTestCase

/**
 * Auto-generated rest-assured Test class</br>
 * ${testSuiteVO.description}
 * @author TestAnyGen(v0.1)
 *
 */
public class ${testSuiteVO.targetName?cap_first}Test extends MyCustomTestCase{
	String apiPath = "${testSuiteVO.apiPath}";
<#if testSuiteVO.methodType = "get">
	//TODO	
	String sharedKeyData = "aKeyToSelect";
<#elseif testSuiteVO.methodType = "post">
	//TODO
	String generatedDataToDelete = "aKeyToCleanseAfterTest";
<#elseif testSuiteVO.methodType = "put">
	//TODO	
	String sharedKeyData = "aKeyToModify";
<#elseif testSuiteVO.methodType = "delete">
	//TODO	
	String sharedKeyData = "aKeyToDelete";
<#else>
	//NotDefined RestAPI Method type : ${testSuiteVO.methodType}
</#if>
	
	public ${testSuiteVO.targetName?cap_first}Test() {
		super();
		<#if testSuiteVO.baseURL??>
		//RestAssured.baseURI = "${testSuiteVO.baseURL}";	baseURI commonly managed in global
		</#if>
		<#if testSuiteVO.basePath??>
		RestAssured.basePath = "${testSuiteVO.basePath}";
		</#if>
	}
	
	@Before
	public void setUp() throws Exception {
		//authorized request spec maybe needed.
		//test data preparation can be needed.
	}

	@After
	public void tearDown() throws Exception {
	}
	
	<#if testSuiteVO.getTestCaseList()??>
	<#list testSuiteVO.getTestCaseList() as testCaseVO>
	<#include "/restassured_positive_particle.ftl">
	</#list>
	<#else>
	//there is no testcase anlyzed!!
	</#if>
	
}