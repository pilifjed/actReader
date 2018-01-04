package pack;

public class ElementNotFound extends Exception {
    String element;

    public ElementNotFound(String el){
        this.element=el;
    }

    @Override
    public String toString() {
        return "Element \""+ element + "\" not found";
    }
}
