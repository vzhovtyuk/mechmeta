package net.myrts.mechmeta;

/**
 *Factory class for content loader instantiation based on configuration.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/21/15
 *         Time: 9:41 AM
 */
public class ContentLoaderFactory {
    public ContentLoader create() {
        return new ClasspathContentLoader();
    }
}
