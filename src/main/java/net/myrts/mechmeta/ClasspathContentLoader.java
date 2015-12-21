package net.myrts.mechmeta;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/21/15
 *         Time: 9:35 AM
 */
public class ClasspathContentLoader implements ContentLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathContentLoader.class);

    /**
     * System property name defining the name of profile to load.
     */
    public static final String MECHMETA_PROFILE = "mechmeta.profile";


    @Override
    public String loadContent(Class beanType) {
        final File file = getFile(beanType);
        if (file == null) {
            throw new IllegalArgumentException("unable to find file " + beanType);
        }
        if (!file.exists()) {
            throw new IllegalArgumentException("file does not exists " + beanType);
        }
        return getContent(file);
    }

    private String getContent(File file) {
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException("Unable to configure: " + e.getMessage(), e);
        }
    }

    private File getFile(Class beanType) {
        Config config = (Config) beanType.getAnnotation(Config.class);
        String filePath = beanType.getSimpleName();
        if (StringUtils.isNotEmpty(config.name())) {
            filePath = config.name();
        }
        if (config.useFullPath()) {
            String className = beanType.getName();
            String[] strings = className.split("\\.");
            StringBuffer sb = new StringBuffer(className.length());
            for (int indx = 0; indx < strings.length - 1; indx++) {
                sb.append(strings[indx].toLowerCase()).append(File.separator);
            }
            filePath = sb.toString() + filePath;
        }
        final ClassLoader myLoader = getClass().getClassLoader();
        final URL url = myLoader.getResource(prepareResourceFileName(filePath));
        return url == null ? null : new File(url.getFile());
    }

    private String prepareResourceFileName(String className) {
        return System.getProperty(MECHMETA_PROFILE, "")
                + File.separator
                + toCamelCase(className) + ".json";
    }

    private String toCamelCase(String className) {
        String[] strings = className.split("\\.");
        StringBuilder sb = new StringBuilder();
        final String simpleClassName = strings[strings.length - 1];
        final char firstLetter = simpleClassName.toLowerCase().charAt(0);
        return sb.append(firstLetter).append(simpleClassName.substring(1)).toString();
    }
}
