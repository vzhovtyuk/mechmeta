package net.myrts.mechmeta;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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
        assertEquals("fieldIntA should be configured ", 123, confClass.getFieldIntA().intValue());
    }

    @Test
    public void shouldConfigureRenamedBean() {
        System.setProperty("mechmeta.profile", "configprofile");
        final ConfigClassNamed confClass = new ConfigClassNamed();
        ConfigManager.INSTANCE.configure(confClass);

        assertNotNull("Configured bean should be defined", confClass);
        assertEquals("FieldCharA should be configured ", 'b', confClass.getFieldCharA());
        assertEquals("fieldLongA should be configured ", 32563389, confClass.getFieldLongA());
    }

    @Test(expected = RuntimeException.class)
    public void shouldConfigureBeanWrongProfile() {
        System.setProperty("mechmeta.profile", "config");
        final ConfigClass confClass = new ConfigClass();
        ConfigManager.INSTANCE.configure(confClass);
        fail("exception thrown");
    }
}
