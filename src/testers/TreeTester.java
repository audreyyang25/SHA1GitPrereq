package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Tree;

class TreeTester {
	private static String actualHash;
	private static String upper;
	private static File actualFile;
	private static File upperFile;

	@BeforeAll
	static void setUp () throws NoSuchAlgorithmException, IOException {
		ArrayList <String> list = new ArrayList <String> ();
		list.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		list.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		list.add("blob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		Tree treee = new Tree (list);
		actualHash = "10eeb6d15e2e46d10aaad8d8791fb82edfa49741";
		upper = actualHash.toUpperCase();
		actualFile = new File ("./objects/" + actualHash);
		
		upperFile = new File ("./objects/" + upper);
	}
	
	@Test
	//you don't have deliminated concatenation
	void testFileNameAccuracy () {
		assertTrue (actualFile.exists()||upperFile.exists());
	}
	
	@Test
	//it shouldn't be a zip file(?)
	void testContents () throws IOException {
		String actualContent = ("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f\nblob : 01d82591292494afd1602d175e165f94992f6f5f\nblob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83\n");
		System.out.print(actualContent);
		String content = this.content("./objects/" + actualHash);
		String upperContent = this.content("./objects/" + upper);
		String written = this.content("./wrContents.txt");
		assertTrue (written.equals(content) || written.equals(upperContent));
	}

	
	private String content (String filepath) throws IOException {
		File file = new File (filepath);
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		StringBuilder sb = new StringBuilder(); 
		String line = br.readLine(); 
		while (line != null) { 
			sb.append(line).append("\n"); 
			line = br.readLine(); 
		} 
		String fileAsString = sb.toString();
		return fileAsString;
	}

}
