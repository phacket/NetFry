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
 * Esta clase realiza valida que el parámetro sea numerico.
 *
 * @author German Hernandez Lopez
 */

@FacesValidator("numericvalidator")
public class NumericValidator implements Validator {

    private static final String NUMBER = "^[1-9][0-9]?$|^100$";
    private Pattern pattern;
    private Matcher matcher;

    public NumericValidator() {

        pattern = Pattern.compile(NUMBER);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El campo semanas debe tener valores entre 1 y 99");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
