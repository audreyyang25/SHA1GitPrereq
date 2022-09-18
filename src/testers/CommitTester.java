package testers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import git.Commit;

public class CommitTester {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		Commit c = new Commit ("./objects/86F7E437FAA5A7FCE15D1DDCB9EAEAEA377667B8.zip", "test", "Audrey", null);
		Commit other = new Commit ("./objects/AAF4C61DDCC5E8A2DABEDE0F3B482CD9AEA9434D.zip", "second commit", "Audrey", c);
		c.writesFileToObjects();
		other.writesFileToObjects();
		System.out.println (other.returnSha());
		
	}

}
