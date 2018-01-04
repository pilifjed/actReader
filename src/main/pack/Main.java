package pack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        Parser runConf = new Parser(args);
        System.out.print(runConf);
        System.out.println();
        Preparation prep;
        String path=runConf.filePath;
        while(true) {
            try {
                prep = new Preparation(path);
                break;
            } catch (FileNotFoundException ex) {
                System.out.println("FileNotFoundException: " + ex + "\nInsert the proper directory or press ENTER key to finish program.");
                Scanner userInput = new Scanner(System.in);
                path = userInput.nextLine();
                if(path.length()==0)
                    System.exit(1);
            } catch (IOException ex) {
                System.out.println("IOException: " + ex);
                System.exit(1);
            }
        }
        try {
            DocumentTree cst = new DocumentTree(prep.file);
            String element;
            char type;
            for (int i = 0; i < runConf.elementClasses.length(); i++) {
                element = runConf.elementPath.get(i);
                type = runConf.elementClasses.charAt(i);
                if (runConf.elementPath.get(i).contains("-")) {
                    String[] names = runConf.extractNames(i);
                    cst = cst.findRange(names[0], names[1], type);
                } else {
                    cst = cst.findElement(element, type);
                }
            }
            if (runConf.tOfContents)
                System.out.print(cst.contents());
            else
                System.out.print(cst);
        } catch (ElementNotFound ex){
            System.out.print("ElementNotFoundException: " + ex);
            System.exit(1);
        }
    }
}
