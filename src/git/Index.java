package git;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Index {
	private File index;
	private File obj;
	private HashMap <String, String> txtMap;
	private String hash;

	// does it have to be saved in a test folder?
	public Index () {
		File test = new File ("./test");
		test.mkdir();
	}


	public void init () {
		index = new File ("./test/index");
		obj = new File ("./test/objects");
		obj.mkdir(); 
		txtMap = new HashMap <String, String> ();
	}

	//are we assuming that the file is already in test?
	public void add (String fileName) throws NoSuchAlgorithmException, IOException {
		Blob blob = new Blob ("./test/" + fileName);
		hash = blob.getHash();
		txtMap.put (fileName, hash);

		//is it ok to rewrite the index file each time, or should it be adding on to previous?
		this.updateIndex();
	}

	public void updateIndex () throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter(index));

		// iterate map entries
		for (Map.Entry<String, String> entry :
			txtMap.entrySet()) {

			// put key and value separated by a colon
			bf.write(entry.getKey() + " : "
					+ entry.getValue());

			// new line
			bf.newLine();
		}
		bf.close();
	}
	
	public void remove (String fileName) throws IOException, NoSuchAlgorithmException {
		File f = new File ("test/objects/"+txtMap.get(fileName)+".zip");
		f.delete();
		txtMap.remove(fileName);
		this.updateIndex();
	}
}


