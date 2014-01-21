package be.vdab.web;

/*
 * Deze classe stelt de dispatcherservlet voor.
 * Stuurt requests door naar Spring controller Beans
 */
import javax.servlet.Filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import be.vdab.dao.CreateDAOBeans;
import be.vdab.services.CreateServiceBeans;

/*
 * Deze class erft van AbstractAnnotationConfigDispatcherServletInitializer. 
 * Deze class registreert de DispatcherServlet als servlet bij de webserver.
 */
public class Initializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	/*
	 * Spring moet bij het opstarten van de website de classes CreateDAOBeans en
	 * CreateServiceBeans uitvoeren (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractAnnotationConfigDispatcherServletInitializer
	 * #getRootConfigClasses()
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { CreateDAOBeans.class, CreateServiceBeans.class };
	}

	/*
	 * Je geeft in deze method aan aan welke classes de Java Config code
	 * bevatten voor je controller beans
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.support.
	 * AbstractAnnotationConfigDispatcherServletInitializer
	 * #getServletConfigClasses()
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { CreateControllerBeans.class };
		/*
		 * Je geeft aan dat de class CreateControllerBeans de Java Config code
		 * bevat van je controller beans
		 */
	}

	/*
	 * Je associeert in deze method de DispatcherServlet met URL patronen. De
	 * webserver stuurt requests, waarvan de URL overeenkomt met één van deze
	 * URL patronen naar de DispatcherServlet
	 * 
	 * @see
	 * org.springframework.web.servlet.support.AbstractDispatcherServletInitializer
	 * #getServletMappings()
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
		/*
		 * Het URL patroon / staat voor alle requests van je website. Je stuurt
		 * dus alle requests van je website naar de DispatcherServlet
		 */
	}

	/*
	 * Je geeft in deze method een array van filters terug die Spring MVC
	 * toepast op de DispatcherServlet.
	 * 
	 * Als je de parameter encoding invult met UTF-8, voert de filter op
	 * requests de method setCharacterEncoding(“UTF-8”) uit. Als je de parameter
	 * encoding invult met UTF-16, voert de filter op requests de method
	 * setCharacterEncoding(“UTF-16”) uit.
	 */
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] { characterEncodingFilter,
				new OpenEntityManagerInViewFilter() };
		/*
		 * OpenEntityManagerInViewFilter werkt intern samen met
		 * JPATransactionManager
		 * 
		 * - JPATransactionManager past transacties toe op dezelfde
		 * EntityManager die OpenEntityManagerInViewFilter opende.
		 * 
		 * - JPATransactionManager laat na de transactie de EntityManager open.
		 * JPA kan via die EntityManager nog records lezen in de presentation
		 * layer.
		 * 
		 * - JPATransactionManager voert op het einde van de transactie de
		 * method flush uit op de EntityManager. Recordwijzigingen en
		 * -verwijderingen gebeuren zo zeker nog in de services layer en niet op
		 * het einde van het verwerken van de browser request. Zo kan je
		 * exceptions die kunnen optreden bij deze recordwijzigingen en
		 * recordverwijderingen nog opvangen vooraleer je beslist welke JSP je
		 * toont.
		 */
	}
}
