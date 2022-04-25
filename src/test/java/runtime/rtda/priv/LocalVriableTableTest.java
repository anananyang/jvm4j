package runtime.rtda.priv;


import org.testng.Assert;
import org.testng.annotations.Test;

public class LocalVriableTableTest {

    @Test
    public void test() {
        LocalVariableTable localVariableTable = new LocalVariableTable(100);
        localVariableTable.setInt(0, 100);
        localVariableTable.setInt(1, -100);
        localVariableTable.setLong(2, 2997924580l);
        localVariableTable.setLong(4, -2997924580l);
        localVariableTable.setFloat(6, 3.1415926f);
        localVariableTable.setDouble(7, 2.71828182845);

        Assert.assertEquals(100, localVariableTable.getInt(0));
        Assert.assertEquals(-100, localVariableTable.getInt(1));
        Assert.assertEquals(2997924580l, localVariableTable.getLong(2));
        Assert.assertEquals(-2997924580l, localVariableTable.getLong(4));
        Assert.assertEquals(3.1415926f, localVariableTable.getFloat(6));
        Assert.assertEquals(2.71828182845, localVariableTable.getDouble(7));

    }
}
