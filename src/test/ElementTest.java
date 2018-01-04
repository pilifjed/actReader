import org.junit.Test;
import pack.Element;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ElementTest {

    String name = "Art. 1.";
    char elementType = 'A';
    Element el = new Element(name,elementType);
    Element child = new Element("1.",'U');
    Element child2 = new Element("1)",'P');
    Element child3 = new Element("2.",'U');

    @Test
    public void testConstructor() throws Exception {
        assertEquals(name, el.getName());
        assertEquals(elementType,el.getElementType());
    }

    @Test
    public void testAddChildGetLastChild() throws Exception {
        el.addChild(child);
        assertEquals(child.getName(), el.getLastChild().getName());
    }

    @Test
    public void testGetSetContent() throws Exception {
        el.setContent("el content");
        assertEquals("el content", el.getContent());
    }

    @Test
    public void testGetSetParent() throws Exception {
        child.setParent(el);
        assertEquals(name, child.getParent().getName());
    }

    @Test
    public void testHasChild() throws Exception {
        el.addChild(child);
        assertTrue(el.hasChild());
        assertFalse(child.hasChild());
    }

    @Test
    public void testElement() throws Exception {
        el.setContent("el");

        el.addChild(child);
        child.setContent("child");

        el.addChild(child3);
        child3.setContent("child3");

        child.addChild(child2);
        child2.setContent("child2");

        assertEquals(el.getName() + "\n" +
                "    " + child.getName() + "\n" +
                "        " + child2.getName() + "\n" +
                "    " + child3.getName() + "\n", el.contents());

        assertEquals(el.getName() + "\n" +
                        "    " + el.getContent() + "\n" +
                        "    " + child.getName() + "\n" +
                        "        " + child.getContent() + "\n" +
                        "        " + child2.getName() + "\n" +
                        "            " + child2.getContent() + "\n" +
                        "    " + child3.getName() + "\n" +
                        "        " + child3.getContent() + "\n", el.toString());
    }




}