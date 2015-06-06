package com.hivesys.core;

import com.box.view.BoxViewException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swoorup
 */
public class DocumentUploadQueueManager {

    private static final DocumentUploadQueueManager singleton = new DocumentUploadQueueManager();
    private final Queue<Document> documentQueue = new ConcurrentLinkedQueue<>();
    UploaderThread uploaderThread = new UploaderThread();

    public DocumentUploadQueueManager() {
        uploaderThread.start();
        uploaderThread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            try {
                throw e;
            } catch (Throwable ex) {
                Logger.getLogger(DocumentUploadQueueManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void placeInUploadQueue(Document doc) {
        getDocumentQueue().add(doc);
    }

    public int getRemainingDocumentNumber() {
        return this.getDocumentQueue().size();
    }

    /**
     * @return the documentQueue
     */
    public Queue<Document> getDocumentQueue() {
        return documentQueue;
    }

    public class UploaderThread extends Thread {

        public UploaderThread() {

        }

        @Override
        public void run() {
            while (true) {
                try {

                    Document document;
                    while ((document = getDocumentQueue().poll()) != null) {

                        //System.out.println("Processing : " + document.getRootFileName());
                        try {
                            ContentStore.getInstance().storeFileToRepository(document);
                        } catch (SQLException ex) {
                            document.setIsErrorOccured(true);
                        } catch (IOException ex) {
                            document.setIsErrorOccured(true);
                            Logger.getLogger(DocumentUploadQueueManager.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BoxViewException ex) {
                            Logger.getLogger(DocumentUploadQueueManager.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DocumentUploadQueueManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static DocumentUploadQueueManager getInstance() {
        return singleton;
    }
}
