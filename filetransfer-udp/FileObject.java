import java.io.*;

public class FileObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String filename;
    private String srcFolder;
    private String dstFolder;
    private byte[] content;

    public FileObject() {

    }

    public FileObject(String filename, String srcFolder, String dstFolder) {
        this.filename = filename;
        this.srcFolder = srcFolder;
        this.dstFolder = dstFolder;
    }

    public String getFilename() {
        return filename;
    }

    public String getSourceFolder() {
        return srcFolder;
    }

    public String getDestinationFolder() {
        return dstFolder;
    }

    public byte[] getFileContent() {
        return content;
    }

    public void setFileContent(byte[] content) {
        this.content = content;
    }

}