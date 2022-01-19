package ruan.cong.summerframework.core.io;

public interface ResourceLoader {

    String CLASS_PATH_URL = "classpath:";

    Resource getResource(String location);
}
