
package com.seguriboxltvweb.jsf.util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *Clase que contiene el método de validación de la contraseña.
 * @author Ing Germán
 */
@FacesValidator("passwordValidation")
public class PasswordValidation implements Validator{

    /**
     * Método que valida si el campo contraseña y el campo repetir contraseña son iguales.
     * @param context
     * @param component campo contraseña
     * @param value campo repetir contraseña.
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        
        String password = (String) value;
        String confirm = (String) component.getAttributes().get("confirm");
        
        if (password == null || confirm == null) {
            return; // Just ignore and let required="true" do its job.
        }

        if (!password.equals(confirm)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_FATAL,"Las contraseñas no coinciden",null));
        }
    }
    
    
}
