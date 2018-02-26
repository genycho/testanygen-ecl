import org.json.simple.JSONObject;

/**
 * ${description}
 * @author TestAnyGen(v0.1)
 *
 */
public class ${fileName?cap_first} {

	public static JSONObject getPostData() {
		JSONObject jsonObject = new JSONObject();
		<#list paramMap as key, dataVO>
		jsonObject.put("${key}", "${dataVO.value}");//${dataVO.type} type
		</#list>
		return jsonObject;
	}

	public static JSONObject getPutData() {
		JSONObject jsonObject = new JSONObject();
		<#list paramMap as key, dataVO>
		jsonObject.put("${key}", "${dataVO.value}");//${dataVO.type} type
		</#list>
		return jsonObject;
	}

}