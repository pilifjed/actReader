package pack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrLinkList {
    private ArrayList<List<Element>> list = new ArrayList<>();

    public ArrLinkList(int size){
        for(int i=0;i<size;i++){
            List<Element> sublist = new LinkedList<>();
            this.list.add(i,sublist);
        }
    }

    public void add(int where,Element what){
        this.list.get(where).add(what);
    }

    public List<Element> getList(int where){
        return this.list.get(where);
    }

}
