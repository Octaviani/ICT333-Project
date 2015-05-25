package com.hivesys.dashboard.view.repository;

import com.hivesys.core.ContentStore;
import com.hivesys.dashboard.domain.FileInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import pl.exsio.plupload.PluploadFile;

/**
 *
 * @author swoorup
 */
public class FileInfoPanel extends FormLayout {

    TextField mtxtTitle;
    TextField mtxtAuthor;
    DateField mdateCreated;
    DateField mdateUploaded;
    TextArea mtxtDescription;
    Label mPrevCopies;

    PluploadFile mfile;
    FileInfo fInfo;

    FileInfoPanel(PluploadFile file) {
        initViewComponents();
        initData(file);

    }

    private void initViewComponents() {
        mtxtTitle = new TextField("Title");
        mtxtAuthor = new TextField("Author");
        mdateCreated = new DateField("Date Created");
        mdateUploaded = new DateField("Date Uploaded");
        mPrevCopies = new Label("Previous Copies");
        mtxtDescription = new TextArea("Brief Description");
        

        this.addComponent(mtxtTitle);
        this.addComponent(mtxtAuthor);
        this.addComponent(mdateCreated);
        this.addComponent(mdateUploaded);
        this.addComponent(mtxtDescription);
        this.addComponent(mPrevCopies);
        this.setComponentAlignment(mtxtTitle, Alignment.TOP_CENTER);
        this.setComponentAlignment(mtxtAuthor, Alignment.TOP_CENTER);
        this.setComponentAlignment(mdateCreated, Alignment.TOP_CENTER);
        this.setComponentAlignment(mdateUploaded, Alignment.TOP_CENTER);
        this.setComponentAlignment(mPrevCopies, Alignment.TOP_CENTER);
        this.setComponentAlignment(mtxtDescription, Alignment.TOP_CENTER);

        this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        this.setSizeFull();
    }

    private void initData(PluploadFile file) {
        mfile = file;
        fInfo = new FileInfo();

        String tmpFilePath = mfile.getUploadedFile().toString();
        fInfo.setRootFileName(mfile.getName());
        fInfo = ContentStore.getInstance().parseFileInfoFromFile(tmpFilePath, fInfo);
        setDataFromDomain();
    }

    private void setDataFromDomain() {
        this.setCaption(mfile.getName());

        mtxtTitle.setValue(fInfo.getTitle());
        mtxtAuthor.setValue(fInfo.getAuthor());
        mdateCreated.setValue(fInfo.getDateCreated());
    }

    public void setDataToDomain() {
        fInfo.setTitle(mtxtTitle.getValue());
        fInfo.setAuthor(mtxtAuthor.getValue());
        fInfo.setDateCreated(mdateCreated.getValue());
        fInfo.setDescription(mtxtDescription.getValue());
    }

    public void CommitChangesToDomain() throws Exception {
        String tmpFilePath = mfile.getUploadedFile().toString();
        ContentStore.getInstance().storeFileToRepository(tmpFilePath, fInfo);
    }

}
