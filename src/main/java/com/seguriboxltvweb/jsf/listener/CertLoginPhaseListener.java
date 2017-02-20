/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguriboxltvweb.jsf.listener;

import java.security.cert.X509Certificate;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * JSF Phase listener that checks if the user is already logged in and, besides,
 * it performs a silent login if the user presents a certificate that is valid.
 * Basically it checks if the loginBean is already filled.
 *
 * @author ricky
 */
public class CertLoginPhaseListener implements PhaseListener {

    /**
     * logger for the class
     */
    static private final Logger logger = LoggerFactory.getLogger(CertLoginPhaseListener.class);

    /**
     * Execute the logic before RESTORE_VIEW. The logic checks if the user
     * presents a certificate or if he is already logged in the application. In
     * both cases the user goes to the index page, otherwise the user is
     * immediately redirected to login page.
     *
     * @param event The phase event
     */
    public void afterPhase(PhaseEvent event) {
        logger.debug("entering afterPhase");
        FacesContext facesContext = event.getFacesContext();
        logger.debug("Phase: {}", event.getPhaseId());
        // check if we're not in the login page
        boolean loginPage = (facesContext.getViewRoot().getViewId().lastIndexOf("login.xhtml") > -1) ? true : false;
        boolean logoutPage = (facesContext.getViewRoot().getViewId().lastIndexOf("logout.xhtml") > -1) ? true : false;
        logger.debug("view id: {}", facesContext.getViewRoot().getViewId());
        // get the certificate
        HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        X509Certificate[] certs = (X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");
        if (certs != null && certs.length > 0) {
            try {
                // login silently without password via certificate, the trustedLogin
                // returns the username of the user if certificate is valid and
                // the user is mapped against the ldap
                ExpressionFactory ef = facesContext.getApplication().getExpressionFactory();
                MethodExpression me = ef.createMethodExpression(facesContext.getELContext(),
                        "#{loginController.trustedLogin}", null, new Class[]{X509Certificate.class});
                logger.debug("login using the cert");
                me.invoke(facesContext.getELContext(), new Object[]{certs[0]});
            } catch (Exception e) {
                logger.error("Error checking certificate", e);
            }
        }
        // check if the user is logged in
        ExpressionFactory ef = facesContext.getApplication().getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(facesContext.getELContext(), "#{loginController.loggedIn}", Boolean.class);
        boolean isLoggedIn = (Boolean) ve.getValue(facesContext.getELContext());
        logger.debug("isLoggedIn: {}", isLoggedIn);
        if (!loginPage && !isLoggedIn) {
            logger.debug("session out => redirecting to logout");
            // do a logout to drive the user to the login page
            if (logoutPage) {
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "logout");
            } else {
                facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "login");
            }

        } else if (loginPage && isLoggedIn) {
            // already login => go to main page
            facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "index");
        }
        logger.trace("AfterPhase: " + event.getPhaseId());

    }

    /**
     *
     * @param event
     */
    public void beforePhase(PhaseEvent event) {
        // nothing to do

    }

    /**
     *
     * @return The phase Id
     */
    public PhaseId getPhaseId() {
        //return PhaseId.RESTORE_VIEW;
        return PhaseId.ANY_PHASE;
    }
}