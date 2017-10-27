/*
 * SeqList.java
 *
 * Created on 14 décembre 2006, 15:52
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

/**
 *
 * @author cauchy
 */
public class SeqList {
    String[] defs;
    String[] seqs;
    /** Creates a new instance of SeqList */
    public SeqList(String [] d, String[] s) {
        defs=d;
        seqs=s;
        
    }
    public void setDef(String s,int i)
    {
        defs[i]=s;
    }
    public void setStr(String s,int i)
    {
        seqs[i]=s;
    }
    public String returnDef(int i)
    {
        return defs[i];
    }
    public String returnSeq(int i)
    {
        return seqs[i];
    }
    public String returnFormattedSeq(int i)
    {
        String tmp="";
        for(int j=0;j<seqs[i].length();j++)
        {
            tmp+=seqs[i].substring(j,j+1);
            if(j>0&&(j+1)%100==0)
            {
                tmp+="\r\n";
            }
        }
        
        return tmp;
    }
    public int getNumLines()
    {
        int numLines=0;
        for(int i=0;i<seqs.length;i++)
        {
            numLines+=1+(int)seqs[i].length()/100;
        }
        
        return numLines+seqs.length;
    }
    
    public void addDef(String s)
    {
        String[] tmp = new String[defs.length+1];
        for(int i=0;i<defs.length;i++)
        {
            tmp[i]=defs[i];
        }
        tmp[defs.length]=s;
        defs=tmp;
                
    }
    public void addStr(String s)
    {
        String[] tmp = new String[seqs.length+1];
        for(int i=0;i<seqs.length;i++)
        {
            tmp[i]=seqs[i];
        }
        tmp[seqs.length]=s;
        seqs=tmp;
    }
    public int getNumSeqs()
    {
        return seqs.length;
    }
    public int getNumDefs()
    {
        return defs.length;
    }
    public String[] getSeqs()
    {
        return seqs;
    }
    public String[] getDefs()
    {
        return defs;
    }
    
    
}
