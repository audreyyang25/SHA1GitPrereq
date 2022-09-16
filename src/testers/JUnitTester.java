package testers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import git.Blob;
import git.Index;

class JUnitTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Path p = Paths.get("junit.txt");
        try {
            Files.writeString(p, "hello my name is idalis", StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Path p = Paths.get("junit.txt");
		Files.deleteIfExists(p);
	}

	boolean createdFile (Blob b) {
		//file is being created, same contents as the original
		//objects folder is being created in init in index
		Path p = Paths.get("junit.txt");
		if (Files.exists(p))
			return true;
		return false;
	}
	
	boolean correctSHA1 (Blob b) throws NoSuchAlgorithmException, IOException {
//		System.out.println (b.generateSHA1Hash ("junit.txt"));
		if (b.generateSHA1Hash ("junit.txt").equalsIgnoreCase("b32e5c40d8f8a5169baa467ddd3ec7252cb03948"))
			return true;
		return false;
	}
	
	boolean correctContent (Blob b) throws IOException {
//		System.out.println (readFile (b.getHash()));
		StringBuilder sb = new StringBuilder();
		sb.append("hello my name is idalis");

		File f = new File("poop.txt");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry e = new ZipEntry("myOwnZippie");
		out.putNextEntry(e);

		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();

		out.close();
		Path audrey = Paths.get(b.getHash());
		Path myOwn = Paths.get("myOwnZippie");
		if (audrey.equals(myOwn))
			return true;
		return false;
	}
	
	
	boolean addDeleteIndex (Index i) throws NoSuchAlgorithmException, IOException {
		boolean passesAll = false;
		/**
		 * CREATING FILES
		 */
		FileWriter fOne = new FileWriter("f1");//output file
		PrintWriter printW = new PrintWriter (fOne);//writing stuff onto fw
		printW.write("hello");
		if(printW != null) {
		   printW.flush();
		   printW.close();
		}
		FileWriter fTwo = new FileWriter("f2");//output file
		PrintWriter printW2 = new PrintWriter (fTwo);//writing stuff onto fw
		printW2.write("goodbye");
		if(printW2 != null) {
		   printW2.flush();
		   printW2.close();
		}
		i.add("f1");
		Path pf1 = Paths.get("./index");
//		String content = Files.readString(pf1);
		i.add("f2");
		String content2 = Files.readString(pf1);
		/**
		 * TESTING ADD INDEX
		 */
		if (content2.substring(0,2).equals("f1"))
			passesAll = true;
		if (content2.substring(5,45).equalsIgnoreCase("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d"))
			passesAll = true;
		if (content2.substring(46,48).equalsIgnoreCase("f2"))
			passesAll = true;
		if (content2.substring(51,91).equalsIgnoreCase("3c8ec4874488f6090a157b014ce3397ca8e06d4f"))
			passesAll = true;
		
		/**
		 * TESTING DELETE INDEX
		 */
		i.remove("f1");
		i.remove("f2");
		String contentAfterDel = Files.readString(pf1);
		if (contentAfterDel.length()>0) {
			passesAll = false;
		}
		return passesAll;
//		Path pf2 = Paths.get("f2");
	}
	
	@Test
	void test() throws NoSuchAlgorithmException, IOException {
		Blob b = new Blob ("junit.txt");
//		fail("Not yet implemented");
		/** BLOB TESTING
		 * 
		 */
		assertTrue ("Failed to create a file", createdFile(b));
		assertTrue ("Incorrect SHA1 name", correctSHA1(b));
		/** INDEX TESTING
		 * make index, add file, create a new file call objects/SHA1 name
		 * assertTrue file.exists
		 */
		Index i = new Index();
		i.init();
		Path p = Paths.get("./objects");
		assertTrue (Files.exists(p));
		Path pp = Paths.get("./index");
		System.out.println (addDeleteIndex(i));
		assertTrue (Files.exists(pp));
		assertTrue ("Did not add and delete correctly from index file", addDeleteIndex(i));
		Path ppp = Paths.get("f1");
		Path pppp = Paths.get("f2");
		assertTrue ("Incorect content in SHA1 file",correctContent(b));
		assertFalse (Files.exists(ppp));
		assertFalse (Files.exists(pppp));
		/**
		 * blob testing content w zip
		 */
	}

}
