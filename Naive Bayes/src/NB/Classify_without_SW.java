package NB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;



public class Classify_without_SW {

	
	
	public void classifier(String SWfile)
	{
		Processing pr=new Processing();
		RemoveSW rw=new RemoveSW();
		rw.remove_sw(SWfile);
		rw.totalwordcount();
		pr.get_conditional_prob_word();
	}
}