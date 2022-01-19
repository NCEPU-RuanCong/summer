package ruan.cong.summerframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlResource implements Resource{
    private URL url;

    public UrlResource(URL url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = url.openConnection().getInputStream();
        return inputStream;
    }
}
