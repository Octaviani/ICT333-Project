/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author swoorup
 */
public class NoValueSet extends Exception {

    /**
     * NotInRangeException Constructor
     */
    public NoValueSet() {
        super("Error: Value Not Set!");
    }

    /**
     * NotInRangeException Constructor
     *
     * @param message: Message to spit on Exception Event
     */
    public NoValueSet(String message) {
        super(message);
    }
}

