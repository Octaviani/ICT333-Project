/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.view.repository;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.Component;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author swoorup
 */
@StyleSheet("dragndropexample.css")
public abstract class DragAndDropBox extends DragAndDropWrapper implements DropHandler {

    private static final long FILE_SIZE_LIMIT = 100 * 1024 * 1024; // 2MB
    ProgressBar progress;

    public DragAndDropBox(final Component root, ProgressBar prog) {
        super(root);
        setDropHandler(this);
        progress = prog;
    }

    @Override
    public void drop(final DragAndDropEvent dropEvent) {

        // expecting this to be an html5 drag
        final DragAndDropWrapper.WrapperTransferable tr = (DragAndDropWrapper.WrapperTransferable) dropEvent.getTransferable();
        final Html5File[] files = tr.getFiles();
        if (files != null) {
            for (final Html5File html5File : files) {
                final String fileName = html5File.getFileName();

                if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                    Notification.show("File rejected. Max 100Mb files are accepted!", Notification.Type.WARNING_MESSAGE);
                } else {

                    final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    final StreamVariable streamVariable = new StreamVariable() {

                        @Override
                        public OutputStream getOutputStream() {
                            return bas;
                        }

                        @Override
                        public boolean listenProgress() {
                            return false;
                        }

                        @Override
                        public void onProgress(
                                final StreamVariable.StreamingProgressEvent event) {
                        }

                        @Override
                        public void streamingStarted(
                                final StreamVariable.StreamingStartEvent event) {
                        }

                        @Override
                        public void streamingFinished(
                                final StreamVariable.StreamingEndEvent event) {
                            progress.setVisible(false);
                            showFile(fileName, html5File.getType(), bas);
                            processFile(fileName, html5File.getType(), bas);
                        }

                        @Override
                        public void streamingFailed(
                                final StreamVariable.StreamingErrorEvent event) {
                            progress.setVisible(false);
                        }

                        @Override
                        public boolean isInterrupted() {
                            return false;
                        }
                    };
                    html5File.setStreamVariable(streamVariable);
                    progress.setVisible(true);
                }
            }

        } else {
            final String text = tr.getText();
            if (text != null) {
                showText(text);
            }
        }
    }

    private void showText(final String text) {
        showComponent(new Label(text), "Wrapped text content");
    }
    
    
    abstract void processFile(final String name, final String type, final ByteArrayOutputStream bas);

    private void showFile(final String name, final String type, final ByteArrayOutputStream bas) {
        // resource for serving the file contents
        final StreamResource.StreamSource streamSource = () -> {
            if (bas != null) {
                final byte[] byteArray = bas.toByteArray();
                return new ByteArrayInputStream(byteArray);
            }
            return null;
        };
        final StreamResource resource = new StreamResource(streamSource, name);

        // show the file contents - images only for now
        final Embedded embedded = new Embedded(name, resource);
        showComponent(embedded, name);
    }

    private void showComponent(final Component c, final String name) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();
        layout.setMargin(true);
        final Window w = new Window(name, layout);
        w.addStyleName("dropdisplaywindow");
        w.setSizeUndefined();
        w.setResizable(false);
        c.setSizeUndefined();
        layout.addComponent(c);
        UI.getCurrent().addWindow(w);

    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }
}
