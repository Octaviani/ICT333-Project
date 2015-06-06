package com.hivesys.dashboard.view.repository;

import com.hivesys.core.ContentStore;
import com.hivesys.core.Document;
import com.hivesys.core.DocumentUploadQueueManager;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.io.IOException;

import java.sql.SQLException;
import pl.exsio.plupload.PluploadFile;

/**
 *
 * @author swoorup
 */
public class FileInfoPanel extends FormLayout {

    private final BeanFieldGroup<Document> fieldGroup;
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */

    @PropertyId("title")
    TextField mtxtTitle;

    @PropertyId("author")
    TextField mtxtAuthor;

    @PropertyId("dateCreated")
    DateField mdateCreated;

    @PropertyId("dateUploaded")
    DateField mdateUploaded;

    @PropertyId("description")
    TextArea mtxtDescription;

    PluploadFile mfile;
    private Document mDocument;

    FileInfoPanel(PluploadFile file) {
        initViewComponents();
        initData(file);

        fieldGroup = new BeanFieldGroup<>(Document.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(mDocument);
    }

    private void initViewComponents() {
        mtxtTitle = new TextField("Title");
        mtxtAuthor = new TextField("Author");
        mdateCreated = new DateField("Date Created");
        mdateUploaded = new DateField("Date Uploaded");
        mtxtDescription = new TextArea("Brief Description");

        mtxtTitle.addValidator(new BeanValidator(Document.class, "title"));
        mtxtAuthor.addValidator(new BeanValidator(Document.class, "Author"));
        mdateCreated.addValidator(new BeanValidator(Document.class, "dateCreated"));
        mdateUploaded.addValidator(new BeanValidator(Document.class, "dateUploaded"));
        //mtxtDescription.addValidator(new BeanValidator(Document.class, "description"));

        this.addComponent(mtxtTitle);
        this.addComponent(mtxtAuthor);
        this.addComponent(mdateCreated);
        this.addComponent(mdateUploaded);
        this.addComponent(mtxtDescription);

        this.setComponentAlignment(mtxtTitle, Alignment.TOP_CENTER);
        this.setComponentAlignment(mtxtAuthor, Alignment.TOP_CENTER);
        this.setComponentAlignment(mdateCreated, Alignment.TOP_CENTER);
        this.setComponentAlignment(mdateUploaded, Alignment.TOP_CENTER);
        this.setComponentAlignment(mtxtDescription, Alignment.TOP_CENTER);

        this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        this.setSizeFull();
    }

    private void initData(PluploadFile file) {
        mfile = file;
        setDocument(new Document());

        String tmpFilePath = mfile.getUploadedFile().toString();
        getDocument().setRootFileName(mfile.getName());
        setDocument(ContentStore.getInstance().parseFileInfoFromFile(tmpFilePath, getDocument()));
        this.setCaption(mfile.getName());
    }

    public void CommitChangesToDomain() throws SQLException, IOException, FieldGroup.CommitException {

        this.fieldGroup.commit();
        String tmpFilePath = mfile.getUploadedFile().toString();
        getDocument().setContentFilepath(tmpFilePath);
        DocumentUploadQueueManager.getInstance().placeInUploadQueue(mDocument);
    }

    /**
     * @return the fInfo
     */
    public Document getDocument() {
        return mDocument;
    }

    /**
     * @param document the fInfo to set
     */
    public void setDocument(Document document) {
        this.mDocument = document;
    }

}
