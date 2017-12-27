package pack;

import java.util.LinkedList;

public class Element {
    private char elementType;
    private String name;
    private String content = "";
    private Element parent;
    private LinkedList<Element> childList = new LinkedList<>();

    public Element(String name, char elementType){
        this.name=name;
        this.elementType=elementType;
    }

    public void addChild(Element child){
        child.setParent(this);
        this.childList.add(child);
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getName(){
        return this.name;
    }
    public String getContent(){
        return this.content;
    }
    public Element getParent(){
        return this.parent;
    }
    public Element setParent(Element parent){
        return this.parent = parent;
    }

    public char getElementType(){
        return this.elementType;
    }

    public Element getLastChild(){
        return childList.getLast();
    }

    public LinkedList<Element> getChildList(){
        return this.childList;
    }
    public Element getFirstChild(){
        return childList.getFirst();
    }

    public Element getChild(String name){
        for (Element child: this.childList) {
            if(child.name==name)
                return child;
        }
        return null;
    }

    public boolean equals(Element other){
        return this.name.equals(other.name) && this.content.equals(other.content);
    }

    public boolean hasChild(){
        return this.childList.size()>0;
    }

    public String toString(){
        return toStringHelper(0);
    }

    private String toStringHelper(int i){
        String toPrint="";
        if(!this.getName().equals(""))
            toPrint += spaces(i) + this.getName() +"\n";
        if(!this.getContent().equals(""))
            toPrint += spaces(i) + "    " + this.getContent()+"\n";
        if(!this.getName().equals("")) {
            for (Element child : this.childList) {
                toPrint += child.toStringHelper(i + 1);
            }
        }
        else{
            for (Element child : this.childList) {
                toPrint += child.toStringHelper(i);
            }
        }
        return toPrint;
    }

    private String spaces(int count){
        String out = "";
        while(count>0){
            out+="    ";
            count--;
        }
        return out;
    }
}
