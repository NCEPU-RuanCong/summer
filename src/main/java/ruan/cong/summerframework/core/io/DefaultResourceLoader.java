package ruan.cong.summerframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;
import ruan.cong.summerframework.beans.factory.exception.ResourceException;
import ruan.cong.summerframework.utils.StringUtils;

public class DefaultResourceLoader implements ResourceLoader{

    @Override
    public Resource getResource(String location) {
        if (StringUtils.isEmpty(location)){
            throw new ResourceException("empty resource path");
        }
        if (location.startsWith(CLASS_PATH_URL)) {
            return new ClassPathResource(location.substring(CLASS_PATH_URL.length()));
        }
        try{
            URL url = new URL(location);
            return new UrlResource(url);
        } catch (MalformedURLException e) {
            throw new ResourceException("no such url resource, location :" + location);
        }
    }
}
