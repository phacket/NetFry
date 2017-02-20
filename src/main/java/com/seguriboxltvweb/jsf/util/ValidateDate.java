
package com.seguriboxltvweb.jsf.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *Clase que contiene el método para validar si el formato de fecha es correcto.
 * @author Ing Germán
 */

@FacesValidator(value="validateDate")
public class ValidateDate implements Validator {

    private static final String DATE ="^[0-3]?[0-9]/[0-1]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
    private final Pattern pattern;
    private Matcher matcher;

    public ValidateDate() {
        pattern = Pattern.compile(DATE);
    }
    
    /**
     * Método que valida que el formato de fecha sea correcto.
     * @param value fecha a evaluar
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Date date = (Date) value;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
        String dateFormat = sdf.format(date);
        matcher = pattern.matcher(dateFormat);
        if (!matcher.matches()) {
            String message = ResourceBundle.getBundle("/ValidatorMessages").getString("DateIncorrect");
            FacesMessage msg
                    = new FacesMessage(message);
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }

}
