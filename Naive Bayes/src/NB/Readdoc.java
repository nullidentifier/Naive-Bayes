package NB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Readdoc {

	public static Map<String,Integer> spamdict=new Hashtable<>();
	public static Map<String,Integer> hamdict=new Hashtable<>();
	public static Map<String,Double> prob_spamwords=new Hashtable<>();
	public static Map<String,Double> prob_hamwords=new Hashtable<>();
	public static double spamdoccount=0.0,hamdoccount=0.0,totalspamwords=0.0,totalhamwords=0.0;
	public static double spam_prob=0.0,ham_prob=0.0;
	
	
	
	
	public static int createdict(String dir,String type)
	{
		Processing pr=new Processing();
		int count=0,uniquewords=0;
		File terget_dir = new File(dir);
		File[] files = terget_dir.listFiles();		
		Map<String,Integer> dict=new Hashtable<String,Integer>();
		Map<Integer,String> punc=new Hashtable<>();
		//int i=0;
		double words=0.0;
		punc=pr.getpunc_table();
		for (File f : files) 
		{
			count++;
			if(f.isFile()) 
			{
				BufferedReader inputStream = null;
				try
				{
					inputStream = new BufferedReader(
					new FileReader(f));
					String line;
					//String[] value;
					while ((line = inputStream.readLine()) != null)
					{
						//value=line.split("\\s");
						Scanner sc=new Scanner(line);
						int i=0;
						String str;
						while(sc.hasNext())
						{
							str=sc.next();
							if(!punc.containsValue(str))
							{
								words++;
								if(!dict.containsKey(str))
								{
									dict.put(str,1);
									uniquewords++;
								}
								else
								{
									dict.put(str,dict.get(str)+1);
								}			

							}
						}
					}
				}catch(IOException e){
					e.printStackTrace();
				}
				finally
				{
					if (inputStream != null)
					{
						try {
							inputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				//	System.out.println("count is"+count);
				//	System.out.println("no of unique words is:"+uniquewords);
			}
		}
		if(type.equals("spam"))
		{
			spamdict=dict;
			totalspamwords=words;
		}
		else if(type.equals("ham"))
		{
			hamdict=dict;
			totalhamwords=words;
		}
		//	System.out.println("totalspamcount:"+totalspamwords+" hamwords:"+totalhamwords);
		return count;
	}

//calling createdict() for creating dictionary of words for spam and ham training documents
	public void read_dir(String spam_dir,String ham_dir) 
	{
		Processing pr=new Processing();
		double total;
		spamdoccount=createdict(spam_dir,"spam");
		hamdoccount=createdict(ham_dir,"ham");
		//System.out.println("spamdict size :"+Readdoc.spamdict.size());
		//System.out.println("hamdict size :"+Readdoc.hamdict.size());
		total=spamdoccount+hamdoccount;
		spam_prob=pr.get_class_prob(spamdoccount,total);
		ham_prob=pr.get_class_prob(hamdoccount,total);
		pr.get_conditional_prob_word();
	}


}
