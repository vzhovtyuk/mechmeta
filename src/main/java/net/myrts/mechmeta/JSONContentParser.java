package net.myrts.mechmeta;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Content parser to get configuration from json.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/21/15
 *         Time: 9:35 AM
 */
public class JSONContentParser implements ContentParser {

    @Override
    public Map<String, Object> parseContent(String content) {
            Map<String, Object> configValues = new HashMap<>();

            JSONObject jsonObject = new JSONObject(content);
            for(String key : jsonObject.keySet()) {
                configValues.put(key, jsonObject.get(key));
            }
            return configValues;
        }
}
