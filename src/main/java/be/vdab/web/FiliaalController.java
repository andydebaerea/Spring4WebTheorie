package be.vdab.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import be.vdab.entities.Filiaal;
import be.vdab.exceptions.FiliaalHeeftNogWerknemersException;
import be.vdab.services.FiliaalService;

@Controller
// Een class, waarvoor je @Controller tikt, is een controller bean.
@RequestMapping("/filialen")
/*
 * hiermee geef je aan dat alle request beginnende met /filialen voor deze
 * classe zijn
 */
public class FiliaalController {
	private final FiliaalService filiaalService;

	@Autowired
	/*
	 * met deze annotation injecteert Spring de parameter filiaalService met de
	 * bean die de interface FiliaalService implementeert: FiliaalServiceImpl
	 */
	FiliaalController(FiliaalService filiaalService) {
		this.filiaalService = filiaalService;
	}

	/*
	 * Een method, waarvoor je @RequestMapping tikt, verwerkt requests naar de
	 * URL vermeld bij @RequestMapping. De method findAll verwerkt requests naar
	 * /filialen
	 */
	@RequestMapping(method = RequestMethod.GET)
	ModelAndView findAll() {
		return new ModelAndView("filialen/filialen", "filialen",
				filiaalService.findAll());
		/*
		 * Je geeft de request door aan een JSP. Dit is in Spring MVC eenvoudig:
		 * je method geeft een String terug met de locatie en de naam van de JSP
		 * 
		 * Als je slechts één request attribuut moet doorgeven aan de view,
		 * gebruik je de ModelAndView constructor met drie parameters.
		 * 
		 * a. de view die je wil activeren b. de naam van het request attribuut
		 * (dat Spring maakt) c. de inhoud van dit request attribuut
		 */
	}

	@RequestMapping(value = "toevoegen", method = RequestMethod.GET)
	ModelAndView createForm() {
		return new ModelAndView("filialen/toevoegen", "filiaal", new Filiaal());
	}

	@RequestMapping(method = RequestMethod.POST)
	String create(@Valid Filiaal filiaal, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "filialen/toevoegen";
		}
		filiaalService.create(filiaal);
		return "redirect:/filialen";
	}

	/*
	 * Geeft aan de hand van idNummer van filiaal dit filiaal terug
	 * 
	 * Bij de class FiliaalController staat @RequestMapping("/filialen"). Deze
	 * class verwerkt dus requests naar URL’s die beginnen met /filialen. Je
	 * voegt hier {id} toe en je bekomt zo de URI template /filialen/{id}. De
	 * method read verwerkt requests naar URL’s die passen bij /filialen/{id}.
	 * 
	 * Je tikt vóór een method parameter @PathVariable. Spring vult deze method
	 * parameter met de waarde van de path variabele met dezelfde naam (id) in
	 * de URL van de binnengekomen request.
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	ModelAndView read(@PathVariable long id) {
		return new ModelAndView("filialen/filiaal", "filiaal",
				filiaalService.read(id));
	}

	/*
	 * Methode om een filiaal te verwijderen
	 * 
	 * Je voegt een RedirectAttributes parameter toe aan de @RequestMapping
	 * method. Je gebruikt zo’n parameter om request parameters toe te voegen
	 * aan een GET request die je maakt via een redirect en in de URL path
	 * variabelen in te vullen
	 * 
	 * Je vindt het te verwijderen filiaal niet meer in de database. Je laat de
	 * browser een nieuwe request maken naar de root van je website.
	 * 
	 * De URI template bij (5) bevat een path variabele id. Je vult deze path
	 * variabele in met de RedirectAttributes method addAttribute. Je geeft de
	 * naam van de path variabele mee en de inhoud.
	 * 
	 * Je roept terug de method addAttribute op. De waarde naam komt niet voor
	 * als path variabele in de URI template bij (5). Spring zal aan de URL een
	 * request parameter naam toevoegen met als inhoud de naam van het filiaal
	 * (&naam=Andros)
	 * 
	 * Je laat de browser via een redirect een GET request maken. Je vermeldt de
	 * URI template /filialen{id}/verwijderd. Via de tussenkomst van de
	 * RedirectAttributes wordt de bijbehorende URL bvb.
	 * /filialen/1/verwijderd&naam=Andros
	 */
	@RequestMapping(value = "{id}/verwijderen", method = RequestMethod.POST)
	String delete(@PathVariable long id, RedirectAttributes redirectAttributes) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			return "redirect:/";
		}
		try {
			filiaalService.delete(id);
			redirectAttributes.addAttribute("id", id);
			redirectAttributes.addAttribute("naam", filiaal.getNaam());
			return "redirect:/filialen/{id}/verwijderd";
		} catch (FiliaalHeeftNogWerknemersException ex) {
			redirectAttributes.addAttribute("id", id);
			redirectAttributes.addAttribute("fout",
					"Filiaal is niet verwijderd, het bevat nog werknemers");
			return "redirect:/filialen/{id}";
		}
	}

	/*
	 * methode als er een filiaal sucsesvol verwijderd is
	 */
	@RequestMapping(value = "{id}/verwijderd", method = RequestMethod.GET)
	ModelAndView deleted(@PathVariable long id, String naam) {
		return new ModelAndView("filialen/verwijderd", "naam", naam);
	}

	/*
	 * 
	 */
	@RequestMapping(value = "vantotpostcode", method = RequestMethod.GET)
	ModelAndView findByPostcodeForm() {
		return new ModelAndView("filialen/vantotpostcode",
				"vanTotPostcodeForm", new VanTotPostcodeForm());
	}

	/*
	 * De parameter vanTotPostcodeForm wijst naar een VanTotPostcodeForm object.
	 * De properties van dit object bevatten de inhoud van de vakken vanpostcode
	 * en totpostcode van de HTML form. Met @Valid valideert Spring deze
	 * properties ten opzichte van de bijbehorende annotations.
	 * 
	 * Spring plaatst validatiefouten in de parameter bindingResult.
	 */
	@RequestMapping(method = RequestMethod.GET, params = { "vanpostcode",
			"totpostcode" })
	ModelAndView findByPostcodeBetween(
			@Valid VanTotPostcodeForm vanTotPostcodeForm,
			BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView("filialen/vantotpostcode");
		if (!bindingResult.hasErrors() && !vanTotPostcodeForm.isValid()) {
			bindingResult.reject("fouteVanTotPostcode",
					new Object[] { vanTotPostcodeForm.getVanpostcode(),
							vanTotPostcodeForm.getTotpostcode() }, "");
		}
		if (!bindingResult.hasErrors()) {
			modelAndView.addObject("filialen", filiaalService
					.findByPostcodeBetween(vanTotPostcodeForm.getVanpostcode(),
							vanTotPostcodeForm.getTotpostcode()));
		}
		return modelAndView;
	}

	@RequestMapping(value = "{id}/wijzigen", method = RequestMethod.GET)
	ModelAndView updateForm(@PathVariable long id) {
		Filiaal filiaal = filiaalService.read(id);
		if (filiaal == null) {
			return new ModelAndView("redirect:/filialen");
		}
		return new ModelAndView("filialen/wijzigen", "filiaal", filiaal);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.POST)
	String update(@Valid Filiaal filiaal, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "filialen/wijzigen";
		}
		filiaalService.update(filiaal);
		return "redirect:/filialen";
	}
}
