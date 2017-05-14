package br.ucb.joaoiora.model;

import java.io.Serializable;

/**
 * Created by Joao on 13/05/2017.
 */
public class FileObject implements Serializable {

    /**
     *
     */
    private String srcFolder;

    /**
     *
     */
    private String destFolder;

    /**
     *
     */
    private String filename;

    /**
     *
     */
    private byte[] content;

    /**
     *
     */
    public FileObject() {
//        this.setDestFolder(System.getProperty("java.io.tmpdir")); // TODO keep it dynamic
    }

    /**
     *
     * @param srcFolder
     * @param filename
     */
    public FileObject(String srcFolder, String filename) {
        this.setSrcFolder(srcFolder);
        this.setDestFolder(System.getProperty("java.io.tmpdir")); // TODO keep it dynamic
        this.setFilename(filename);
    }

    /**
     *
     * @return
     */
    public String getSrcFolder() {
        return srcFolder;
    }

    /**
     *
     * @param srcFolder
     */
    public void setSrcFolder(String srcFolder) {
        this.srcFolder = srcFolder;
    }

    /**
     *
     * @return
     */
    public String getDestFolder() {
        return destFolder;
    }

    /**
     *
     * @param destFolder
     */
    public void setDestFolder(String destFolder) {
        this.destFolder = destFolder;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     */
    public byte[] getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

}
