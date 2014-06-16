package com.vikas.web;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
public class XslController {

	private static Logger LOGGER = LoggerFactory.getLogger(XslController.class);

	@RequestMapping(value = "/personal/myPhotos.htm", method = RequestMethod.GET)
	public String photos(Model model) {

		String htmlStr = getHtmlStr("data/photo.xml", "data/myPhotos.xsl", null);

		model.addAttribute("htmlStr", htmlStr);

		return "myPhotos";
	}

	@RequestMapping(value = "/personal/familyPhotos.htm", method = RequestMethod.GET)
	public String familyPhotos(Model model) {

		String htmlStr = getHtmlStr("data/photo.xml", "data/familyPhotos.xsl",
				null);

		model.addAttribute("htmlStr", htmlStr);

		return "familyPhotos";
	}

	@RequestMapping(value = "/chess/favourites.htm", method = RequestMethod.GET)
	public String favourites(Model model) {

		String htmlStr = getHtmlStr("data/favourite.xml", "data/favourite.xsl",
				null);

		model.addAttribute("htmlStr", htmlStr);

		return "favourites";
	}

	@RequestMapping(value = "/chess/iccVideos.htm", method = RequestMethod.GET)
	public String viewGames(Model model,
			@RequestParam(required = false) Integer gameId) {

		Map<String, String> gidParam = new HashMap<String, String>();
		gidParam.put("gid", String.valueOf(gameId));

		String htmlStr = getHtmlStr("data/video.xml", "data/video.xsl",
				gidParam);

		model.addAttribute("htmlStr", htmlStr);

		return "iccVideos";
	}

	private String getHtmlStr(String xmlFilename, String xslFilename,
			Map<String, String> param) {

		StringWriter writer = new StringWriter();
		try {
			Resource xmlFile = new ClassPathResource(xmlFilename);
			Resource xslFile = new ClassPathResource(xslFilename);

			// Create a transform factory instance.
			TransformerFactory tfactory = TransformerFactory.newInstance();

			// Create a transformer for the stylesheet.
			Transformer transformer = tfactory.newTransformer(new StreamSource(
					xslFile.getFile()));

			if (param != null) {
				for (Map.Entry<String, String> entry : param.entrySet()) {
					transformer.setParameter(entry.getKey(), entry.getValue());
				}
			}

			// Transform the source XML to String.
			transformer.transform(new StreamSource(xmlFile.getFile()),
					new StreamResult(writer));
		} catch (Exception e) {
			LOGGER.error("Error while processing xsl {} for xml {} : {}",
					xslFilename, xmlFilename, e.toString());
		}

		return writer.toString();
	}
}