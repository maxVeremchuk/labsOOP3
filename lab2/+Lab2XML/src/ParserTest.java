import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void domParser() {
        Devise dev = Parser.domParser();

        Assert.assertEquals("msg", "1" , dev.getComponent()[0].getId());
        Assert.assertEquals("msg", "4" , dev.getComponent()[1].getId());
        Assert.assertEquals("msg", "2" , dev.getComponent()[2].getId());
        Assert.assertEquals("msg", "3" , dev.getComponent()[3].getId());
    }

    @Test
    void saxParser() {
        Devise dev = Parser.saxParser();

        Assert.assertEquals("msg", "1" , dev.getComponent()[0].getId());
        Assert.assertEquals("msg", "4" , dev.getComponent()[1].getId());
        Assert.assertEquals("msg", "2" , dev.getComponent()[2].getId());
        Assert.assertEquals("msg", "3" , dev.getComponent()[3].getId());
    }

    @Test
    void staxParser() {
        Devise dev = Parser.staxParser();

        Assert.assertEquals("msg", "1" , dev.getComponent()[0].getId());
        Assert.assertEquals("msg", "4" , dev.getComponent()[1].getId());
        Assert.assertEquals("msg", "2" , dev.getComponent()[2].getId());
        Assert.assertEquals("msg", "3" , dev.getComponent()[3].getId());
    }
}