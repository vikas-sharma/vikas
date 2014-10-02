package com.vikas.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

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

				boolean first = true;

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

					if (!first && !arr[0].endsWith("Jan")) {
						continue;
					}
					first = false;

					jGenerator.writeStartArray();

					jGenerator.writeNumber(arr[0].substring(0, 4));
					jGenerator.writeNumber(arr[1]);

					jGenerator.writeEndArray();
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
}
