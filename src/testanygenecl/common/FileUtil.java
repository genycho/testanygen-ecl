package testanygenecl.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
	
//	if (!new File(dbFilePath).exists()) {
//		InputStream is = this.getClass().getClassLoader()
//				.getResourceAsStream(TestopiaConstants.TEMPLATE_DB_FILENAME);
//			FileUtil.copyFile(is, dbFilePath);
//	}

	public static boolean copyFile(InputStream inFile, String to)
			throws IOException {
		InputStream in = null;
		OutputStream out = null;
		in = new BufferedInputStream(inFile);
		OutputStream outFile = new FileOutputStream(to);
		out = new BufferedOutputStream(outFile);
		while (true) {
			int data = in.read();
			if (data == -1)
				break;
			out.write(data);
		}
		out.flush();
		out.close();
		return true;
	}

}
