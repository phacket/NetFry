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
 * Clase que contiene el método para validar el formato de número telefónico.
 *
 * @author Ing Germán Hern{andez López
 */
@FacesValidator("telnumbervalidator")
public class TelNumberValidator implements Validator {

    private static final String NUMBER = "^[0-9]{10}+$";
    private Pattern pattern;
    private Matcher matcher;

    public TelNumberValidator() {

        pattern = Pattern.compile(NUMBER);
    }

    /**
     * Método para validar el formato de del número telefónico que es como este
     * 222012305859, 10 dígitos
     *
     * @param value numero telefonico a validar
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El número telefónico debe tener 10 dígitos y  contener solo números");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
