/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.repository;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author swoorup
 */
public class RepositoryUpload {

    ProgressBar progress;
    
    public RepositoryUpload()

    {
        final Label infoLabel = new Label("asasasasasasasxasx");

        final VerticalLayout dropPane = new VerticalLayout(infoLabel);

        dropPane.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropPane.setWidth(280.0f, Unit.PIXELS);
        dropPane.setHeight(200.0f, Unit.PIXELS);
        dropPane.addStyleName("drop-area");


        progress = new ProgressBar();
        progress.setIndeterminate(true);
        progress.setVisible(false);
        dropPane.addComponent(progress);
        
        
        final DragAndDropBox dropBox = new DragAndDropBox(dropPane, progress) {

            @Override
            void processFile(String name, String type, ByteArrayOutputStream bas) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        dropBox.setSizeUndefined();
    }

    

}
