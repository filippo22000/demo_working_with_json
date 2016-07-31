package com.fdp.example;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ExceptionDepthComparator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;

/**
 * @author filippo di pisa
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class DummyServiceTest {


	@Test
	public void testName() throws Exception {
		String jsonFileAsAString = loadJsonFileAsAString("complex_object.json");
		Object read = JsonPath.read(jsonFileAsAString,
									"$..street");


		DocumentContext parse1 = JsonPath.parse(jsonFileAsAString);

		JSONObject jsonObject = new JSONObject(jsonFileAsAString);
		Map emptyMap = Collections.EMPTY_MAP;
		List<String> parse = parse(jsonObject,
								   new ArrayList<>(),
								   null);
		int a = 1;

	}

	private List<String> parse(JSONObject json,
							   List<String> list,
							   String existingKey) {
		Iterator<String> keys = json.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			String keyToAdd = key;
			if (existingKey != null) {
				StringBuilder sb = new StringBuilder(existingKey);
				sb.append(".")
				  .append(key);
				keyToAdd = sb.toString();
			}
			try {
				JSONObject value = json.getJSONObject(key);
				parse(value,
					  list,
					  key);
			} catch (Exception e) {
				try {
					JSONArray jsonArray = json.getJSONArray(key);
					JSONObject value1 = (JSONObject) jsonArray.get(0);
					parse(value1,
						  list,
						  key);
				} catch (Exception e1){

					list.add(keyToAdd);
				}
			}

		}

		return list;
	}

	@Autowired
	private ResourceLoader resourceLoader;

	protected String loadJsonFileAsAString(String jsonFilePath) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + jsonFilePath);
		return IOUtils.toString(resource.getInputStream());
	}


}