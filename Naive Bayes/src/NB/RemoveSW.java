package NB;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class RemoveSW extends Readdoc{

	public static double spam_prob_wsw=0.0, ham_prob_wsw=0.0; 
	int total_spam_words=0, total_ham_words=0;
	
	public void remove_sw(String SWfile)
	{
		//int i=0;
		double words=0.0;
		BufferedReader inputStream = null;

		try
		{
			inputStream = new BufferedReader(
					new FileReader(SWfile));
			String line;
			while ((line = inputStream.readLine()) != null)
			{
				Scanner sc=new Scanner(line);
				int i=0;
				String str;
				while(sc.hasNext())
				{
					str=sc.next();
					
						if(spamdict.containsKey(str))
						{
							spamdict.remove(str);
						}
						if(hamdict.containsKey(str))
						{
							hamdict.remove(str);
						}			

					}
				}
			////System.out.println("spam dict without stop words:"+spamdict.size());
		//	System.out.println("ham dict without stop words:"+hamdict.size());
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public int totalwordcount()
	{
		totalspamwords=0.0;
		totalhamwords=0.0;
		for( String key:spamdict.keySet())
		{
			totalspamwords+= spamdict.get(key);

		}
		for( String key:hamdict.keySet())
		{
			totalhamwords+= hamdict.get(key);

		}
		return 0;
		
	}
	
	
	
}
