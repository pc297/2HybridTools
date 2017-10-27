/*
 * MetaParser.java
 *
 * Created on 14 décembre 2006, 16:06
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

/**
 *
 * @author cauchy
 */
public class MetaParser {
    String text;
    int numSeqs=0;
    SeqList seqs;
    Parser myParser;
    /** Creates a new instance of MetaParser */
    public MetaParser(String s) {
        text=s;
        
    }
    public int getNumSeqs()
    {
        for(int i=0;i<text.length();i++)
        {
            if(text.charAt(i)=='>')
            {
                numSeqs++;
            }
        }
        return numSeqs;
        
        
    }
    public SeqList initialiseSeqLists()
    {
        seqs=new SeqList(new String[numSeqs], new String[numSeqs]);
        return seqs;
    }
    public SeqList getSequences()
    {
        
        int listPos=0;
        int stringPos=0;
        int oldPos=0;
        String tmp="";
        while(text.indexOf(">", stringPos)!=-1)
        {
            //System.out.println();
            try
            {
                oldPos=stringPos;
                tmp=text.substring(text.indexOf(">", stringPos), text.indexOf(">", stringPos=text.indexOf(">", stringPos)+1));
            
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
                tmp=text.substring(text.indexOf(">", oldPos));
            }
            myParser= new Parser(tmp);
            seqs.setDef(myParser.getDescription(), listPos);
            seqs.setStr(myParser.getSequenceString(), listPos);
            listPos++;
        }
        return seqs;
    }
    
}
