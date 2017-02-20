
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
 *Clase que hace la validación si el parámetro dado es un número.
 * @author Ing Germán Hernández López
 * 
 */

@FacesValidator("validateNumber")
public class validateNumber implements Validator {

    private static final String NUM ="[0-9]+";
    private Pattern pattern;
    private Matcher matcher;

    public validateNumber() {

        pattern = Pattern.compile(NUM);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("Formato invalido, se espera valor numerico positivo");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
