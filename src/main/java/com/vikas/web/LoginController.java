package com.vikas.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vikas.domain.Person;
import com.vikas.service.LoginService;
import com.vikas.web.validation.LoginValidator;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
public class LoginController {

	@Autowired
	private LoginValidator loginValidator;

	@Autowired
	LoginService loginService;

	@Autowired
	private ReCaptcha reCaptcha;

	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public String register(Model model, HttpServletRequest request) {

		Person person = new Person();

		person.setCountry(loginService.getCountry(request.getRemoteAddr()));
		model.addAttribute("person", person);

		List<String> countryList = loginService.getCountryList();
		model.addAttribute("countryList", countryList);

		String reCaptchaHtml = reCaptcha.createRecaptchaHtml(null, null);
		model.addAttribute("reCaptchaHtml", reCaptchaHtml);

		return "register";
	}

	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public String createAccount(HttpServletRequest request,
			@RequestParam("recaptcha_challenge_field") String challenge,
			@RequestParam("recaptcha_response_field") String response,
			@Valid Person person, BindingResult result, Model model) {

		String name = person.getName().toLowerCase();
		person.setName(name);

		loginValidator.validate(person, result);

		String ipAddress = request.getRemoteAddr();
		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(ipAddress,
				challenge, response);

		if (!reCaptchaResponse.isValid()) {
			result.rejectValue("captcha", "errors.badCaptcha");
		}

		String html = reCaptcha.createRecaptchaHtml(null, null);
		model.addAttribute("reCaptchaHtml", html);

		if (result.hasErrors()) {

			List<String> countryList = loginService.getCountryList();
			model.addAttribute("countryList", countryList);

			return "register";
		}

		person.setIpAddress(ipAddress);

		person.setName(person.getName().toLowerCase());

		loginService.createAccount(person, "ROLE_USER");

		return "redirect:success.htm";
	}

	@RequestMapping(value = "/activate.htm", method = RequestMethod.GET)
	public String activatePerson(@RequestParam long pid,
			@RequestParam int authKey) {

		boolean result = loginService.activatePerson(pid, authKey);

		if (!result) {
			// TODO message user about invalid pid or authKey
		}

		return "login";
	}

	@RequestMapping(value = "/forgot.htm", method = RequestMethod.GET)
	public String forgot(Model model, @RequestParam(required = false) String msg) {

		if ("success".equals(msg)) {
			model.addAttribute("msg", "success");
			return "forgot";
		}

		model.addAttribute("person", new Person());

		if ("fail".equals(msg)) {
			model.addAttribute("msg", "fail");
			return "forgot";
		}

		return "forgot";
	}

	@RequestMapping(value = "/forgot.htm", method = RequestMethod.POST)
	public String forgot(@ModelAttribute("person") Person person,
			RedirectAttributes redirectAttributes) {

		boolean success = loginService.sendResetPasswordMail(person);

		if (success) {
			redirectAttributes.addAttribute("msg", "success");
		} else {
			redirectAttributes.addAttribute("msg", "fail");
		}

		return "redirect:forgot.htm";
	}

	@RequestMapping(value = "/reset_password.htm", method = RequestMethod.GET)
	public String passwordReset(Model model, @RequestParam long pid,
			@RequestParam int authKey) {

		Person person = loginService.validatePerson(pid, authKey);

		if (person == null) {
			return "login";
		}

		person.setPassword("");

		model.addAttribute("person", person);

		return "resetPassword";
	}

	@RequestMapping(value = "/reset_password.htm", method = RequestMethod.POST)
	public String passwordReset(@ModelAttribute("person") Person person) {

		loginService.resetPassword(person);

		return "redirect:login.htm";
	}
}
