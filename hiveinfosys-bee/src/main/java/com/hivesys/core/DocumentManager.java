/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author swoorup
 */
public class DocumentManager {
    Queue<Document> documentQueue = new ConcurrentLinkedQueue<>();

    public DocumentManager() {
    }
    
    public void placeInUploadQueue(Document doc) {
        if (documentQueue.size() == 0) {
            // instantiate a thread
            
            
        }
    }
    
    public int getRemainingDocumentNumber() {
        return this.documentQueue.size();
    }
    
    public class UploaderThread extends Thread {
        public UploaderThread(){
            
        }
        
        @Override
        public void run() {
            for (Document doc: documentQueue) {
                // upload to bla
            }
        }
    }
}
