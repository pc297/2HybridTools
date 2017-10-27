import java.text.*;

public class GFormat
//a class holding static methods for 
//formatting purposes
{
	public static String padRight(String s, int n)
	//if s has fewer than n characters then 
	//adds enough spaces to the right of s to make up to n
	{
		String ns =s;
		while (ns.length()<n)
			ns=ns+' ';
		return ns;
	}
	
	public static String padLeft(String s, int n)	
	//if s has fewer than n characters then 
	//adds enough spaces to the left of s to make up to n
	{
		String ns =s;
		while (ns.length()<n)
			ns=' '+ns;
		return ns;
	}
	
	public static String fixDecimalPlaces(double d, int n)
	//returns a String representation of d rounded to 
	//n decimal places.  If necessary 0's are added on right
	{
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(n);
		nf.setMinimumFractionDigits(n);
		return nf.format(d);
	}
	
}