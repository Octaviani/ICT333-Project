package com.hivesys.dashboard.view.repository;

import com.hivesys.exception.ContentAlreadyExistException;
import pl.exsio.plupload.*;
import pl.exsio.plupload.manager.PluploadManager;

import com.porotype.iconfont.FontAwesome.Icon;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.shared.ui.label.ContentMode;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class UploadView extends Panel implements View {

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

        filesToCommit = new ArrayList<>();
        filesToCommit.clear();
        setContent(null);

        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();

        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        root.addComponent(buildHeader("Uploads"));
        root.addComponent(buildSparklines());
        Component content = buildContent();
        root.addComponent(content);
        root.setExpandRatio(content, 1);
    }

    private Component buildHeader(String headername) {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        labelTitle = new Label(headername);
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

        ProgressBar progress = new ProgressBar();
        progress.setIndeterminate(true);
        progress.setVisible(false);

        manager.getUploader().addFileUploadedListener((PluploadFile file) -> {
            dashboardPanels.addComponent(buildFileInfoPanel(file));
            manager.getUploader().removeFile(file.getId());
        });

        // handle errors
        manager.getUploader().addErrorListener((PluploadError error) -> {
            Notification.show("There was an error: " + error.getMessage()
                    + " (" + error.getType() + ")",
                    Notification.Type.ERROR_MESSAGE);
        });

        manager.getUploader().addUploadCompleteListener(() -> {
            Notification.show("Upload Complete!", Notification.Type.TRAY_NOTIFICATION);
            VerticalLayout parent1 = (VerticalLayout) manager.getParent();
            parent1.removeComponent(manager);
            Button btnCommit = new Button("Commit  " + Icon.ok_sign);
            btnCommit.setWidth("100%");
            //btnCommit.setHtmlContentAllowed(true);
            btnCommit.setHtmlContentAllowed(true);
            btnCommit.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            btnCommit.addClickListener((Button.ClickEvent event) -> {
                commitFiles();
            });
            Button btnCancel = new Button("Cancel  " + Icon.remove_sign);
            btnCancel.setWidth("100%");
            btnCancel.setHtmlContentAllowed(true);
            btnCancel.setStyleName(ValoTheme.BUTTON_DANGER);
            btnCancel.addClickListener((Button.ClickEvent event) -> {
                UploadView.this.init();
            });
            parent1.addComponent(btnCommit);
            parent1.addComponent(new Label("<br>", ContentMode.HTML));
            parent1.addComponent(btnCancel);
            parent1.addComponent(new Label("<br>", ContentMode.HTML));
            parent1.addComponent(new Label("<br>", ContentMode.HTML));
            
        });

        manager.setStartButtonCaption("Analyse files");
        manager.setStopButtonCaption("Cancel");

        final VerticalLayout vertLayout = new VerticalLayout();
        vertLayout.setSizeUndefined();
        vertLayout.setWidth("100%");
        // vertLayout.setHeight("400px");

        // Wrap the layout to allow handling drops
        vertLayout.addComponent(manager);

        //vertLayout.setComponentAlignment(manager, Alignment.MIDDLE_RIGHT);

        final Label infoLabel = new Label("asasasasasasasxasx");

        final VerticalLayout dropPane = new VerticalLayout(infoLabel);

        dropPane.setComponentAlignment(infoLabel, Alignment.MIDDLE_CENTER);
        dropPane.setWidth(280.0f, Unit.PIXELS);
        dropPane.setHeight(200.0f, Unit.PIXELS);
        dropPane.addStyleName("drop-area");

        ProgressBar pbar = new ProgressBar();
        pbar.setIndeterminate(true);
        pbar.setVisible(false);
        dropPane.addComponent(pbar);

        final DragAndDropBox box = new DragAndDropBox(dropPane, pbar) {

            @Override
            void processFile(String name, String type, ByteArrayOutputStream bas) {
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

            }
        };
        box.setSizeUndefined();
        //vertLayout.addComponent(box);

        
        
        dashboardPanels.addComponent(vertLayout);

        return dashboardPanels;
    }

    private Component buildFileInfoPanel(PluploadFile file) {
        FileInfoPanel fileToCommit = new FileInfoPanel(file);
        filesToCommit.add(fileToCommit);
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

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                dashboardPanels.removeComponent(slot);
                filesToCommit.remove((FileInfoPanel) content);
                if (filesToCommit.isEmpty()) {
                    init();
                }

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
                Notification.show(f.getfInfo().getRootFileName() + "has successfully been uploaded!", Notification.Type.TRAY_NOTIFICATION);
            } catch (ContentAlreadyExistException error) {
                Notification.show("Cannot upload: " + f.mfile.getUploadedFile().toString() + "\nFile Already Exists",
                        Notification.Type.ERROR_MESSAGE);
            } catch (SQLException ex) {
               Notification.show("Cannot upload: " + f.mfile.getUploadedFile().toString() + "\nFile Already Exists",
                        Notification.Type.ERROR_MESSAGE);
            }
        });

        
        init();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
