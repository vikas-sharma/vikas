package com.vikas.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.vikas.domain.Person;
import com.vikas.service.LoginService;

/**
 * 
 * @author Vikas Sharma
 */
@Controller
@SessionAttributes({ "person" })
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/register.htm", method = RequestMethod.GET)
	public String register(Model model) {

		Person person = new Person();
		model.addAttribute("person", person);

		return "register";
	}

	@RequestMapping(value = "/register.htm", method = RequestMethod.POST)
	public String createAccount(@ModelAttribute("person") @Valid Person person,
			SessionStatus sessionStatus) {

		sessionStatus.setComplete();

		// TODO write custom validation logic

		loginService.createAuthKey(person);

		loginService.createAccount(person, "ROLE_USER");

		loginService.sendActivationMail(person);

		return "success";
	}

	@RequestMapping(value = "/activate.htm", method = RequestMethod.GET)
	public String activatePerson(@RequestParam long pid,
			@RequestParam int authKey) {

		boolean result = loginService.activatePerson(pid, authKey);

		return "login";
	}

	@RequestMapping(value = "/forgot.htm", method = RequestMethod.GET)
	public String forgot(Model model, @RequestParam(required = false) String msg) {

		if ("success".equals(msg)) {
			return "forgotSuccess";
		}

		Person person = new Person();
		model.addAttribute(person);

		if ("fail".equals(msg)) {
			return "forgotFail";
		}

		return "forgot";
	}

	@RequestMapping(value = "/forgot.htm", method = RequestMethod.POST)
	public ModelAndView forgot(@ModelAttribute("person") Person person,
			SessionStatus sessionStatus) {

		sessionStatus.setComplete();

		Person p = loginService.findByEmailAddress(person.getEmailAddress());

		if (p == null) {

			p = loginService.findByUsername(person.getName());
			if (p == null) {
				return new ModelAndView(new RedirectView(
						"/vikasworld/forgot.htm?msg=fail"));
			}
		}

		loginService.sendResetPasswordMail(p);

		return new ModelAndView(new RedirectView(
				"/vikasworld/forgot.htm?msg=success"));
	}

	@RequestMapping(value = "/password_reset.htm", method = RequestMethod.GET)
	public String passwordReset(Model model, @RequestParam long pid,
			@RequestParam int authKey) {

		Person person = loginService.validatePerson(pid, authKey);

		if (person == null) {
			return "login";
		}

		person.setPassword("");

		model.addAttribute("person", person);

		return "reset";
	}

	@RequestMapping(value = "/password_reset.htm", method = RequestMethod.POST)
	public String passwordReset(@ModelAttribute("person") Person person,
			SessionStatus sessionStatus) {

		sessionStatus.setComplete();

		loginService.resetPassword(person);

		return "login";
	}
}
