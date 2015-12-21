package net.myrts.mechmeta;

/**
 * Factory class for content parser instantiation based on configuration.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/21/15
 *         Time: 9:41 AM
 */
public class ContentParserFactory {
    public ContentParser create() {
        return new JSONContentParser();
    }
}
