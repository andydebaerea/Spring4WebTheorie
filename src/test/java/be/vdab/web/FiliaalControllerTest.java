package be.vdab.web;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import be.vdab.entities.Filiaal;
import be.vdab.services.FiliaalService;
import be.vdab.valueobjects.Adres;

public class FiliaalControllerTest {
	private FiliaalController filiaalController;
	private FiliaalService filiaalService;
	private Iterable<Filiaal> filialen;
	private Filiaal filiaal;

	/*
	 * De class Mockito bevat een static method mock. Je geeft aan deze method
	 * een interface mee. De method maakt ‘on the fly’ een class die deze
	 * interface implementeert. De methods in deze class doen default niets,
	 * behalve een waarde teruggeven, als het returntype van de method verschilt
	 * van void. Als het returntype een getal is, geven de methods nul terug.
	 * Als het returntype een class of interface is, geven de methods null
	 * terug. Je zal verder in de cursus dit gedrag bijsturen. De method mock
	 * maakt van deze class een instance en geeft je die instance als return
	 * waarde.
	 * 
	 * Je maakt een instance van de class die je wil testen: FiliaalController.
	 * Je injecteert de dummy implementatie van FiliaalService in de
	 * constructor.
	 * 
	 * De method findAll, in de class die Mockito genereert, geeft standaard
	 * null terug. Je definieert hier dat deze method de lege lijst in de
	 * variabele filialen moet teruggeven.
	 * 
	 * Als je op filiaalService de method read oproept met een parameter 1, moet
	 * deze dummy het Filiaal object uit de variabele filiaal teruggeven. Als je
	 * de method oproept met een ander getal, geeft de method null terug. Dit is
	 * het standaard gedrag van een Mockito dummy
	 */
	@Before
	public void setUp() {
		filialen = Collections.emptyList();
		filiaalService = Mockito.mock(FiliaalService.class);
		Mockito.when(filiaalService.findAll()).thenReturn(filialen);
		filiaalController = new FiliaalController(filiaalService);
		filiaal = new Filiaal("naam1", true, BigDecimal.ONE, new Date(),
				new Adres("straat1", "huisnr1", 1, "gemeente1"));
		Mockito.when(filiaalService.read(1L)).thenReturn(filiaal);
	}

	/*
	 * test of een controller method de juiste view activeert.
	 * 
	 * Je verkrijgt de naam van de view, die onderdeel is van ModelAndView, met
	 * de method getViewName.
	 */
	@Test
	public void findAllActiveertJuisteView() {
		Assert.assertEquals("filialen/filialen", filiaalController.findAll()
				.getViewName());
	}

	/*
	 * Je test of de ModelAndView een attribuut met de naam filialen bevat en of
	 * de inhoud gelijk is aan de return waarde van de method findAll van de
	 * FiliaalService dummy
	 */
	@Test
	public void findAllMaakRequestAttribuutFilialen() {
		Assert.assertSame(filialen, filiaalController.findAll().getModelMap()
				.get("filialen"));
	}

	@Test
	public void readActiveertJuisteView() {
		Assert.assertEquals("filialen/filiaal", filiaalController.read(1L)
				.getViewName());
	}

	/*
	 * Het filiaal in het request attribuut filiaal moet hetzelfde zijn als het
	 * filiaal dat de method read van de FiliaalService dummy teruggeeft bij een
	 * method oproep read(1L)
	 */
	@Test
	public void readMetBestaandeIDGeeftFiliaalTerug() {
		Assert.assertSame(filiaal, filiaalController.read(1L).getModelMap()
				.get("filiaal"));
	}

	/*
	 * De method read van de FiliaalService dummy geeft null terug als je een
	 * andere waarde dan 1 meegeeft. Het request attribuut filiaal moet dan ook
	 * null bevatten.
	 */
	@Test
	public void readMetOnbestaandeIDGeeftNullTerug() {
		Assert.assertNull(filiaalController.read(666L).getModelMap()
				.get("filiaal"));
	}
}
