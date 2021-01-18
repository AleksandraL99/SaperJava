import external_elements.Field;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {

    @Test
    public void testField() {
        Field field = new Field();
        int downloaded_value;
        field.setValue(3);
        downloaded_value = field.getValue();
        Assert.assertEquals(3, downloaded_value);
    }
}