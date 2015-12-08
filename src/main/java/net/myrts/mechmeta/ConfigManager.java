package net.myrts.mechmeta;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * Configuration manager singleton.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/7/15
 *         Time: 5:37 PM
 */
public enum ConfigManager {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);
    /**
     * System property name defining the name of profile to load.
     */
    public static final String MECHMETA_PROFILE = "mechmeta.profile";

    /**
     * Configure passed bean object with values from configuration source.
     *
     * @param o object to configure
     */
    public void configure(Object o) {
        if (o == null) {
            throw new IllegalArgumentException("Object to configure is undefined");
        }
        Class beanType = o.getClass();
        Config config = (Config) beanType.getAnnotation(Config.class);
        if (config != null) {
            try {
                //TODO extract get content to SourceLoader
                final File file = getFile(beanType);
                if (file == null) {
                    throw new IllegalArgumentException("unable to find file " + beanType);
                }
                if(!file.exists()) {
                    throw new IllegalArgumentException("file does not exists " + beanType);
                }
                //TODO extract get content to SourceLoader
                final String content = getContent(file);
                //TODO to SourceParser
                JSONObject jsonObject = new JSONObject(content);
                for (Field field : beanType.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object fieldValue = jsonObject.get(fieldName);
                    BeanUtils.setProperty(o, fieldName, fieldValue);
                }
            } catch (InvocationTargetException e) {
                LOGGER.error("Failed to invoke " + e.getMessage(), e);
                throw new RuntimeException("Unable to configure: " + e.getMessage(), e);
            } catch (IllegalAccessException e) {
                LOGGER.error("Failed to access " + e.getMessage(), e);
                throw new RuntimeException("Unable to configure: " + e.getMessage(), e);
            }
        }
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
            String className  = beanType.getName();
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
