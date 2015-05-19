package com.hivesys.dashboard.view.repository;

import pl.exsio.plupload.*;
import pl.exsio.plupload.manager.PluploadManager;

import com.porotype.iconfont.FontAwesome;
import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.ui.label.ContentMode;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UploadView extends Panel implements View {

    private static final long serialVersionUID = 1L;
    public static final String NAME = "UploadView";
    public static final String TITLE_ID = "dashboard-title";

    private VerticalLayout root;
    private CssLayout dashboardPanels;
    private Label labelTitle;

    ArrayList<FileInfoPanel> filesToCommit;

    public UploadView() {
        init();
    }

    private void init() {
        // Initialize the font awesome icons addon pack

        filesToCommit = new ArrayList<FileInfoPanel>();
        filesToCommit.clear();
        setContent(null);
        FontAwesome.load();
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        root.addComponent(buildHeader());
        root.addComponent(buildSparklines());
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        labelTitle = new Label("Uploads");
        labelTitle.setId(TITLE_ID);
        labelTitle.setSizeUndefined();
        labelTitle.addStyleName(ValoTheme.LABEL_H1);
        labelTitle.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(labelTitle);

        return header;
    }

    private Component buildSparklines() {
        CssLayout sparks = new CssLayout();
        sparks.addStyleName("sparks");
        sparks.setWidth("100%");

        return sparks;
    }

    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.addStyleName("dashboard-panels");

        PluploadManager manager = new PluploadManager();
        manager.setCaption("Please select files to analyse");
        manager.getUploader().setMaxFileSize("100mb");
        manager.getUploader().addFileUploadedListener(
                new Plupload.FileUploadedListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onFileUploaded(PluploadFile file) {
                        //Notification.show("I've just uploaded file: " + file.getUploadedFile().toString());
                        dashboardPanels.addComponent(buildFileInfoPanel(file));
                        manager.getUploader().removeFile(file.getId());
                    }
                });

        // handle errors
        manager.getUploader().addErrorListener(new Plupload.ErrorListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void onError(PluploadError error) {
                Notification.show("There was an error: " + error.getMessage()
                        + " (" + error.getType() + ")",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

        manager.getUploader().addUploadCompleteListener(
                new Plupload.UploadCompleteListener() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onUploadComplete() {
                        Notification.show("Upload Complete!", Notification.Type.TRAY_NOTIFICATION);

                        VerticalLayout parent = (VerticalLayout) manager.getParent();
                        parent.removeComponent(manager);

                        HorizontalLayout hLayout = new HorizontalLayout();
                        Button btnCommit = new Button("Commit  " + Icon.ok_sign);
                        btnCommit.setCaptionAsHtml(true);
                        btnCommit.setStyleName(ValoTheme.BUTTON_FRIENDLY);
                        btnCommit.addClickListener((Button.ClickEvent event) -> {
                            commitFiles();
                        });

                        Button btnCancel = new Button("Cancel  " + Icon.remove_sign);
                        btnCancel.setCaptionAsHtml(true);
                        btnCancel.setStyleName(ValoTheme.BUTTON_DANGER);
                        btnCancel.addClickListener((Button.ClickEvent event) -> {
                            UploadView.this.init();
                        });

                        hLayout.addComponent(btnCommit);
                        hLayout.addComponent(btnCancel);
                        hLayout.setWidth("100%");
                        hLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_RIGHT);
                        parent.addComponent(hLayout);
                        parent.addComponent(new Label("<br>", ContentMode.HTML));
                    }
                });

        manager.setStartButtonCaption("Analyze files");
        manager.setStopButtonCaption("Cancel");

        final VerticalLayout vertLayout = new VerticalLayout();
        vertLayout.setWidth("100%");
        // vertLayout.setHeight("400px");

        // Wrap the layout to allow handling drops
        vertLayout.addComponent(manager);

        dashboardPanels.addComponent(vertLayout);

        return dashboardPanels;
    }

    private Component buildFileInfoPanel(PluploadFile file) {
        FileInfoPanel fileToCommit = new FileInfoPanel(file);
        filesToCommit.add(new FileInfoPanel(file));
        Component panel = createContentWrapper(fileToCommit);
        panel.addStyleName("notes");
        return panel;
    }

    private Component createContentWrapper(final Component content) {
        final CssLayout slot = new CssLayout();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.setHtmlContentAllowed(true);
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

        MenuItem max = tools.addItem(Icon.resize_full.toString(), new Command() {
            private static final long serialVersionUID = 1L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setText(Icon.resize_small.toString());
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setText(Icon.resize_full.toString());
                    toggleMaximized(slot, false);
                }
            }

        });
        max.setStyleName("icon-only");

        MenuItem close = tools.addItem("" + Icon.remove, new Command() {
            private static final long serialVersionUID = 1L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                dashboardPanels.removeComponent(slot);

            }
        });
        close.setStyleName("icon-only");

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }

    private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Component component : root) {
            component.setVisible(!maximized);
        }
        dashboardPanels.setVisible(true);

        for (Component c : dashboardPanels) {
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }

    private void commitFiles() {
        this.filesToCommit.stream().forEach((FileInfoPanel f) -> {
            f.setDataToDomain();
            try {
                f.CommitChangesToDomain();
            } catch (Exception error) {
                Notification.show("Cannot upload: " + f.mfile.getUploadedFile().toString() + "\nFile Already Exists",
                        Notification.Type.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
