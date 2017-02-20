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
 * Método que contiene el método para la validación del formato del RFC.
 *
 * @author Ing Germán
 */
@FacesValidator("validaterfc")
public class ValidateRFC implements Validator {

    private static final String RFC = "^([A-ZÑ\\x26]{3,4}([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z|\\d]{3})$";
    private Pattern pattern;
    private Matcher matcher;

    public ValidateRFC() {

        pattern = Pattern.compile(RFC);
    }

    /**
     * Método que valida el formato del RFC tomando como base el formato de
     * hacienda de la ciudad de México.
     *
     * @param value objeto a validar
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El formato del RFC es incorrecto");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
