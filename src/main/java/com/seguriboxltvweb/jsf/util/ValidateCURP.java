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
 * Clase que contiene el método para validar el formato de la CURP.
 *
 * @author Ing Germán
 */
@FacesValidator("validatecurp")
public class ValidateCURP implements Validator {

    private static final String CURP = "[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}"
            + "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])"
            + "[HM]{1}"
            + "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)"
            + "[B-DF-HJ-NP-TV-Z]{3}"
            + "[0-9A-Z]{1}[0-9]{1}$";
    private Pattern pattern;
    private Matcher matcher;

    public ValidateCURP() {

        pattern = Pattern.compile(CURP);
    }

    /**
     * Método para validar el formato de la CURP .
     *
     * @param value CURP a validad.
     * @throws ValidatorException
     */

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El formato del CURP es incorrecto");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
