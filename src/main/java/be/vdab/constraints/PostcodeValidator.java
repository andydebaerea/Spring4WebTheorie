package be.vdab.constraints;

/*
 * Een class waarin je de validatiecode van een bean validation annotation schrijft, 
 * implementeert de interface ConstraintValidator. Je geeft tussen < en > eerst de eigen bean validation annotation mee. 
 * Je definieert daarna het type variabele waarbij je de annotation mag tikken. 
 * Je mag @Postcode tikken bij een Integer (of int) variabele.
 * 
 * Het gebruik van de method initialize valt buiten deze cursus.
 * 
 * Bean validation roept de method isValid op bij het valideren van een variabele voorzien van @Postcode. 
 * Je geeft true terug als de variabele een correcte waarde bevat.
 * 
 * Zoals de ingebakken bean validation annotations (@Min, @Max, ...) 
 * produceer je geen foutmelding als de te valideren waarde gelijk is aan null. 
 * De validatie hiervan gebeurt door @NotNull
 */

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostcodeValidator implements
		ConstraintValidator<Postcode, Integer> {
	private static final int MIN_POSTCODE = 1000;
	private static final int MAX_POSTCODE = 9999;

	@Override
	public void initialize(Postcode postcode) {
	}

	@Override
	public boolean isValid(Integer postcode, ConstraintValidatorContext context) {
		return postcode == null
				|| (postcode >= MIN_POSTCODE && postcode <= MAX_POSTCODE);
	}
}
