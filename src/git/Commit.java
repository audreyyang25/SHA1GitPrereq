package git;

import java.io.File;
import java.io.FileInputStream;
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
	private String SHA1Hash;
	
	public Commit (String treeSHA, String summary1, String author1) throws NoSuchAlgorithmException, IOException {
		nextCommit = null; // do I need both this and parent?
		parent = null;
		pTree = treeSHA;
		summary = summary1;
		author = author1;
		date = this.getDate();
		this.writeFile("commit.txt");
		SHA1Hash = this.generateSHA1Hash("./commit.txt");
		File obj = new File ("./objects");
		obj.mkdir();
		

	}
	
	public String generateSHA1Hash (String filePath) throws IOException, NoSuchAlgorithmException {
		//https://gist.github.com/zeroleaf/6809843
		FileInputStream fileInputStream = new FileInputStream(filePath);
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
		byte[] bytes = new byte[1024];
		// read all file content
		while (digestInputStream.read(bytes) > 0);

		// digest = digestInputStream.getMessageDigest();
		byte[] resultByteArry = digest.digest();
		SHA1Hash = bytesToHexString(resultByteArry);
		return SHA1Hash;
		
	}
	
	private String getDate () {
		Calendar date = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        String strDate = dateFormat.format(date);
        System.out.print(strDate);
        return strDate;
	}
	
	private void writeFile (String fileName) {
		//what is the ptree value -- the hash? or what's stored inside?
		String content = pTree + "\n" + "./" + fileName + "\n" + author + "\n" + date + "\n" + summary;
		 Path p = Paths.get(fileName);
	        try {
	            Files.writeString(p, content, StandardCharsets.ISO_8859_1);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
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
