package pack;

import java.util.LinkedList;

import static pack.preparation.*;

public class reader {

    public static void main(String[] args){
        LinkedList<String> file = rmTrash("/Users/filipdej/Desktop/konstytucja.txt");
        for (String element: file){
            System.out.println(element);
        }
    }
}
