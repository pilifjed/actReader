package pack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Character.isWhitespace;

public class preparation {

    public static LinkedList<String> prepareFile(String fileName){
        LinkedList<String> modFile = getFile(fileName);
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
        return modFile;
    }

    public static LinkedList<String> rmTrash(String fileName){
        LinkedList<String> modFile = prepareFile(fileName);
        int i=0;
        while(modFile.get(i)!=modFile.getLast()){
            if(modFile.get(i).matches("(E©.*)|(E{1})")){
                modFile.remove(i);
            }
            else
                i++;
        }
        return modFile;
    }

    private static LinkedList<String> getFile(String fileName){
        LinkedList<String> modFile = new LinkedList<>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                classifyLine(line,modFile);
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Wystapil problem z otwarciem pliku '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Wystapil problem podczas odczytu pliku '" + fileName + "'");
        }
        finally{
            return modFile;
        }
    }

    private static String classifyLine(String line, LinkedList<String> modFile){
        int tmp=0;
        if(line.matches("(^DZIAŁ .*)")){
            tmp = patEnd(line,2);
            modFile.add("D"+line.substring(0,tmp-1));
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

    private static int patEnd(String line, int nb){
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
