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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

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
	
//	boolean correctContent (Blob b) throws IOException {
////		System.out.println (readFile (b.getHash()));
//		Path p = Paths.get(b.getHash());
////		p.toAbsolutePath()
////		System.out.println(p.toAbsolutePath());
////		String pathie = "/Users/idalis/eclipse-workspace/SHA1GitPrereq/B32E5C40D8F8A5169BAA467DDD3EC7252CB03948";
//		String s = b.content("B32E5C40D8F8A5169BAA467DDD3EC7252CB03948");
//
//		if (s.equals("hello my name is idalis"))
//			return true;
//		return false;
//	}

	
	@Test
	void test() throws NoSuchAlgorithmException, IOException {
		Blob b = new Blob ("junit.txt");
//		fail("Not yet implemented");
		/** BLOB TESTING
		 * 
		 */
		assertTrue ("Failed to create a file", createdFile(b));
		assertTrue ("Incorrect SHA1 name", correctSHA1(b));
//		assertTrue ("Incorect content in SHA1 file",correctContent(b));
		/** INDEX TESTING
		 * make index, add file, create a new file call objects/SHA1 name
		 * assertTrue file.exists
		 */
		Index i = new Index();
		i.init();
		Path p = Paths.get("./objects");
		assertTrue (Files.exists(p));
		assertTrue ("Incorrect SHA1 name", correctSHA1(b));
	}

}
