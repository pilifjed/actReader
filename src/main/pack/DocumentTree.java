package pack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DocumentTree {
    public Element root;
    private Element lastAccessed;
    private String hierarchy = "WDRTAUPOE"; //CałyDokument,Dzial,Rozdzial,Tytul,Artykul,Ustep,Punkt,Podpunkt,Tresc
    private ArrLinkList hierGroups = new ArrLinkList(9);

    public DocumentTree(LinkedList<String> file){
        char controller=file.getFirst().charAt(0);
        String data= file.getFirst().substring(1);
        if(controller!=hierarchy.charAt(0)){
            this.root = new Element("",hierarchy.charAt(0));
            Element parent = this.root;
            Element toInsert;
            for(int i=1; hierarchy.charAt(i)!=controller; i++){
                toInsert = new Element("",hierarchy.charAt(i));
                parent.addChild(toInsert);
                parent=toInsert;
            }
            toInsert = new Element(data,controller);
            parent.addChild(toInsert);
            this.lastAccessed=toInsert;
        }
        else{
            this.root = new Element(data,controller);
            this.hierGroups.add(importance(this.root.getElementType()),this.root);
            this.lastAccessed=this.root;
        }

        file.removeFirst();
        for(String line : file){
            this.insertElement(line);
        }
    }
    private DocumentTree(Element root, ArrLinkList hierGroups){
        this.root=root;
        this.hierGroups=hierGroups;
    }

    public DocumentTree subtree(Element root){
        return new DocumentTree(root,this.hierGroups);
    }

    private int importance(char toCheck){
        return this.hierarchy.indexOf(toCheck);
    }

    private void insertElement(String line){
        char controller = line.charAt(0);
        String data = line.substring(1);
        Element toInsert = new Element(data,controller);
        if(controller=='E'){
            this.lastAccessed.setContent(data);
        }
        else{
            while(importance(this.lastAccessed.getElementType())>=importance(controller)) {
                this.lastAccessed = lastAccessed.getParent();
                //System.out.println(lastAccessed.getElementType());
            }
            this.lastAccessed.addChild(toInsert);
            this.hierGroups.add(importance(toInsert.getElementType()),toInsert);
            this.lastAccessed=lastAccessed.getLastChild();
            //System.out.println(lastAccessed.getParent().getElementType() + "->" + lastAccessed.getElementType());
        }
    }

    public DocumentTree findElement(String name, char type){
        int imp = importance(type);
        int from = this.hierGroups.getList(this.importance(type)).indexOf(this.mostLeft(this.root,type));
        int to = this.hierGroups.getList(this.importance(type)).indexOf(this.mostRight(this.root,type));
        List<Element> listToSearch = this.hierGroups.getList(imp);
        for(int i = from;i<=to ;i++){
            if(listToSearch.get(i).getName().contains(name))
                return this.subtree(listToSearch.get(i));
        }
        return null;
    }

    public Element mostLeft(Element temp, char type){
        if(temp.getElementType()==type)
            return temp;
        else{
            if(temp.hasChild()){
                Element prev = null;
                for (Element child:temp.getChildList()) {
                    prev = mostLeft(child,type);
                    if(prev!=null)
                        break;
                }
                return prev;
            }
            return null;
        }
    }
    public Element mostRight(Element temp, char type){
        if(temp.getElementType()==type)
            return temp;
        else{
            if(temp.hasChild()){
                Element prev = null;
                for (int i=temp.getChildList().size()-1;i>=0;i--){
                    prev = mostRight(temp.getChildList().get(i),type);
                    if(prev!=null)
                        break;
                }
                return prev;
            }
            return null;
        }
    }

    public String toString() {
        return this.root.toString();
    }
}
