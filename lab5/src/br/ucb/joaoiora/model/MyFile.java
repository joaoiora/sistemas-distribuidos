package br.ucb.joaoiora.model;

/**
 * Created by joaocarlos on 08/05/17.
 */
public class MyFile {

    private String filename;
    private String sourcePath;
    private String destinationPath;
    private byte[] content;

    public MyFile(String filename, String sourcePath) {
        this.filename = filename;
        this.sourcePath = sourcePath;
    }

    public String getFilename() {
        return filename;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
