/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.dashboard.domain;

import java.io.File;
import java.util.Date;

/**
 *
 * @author swoorup
 */
public class FileInfo {
    private String contentFilepath;
    
    private String rootFileName;
    private String fullFileName;
    private String title;
    private String description;
    private String Author;
    private String crcHash;
    
    private Date dateCreated;
    private Date dateUploaded;

    /**
     * @return the contentFilepath
     */
    public String getContentFilepath() {
        return contentFilepath;
    }

    /**
     * @param contentFilepath the contentFilepath to set
     */
    public void setContentFilepath(String contentFilepath) {
        this.contentFilepath = contentFilepath;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the Author
     */
    public String getAuthor() {
        return Author;
    }

    /**
     * @param Author the Author to set
     */
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    /**
     * @return the crcHash
     */
    public String getCrcHash() {
        return crcHash;
    }

    /**
     * @param crcHash the crcHash to set
     */
    public void setCrcHash(String crcHash) {
        this.crcHash = crcHash;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the dateUploaded
     */
    public Date getDateUploaded() {
        return dateUploaded;
    }

    /**
     * @param dateUploaded the dateUploaded to set
     */
    public void setDateUploaded(Date dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    /**
     * @return the rootFileName
     */
    public String getRootFileName() {
        return rootFileName;
    }

    /**
     * @param rootFileName the rootFileName to set
     */
    public void setRootFileName(String rootFileName) {
        this.rootFileName = rootFileName;
    }

    /**
     * @return the fullFileName
     */
    public String getFullFileName() {
        return fullFileName;
    }

    /**
     * @param fullFileName the fullFileName to set
     */
    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

}
