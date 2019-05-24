/*
 * Sequence.java
 *
 * Created on 11 janvier 2007, 14:59
 */

/**
 *
 * @author  Pierre Cauchy
 */


public class Sequence 
{
    //instance vars. seq is string represention of DNA seq obtained in Parser
    String seq;
    
    //translation settings
    boolean reverse;
    int readingFrame;
    int start;
    int end;
    int numNs=2;
    String termvector="AAACCACTTT";
    String vector="AAAAAGCA";
    int vectorstart=14;
    int termvectorstart=28;
    int posN;
    int mismatches;
    
    
    
    
    
    //array representation of DNA seq according to settings
    String [] codonSequence;
    
    //codon counter
    int numCodon=0;
    
    /** Creates a new instance of Sequence */
    //constructor. settings passed as params
    public Sequence(String s, boolean b, int i, int a, int e, int c)
    {
        seq=s;
        reverse=b;
        readingFrame=i;
        start = a;
        end = e;
        seq=seq.toUpperCase();
        
        
    }
    
    //alt constructor, if no range specified
    public Sequence(String s, boolean b, int i)
    {
        seq=s;
        reverse=b;
        readingFrame=i;
        start=0;
        end=seq.length();
        seq=seq.toUpperCase();
        
    }
    //alt constructor, if mismatches specified
    public Sequence(String s, boolean b, int i, int a, int e, int c, int m)
    {
        seq=s;
        reverse=b;
        readingFrame=i;
        start = a;
        end = e;
        seq=seq.toUpperCase();
        mismatches=1;
        
    }
    
    
    /* applies settings. moves up/down according to reading frame
     * reverses or not, gets subsequence if desired
     **/
    public void processSequence()
    {
        
            if(!reverse)
            {
                start+=readingFrame-1;
                
            }
        
        
        
        
             
        
        if(reverse)
        {
            String tmp="";
            for(int i=seq.length()-1;i>=0;i--)
            {
                tmp+=seq.charAt(i);
            }
            
            seq=tmp;
            start+=readingFrame-1;
                
            
        }
        
        seq=seq.substring(start,end);
        
    }
    public void weed()
    {
        if(seq.indexOf("N")!=-1)
        {
            //seq=seq.substring(seq.toUpperCase().indexOf(vector)+vectorstart, seq.toUpperCase().indexOf("N", seq.toUpperCase().indexOf(vector)+vectorstart+1));
            int countN=0;
            posN=vectorstart;
            while((countN<numNs)&&((posN=seq.toUpperCase().indexOf("N", posN+1))<seq.length())&&((seq.toUpperCase().indexOf("N", posN+1))>=0))
            {
                countN++;
            }
            try
            {
            //added 09.05.19   
            if(seq.toUpperCase().indexOf(vector)==-1)
            {
                throw new StringIndexOutOfBoundsException();
            }
                
                
                seq=seq.substring(seq.toUpperCase().indexOf(vector)+vectorstart, posN);
            
            
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
// allows 1 mismatch in vector sequence if not found; takes first hit as vector at beginning                
                                                    //final int mismatches = 1;
    final String text = seq;
    final String pattern = vector;    
    int counts=0; 
    for(int iter = 0; iter < text.length() - pattern.length() + 1; iter++)
    {
        int missed = 0;
        int ator = 0;

        do
        {
            if(text.charAt(iter + ator) != pattern.charAt(ator))
            {
                missed++;
            }
        }while(++ator < pattern.length() && missed <= mismatches);

        if(missed <= mismatches)
        {
            System.out.println("Index: " + iter + " Pattern: " + text.substring(iter, iter + pattern.length()));
            counts++;
            if(counts==1)
            {
                try
                {
                seq=seq.substring(iter+vectorstart, posN);
                }
                catch(StringIndexOutOfBoundsException sioobe3)
                {
                    seq=seq.substring(0,seq.length()).toUpperCase();
                }
            }
        }
        //else
        //{
        //    seq=seq.substring(0,posN).toUpperCase();
        //}
    }
                
                
            }
            
        }
        else
        {
            //seq=seq.substring(seq.toUpperCase().indexOf(vector)+vectorstart+readingFrame);
            //int countN=0;
            //int posN=termvectorstart;
            //while((countN<numNs)&&((posN=seq.toUpperCase().indexOf("N", posN+1))<seq.length()))
            //{
                //countN++;
            //}
            posN=seq.length();
            try
            {
                seq=seq.substring(seq.toUpperCase().indexOf(vector)+vectorstart);
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
                seq=seq.substring(0,seq.length()).toUpperCase();
            }
            
        }
        end=seq.length();
        
            
    }
    public void weed5PrimeEnd()
    {
        if(seq.indexOf("N")!=-1)
        {
            //seq=seq.substring(seq.toUpperCase().indexOf(termvector)+termvectorstart, seq.toUpperCase().indexOf("N", seq.toUpperCase().indexOf(termvector)+termvectorstart+1));
            int countN=0;
            posN=termvectorstart;
            while((countN<numNs)&&((posN=seq.toUpperCase().indexOf("N", posN+1))<seq.length()))
            {
                countN++;
            }
            try
            {
                //added 09.05.19   
            if(seq.toUpperCase().indexOf(termvector)==-1)
            {
                throw new StringIndexOutOfBoundsException();
            }

                seq=seq.substring(seq.toUpperCase().indexOf(termvector)+termvectorstart, posN);
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
// allows 1 mismatch in vector sequence if not found; takes first hit as vector at beginning                
                                                    //final int mismatches = 1;
    final String text = seq;
    final String pattern = termvector;    
    int counts=0; 
    for(int iter = 0; iter < text.length() - pattern.length() + 1; iter++)
    {
        int missed = 0;
        int ator = 0;

        do
        {
            if(text.charAt(iter + ator) != pattern.charAt(ator))
            {
                missed++;
            }
        }while(++ator < pattern.length() && missed <= mismatches);

        if(missed <= mismatches)
        {
            System.out.println("Index: " + iter + " Pattern: " + text.substring(iter, iter + pattern.length()));
            counts++;
            if(counts==1)
            {
                try
                {
                seq=seq.substring(iter+termvectorstart, posN);
                }
                catch(StringIndexOutOfBoundsException sioobe3)
                {
                    seq=seq.substring(0,seq.length()).toUpperCase();
                }
            }
        }
        //else
        //{
        //    seq=seq.substring(0,posN).toUpperCase();
        //}
    }
                
                
            }
            
            
            
        }
        else
        {
            try
            {
                seq=seq.substring(seq.toUpperCase().indexOf(termvector)+termvectorstart);
            }
            catch(StringIndexOutOfBoundsException sioobe)
            {
                seq=seq.substring(0,seq.length()).toUpperCase();
            }
            
            
            
        }
        while(seq.length()%3!=0)
        {
            seq=seq.substring(0, seq.length()-1);
        }
        end=seq.length();
        
        
    }
    
