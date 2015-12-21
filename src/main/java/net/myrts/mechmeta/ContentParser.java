package net.myrts.mechmeta;

import java.util.Map;

/**
 * Generaic interfact for content parsers.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/21/15
 *         Time: 9:40 AM
 */
public interface ContentParser {
    Map<String, Object> parseContent(String content);
}
