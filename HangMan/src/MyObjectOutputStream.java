import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class MyObjectOutputStream extends ObjectOutputStream {
	private static File f;

	public static  MyObjectOutputStream newInstance(File file, OutputStream out)
			throws IOException {
		f = file;
		return new MyObjectOutputStream(out, f);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		if (!f.exists() || (f.exists() && f.length() == 0)) {
			super.writeStreamHeader();
		} else {
			super.reset();
		}

	}

	public MyObjectOutputStream(OutputStream out, File f) throws IOException {
		super(out);
	}

}
