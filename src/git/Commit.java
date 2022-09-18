package git;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Commit {
	private Commit nextCommit;
	private Commit parent;
	private String pTree;
	private String summary;
	private String author;
	private String date;
	private String hash;
	private String content;
	
	public Commit (String treeSHAPath, String summary1, String author1, Commit parent1) throws NoSuchAlgorithmException, IOException {
		nextCommit = null;
		parent = parent1;
		this.connectParent();
		pTree = treeSHAPath;
		summary = summary1;
		author = author1;
		date = this.getDate().substring(0, 10);

	}
	
	//creates a file with these contents
	public File contentOfFile () throws NoSuchAlgorithmException, IOException {
		File f = new File (pTree);
		String pSHA = "";
		String c = "";
		if (parent == null)
			pSHA = "null";
		else
			pSHA = "./objects/" + parent.returnSha();
		if (nextCommit == null)
			c = "null";
		else
			c = "./objects/" + nextCommit.returnSha();
		
		content = f.getCanonicalPath() + "\n" + pSHA + "\n" + c + "\n" + author + "\n" + date + "\n" + summary;
		this.writeFile("commit.txt", content);
		File contentFile = new File ("./commit.txt");
		return contentFile;
	}
	
	public String returnSha () throws NoSuchAlgorithmException, IOException {
		String s = this.generateSHA1Hash(this.contentOfFile().getAbsolutePath());
		return s;
	}
	
	//creates the SHA-named file in objects
	public void writesFileToObjects () throws IOException, NoSuchAlgorithmException {
		this.contentOfFile();
		hash = this.generateSHA1Hash("./commit.txt");
		File obj = new File ("./objects");
		obj.mkdir();
		this.createsNewFile();
		File commitText = new File ("./commit.txt");
		commitText.delete();
	}
	
	//sets the parent's nextCommit to child
	public void setNextCommit (Commit child) throws NoSuchAlgorithmException, IOException {
		nextCommit = child;
		this.contentOfFile();
//		this.writesFileToObjects();
	}
	
	//sets the parent's nextCommit to this Commit
	public boolean connectParent () throws NoSuchAlgorithmException, IOException {
		if (parent != null) {
			parent.setNextCommit(this);
			return true;
		}
		return false;
	}

	//generates SHA1Hash
	private String generateSHA1Hash (String filePath) throws IOException, NoSuchAlgorithmException {
		//https://gist.github.com/zeroleaf/6809843
		FileInputStream fileInputStream = new FileInputStream(filePath);
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
		byte[] bytes = new byte[1024];
		// read all file content
		while (digestInputStream.read(bytes) > 0);

		// digest = digestInputStream.getMessageDigest();
		byte[] resultByteArry = digest.digest();
		hash = bytesToHexString(resultByteArry);
		return hash;
		
	}
//	
	//returns the exact date
	private String getDate () {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(cal.getTime());
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
	
	//names the file 
	private String createsNewFile () throws IOException {
		File f = new File ("objects/" + hash);
		String path = f.getAbsolutePath();
		FileWriter writer = new FileWriter(path);
		
		writer.write (content);
		writer.close();
		return path;
	}
	
	//helper method
	public static String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			int value = b & 0xFF;
			if (value < 16) {
				// if value less than 16, then it's hex String will be only
				// one character, so we need to append a character of '0'
				sb.append("0");
			}
			sb.append(Integer.toHexString(value).toUpperCase());
		}
		return sb.toString();
	}
}
