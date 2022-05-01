package runtime.rtda.priv;

import org.testng.Assert;
import org.testng.annotations.Test;

public class OperantStackTest {

    @Test
    public void test() {
        OperandStack operandStack = new OperandStack(100);
        operandStack.pushInt(100);
        operandStack.pushInt(-100);
        operandStack.pushLong(2997924580l);
        operandStack.pushLong(-2997924580l);
        operandStack.pushFloat(3.1415926f);
        operandStack.pushDouble(2.71828182845);

        Assert.assertEquals(2.71828182845, operandStack.popDouble());
        Assert.assertEquals(3.1415926f, operandStack.popFloat());
        Assert.assertEquals(-2997924580l, operandStack.popLong());
        Assert.assertEquals(2997924580l, operandStack.popLong());
        Assert.assertEquals(-100, operandStack.popInt());
        Assert.assertEquals(100, operandStack.popInt());

    }
}
