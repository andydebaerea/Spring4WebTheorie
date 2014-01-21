package be.vdab.constraints;

/*
 * Je definieert met @Target voor welke onderdelen van een Java source je de eigen annotation kan tikken. 
 * METHOD voor een method (Je kan validation annotations schrijven voor getters) 
 * FIELD voor de declaratie van een instance variabele (Je kan validation annotations schrijven voor private variabelen) 
 * ANNOTATION_TYPE voor een andere annotation (De toepassing hiervan valt buiten het bereik van deze cursus.)
 * 
 * Je definieert met @Retention hoe lang Java de annotation behoudt. 
 * RUNTIME betekent dat de annotation bij het uitvoeren van het programma nog moet ter beschikking zijn. 
 * Dit is het geval bij een validation annotation. Als je bijvoorbeeld SOURCE zou kiezen in plaats van RUNTIME, 
 * zou de Java compiler de annotation niet opnemen in de gecompileerde code.
 * 
 * Je definieert hier (in de source file Postcode.java) enkel de annotation @Postcode. 
 * Je definieert in een aparte class (PostcodeValidator) de code waarmee je de validatie doet (controle of de waarde tussen 1000 en 9999 ligt). 
 * Je koppelt deze class met de annotation via @Constraint.
 * 
 * Je definieert een eigen Java annotation met het keyword @interface.
 * 
 * Je definieert parameters van een Java annotation met de syntax van een method declaratie. 
 * String message() betekent dus dat je aan @Postcode een parameter message kan meegeven. 
 * Je hebt reeds gezien dat je aan iedere validation annotation een parameter message kan meegeven. 
 * De parameter message bevat de key van de foutboodschap die bij een bean validation annotation hoort. 
 * De parameter message is geen verplichte parameter. Als je de parameter niet meegeeft, krijgt hij een default waarde. 
 * Je geeft deze default waarde mee met het keyword default, gevolgd door die default waarde.
 * 
 * Iedere validation annotation moet ook een optionele parameter groups hebben. 
 * Je ziet verder in deze cursus wat validation groups zijn.
 * 
 * Iedere validation annotation moet ook een optionele parameter payload hebben. 
 * Het gebruik van deze parameter valt buiten deze cursus.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostcodeValidator.class)
public @interface Postcode {
	String message() default "{be.vdab.constraints.Postcode}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
