/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Ing Germán
 */
@FacesValidator("hsmvalid")
public class ValidateHsmnull implements Validator {
//    private static final String TEXT_PATTERN = "^[_A-Za-z0-9-]+(\\." +
//			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
//			"(\\.[A-Za-z]{2,})$";

    private static final String TEXT_PATTERN = "--Seleccionar Llave--";
 
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value.toString().equals(TEXT_PATTERN)){
        
            FacesMessage msg
                    = new FacesMessage("No se ha seleccionado la llave");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
        
    }
}