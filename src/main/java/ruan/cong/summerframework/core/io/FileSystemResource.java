package ruan.cong.summerframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileSystemResource implements Resource{

    private final String filePath;

    private final File file;

    public FileSystemResource(String filePath) {
        file = new File(filePath);
        this.filePath = filePath;
    }

    public FileSystemResource(String filePath, File file) {
        this.filePath = filePath;
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
