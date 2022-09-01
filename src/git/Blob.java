package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
	private static String content;
	
	public Blob (String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        // read all file content
        while (digestInputStream.read(bytes) > 0);

//        digest = digestInputStream.getMessageDigest();
        byte[] resultByteArry = digest.digest();
        String hash = bytesToHexString(resultByteArry);
        
        content = Blob.content(filePath);
        
        System.out.println ("Reading contents of " + filePath + ": " + content);
        
        
        System.out.println ("Creating new blob " + hash + " from content: " + content);
////        Blob.createsNewFile(hash);
    }
	
	public static String content (String filepath) throws IOException {
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
	
	public static void createsNewFile (String hash) throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File ("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test");
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println (content);
		writer.close();
	}
	
	private static File File(String hash) {
		// TODO Auto-generated method stub
		return null;
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

