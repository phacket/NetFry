/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguriboxltvweb.jsf.util;

import com.google.gson.Gson;
import com.seguriboxltvweb.domain.SeguriServer;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.client.Entity.entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import static javax.ws.rs.client.Entity.entity;

/**
 *
 * @author Ing Germán
 */
public class A {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        WebTarget webTarget ;
//         Client client;
//        client = ClientBuilder.newClient();
//        String BASE_URI = "http://201.122.192.62:8282/SDBridge/seguridata/seguriserverfake";
//        webTarget = client.target(BASE_URI).path("test");
//        SeguriServer s=new SeguriServer();
//        s.setDnC("90500");
//        s.setDnCn("german hernandez lopez");
//        s.setDnE("inggermantics@gmail.com");
//        s.setDnL("cuamanco");
//        s.setDnO("acfTchnologies");
//        s.setDnOu("admins");
//        s.setDnS("Tlaxcala");
//        s.setPkcs10b64("hcc9KFLx/IHXSCaFu39PRprk/oz2+do2EIIron8/Yfc=");
//        s.setValidFrom(Util.dateTooUtc(new Date()));
//        s.setValidTo(Util.dateTooUtc(new Date()));
//        try {
//            String res=new Gson().toJson(s);
//            Response response = webTarget.request(MediaType.APPLICATION_JSON+ ";charset=utf-8")
//                    .get();
//             String msj=response.readEntity(String.class);
//            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
//                throw new Exception(msj);
//            } else {
//                
//            }
//        } catch (Exception e) {
//            
//        }

        WebTarget webTarget;
        Client client;
        client = ClientBuilder.newClient();
        String BASE_URI = "http://201.122.192.62:8282/SDBridge/sdapi/seguriserverfake";
        webTarget = client.target(BASE_URI);
        SeguriServer s = new SeguriServer();
        s.setDnC("90500");
        s.setDnCn("german hernandez lopez");
        s.setDnE("inggermantics@gmail.com");
        s.setDnL("cuamanco");
        s.setDnO("acfTchnologies");
        s.setDnOu("admins");
        s.setDnS("Tlaxcala");
        s.setPkcs10b64("hcc9KFLx/IHXSCaFu39PRprk/oz2+do2EIIron8/Yfc=");
        s.setValidFrom(Util.dateTooUtc(new Date()));
        s.setValidTo(Util.dateTooUtc(new Date()));
        Long l=new Date().getTime();
        
        try {
            String res = new Gson().toJson(s);
            
            String ur = MessageFormat.format("gethash/{0}/{1}", new Object[]{"1476853127056", "1476853126056"});
            Response response = webTarget.path(ur).request(MediaType.APPLICATION_JSON + ";charset=utf-8")
                    .post(entity(res, MediaType.APPLICATION_JSON+ ";charset=utf-8"), Response.class);
            String msj = response.readEntity(String.class);
            
            
            if (response.getStatus() != Response.Status.OK.getStatusCode()) {
                throw new Exception(msj);
            } else {
                
            }
        } catch (Exception e) {
            
        }

    }

    private static Date parse(final String str) {
        Calendar c = DatatypeConverter.parseDate(str);
        return c.getTime();
    }

}
