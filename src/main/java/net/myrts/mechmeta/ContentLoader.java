package net.myrts.mechmeta;

/**
 * Generic interface for content loaders.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/21/15
 *         Time: 9:41 AM
 */
public interface ContentLoader {
    String loadContent(Class beanType);
}
