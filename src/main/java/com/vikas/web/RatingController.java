package com.vikas.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

/**
 * 
 * @author Vikas Sharma
 *
 */
@Controller
@RequestMapping("chess")
public class RatingController {

	private static Logger LOGGER = LoggerFactory
			.getLogger(RatingController.class);

	@Autowired
	private ServletContext context;

	private static final Map<String, Float> monthsMap;

	static {

		monthsMap = new HashMap<String, Float>();

		monthsMap.put("Jan", (float) 1 / 12);
		monthsMap.put("Feb", (float) 2 / 12);
		monthsMap.put("Mar", (float) 3 / 12);
		monthsMap.put("Apr", (float) 4 / 12);
		monthsMap.put("May", (float) 5 / 12);
		monthsMap.put("Jun", (float) 6 / 12);
		monthsMap.put("Jul", (float) 7 / 12);
		monthsMap.put("Aug", (float) 8 / 12);
		monthsMap.put("Sep", (float) 9 / 12);
		monthsMap.put("Oct", (float) 10 / 12);
		monthsMap.put("Nov", (float) 11 / 12);
		monthsMap.put("Dec", (float) 12 / 12);

	}

	@RequestMapping(value = "/rating/init.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getTopPlayersRatingList() {

		File directory = null;
		FileInputStream fis = null;
		BufferedReader reader = null;

		StringWriter writer = new StringWriter();

		try {

			JsonFactory jfactory = new JsonFactory();

			JsonGenerator jGenerator = jfactory.createGenerator(writer);
			jGenerator.writeStartObject();

			directory = new ClassPathResource("/data/rating").getFile();

			for (final File file : directory.listFiles()) {

				String filename = file.getName();

				fis = new FileInputStream(file.getAbsolutePath());

				reader = new BufferedReader(new InputStreamReader(fis));

				String[] arr;

				jGenerator.writeObjectFieldStart(filename);
				jGenerator.writeStringField("label", filename);

				jGenerator.writeArrayFieldStart("data");

				String rating;
				while (true) {

					rating = reader.readLine();
					if (rating == null) {

						break;
					}

					arr = rating.trim().split("\\s+");

					write(jGenerator, arr[0], arr[1]);
				}
				jGenerator.writeEndArray();
				jGenerator.writeEndObject();
			}

			jGenerator.writeEndObject();
			jGenerator.close();

		} catch (Exception ex) {
			LOGGER.error("Error in file handling.", ex);
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				LOGGER.error("Error in file handling.", ex);
			}
		}

		return writer.toString();
	}

	private void write(JsonGenerator jGenerator, String date, String rating)
			throws IOException {

		String year = date.substring(0, 4);
		String month = date.substring(5);

		jGenerator.writeStartArray();

		jGenerator.writeNumber(Integer.parseInt(year) + monthsMap.get(month));
		jGenerator.writeNumber(rating);

		jGenerator.writeEndArray();
	}
}
