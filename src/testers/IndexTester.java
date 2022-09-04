package testers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import git.Index;

public class IndexTester {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub
		Index git = new Index();
		git.init();
		
		git.add("someFile");
		
//		git.add("aaa");
//		git.add("bbb");
//		
//		git.remove("someFile");
	}

}
