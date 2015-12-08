package net.myrts.mechmeta;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 12/7/15
 *         Time: 5:44 PM
 */
@Config
public class ConfigClass {
    private String fieldA;
    private Integer fieldIntA;

    public String getFieldA() {
        return fieldA;
    }

    public void setFieldA(String fieldA) {
        this.fieldA = fieldA;
    }

    public Integer getFieldIntA() {
        return fieldIntA;
    }

    public void setFieldIntA(Integer fieldIntA) {
        this.fieldIntA = fieldIntA;
    }
}
