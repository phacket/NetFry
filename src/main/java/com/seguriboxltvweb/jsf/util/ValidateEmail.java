
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
 *Método que contiene el método para validar el formato del correo electrónico.
 * @author Ing Germán
 */

@FacesValidator("validateEmail")
public class ValidateEmail implements Validator {

    private static final String EMAIL ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    private Matcher matcher;

    public ValidateEmail() {

        pattern = Pattern.compile(EMAIL);
    }
    /**
     * Método que valida el formato del correo electrónico en su caso es este: algo@algo.algo 
     * @param value correo electronico para validar el formato
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El formato del email es incorrecto.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
