/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;

/**
 *
 * @author swoorup
 */
public class Utilities {

    public static String getCrcHash(String filename) throws FileNotFoundException, IOException {

        String crcHash;
        FileInputStream inFile = new FileInputStream(new File(filename));
        CRC32 crcMaker = new CRC32();
        byte[] buffer = new byte[2 * 1024 * 1024]; // stream 2 MB and compute the hash instead of loading full file
        int bytesRead;
        while ((bytesRead = inFile.read(buffer)) != -1) {
            crcMaker.update(buffer, 0, bytesRead);
        }
        crcHash = Long.toHexString(crcMaker.getValue());

        return crcHash;
    }
}
