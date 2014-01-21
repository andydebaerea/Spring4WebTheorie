package be.vdab.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("be.vdab.web")
/*
 * Je vraagt Spring de package be.vdab.web te doorzoeken naar classes voorzien
 * van @Compoment, @Repository, @Service of @Controller en van zo’n classes
 * beans te maken
 */
public class CreateControllerBeans extends WebMvcConfigurationSupport {
	/*
	 * De class WebMvcConfigurationSupport initialiseert Spring MVC
	 */

	/*
	 * Je definieert in de method addResourceHandlers welke requests de
	 * DispatcherServlet niet naar controller beans moet sturen. (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
	 * #addResourceHandlers(org.springframework.web.servlet.config.annotation.
	 * ResourceHandlerRegistry)
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**").addResourceLocations(
				"/images/");
		registry.addResourceHandler("/styles/**").addResourceLocations(
				"/styles/");
		registry.addResourceHandler("/scripts/**").addResourceLocations(
				"/scripts/");
		/*
		 * Je voert op de parameter registry de method addResourceHandler uit.
		 * Je geeft als parameter het URL patroon van static resources mee.
		 * /images/** betekent alle URL’s die beginnen met /images/. De twee
		 * sterretjes betekenen dat de URL na /images/ nog één of meerdere /
		 * tekens kan bevatten (bvb. de URL /images/products/12.jpg). Als je
		 * maar één sterretje gebruikt, past het patroon bij /images/12.jpg,
		 * maar niet bij /images/products/12.jpg. Je voert op het resultaat van
		 * de method addResourceHandler de method addResourceLocations uit. Je
		 * geeft als parameter de locatie mee van de static resources. Dit is
		 * een locatie in het web gedeelte van je project (de folder webapp).
		 */
	}

	/*
	 * Je maakt een bean van de class InternalResourceViewResolver. Spring
	 * gebruikt zo’n bean bij het activeren van een JSP.
	 * 
	 * De bean concateneert de inhoud van de property prefix vóór de String die
	 * een @RequestMapping method teruggeeft.
	 * 
	 * De bean concateneert de inhoud van de property suffix ná de String die
	 * een @RequestMapping method teruggeeft. Als een
	 * 
	 * @RequestMapping method filialen/toevoegen teruggeeft, maakt de bean
	 * hiervan /WEB-INF/JSP/filialen/toevoegen.jsp
	 */
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/JSP/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	/*
	 * Spring geeft requests naar de URL /info rechtstreeks door naar
	 * /WEB-INF/JSP/info.jsp.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
	 * #addViewControllers(org.springframework.web.servlet.config.annotation.
	 * ViewControllerRegistry)
	 */
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/info").setViewName("info");
	}

	/*
	 * Een object dat de interface MessageSource implementeert leest teksten uit
	 * resource bundles. De class ReloadableResourceBundleMessageSource
	 * implementeert deze interface
	 * 
	 * Je vult basename met de locatie en de base name van de properties
	 * bestanden
	 * 
	 * Default staat de property fallbackToSystemLocale op true. Als Spring een
	 * tekst niet vindt in het properties bestand met taalcode en landcode van
	 * de gebruiker en ook niet in het properties bestand met taalcode van de
	 * gebruiker, zoekt Spring de tekst dan in het properties bestand met de
	 * taalcode van het besturingssysteem. Dit is vervelend: je website gedraagt
	 * zich anders naargelang je hem installeert op een besturingssysteem met
	 * een andere taal. Je plaatst daarom de property fallbackToSystemLocale op
	 * false. Als Spring nu een tekst niet vindt in het properties bestand met
	 * taalcode en landcode van de gebruiker en ook niet in het properties
	 * bestand met taalcode van de gebruiker, zoekt Spring de tekst in het
	 * properties bestand zonder taalcode.
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:/resourceBundles/teksten");
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

	/*
	 * Als gebruiker taal selecteerd onthoud deze bean welke taal het is
	 */
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setCookieMaxAge(604800); // 604800=7 dagen
		return resolver;
	}

	/*
	 * De LocaleChangeInterceptor is een interceptor die behoort tot het Spring
	 * framework. Deze interceptor onderzoekt of een request een parameter
	 * locale bevat. Als dit zo is, roept de interceptor de method setLocale van
	 * de LocaleResolver op en geeft aan deze method deze request parameter mee.
	 * 
	 * Je stelt zo de locale in bij de class SessionLocaleResolver.
	 * 
	 * Je moet elke interceptor registreren in een method addInterceptors van de
	 * class CreateControllerBeans
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
	 * #addInterceptors(org.springframework.web.servlet.config.annotation.
	 * InterceptorRegistry)
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}

	/*
	 * Je maakt een bean van de class LocalValidatorFactoryBean. Deze class
	 * initialiseert bean validation
	 * 
	 * Je verwijst de property validationMessageSource naar de bean gemaakt in
	 * de method messageSource. Deze bean verwijst zelf naar de bestanden met
	 * foutboodschappen en vertaalbare teksten. De LocalValidatorFactoryBean
	 * bean zal zo, bij het valideren van bean; validations foutboodschappen
	 * zoeken in teksten.properties, teksten_en.properties, ...
	 */
	@Bean
	public LocalValidatorFactoryBean validatorFactory() {
		LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();
		validatorFactory.setValidationMessageSource(messageSource());
		return validatorFactory;
	}

	@Override
	// importeer Validator uit org.springframework.validation
	public Validator getValidator() {
		return new SpringValidatorAdapter(validatorFactory().getValidator());
	}
}
