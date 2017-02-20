/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguriboxltvweb.jsf.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author MiguelADM
 * Clase que obtiene la lista de codigo de paises ordenados alfabeticamente por el nombre del país
 */
public class CountryCode  implements Comparable<CountryCode> {
    
    private String codigoPais;
    private String nombrePais;
    //Constructor
    public CountryCode()
    {
        super();
    }
    public CountryCode(String codigo, String nombre)
    {
        super();
        this.codigoPais = codigo;
        this.nombrePais = nombre;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    @Override
    public int compareTo(CountryCode o) {
        return this.nombrePais.compareTo(o.nombrePais);
    }
    
    public List<CountryCode> GetCountry()
    {
        String[] locales = Locale.getISOCountries();
        List<CountryCode> paises = new ArrayList();
    for (String countryCode : locales) {

        Locale obj = new Locale("", countryCode);
                CountryCode cc = new CountryCode(obj.getCountry(), obj.getDisplayCountry());
                paises.add(cc);
    }
        Collections.sort(paises);
        return paises;
    }
}