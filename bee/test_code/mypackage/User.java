/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypackage;

/**
 *
 * @author swoorup
 */
public class User {

   private String name, passwordHash;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the passwordhash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * @param passwordhash the passwordhash to set
     */
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
