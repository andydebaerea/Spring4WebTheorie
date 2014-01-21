package be.vdab.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.entities.Lening;

@Controller
@RequestMapping("/leningen")
@SessionAttributes("lening")
public class LeningController {
	private final static String STAP1_JSP = "leningen/stap1";
	private final static String STAP2_JSP = "leningen/stap2";
	private final Logger logger = LoggerFactory
			.getLogger(LeningController.class);

	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
	ModelAndView createForm1() {
		return new ModelAndView(STAP1_JSP, "lening", new Lening());
	}

	@RequestMapping(method = RequestMethod.POST, params = "van1naar2")
	String createForm1Naar2(@Validated(Lening.Stap1.class) Lening lening,
			BindingResult bindingResult) {
		return bindingResult.hasErrors() ? STAP1_JSP : STAP2_JSP;
	}

	@RequestMapping(method = RequestMethod.POST, params = "van2naar1")
	String createForm2Naar1(@ModelAttribute Lening lening) {
		return STAP1_JSP;
	}

	@RequestMapping(method = RequestMethod.POST, params = "bevestigen")
	String create(@Validated(Lening.Stap2.class) Lening lening,
			BindingResult bindingResult, SessionStatus sessionStatus) {
		if (bindingResult.hasErrors()) {
			return STAP2_JSP;
		}
		logger.info("Lening record toevoegen aan de database");
		sessionStatus.setComplete();
		return "redirect:/";
	}

	@RequestMapping(method = RequestMethod.POST, params = "nogeennummer")
	String nogEenNummer(@ModelAttribute Lening lening) {
		lening.nogEenTelefoonNr();
		return STAP1_JSP;
	}
}
