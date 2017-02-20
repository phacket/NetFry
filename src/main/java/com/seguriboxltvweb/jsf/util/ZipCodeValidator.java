
package com.seguriboxltvweb.jsf.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *Clase validadora del formato de código postal.
 * @author Ing Germán Hernández López
 * 
 */

@FacesValidator("zipcodevalidator")
public class ZipCodeValidator implements Validator {

    private static final String NUMBER = "^[0-9]{5}+$";
    private Pattern pattern;
    private Matcher matcher;

    public ZipCodeValidator() {

        pattern = Pattern.compile(NUMBER);
    }

    /**
     * Método que valida que el formato de código postal es correcto. 
     * @param value parametro para validar
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El código postal sólo debe tener 5 dígitos");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
