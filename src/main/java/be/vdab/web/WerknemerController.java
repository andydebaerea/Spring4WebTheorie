package be.vdab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.services.WerknemerService;

@Controller
@RequestMapping("/werknemers")
public class WerknemerController {
	private final WerknemerService werknemerService;

	@Autowired
	WerknemerController(WerknemerService werknemerService) {
		this.werknemerService = werknemerService;
	}

	@RequestMapping
	ModelAndView findAll() {
		return new ModelAndView("werknemers/werknemers", "werknemers",
				werknemerService.findAll());
	}
}
