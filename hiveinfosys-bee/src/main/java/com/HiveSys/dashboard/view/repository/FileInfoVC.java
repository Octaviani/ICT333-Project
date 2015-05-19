package com.HiveSys.dashboard.view.repository;

import com.HiveSys.core.ContentStore;
import com.HiveSys.dashboard.domain.FileInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.exsio.plupload.PluploadFile;

/**
 *
 * @author swoorup
 */
public class FileInfoVC extends FormLayout {

    TextField mtxtTitle;
    TextField mtxtFileType;
    TextField mtxtAuthor ;
    DateField mdateCreated ;
    DateField mdateModified ;
    
    PluploadFile mfile;
    
    FileInfoVC(PluploadFile file)
    {
        mtxtTitle = new TextField("Title");
        mtxtFileType = new TextField("File Type");
        mtxtAuthor = new TextField("Author");
        mdateCreated = new DateField("Date Created");
        mdateModified = new DateField("Date Modified");
        mfile = file;
        
        this.setCaption(file.getName());
        
        FileInfo fInfo;
        fInfo = new FileInfo();
        ContentStore cs = new ContentStore();
        fInfo = cs.parseFileInfoFromFile(file.getUploadedFile().toString(), fInfo);

        mtxtTitle.setValue(file.getName());
        mtxtFileType.setValue(fInfo.getFiletype());
        mtxtAuthor.setValue(fInfo.getAuthor());
        mdateModified.setValue((fInfo.getDateModified()));
        mdateCreated.setValue(fInfo.getDateCreated());


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
    
    public void CommitChangesToDomain()
    {
        
    }
    
    
}
