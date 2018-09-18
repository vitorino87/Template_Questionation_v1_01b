package troque.me;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;

public class Novas_Funcionalidades extends Questao {

	// private static final int READ_REQUEST_CODE = 42;
	private static final int PICKFILE_RESULT_CODE = 1;

	public ArrayList<String> realizarLeituraDaQuestao(InputStream file) throws FileNotFoundException, IOException {
		ArrayList<String> ar = new ArrayList<String>();
		try {
			// File file = (file)
			// FileInputStream fis = new FileInputStream("teste.txt");

			Charset cs = Charset.forName("UTF-8");
			InputStreamReader isr = new InputStreamReader(file, cs);
			int ch;
			String text = "";
			while ((ch = isr.read()) != -1) {
				if (ch != 10 && ch != 13) {
					text += String.valueOf((char) ch);
				} else {
					if (text.equals("")) {
						text = " ";
					}
					ar.add(text);
					text = "";
					isr.skip(1);
				}
			}
			file.close();
			isr.close();

		} catch (Exception ex) {

			// JOptionPane.showMessageDialog(null, ex.toString());
		}

		return ar;
	}

	public void performFileSearch() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("file/*");
		startActivityForResult(intent, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				String FilePath = data.getData().getPath();
				try {
					InputStream is = new FileInputStream(FilePath);
					try {
						ar = realizarLeituraDaQuestao(is);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// textFile.setText(FilePath);
			}
			break;

		}

	}

	// o código abaixo foi disponibilizado pelo site developer.android
	// DEIXEI AQUI SÓ COMO REGISTRO.
	// @Override
	// public void onActivityResult(int requestCode, int resultCode,
	// Intent resultData) {

	// The ACTION_OPEN_DOCUMENT intent was sent with the request code
	// READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
	// response to some other intent, and the code below shouldn't run at all.

	// if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK)
	// {
	// The document selected by the user won't be returned in the intent.
	// Instead, a URI to that document will be contained in the return intent
	// provided to this method as a parameter.
	// Pull that URI using resultData.getData().
	// Uri uri = null;
	// if (resultData != null) {
	// uri = resultData.getData();
	// Log.i(TAG, "Uri: " + uri.toString());
	// showImage(uri);
	// }
	// }
	// }
}
