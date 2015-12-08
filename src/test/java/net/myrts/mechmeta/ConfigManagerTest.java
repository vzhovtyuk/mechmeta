package net.myrts.mechmeta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/7/15
 *         Time: 5:37 PM
 */
public class ConfigManagerTest {
    @Test
    public void shouldConfigureBean() {
        System.setProperty("mechmeta.profile", "configprofile");
        final ConfigClass confClass = new ConfigClass();
        ConfigManager.INSTANCE.configure(confClass);

        assertNotNull("Configured bean should be defined", confClass);
        assertEquals("FieldA should be configured ", "fieldAValue", confClass.getFieldA());

    }
}
