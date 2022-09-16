package git;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Tree {
	public Tree (ArrayList <String> listy) throws NoSuchAlgorithmException, IOException {
		//create string from arraylist create string with new line
		//turn string into parameter
		//create file from sha1
		String fromList = listy.get(0);
		for (int i = 1; i < listy.size(); i ++) {
			fromList += listy.get(i);
		}
		FileWriter fOne = new FileWriter("tree.txt");//output file
		PrintWriter printW = new PrintWriter (fOne);//writing stuff onto fw
		printW.write(fromList);
		if(printW != null) {
		   printW.flush();
		   printW.close();
		}
		Blob b = new Blob ("tree.txt");
		String newFile = b.generateSHA1Hash(fromList);
		FileWriter fTwo = new FileWriter(newFile);//output file
		PrintWriter printW2 = new PrintWriter (fTwo);//writing stuff onto fw
		printW2.write("fromList");
		if(printW2 != null) {
		   printW2.flush();
		   printW2.close();
		}
	}
}
