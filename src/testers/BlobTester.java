package testers;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import git.Blob;

public class BlobTester {
	public static void main (String [] args) throws IOException, NoSuchAlgorithmException {
		Blob blob = new Blob ("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test/bbb.txt");
		Blob blob2 = new Blob ("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test/someFile.txt");
//		System.out.println (Blob.content("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test/bbb.txt"));
//		System.out.println (blob.getSHA1Hash());
//		Blob.zipFile("/Users/audreyyang/eclipse-workspace/SHA1GitPrereq/test/bbb.txt");
	}
	

}