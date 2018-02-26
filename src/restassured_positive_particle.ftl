
	/**
	 *	 ${testCaseVO.description}
	 */
	@Test
	public void test${testCaseVO.name}() throws Exception {
		RequestSpecification requestSpec = getDefaultBasicRequestSpec("access-token-type");
<#if testSuiteVO.methodType = "get">		
		<#-- for GET Method : no input, path, query, body parameter handling -->
		<#if testCaseVO.inputParameters??>
		<#list testCaseVO.inputParameters as inputParameterVO>
		<#if inputParameterVO.inType = "path">
		requestSpec.pathParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#elseif inputParameterVO.inType = "query">
		requestSpec.queryParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#else>
		//unknown-type(path/query/body)::${inputParameterVO.inType}
		</#if>
		</#list>
		</#if>
		requestSpec.log().all();
		
		Response response = RestAssured
		.given()
			.spec(requestSpec)
		.expect()
			.statusCode(200)
			.log().all()
		.when()
			.get(apiPath)
			.andReturn();
		
		/* 3. response printing & detail assertions */
		JsonPath jsonResponse = new JsonPath(response.asString());
		//TODO	detail assertion needed
<#elseif testSuiteVO.methodType = "post">
				<#-- for POST Method : no input, path, query, body parameter handling -->
		<#if testCaseVO.inputParameters??>
		<#list testCaseVO.inputParameters as inputParameterVO>
		<#if inputParameterVO.inType = "path">
		requestSpec.pathParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#elseif inputParameterVO.inType = "query">
		requestSpec.queryParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#elseif inputParameterVO.inType = "body">
		//TODO	generate TestData Object Factory
		JSONObject inputJSON = ${inputParameterVO.name?cap_first}DataFactory.getPostData();//TODO	TestDataPrepare
		<#else>
		//unkown type parameter - ${inputParameterVO.inType}, ${inputParameterVO.name}, ${inputParameterVO.value}
		</#if>
		</#list>
		</#if>
		requestSpec.log().all();
		
		Response response = RestAssured
		.given()
			.spec(requestSpec)
		.expect()
			.statusCode(201)
			.log().all()
		.when()
			.post(apiPath)
			.andReturn();
		
		/* 3. response printing & detail assertions */
		JsonPath jsonResponse = new JsonPath(response.asString());
		//TODO	detail assertion needed
		//generatedDataToDelete = jsonResponse.getString("id");
<#elseif testSuiteVO.methodType = "put">
				<#-- for PUT Method : no input, path, query, body parameter handling -->
		<#if testCaseVO.inputParameters??>
		<#list testCaseVO.inputParameters as inputParameterVO>
		<#if inputParameterVO.inType = "path">
		requestSpec.pathParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#elseif inputParameterVO.inType = "query">
		requestSpec.queryParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#elseif inputParameterVO.inType = "body">
		//TODO	generate TestData Object Factory
		JSONObject inputJSON = ${inputParameterVO.name?cap_first}DataFactory.getPutData();//TODO	TestDataPrepare
		<#else>
		//unkown type parameter - ${inputParameterVO.inType}, ${inputParameterVO.name}, ${inputParameterVO.value}
		</#if>
		</#list>
		</#if>
		requestSpec.log().all();
		
		Response response = RestAssured
		.given()
			.spec(requestSpec)
		.expect()
			.statusCode(200)
			.log().all()
		.when()
			.put(apiPath)
			.andReturn();
		
		/* 3. response printing & detail assertions */
		JsonPath jsonResponse = new JsonPath(response.asString());
		//TODO	detail assertion needed
<#elseif testSuiteVO.methodType = "delete">
		<#-- for GET Method : no input, path, query, body parameter handling -->
		<#if testCaseVO.inputParameters??>
		<#list testCaseVO.inputParameters as inputParameterVO>
		<#if inputParameterVO.inType = "path">
		requestSpec.pathParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		<#elseif inputParameterVO.inType = "query">
		requestSpec.queryParam("${inputParameterVO.name}", "${inputParameterVO.value}");
		</#if>
		</#list>
		</#if>
		requestSpec.log().all();
		
		Response response = RestAssured
		.given()
			.spec(requestSpec)
		.expect()
			.statusCode(204)
			.log().all()
		.when()
			.delete(apiPath)
			.andReturn();
		
		/* 3. response printing & detail assertions */
		//TODO	detail assertion needed
</#if>
	}
	