package net.myrts.mechmeta;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/7/15
 *         Time: 5:44 PM
 */
@Config(name = "configClassRenamed", useFullPath = false)
public class ConfigClassNamed {
    private long fieldLongA;
    private char fieldCharA;

    public void setFieldLongA(long fieldLongA) {
        this.fieldLongA = fieldLongA;
    }

    public void setFieldCharA(char fieldCharA) {
        this.fieldCharA = fieldCharA;
    }

    public long getFieldLongA() {
        return fieldLongA;
    }

    public char getFieldCharA() {
        return fieldCharA;
    }
}
