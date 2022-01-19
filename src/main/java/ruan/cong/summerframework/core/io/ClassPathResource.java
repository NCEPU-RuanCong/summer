package ruan.cong.summerframework.core.io;

import java.io.InputStream;
import ruan.cong.summerframework.utils.ClassUtils;

public class ClassPathResource implements Resource{

    private String classpath;

    private ClassLoader classLoader;

    public ClassPathResource(String classpath) {
        this(classpath, (ClassLoader)null);
    }

    public ClassPathResource(String classpath, ClassLoader classLoader) {
        this.classpath = classpath;
        this.classLoader = null == classLoader ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() {
        InputStream is = classLoader.getResourceAsStream(classpath);
        return is;
    }
}
