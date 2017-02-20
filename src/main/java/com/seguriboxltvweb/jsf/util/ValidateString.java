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
 * Clase que contiene el método para validar el formato de una cadena.
 *
 * @author Ing Germán Hernández López.
 */
@FacesValidator("textValidator")
public class ValidateString implements Validator {
//    private static final String TEXT_PATTERN = "^[_A-Za-z0-9-]+(\\." +
//			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
//			"(\\.[A-Za-z]{2,})$";

    private static final String TEXT_PATTERN = "^[_A-Za-z0-9\\sñáéíóúÁÉÍÓÚÑ-]{3,25}$";
    private Pattern pattern;
    private Matcher matcher;

    public ValidateString() {
        pattern = Pattern.compile(TEXT_PATTERN);
    }

    /**
     * Método que valida si el formato de una cadena es correcto en este caso incluye vocales
     * con acento y la letra ñ

     * @param value parámetro a validar.
     * @throws ValidatorException
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        matcher = pattern.matcher(value.toString());
        if (!matcher.matches()) {

            FacesMessage msg
                    = new FacesMessage("El texto tiene un formato invalido.");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
