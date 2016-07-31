package com.fdp.example;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author filippo di pisa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class JSONUtilsTest {



	@Test
	public void should_parse_json_into_list_of_unique_paths() throws Exception {
		String jsonFileAsAString = loadJsonFileAsAString("complex_object.json");
		JSONObject jsonObject = new JSONObject(jsonFileAsAString);
		List<String> parse = JSONUtils.convertToListOfProperties(jsonObject,
																 new ArrayList<>(),
																 null);
		assertThat(parse, hasSize(13));

		String jsonFileAsAString1 = loadJsonFileAsAString("complex_object1.json");
		JSONObject jsonObject1 = new JSONObject(jsonFileAsAString1);
		List<String> parse1 = JSONUtils.convertToListOfProperties(jsonObject1,
																 new ArrayList<>(),
																 null);
		assertThat(parse1, hasSize(18));
	}

	@Test
	public void should_retrieve_a_json_porperty_using_json_path() throws Exception {
		String jsonFileAsAString = loadJsonFileAsAString("complex_object.json");
		Object read = JsonPath.read(jsonFileAsAString,
									"$..street");


		DocumentContext parse1 = JsonPath.parse(jsonFileAsAString);

		JSONObject jsonObject = new JSONObject(jsonFileAsAString);
		List<String> parse = JSONUtils.convertToListOfProperties(jsonObject,
																 new ArrayList<>(),
																 null);


	}



	@Autowired
	private ResourceLoader resourceLoader;

	protected String loadJsonFileAsAString(String jsonFilePath) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + jsonFilePath);
		return IOUtils.toString(resource.getInputStream());
	}


}