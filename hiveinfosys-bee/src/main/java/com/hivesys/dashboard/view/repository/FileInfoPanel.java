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
    TextField mtxtFileType;
    TextField mtxtAuthor ;
    DateField mdateCreated ;
    DateField mdateModified ;
    
    PluploadFile mfile;
    FileInfo fInfo;
    
    FileInfoPanel(PluploadFile file)
    {
        initViewComponents();
        initData(file);
        
    }
    
    private void initViewComponents()
    {
        mtxtTitle = new TextField("Title");
        mtxtFileType = new TextField("File Type");
        mtxtAuthor = new TextField("Author");
        mdateCreated = new DateField("Date Created");
        mdateModified = new DateField("Date Modified");
        
        this.addComponent(mtxtTitle);
        this.addComponent(mtxtFileType);
        this.addComponent(mtxtAuthor);
        this.addComponent(mdateCreated);
        this.addComponent(mdateModified);
        //this.addComponent(dateUploaded);
        this.setComponentAlignment(mtxtTitle, Alignment.TOP_CENTER);
        this.setComponentAlignment(mtxtFileType, Alignment.TOP_CENTER);
        this.setComponentAlignment(mtxtAuthor, Alignment.TOP_CENTER);
        this.setComponentAlignment(mdateCreated, Alignment.TOP_CENTER);
        this.setComponentAlignment(mdateModified, Alignment.TOP_CENTER);
        //this.setComponentAlignment(dateUploaded, Alignment.TOP_CENTER);

        this.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        this.setSizeFull();
    }
    
    private void initData(PluploadFile file)
    {
        mfile = file;        
        fInfo = new FileInfo();
        
        String tmpFilePath = mfile.getUploadedFile().toString();
        fInfo = ContentStore.getInstance().parseFileInfoFromFile(tmpFilePath, fInfo);
        setDataFromDomain();
    }
    
    private void setDataFromDomain()
    {   
        this.setCaption(mfile.getName());
        
        mtxtTitle.setValue(fInfo.getTitle());
        mtxtFileType.setValue(fInfo.getFiletype());
        mtxtAuthor.setValue(fInfo.getAuthor());
        mdateModified.setValue((fInfo.getDateModified()));
        mdateCreated.setValue(fInfo.getDateCreated());
    }
    
    public void setDataToDomain()
    {
        fInfo.setTitle(mtxtTitle.getValue());
        fInfo.setFiletype(mtxtFileType.getValue());
        fInfo.setAuthor(mtxtAuthor.getValue());
        fInfo.setDateModified(mdateModified.getValue());
        fInfo.setDateCreated(mdateCreated.getValue());
    }
    
    public void CommitChangesToDomain() throws Exception
    {
        String tmpFilePath = mfile.getUploadedFile().toString();
        fInfo = ContentStore.getInstance().parseFileInfoFromFile(tmpFilePath, fInfo);
        ContentStore.getInstance().storeFileToRepository(tmpFilePath, mfile.getName(), fInfo);
    }
    
    
}
