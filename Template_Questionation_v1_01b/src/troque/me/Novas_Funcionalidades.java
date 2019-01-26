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
					//if (text.equals("")) {
					//	text = " ";
					//}
					ar.add(text);
					text = "";
					//isr.skip(1);
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

	// o c�digo abaixo foi disponibilizado pelo site developer.android
	// DEIXEI AQUI S� COMO REGISTRO.
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
	
	public Temas filtrarTemas(ArrayList<String> arr){
		//ArrayList<String> temas = new ArrayList<String>();
		Temas temas = new Temas();
		try{
		for(int i = 0; i<arr.size(); i=i+6){
			String enunciado = arr.get(i);
			String tema = (String) enunciado.subSequence(enunciado.indexOf("[")+1, enunciado.indexOf("]"));	//essa linha faz a limpeza do enunciado da quest�o, retornando apenas o tema
			if(temas.getAr().indexOf(tema)==-1){                                    //essa linha verifica se o tema j� existe na vari�vel temas
				temas.adicionarItem(tema);												//se o tema n�o existir, ele ser� adicionado											
			}															
			temas.adicionarNumRef(temas.getAr().indexOf(tema));	//essa vari�vel ir� armazenar a posi��o da vari�vel temas correspondente a vari�vel b
													//por que essa posi��o? Porque ela � �nica e mais f�cil de processar do que uma String.
													//Assim, cada elemento desta lista ter� um valor relacionado ao tema correspondente.
													//Num contexto geral, essa vari�vel faz a liga��o entre o tema e a quest�o do tema
		}
		}catch(Exception e){
			
		}
		
		return temas;
		
		
		
		//String a = rc.getString(get_EnderecoDaQuestao(i));        //essa linha faz o resgate do enunciado da quest�o
		//String b = (String) a.subSequence(a.indexOf("[")+1, a.indexOf("]"));	//essa linha faz a limpeza do enunciado da quest�o, retornando apenas o tema			
		//if(temas.indexOf(b)==-1){                                    //essa linha verifica se o tema j� existe na vari�vel temas
		//	temas.add(b);												//se o tema n�o existir, ele ser� adicionado											
		//}															
		//conectorAuxiliar.add(temas.indexOf(b));	//essa vari�vel ir� armazenar a posi��o da vari�vel temas correspondente a vari�vel b
												//por que essa posi��o? Porque ela � �nica e mais f�cil de processar do que uma String.
												//Assim, cada elemento desta lista ter� um valor relacionado ao tema correspondente.
												//Num contexto geral, essa vari�vel faz a liga��o entre o tema e a quest�o do tema
	}
	
	public String filtrarImagem(String enunciado){
		try{
			String imagem = (String) enunciado.subSequence(enunciado.indexOf("{")+1, enunciado.indexOf("}"));
			return imagem;
		}catch(Exception ex){
			return "";
		}
	}	
}
