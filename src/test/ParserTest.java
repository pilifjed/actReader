import org.junit.Test;
import pack.Parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserTest {

    @Test
    public void testParser() throws Exception {
        String[] tests = new String[8];
        tests[0]="-p";
        tests[1]="/Users/filipdej/Desktop/konstytucja.txt";
        tests[2]="-c";
        tests[3]="-e";
        tests[4]="DZIAŁ";
        tests[5]="I/Rozdział";
        tests[6]="1/TYTUŁ/Art.";
        tests[7]="1./1.-3./1)/a)";
        Parser p1 = new Parser(tests);
        String names[]= new String[]{"1.", "3."}; int index=4;

        assertEquals("/Users/filipdej/Desktop/konstytucja.txt",p1.filePath);
        assertTrue(p1.tOfContents);
        assertEquals("[DZIAŁ I, Rozdział 1, TYTUŁ, Art. 1., 1.-3., 1), a)]",p1.elementPath.toString());
        assertEquals("DRTAUPO",p1.elementClasses);
        assertArrayEquals(p1.extractNames(index),names);
    }
}