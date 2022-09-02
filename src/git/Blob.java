package git;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Blob {
	private static String content;
	private static String hash;
	private static String zipContent;
	
	public Blob (String filePath) throws IOException, NoSuchAlgorithmException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, digest);
        byte[] bytes = new byte[1024];
        // read all file content
        while (digestInputStream.read(bytes) > 0);

//        digest = digestInputStream.getMessageDigest();
        byte[] resultByteArry = digest.digest();
        hash = bytesToHexString(resultByteArry);
        content = Blob.content(filePath);
        Blob.createsNewFile();
        
        String zipFilePath = Blob.zipFile("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test/objects/" +hash);
        zipContent = Blob.content(zipFilePath);
        System.out.println ("Reading contents of " + filePath + ": " + content);
        
        
        System.out.println ("Creating new blob " + hash + " from content: " + zipContent);
        
    }
	
	public String getSHA1Hash () {
		return hash;
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
	
	public static void createsNewFile () throws IOException {
		FileWriter writer = new FileWriter("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test/objects/" +hash);
		writer.write (content);
		writer.close();
	}

	
	public static String zipFile (String sourceFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(sourceFile);
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
        return sourceFile;
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

