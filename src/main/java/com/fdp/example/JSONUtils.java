package com.fdp.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author filippo di pisa
 */
@Service
public class JSONUtils {

	@Autowired
	Map<String, String> dummyMap;

	@PostConstruct
	public void init() {
		int a = 1;//convertToListOfProperties xml;

		JSONObject jsonObject = convert();
		while (jsonObject.keys()
						 .hasNext()) {
			String next = jsonObject.keys()
									.next()
									.toString();
			Object o = jsonObject.get(next);
		}
	}

	private static final String XML_TEXT = "<map>\n" +
										   "<key1>Tove</key1>\n" +
										   "<key2>Jani</key2>\n" +
										   "</map>";

	private static final int PRETTY_PRINT_INDENT_FACTOR = 4;


	public JSONObject convert() {
		JSONObject xmlJSONObj = XML.toJSONObject(XML_TEXT);
		String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		return xmlJSONObj;
	}

	public static List<String> convertToListOfProperties(JSONObject json,
														 List<String> list,
														 String existingKey) {
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			String keyToAdd = key;
			if (existingKey != null) {
				StringBuilder sb = new StringBuilder(existingKey).append(".")
																 .append(key);
				keyToAdd = sb.toString();
			}
			try {
				JSONObject jsonChild = json.getJSONObject(key);
				convertToListOfProperties(jsonChild,
										  list,
										  key);
			} catch (Exception e) {
				try {
					JSONArray jsonArray = json.getJSONArray(key);
					JSONObject firstJsonItemOfTheArray = (JSONObject) jsonArray.get(0);
					convertToListOfProperties(firstJsonItemOfTheArray,
											  list,
											  key);
				} catch (Exception e1) {
					list.add(keyToAdd);
				}
			}

		}

		return list;
	}


}