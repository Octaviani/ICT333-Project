package com.HiveSys.exception;

public class ContentAlreadyExistException extends Exception {
    //Parameterless Constructor
      public ContentAlreadyExistException() 
      {
          super("Exact content already exist in the repository!");
      }

      //Constructor that accepts a message
      public ContentAlreadyExistException(String message)
      {
         super(message);
      }
}
