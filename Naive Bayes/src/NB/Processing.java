package NB;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Processing extends Readdoc  {

	public static double hamaccuracy=0.0, spamaccuracy=0.0;
	public void get_conditional_prob_word()
	{
		double prob=0.0;
		for( String keystr:spamdict.keySet())
		{
			double wordcount=spamdict.get(keystr);
			prob=(wordcount+1.0)/(totalspamwords+spamdict.size());
			prob=Math.log(prob)/Math.log(2);
			prob_spamwords.put(keystr, prob);
		}
		for( String keystr:hamdict.keySet())
		{
			double wordcount=hamdict.get(keystr);
			prob=(wordcount+1)/(totalhamwords+hamdict.size()); //hamdict.size gives unique words in ham dict
			prob=Math.log(prob)/Math.log(2);
			prob_hamwords.put(keystr, prob);
		}
	}

//get class prob (Nc/N)
	public double get_class_prob(double class_count,double total)
	{
		double prob=0.0;
		prob=Math.log(class_count / total)/Math.log(2);
		return prob;
	}


	public static Map<Integer,String> getpunc_table()
	{
		int i=0;
		Map<Integer,String> punc=new Hashtable<>();
		punc.put(1,",");
		punc.put(2,"\\");
		punc.put(3,"/");
		punc.put(4,".");
		punc.put(5,"*");
		punc.put(6,"!");
		punc.put(7,"?");
		punc.put(8,"'");
		punc.put(9,":");
		punc.put(10,"-");
		punc.put(11,"#");
		punc.put(12,"%");
		punc.put(13,";");
		punc.put(14,"$");
		punc.put(15,"@");
		punc.put(16,"(");
		punc.put(17,")");
		punc.put(18,"_");
		punc.put(19,"+");
		punc.put(20,"<");
		punc.put(21,">");
		punc.put(22,"\"");
		punc.put(23,"&");
		return punc;
	}


	//classify the spamtest data by calclulating its prob
	public static  void classify_spamtestdata(String dir, String type)
	{
		double doccount=0.0;
		double testspamprob=0.0,testhamprob=0.0;
		int count=0,uniquewords=0,spamdoc=0, hamdoc=0;
		File terget_dir = new File(dir);
		File[] files = terget_dir.listFiles();		
		Map<String,Integer> dict=new Hashtable<String,Integer>();
		Map<Integer,String> punc=new Hashtable<>();
		//int i=0;
		double words=0.0;
		punc=getpunc_table();
		for (File f : files) 
		{
			doccount++;
			if(f.isFile()) 
			{
				BufferedReader inputStream = null;
				try
				{
					inputStream = new BufferedReader(new FileReader(f));
					String line;
					 testspamprob=spam_prob;
					 testhamprob=ham_prob;
					while ((line = inputStream.readLine()) != null)
					{
						Scanner sc=new Scanner(line);
						int i=0;
						String str;
						while(sc.hasNext())
						{
							str=sc.next();
							if(!punc.containsValue(str))
							{
								words++;
								if(spamdict.containsKey(str))
								{
									testspamprob+= prob_spamwords.get(str);
								}
								else{
									double p=(1/(totalspamwords+spamdict.size()));
									testspamprob+=Math.log(p)/Math.log(2);
								}
								if(hamdict.containsKey(str))
								{
									testhamprob+=prob_hamwords.get(str);
								}			
								else
								{
									double p=(1/(totalhamwords+hamdict.size()));
									testhamprob+=Math.log(p)/Math.log(2);
								}
							}
						}
					}
				}catch(IOException e){
					e.printStackTrace();
				}
				if(testspamprob>testhamprob)
				{
					spamdoc++;
				}
 			}
		}
		//System.out.println("spamdoc:"+spamdoc+" "+"doccount:"+doccount);
		spamaccuracy=((double)spamdoc/(double)doccount)*100.0;
		//System.out.println("spam accuracy is"+ spamaccuracy);
	}


	public static  void classify_hamtestdata(String dir, String type)
	{
		double doccount=0.0;
		double testspamprob=0.0,testhamprob=0.0; 
		int count=0,uniquewords=0,spamdoc=0, hamdoc=0;
		File terget_dir = new File(dir);
		File[] files = terget_dir.listFiles();		
		Map<String,Integer> dict=new Hashtable<String,Integer>();
		Map<Integer,String> punc=new Hashtable<>();
		double words=0.0;
		punc=getpunc_table();
		for (File f : files) 
		{
			doccount++;
			if(f.isFile()) 
			{
				BufferedReader inputStream = null;
				testspamprob=spam_prob;
			    testhamprob=ham_prob;
				try
				{
					inputStream = new BufferedReader(new FileReader(f));
					String line;
					while ((line = inputStream.readLine()) != null)
					{
						Scanner sc=new Scanner(line);
						String str;
						while(sc.hasNext())
						{
							str=sc.next();
							if(!punc.containsValue(str))
							{
								words++;
								if(spamdict.containsKey(str))
								{
									testspamprob+= prob_spamwords.get(str);
								}
								else
								{
									double p=1/(totalspamwords+spamdict.size());
									testspamprob+=Math.log(p)/Math.log(2);
								}
								if(hamdict.containsKey(str))
								{
									testhamprob+=prob_hamwords.get(str);
								}	
								else
								{
									double p=1/(totalhamwords+hamdict.size());
									testhamprob+=Math.log(p)/Math.log(2);
								}
							}
						}	
					}
				}catch(IOException e){
					e.printStackTrace();
				}
				if(testhamprob>testspamprob)
				{
					hamdoc++;
				}
			}
		}
		//System.out.println("hamdoc:"+hamdoc+" "+"doccount:"+doccount);
		hamaccuracy=((double)hamdoc/(double)doccount)*100.0;
		//System.out.println("ham accuracy is"+ hamaccuracy);
	}
	
	
	public static void write_file(int pos) 
	{    PrintWriter writer;
	BufferedWriter out = null;
	try {
		
		if(pos==0){
		writer = new PrintWriter("Accuracy_Report.txt");
	    writer.println("Accuracy before removing stop words");

	    writer.println("Accuracy of Spam document is :"+spamaccuracy);
	    writer.println("Accuracy of Ham document is :"+hamaccuracy);
	   // writer.println("Accuracy after removing stop words");

	   // writer.println("The second line");
	    
	    writer.close();
		}
		
		else{
		FileWriter fstream = new FileWriter("Accuracy_Report.txt", true);
		out=new BufferedWriter(fstream);
		out.write("\nAccuracy after removing stop words:");
		out.write("\nAccuracy of Spam document is :"+spamaccuracy);
	//	System.out.println("printing---");
		out.write("\nAccuracy of Ham document is :"+hamaccuracy);
		out.close();
		}
		} 
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
