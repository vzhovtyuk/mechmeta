package net.myrts.mechmeta;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

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
                final ContentLoaderFactory loaderFactory = new ContentLoaderFactory();
                final ContentLoader contentLoader = loaderFactory.create();
                final String content = contentLoader.loadContent(beanType);

                final ContentParserFactory parserFactory = new ContentParserFactory();
                final ContentParser contentParser = parserFactory.create();
                final Map<String, Object> configValues = contentParser.parseContent(content);

                for (Field field : beanType.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object fieldValue = configValues.get(fieldName);
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

}
