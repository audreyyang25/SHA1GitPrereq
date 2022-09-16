package testers;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import git.Tree;

class TreeTester {

	@Test
	void testTreeConstructor () throws NoSuchAlgorithmException, IOException {
		ArrayList <String> list = new ArrayList <String> ();
		list.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		list.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		list.add("blob: f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		Tree treee = new Tree (list);
		
		
	}

}
