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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static String getSHAHash(String filename) throws FileNotFoundException, IOException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            try (InputStream is = Files.newInputStream(Paths.get(filename))) {
                DigestInputStream dis = new DigestInputStream(is, md);
                /* Read stream to EOF as normal... */
            }
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02X ", b));
            }
            
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
