import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import git.Tree;

public class TryingTreeOut {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// TODO Auto-generated method stub
		ArrayList <String> arr = new ArrayList <String> ();
		arr.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		arr.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		arr.add("blob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		Tree t = new Tree (arr);
	}

}
