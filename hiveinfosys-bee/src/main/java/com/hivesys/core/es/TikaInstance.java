/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core.es;
import org.apache.tika.Tika;


/**
 *
 */
public class TikaInstance {

    private static final Tika tika = new Tika();

    public static Tika tika() {
        return tika;
    }
}