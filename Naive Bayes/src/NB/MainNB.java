package NB;

public class MainNB {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String spam_dir=args[0];
		String ham_dir=args[1];
		String test_spam=args[2];
		String test_ham=args[3];
		Classify_without_SW cf=new Classify_without_SW();
		Processing pr=new Processing();
		Readdoc rd=new Readdoc();
		rd.read_dir(spam_dir,ham_dir);
		Processing.classify_spamtestdata(args[2],"spam");
		Processing.classify_hamtestdata(args[3],"ham");
	//	System.out.println("spamdict size :"+Readdoc.spamdict.size());
	//	System.out.println("hamdict size :"+Readdoc.hamdict.size());
		Processing.write_file(0);
		cf.classifier(args[4]);	
	//	System.out.println("Classifying After removing Stop words\n");
	//	System.out.println("spamdict size :"+Readdoc.spamdict.size());
	//	System.out.println("hamdict size :"+Readdoc.hamdict.size());
		Processing.classify_spamtestdata(args[2],"spam");
		Processing.classify_hamtestdata(args[3],"ham");
		Processing.write_file(1);
		
	}
}
