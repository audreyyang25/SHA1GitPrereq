package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import git.Tree;

class TreeTester {
	private static String actualHash;
	private static String upper;
	private static File actualFile;
	private static File upperFile;

	//I'm testing it as a zip because your code is producing a zip -- it might be better not to use the Blob class and not zip it?
	//these comments are mainly for myself to figure stuff out so you don't have to read them :)
	@BeforeAll
	static void setUp () throws NoSuchAlgorithmException, IOException {
		ArrayList <String> list = new ArrayList <String> ();
		list.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		list.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		list.add("blob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		Tree treee = new Tree (list);
		actualHash = "10eeb6d15e2e46d10aaad8d8791fb82edfa49741";
		upper = actualHash.toUpperCase();
		actualFile = new File ("./objects/" + actualHash + ".zip");
		upperFile = new File ("./objects/" + upper + ".zip");
	}
	
	//your code is failing all the tests because your Tree constructor throws an error in the setUp
	@Test
	void testFileCreation () {
		File obj = new File ("./objects");
		assertTrue (obj.exists());
	}
	
	@Test
	//you don't have deliminated concatenation
	void testFileNameAccuracy () {
		assertTrue (actualFile.exists()||upperFile.exists());
	}

	@Test
	//comparing zipped contents
	void testContents () throws IOException {
		String actualContent = ("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f\nblob : 01d82591292494afd1602d175e165f94992f6f5f\nblob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83\n");
		this.writeFile("./actualContent", actualContent);
		this.zipFile("./actualContent");
		String content = "";
		if (actualFile.exists())
			content = this.content("./objects/" + actualHash + ".zip");
		else
			content = this.content("./objects/" + upper + ".zip");
		String written = this.content("./actualContent.zip");
		File actual = new File ("./actualContent");
		actual.delete();
		File actualZip = new File ("./actualContent.zip");
		actualZip.delete();
		assertTrue (written.equals(content));
	}
	
	@AfterAll
	static void cleanUp () {
		actualFile.delete();
		upperFile.delete();
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
	
	//writes String into file
		private void writeFile (String fileName, String content) throws IOException {
			
			 Path p = Paths.get(fileName);
		        try {
		            Files.writeString(p, content, StandardCharsets.ISO_8859_1);
		        } catch (IOException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
		}

	// converts file to zipped format
	// opens unzipped file in the objects folder
	private String zipFile (String sourceFile) throws IOException {
		File f = new File (sourceFile);
		String zipFile = sourceFile + ".zip";
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		File fileToZip = new File(sourceFile);
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
		zipOut.putNextEntry(zipEntry);
		byte[] bytes = new byte[1024];
		int length;
		while((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		zipOut.close();
		fis.close();
		fos.close();
		f.delete();
		return zipFile;
	}

}