    //gets reverse strand, done by replacing A with T, C with G and so on
    //trick used: intermediates so that one doesn't get mixed up with bases
    
    
    public void setSequence(String s)
    {
        seq = s;
    }
    
    
    public void setVector(String s,int i)
    {
        vector=s;
        vectorstart=i;
    }
    
    public void setTermVector(String s,int i)
    {
        termvector=s;
        termvectorstart=i;
    }
    
    public void setNumNs(int i)
    {
        numNs=i;
    }
    
    public void getComplementaryStrand()
    {
        seq=seq.replace('A','U');
        seq=seq.replace('T','A');
        seq=seq.replace('G','F');
        seq=seq.replace('C','G');
        seq=seq.replace('U','T');
        seq=seq.replace('F','C');
        
    }
    
    //calculates and returns the GC content.
    public double getGCContent()
    {
        
        int at=0;
        int gc=0;
        for(int i=0;i<seq.length();i++)
        {
            if(seq.charAt(i)=='G'||seq.charAt(i)=='G')
            {
                gc++;
            }
            
            if(seq.charAt(i)=='A'||seq.charAt(i)=='T')
            {
                at++;
            }
        }
        return 100.0*gc/seq.length();
        
    }
    
    
    
    //calculates and returns the Tm value according to the equation Tm=64.9 +41*(g+c-16.4)/(a+t+g+c)
    public double getTm()
    {
        int a=0;
        int t=0;
        int g=0;
        int c=0;
        
        
        for(int i=0;i<seq.length();i++)
        {
            if(seq.charAt(i)=='A')
            {
                a++;
            }
            if(seq.charAt(i)=='T')
            {
                t++;
            }
            if(seq.charAt(i)=='G')
            {
                g++;
            }
            if(seq.charAt(i)=='C')
            {
                c++;
            }
        }
        
        return 64.9 +41*(g+c-16.4)/(a+t+g+c);
        
    }
    
    //Hybridisation temp. is Tm-5
    public double getHybridisationTemp()
    {
        int a=0;
        int t=0;
        int g=0;
        int c=0;
        
        
        for(int i=0;i<seq.length();i++)
        {
            if(seq.charAt(i)=='A')
            {
                a++;
            }
            if(seq.charAt(i)=='T')
            {
                t++;
            }
            if(seq.charAt(i)=='G')
            {
                g++;
            }
            if(seq.charAt(i)=='C')
            {
                c++;
            }
        }
        
        return 64.9 +41*(g+c-16.4)/(a+t+g+c)-5;
        
    }
    
    
    
    //returns codon representation of the latter, to block capitals
    public String[] getCodonsArray()
    {
        
        codonSequence = new String [seq.length()/3];
        java.util.Arrays.fill(codonSequence,"");
        int numCodons = 0;
        
        //if obtained sequence not divisible by 3 leave the remainder
        for(int i=0;(numCodons<seq.length()/3);i++)
        {
            
            //processes each letter of codon
            if((codonSequence[numCodons]).length()<3)
            {
                codonSequence[numCodons]+=Character.toUpperCase((seq.charAt(i)));
                
                
            }
            else
            {
                //last codon
                if(numCodons+1==seq.length()/3)
                {
                    break;
                }
                
                numCodons++;
                codonSequence[numCodons]+=Character.toUpperCase(seq.charAt(i));
            }
            
        }
        
        //for debugging purposes
        //for(int i= 0;i<seq.length()/3;i++)
        //{
            //System.out.print(codonSequence[i]+ " ");
        //}
        //System.out.println();
        
        return codonSequence;
        
    }
    
    public String getSequence()
    {
        return seq;
    }
    
    public int getPosN()
    {
        
        return posN;
        
    }
    
}
