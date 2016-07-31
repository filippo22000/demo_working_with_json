package com.fdp.example;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author filippo di pisa
 */
@Service
public class DummyService {

	@Autowired
	Map<String,String> dummyMap;

	@PostConstruct
	public void init(){
		int a = 1;//parse xml;

		JSONObject jsonObject = convert();
		while(jsonObject.keys().hasNext()){
			String next = jsonObject.keys()
									.next().toString();
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


}