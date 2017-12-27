package pack;

import java.util.LinkedList;

import static pack.Preparation.*;

public class Reader {

    public static void main(String[] args){
        LinkedList<String> file = rmTrash("/Users/filipdej/Desktop/uokik.txt");
//        for(String line : file)
//            System.out.println(line);
        DocumentTree cst = new DocumentTree(file);
        //System.out.print(cst);

        System.out.print(cst.findElement("Art. 2.",'A'));
        //System.out.print(cst.mostRight(cst.root, 'R'));
    }
}
