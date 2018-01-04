package pack;

import java.util.LinkedList;
import java.util.List;

public class Parser {
    public final boolean tOfContents;
    public final List<String> elementPath;
    public final String elementClasses;
    public final String filePath;

    public Parser(String[] args){
        boolean toc = false; List<String> ep = new LinkedList<>(); String fp ="No file name specified!";
        for(int i=0;i<args.length;i++){
            if(args[i].charAt(0)=='-'){
                switch(args[i].charAt(1)){
                    case 'c':
                        toc=true;
                        break;
                    case 'p':
                        i++;
                        fp = args[i];
                        break;
                    case 'e':
                        String path="";
                        for(i+=1;i<args.length && args[i].charAt(0)!='-';i++){
                            path+= " " + args[i];
                        }
                        i--;
                        ep = convElPath(path);
                        break;
                    case 'h':
                        System.out.print(   "SYNOPSIS\nmain -p path/to/file [-e example/expression ][-c][-h]|| -h \n\n" +
                                            "DESCRIPTION\n" +
                                            "-p Sets file path\n" +
                                            "-e Allows to choose which document element should be printed. "+
                                            "By default is set to print whole document\n" +
                                            "   Expression syntax:\n" +
                                            "      DZIAŁ I/Rozdział 1/Art. 1./1./1)/a)\n" +
                                            "      WARNING! Check if used numerals are correct. The usage of roman and arabic numerals differs!\n" +
                                            "      You can also search for range of elements. To do that simply put \"-\" sign between two limit values\n" +
                                            "      Examples:\n" +
                                            "         Art. 1.-Art. 3. will print articles from one to three inclusive\n" +
                                            "         Art. 1./2.-3.   will print range of points from article\n" +
                                            "-c Switches to table of contents mode\n-h Prints help.\n\n");
                        if(i==0)
                            System.exit(0);
                        break;
                }
            }
        }
        this.tOfContents=toc;
        this.elementPath=ep;
        this.filePath=fp;
        this.elementClasses=setElementClasses();
    }

    private List<String> convElPath(String toConvert){
        List <String> out=new LinkedList<>(); String container="";
        for(int j=1;j<toConvert.length();j++){
            if(toConvert.charAt(j)!='/'){
                container+=toConvert.charAt(j);
            }
            else{
                out.add(container);
                container="";
            }
        }
        out.add(container);
        return out;
    }

    private String setElementClasses(){
        String out ="";
        for(int i=0;i<this.elementPath.size();i++){
            out+=classify(this.elementPath.get(i));
        }
        return out;
    }

    //classifies given argument
    private char classify(String toClassify){
        if(toClassify.matches("(^DZIAŁ .*)")){
            return 'D';
        }
        if(toClassify.matches("(^Rozdział .*)")){
            return 'R';
        }
        if(toClassify.matches("^[A-Z][A-Z]+.*")){
            return 'T';
        }
        if(toClassify.matches("(^Art\\. .*)")){
            return 'A';
        }
        if(toClassify.matches("(^\\d+[a-z]*\\..*)")){
            return 'U';
        }
        if(toClassify.matches("(^\\d+[a-z]*\\).*)")){
            return 'P';
        }
        if(toClassify.matches("(^[a-z]+\\).*)")){
            return 'O';
        }
        return 'E';
    }

    // unpacks "a-b" to [a,b]
    public String[] extractNames(int index){
        String[] names = new String[2];
        String raw = this.elementPath.get(index);
        int split =raw.indexOf('-');
        names[0]=raw.substring(0,split);
        names[1]=raw.substring(split+1,raw.length());
        return names;
    }

    @Override
    public String toString() {
        return "Content mode: "+this.tOfContents +"\n"+"File path: "+this.filePath +"\n"+"Element path: "+this.elementPath+"\n";
    }
}
