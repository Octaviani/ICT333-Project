package com.hivesys.dashboard.view.repository;

import com.hivesys.core.ContentStore;
import com.hivesys.dashboard.domain.FileInfo;
import com.hivesys.exception.ContentAlreadyExistException;
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

    TextField mtxtTitle;
    TextField mtxtAuthor;
    DateField mdateCreated;
    DateField mdateUploaded;
    TextArea mtxtDescription;
    Label mPrevCopies;

    PluploadFile mfile;
    private FileInfo fInfo;

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
        setfInfo(new FileInfo());

        String tmpFilePath = mfile.getUploadedFile().toString();
        getfInfo().setRootFileName(mfile.getName());
        setfInfo(ContentStore.getInstance().parseFileInfoFromFile(tmpFilePath, getfInfo()));
        setDataFromDomain();
    }

    private void setDataFromDomain() {
        this.setCaption(mfile.getName());

        mtxtTitle.setValue(getfInfo().getTitle());
        mtxtAuthor.setValue(getfInfo().getAuthor());
        mdateCreated.setValue(getfInfo().getDateCreated());
    }

    public void setDataToDomain() {
        getfInfo().setTitle(mtxtTitle.getValue());
        getfInfo().setAuthor(mtxtAuthor.getValue());
        getfInfo().setDateCreated(mdateCreated.getValue());
        getfInfo().setDescription(mtxtDescription.getValue());
    }

    public void CommitChangesToDomain() throws ContentAlreadyExistException, SQLException, IOException {
        String tmpFilePath = mfile.getUploadedFile().toString();
        ContentStore.getInstance().storeFileToRepository(tmpFilePath, getfInfo());
    }

    /**
     * @return the fInfo
     */
    public FileInfo getfInfo() {
        return fInfo;
    }

    /**
     * @param fInfo the fInfo to set
     */
    public void setfInfo(FileInfo fInfo) {
        this.fInfo = fInfo;
    }

}
