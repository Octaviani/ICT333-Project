/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core;

import com.drew.lang.annotations.NotNull;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author swoorup
 */
public class Document implements Serializable{
    private String contentFilepath;
    
    private String rootFileName;
    
    @NotNull @Size(min=2, max=350) @NotEmpty
    private String title;
    
    private String description;
    
    @NotNull @Size(min=2, max=350) @NotEmpty
    private String Author;
    
    private String hash;
    private String boxViewID;
    
    
    @Past @NotNull
    private Date dateCreated;
    
    @Past @NotNull 
    private Date dateUploaded;
    
    private String versionID;
    
    private int progress;
    private boolean isErrorOccured;
    
    private static final long serialVersionUID = 1L;

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
    public String getHash() {
        return hash;
    }

    /**
     * @param crcHash the crcHash to set
     */
    public void setHash(String crcHash) {
        this.hash = crcHash;
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
     * @return the boxViewID
     */
    public String getBoxViewID() {
        return boxViewID;
    }

    /**
     * @param boxViewID the boxViewID to set
     */
    public void setBoxViewID(String boxViewID) {
        this.boxViewID = boxViewID;
    }

    /**
     * @return the versionID
     */
    public String getVersionID() {
        return versionID;
    }

    /**
     * @param versionID the versionID to set
     */
    public void setVersionID(String versionID) {
        this.versionID = versionID;
    }

    /**
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * @return the isErrorOccured
     */
    public boolean isIsErrorOccured() {
        return isErrorOccured;
    }

    /**
     * @param isErrorOccured the isErrorOccured to set
     */
    public void setIsErrorOccured(boolean isErrorOccured) {
        this.isErrorOccured = isErrorOccured;
    }

}
