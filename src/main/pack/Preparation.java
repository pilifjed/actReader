package pack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.Character.isWhitespace;

//Class that reads file and does a little bit of preprocessing
public class Preparation {

    public LinkedList<String> file;

    public Preparation(String fileName) throws FileNotFoundException, IOException{
        this.file = getFile(fileName);
        this.prepareFile();
        this.rmTrash();
    }

    //Method that gets file
    private LinkedList<String> getFile(String fileName) throws FileNotFoundException, IOException{
        LinkedList<String> modFile = new LinkedList<>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while((line = bufferedReader.readLine()) != null) {
                classifyLine(line,modFile);
            }
            bufferedReader.close();
            return modFile;
        }
        catch(FileNotFoundException ex) {
            throw ex;
        }
        catch(IOException ex) {
            System.out.println("An error occurred while reading file: \"" + fileName + "\"");
            throw ex;
        }
    }
    //Method that removes "-" at the ends of lines ands joins lines of "normal" text.
    private void prepareFile(){
        LinkedList<String> modFile = this.file;
        int i=0;
        while(modFile.get(i)!=modFile.getLast()){
            while(modFile.get(i+1)!=modFile.getLast()){
                if(modFile.get(i).charAt(0)==modFile.get(i+1).charAt(0)) {
                    int t=modFile.get(i).length() - 1;
                    if(modFile.get(i).charAt(t)=='-')
                        modFile.set(i, modFile.get(i).substring(0,t) + modFile.get(i + 1).substring(1));
                    else
                        modFile.set(i, modFile.get(i) + " " + modFile.get(i + 1).substring(1));
                    modFile.remove(i + 1);
                }
                else
                    break;
            }
            i++;
        }
        int s=modFile.size()-1;
        if(modFile.get(s-1).charAt(0)==modFile.get(s).charAt(0)) {
            modFile.set(s - 1, modFile.get(s - 1) + " " + modFile.get(s).substring(1));
            modFile.remove(s);
        }
        this.file = modFile;
    }

    //method that removes thrash data
    private void rmTrash(){
        LinkedList<String> modFile = this.file;
        int i=0;
        while(modFile.get(i)!=modFile.getLast()){
            if(modFile.get(i).matches("(E©.*)|(E{1})")){
                modFile.remove(i);
            }
            else
                i++;
        }
        this.file = modFile;
    }
//method that classifies lines and works for both acts
    private String classifyLine(String line, LinkedList<String> modFile){
        int tmp=0;
        if(line.matches("(^DZIAŁ .*)")){
            tmp = patEnd(line,2);
            modFile.add("D"+line);
        }
        else if(line.matches("(^Rozdział .*)")){
            tmp = patEnd(line,2);
            modFile.add("R"+line.substring(0,tmp+1));
            modFile.add("E"+line.substring(tmp+1));
        }
        else if(line.matches("^[A-Z][A-Z]+.*")){
            modFile.add("T"+line);
        }
        else if(line.matches("(^Art\\. .*)")){
            tmp = patEnd(line,2);
            modFile.add("A"+line.substring(0,tmp+1));
            classifyLine(line.substring(tmp+1),modFile);
        }
        else if(line.matches("(^\\d+[a-z]*\\..*)")){
            tmp = patEnd(line,1);
            modFile.add("U"+line.substring(0,tmp));
            modFile.add("E"+line.substring(tmp+1));
        }
        else if(line.matches("(^\\d+[a-z]*\\).*)")){
            tmp = patEnd(line,1);
            modFile.add("P"+line.substring(0,tmp));
            modFile.add("E"+line.substring(tmp+1));
        }
        else if(line.matches("(^[a-z]+\\).*)")){
            tmp = patEnd(line,1);
            modFile.add("O"+line.substring(0,tmp));
            modFile.add("E"+line.substring(tmp+1));
        }
        else if(line.length()<=1){
            //do nothing
        }
        else{
            modFile.add("E"+line);
        }
        return line;
    }

    //Method that finds specific space in text
    private int patEnd(String line, int nb){
        int i=0;
        while(nb>0){
            if(isWhitespace(line.charAt(i)))
                nb--;
            if(line.length()-1<=i)
                return i;
            i++;
        }
        return i-1;
    }

}
