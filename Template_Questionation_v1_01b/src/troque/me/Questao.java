package troque.me;

import troque.me.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.res.Resources;

import android.graphics.Color;
import android.net.Uri;

///////////////////////////////////////// IN�CIO DA CLASSE ///////////////////////////////////////////////////////////////////////////////////////////////////	

public class Questao extends QuestaoConector
		implements OnItemSelectedListener, OnTouchListener, OnGestureListener, OnDoubleTapListener {

	///////////////////////////////// VARI�VEIS P�BLICAS
	///////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////

	// vari�vel utilizada para trabalhar com o m�todo checked
	private int z = -1;

	// vari�vel para trabalhar com o m�todo embaralhar
	// private int[] w = new int[h];

	// Vari�veis para tratar com os objetos da tela2
	TextView tv;
	RadioButton[] rd = new RadioButton[5];
	Button btnQuestoesErradas, btnChecar, btnBuscar, btnAnterior, btnProximo, btnSortear, btnTema;
	EditText txtBuscar, txtBuscar2;
	Spinner btnSpinner;
	ImageView imageView;
	Chronometer chrono;

	// vari�veis para impedir que o random repita n�meros
	//private int f = 0;

	// vari�vel global para auxiliar contagem das quest�es tem�ticas
	private int auxiliarTema = 0;

	// Essas vari�veis s�o utilizadas para trabalhar com o Spinner
	List<String> temas;
	List<Integer> conectorAuxiliar;
	List<Integer> questoesTematicas;
	LinearLayout linearLayout3, linearLayout2, linearLayout1;

	// vari�vel auxiliar para poder embaralhar as alternativas
	private List<Integer> auxiliarEmbaralharAlternativas = null;

	// vari�vel de recursos
	Resources rc;

	// private static final String TAG = "Gestures";
	GestureDetector gestureDetector;

	ScrollView sv;

	File file;
	InputStream is;
	ArrayList<String> ar;
	int resp = -1;

	// vari�vel para manter o path do arquivo
	String pathFile = "";
		
	//vari�vel para trabalhar com as quest�es erradas
	List<Integer> questoesErradas;
		
	//vari�vel global para trabalhar como index com as questoes erradas
	int indexQuestoesErradas=0;
			
	//vari�vel global tempor�ria para armazenar z
	int zTemp;
		
	//variavel global para verificar se o bot�o sim/n�o do dialog questoes erradas foi clicado 
	boolean zQuestaoErradas = false;
	
	///////////////////////////////// FIM DAS VARI�VEIS P�BLICAS
	///////////////////////////////// /////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////// IN�CIO DO M�TODO PRINCIPAL
	///////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////////////

	// m�todo para trabalhar com a tela2
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		z = restaurarEstado(); //carrega o valor de z que estava no SharedPreferences		
		// w = restaurarW();
		setContentView(R.layout.tela2);
		tv = (TextView) findViewById(R.id.textView1); // ligando os objetos das
														// telas �s vari�veis
		rd[0] = (RadioButton) findViewById(R.id.radio0);
		rd[1] = (RadioButton) findViewById(R.id.radio1);
		rd[2] = (RadioButton) findViewById(R.id.radio2);
		rd[3] = (RadioButton) findViewById(R.id.radio3);
		rd[4] = (RadioButton) findViewById(R.id.radio4);
		btnQuestoesErradas = (Button) findViewById(R.id.btnRandomizar);
		btnChecar = (Button) findViewById(R.id.btnChecar);
		btnBuscar = (Button) findViewById(R.id.btnBuscar);
		btnAnterior = (Button) findViewById(R.id.btnAnterior);
		btnProximo = (Button) findViewById(R.id.btnProximo);
		btnSortear = (Button) findViewById(R.id.btnSortear);
		txtBuscar = (EditText) findViewById(R.id.editText1);		
		btnSpinner = (Spinner) findViewById(R.id.escolhedor);
		btnTema = (Button) findViewById(R.id.btnTema);
		questoesTematicas = new ArrayList<Integer>();
		sv = (ScrollView) findViewById(R.id.scrollView);
		imageView = (ImageView) findViewById(R.id.imageView1);
		questoesErradas = new ArrayList<Integer>();
		chrono = (Chronometer) findViewById(R.id.chronometer1);
		
		// chamando o m�todo que muda as cores
		mudarACorDosLayouts();

		// CustomGestureDetector customGestureDetector = new
		// CustomGestureDetector();
		gestureDetector = new GestureDetector(Questao.this, Questao.this);

		// MainActivity ma = new MainActivity(Questao.this);

		///////////////////////////// IN�CIO DOS M�TODOS DOS
		///////////////////////////// BOT�ES////////////////////////////////////////////////////////////////
		sv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				gestureDetector.onTouchEvent(event);
				sv.onTouchEvent(event);
				return true;
			}
		});

		for (int i = 0; i < 5; i++) {
			rd[i].setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					gestureDetector.onTouchEvent(event);
					return false;
				}
			});
		}
		
		imageView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				gestureDetector.onTouchEvent(event);
				return false;
			}
		});
		
		txtBuscar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				onCreateDialog().show();
			}
		});
		
		

		// m�todo para trabalhar com o Abrir...
		btnSortear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("file/*");
				startActivityForResult(intent, 1);

				// Novas_Funcionalidades fo = new Novas_Funcionalidades();
				// fo.performFileSearch();
				// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				// intent.setType("file/*");
				// startActivity(intent);
				// if (f < h) {
				// o array w possui os n�meros das quest�es embaralhadas, a
				// vari�vel f ir� orientar esse array, iniciando de 0 at� ...
				// z = w[f];
				// carregarQuestao(z);
				// f++; // incrementando a vari�vel f que armazena a posi��o do
				// array que est� sendo percorrido.
				// mudarACorDosLayouts();
				// }
			}
		});

		// m�todo para trabalhar com o bot�o Checar
		btnChecar.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("ShowToast")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (resp != -1) {
					salvarOuRemoverResposta(QuestaoAux.checked(resp, rd, Questao.this, auxiliarEmbaralharAlternativas));
				} else {
					Toast.makeText(Questao.this, "Escolha uma alternativa", Toast.LENGTH_SHORT);
				}

			}
		});

		// m�todo para trabalhar com o bot�o Buscar
		btnBuscar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					resp = carregarQuestao(Integer.parseInt(txtBuscar.getText().toString()));
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mudarACorDosLayouts();
			}
		});

		// m�todo para trabalhar com o bot�o Anterior
		btnAnterior.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (z > 0) {
					try {
						resp = carregarQuestao(--z);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mudarACorDosLayouts();
			}
		});

		// m�todo para trabalhar com o bot�o Pr�ximo
		btnProximo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int aux = h - 1;
				if (z < aux) {
					try {
						resp = carregarQuestao(++z);
						if(verificarFimDeTema(ar,z,questoesErradas))
							carregarDialogQuestoesErradas();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mudarACorDosLayouts();
			}
		});

		// Bot�o para carregar as quest�es erradas
		btnQuestoesErradas.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//z = f = 0;
				// QuestaoAux.embaralhar(w);
				// carregarQuestao(w[f]);
				//mudarACorDosLayouts();
				try {
					if(questoesErradas.size()>++indexQuestoesErradas){						
						resp = carregarQuestao(questoesErradas.get(indexQuestoesErradas));						
					}else{
						indexQuestoesErradas = 0;
						resp = carregarQuestao(questoesErradas.get(indexQuestoesErradas));
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mudarACorDosLayouts();
			}
		});

		// m�todo para selecionar perguntas do mesmo tema, ele trabalha em
		// conjunto com
		// O C�DIGO PARA TRABALHAR COM O SPINNER e com os
		// M�TODOS PARA TRABALHAR COM O ITEM SELECIONADO PELO SPINNER
		btnTema.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (auxiliarTema < questoesTematicas.size()) {
					try {
						//carregarQuestao(questoesTematicas.get(auxiliarTema++));
						if(questoesTematicas.size()>++auxiliarTema){						
							resp = carregarQuestao(questoesTematicas.get(auxiliarTema));						
						}else{
							auxiliarTema = 0;
							resp = carregarQuestao(questoesTematicas.get(auxiliarTema));
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mudarACorDosLayouts();
			}
		});

		///////////////////////////// FIM DOS M�TODOS DOS
		///////////////////////////// BOT�ES////////////////////////////////////////////////////////////////

		///////////////////////////// INICIO DAS INSTRU��ES
		///////////////////////////// AUXILIARES////////////////////////////////////////////////////////////////////

		// INSTRU��O PARA CAPTURAR AS INFORMA��ES DO ARQUIVO.
		rc = this.getResources();
		is = rc.openRawResource(R.raw.teste);
		Novas_Funcionalidades nf = new Novas_Funcionalidades();
		try {
			ar = nf.realizarLeituraDaQuestao(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// INSTRU��O PARA SELECIONAR O ARQUIVO
		// S� SERVE PARA ABRIR ARQUIVOS LOCALIZADOS EM
		// /DATA/DATA/TROQUE.ME/FILES
		// ELE RETORNA O CAMINHO ABSOLUTO DO DIRETORIO ONDE S�O ARMAZENADOS OS
		// ARQUIVOS COM O M�TODO openFileOutput(String, int)
		// Context context = this.getApplicationContext();
		// File file = context.getFilesDir();
		// try {
		// is = new FileInputStream(file);
		// ar = nf.realizarLeituraDaQuestao(is);
		// } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// INSTRU��O PARA CARREGAR O SPINNER
		if (ar != null) {
			carregarTemas();
		
		//temas = new ArrayList<String>();
		//conectorAuxiliar = new ArrayList<Integer>();
		//// temas.add("TODOS OS TEMAS");

		//// enderecoDaQuestao = new ArrayList<Integer>();
		//Temas t;

		//if (ar != null) {
		//	t = nf.filtrarTemas(ar);
		//	temas.addAll(t.getAr());
		//	conectorAuxiliar = t.getRefNumber();
		//}
		//temas.add("TODOS OS TEMAS");

		// for(int i=0;i<get_QtdeDeQuestoes();i++){ //esse la�o permite
		// percorrer por todas as quest�es
		// String a = rc.getString(get_EnderecoDaQuestao(i)); //essa linha faz o
		// resgate do enunciado da quest�o
		// String b = (String) a.subSequence(a.indexOf("[")+1, a.indexOf("]"));
		// //essa linha faz a limpeza do enunciado da quest�o, retornando apenas
		// o tema
		// if(temas.indexOf(b)==-1){ //essa linha verifica se o tema j� existe
		// na vari�vel temas
		// temas.add(b); //se o tema n�o existir, ele ser� adicionado
		// }
		// conectorAuxiliar.add(temas.indexOf(b)); //essa vari�vel ir� armazenar
		// a posi��o da vari�vel temas correspondente a vari�vel b
		// por que essa posi��o? Porque ela � �nica e mais f�cil de processar do
		// que uma String.
		// Assim, cada elemento desta lista ter� um valor relacionado ao tema
		// correspondente.
		// Num contexto geral, essa vari�vel faz a liga��o entre o tema e a
		// quest�o do tema
		// }

		/////////////////////// FIM DAS INSTRU��ES
		/////////////////////// AUXILIARES////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/////////////////////// IN�CIO DO C�DIGO PARA TRABALHAR COM O
		/////////////////////// SPINNER/////////////////////////////////////////////////////////////////////////////////////////////////////////

		// O c�digo abaixo foi retirado na internet especificamente do Tutorials
		// Point
		// Segue o endere�o
		// https://www.tutorialspoint.com/android/android_spinner_control.htm
		// Alterei apenas a vari�vel categorias por temas
		// Spinner click listener
			btnSpinner.setOnItemSelectedListener((OnItemSelectedListener) this);

		// Creating adapter for spinner
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
				temas);

		// Drop down layout style - list view with radio button
			dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		// attaching data adapter to spinner
			btnSpinner.setAdapter(dataAdapter);
			btnSpinner.setSelection(temas.size() - 1);
		}
		/////////////////////// FIM DO C�DIGO PARA TRABALHAR COM O
		/////////////////////// SPINNER/////////////////////////////////////////////////////////////////////////////////////////////////////////
		// PEQUENO LA�O PARA CARREGAR A PRIMEIRA QUEST�O
		//if (z != -1) {
			//try {
				//resp = carregarQuestao(z);
			//} catch (IOException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			//f = z;
		//}
		carregarNovamente(); //carrega o caminho path do arquivo que est� no SharedPreferences
		carregarNovamenteQuestoesErradas(); //carrega as questoes erradas que est�o no SharedPreferences
	}

	///////////////////////////////////////// FIM DO M�TODO PRINCIPAL
	///////////////////////////////////////// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////// IN�CIO DOS M�TODOS AUXILIARES
	///////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////

	@SuppressLint("DefaultLocale")
	public void mudarACorDosLayouts() {
		linearLayout3 = (LinearLayout) findViewById(R.id.linearlayout3);
		linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		Random random = new Random();
		String id = String.format("%06d", random.nextInt(999999));
		linearLayout3.setBackgroundColor(Color.parseColor("#" + id));
		id = String.format("%06d", random.nextInt(999999));
		linearLayout2.setBackgroundColor(Color.parseColor("#" + id));
		id = String.format("%06d", random.nextInt(999999));
		linearLayout1.setBackgroundColor(Color.parseColor("#" + id));
		// Duas formas de mudar de cor
		// rl.setBackgroundColor(Color.parseColor("#01ff90"));
		// rl.setBackgroundColor(Color.parseColor("blue"));
	}

	//////// M�TODO PARA TRABALHAR COM O ITEM SELECIONADO PELO
	//////// SPINNER/////////////////////////////////////////////
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// On selecting a spinner item
		String item = parent.getItemAtPosition(position).toString();
		if (item != "TODOS OS TEMAS") {
			carregarArrayComQuestoesDoTemaSelecionado(item);
			try {
				resp = carregarQuestao(questoesTematicas.get(auxiliarTema++));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				if (z != -1) {
					resp = carregarQuestao(z);
				} else {
					resp = carregarQuestao(0);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Showing selected spinner item
		Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	public void carregarArrayComQuestoesDoTemaSelecionado(String tema) {
		int posicaoDoTema = temas.indexOf(tema);
		int i = conectorAuxiliar.size();
		// int cont = questoesTematicas.size();
		// for(int j=0;j<cont;j++){
		// questoesTematicas.remove(j);
		// }
		// questoesTematicas = new ArrayList<Integer>();
		questoesTematicas.clear();
		for (int j = 0; j < i; j++) {
			if (conectorAuxiliar.get(j) == posicaoDoTema) {
				questoesTematicas.add(j);
			}
		}
		auxiliarTema = 0;
	}

	//////// FIM DO M�TODO PARA TRABALHAR COM O ITEM SELECIONADO PELO
	//////// SPINNER///////////////////////////////////////

	/**
	 * Serve para carregar uma quest�o na tela2
	 * 
	 * @param questao
	 *            precisa informar o n�mero da quest�o por meio da vari�vel
	 *            questao
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public int carregarQuestao(int questao) throws FileNotFoundException, IOException {
		try {

			int q = questao * 6; // serve para saber o range da quest�o atual,
									// por exemplo, se a quest�o atual � a 0,
									// temos 0 * 6 = 0; logo o range ser� 0 + 6,
									// veja o for abaixo
									// outro exemplo, se a quest�o atual � a 1,
									// temos 1 * 6 = 6; logo o range inicia em 6
									// e termina em 6 + 6 = 12

			List<Integer> numbers = new ArrayList<Integer>(); // essa lista
																// serve para
																// embaralhar as
																// quest�es
			for (int i = (q + 1); i < (q + 6); i++) { // nesse la�o j� est�
														// sendo adicionado o
														// range das quest�es
				numbers.add(i);
			}
			Collections.shuffle(numbers); // fun��o para embaralhar			
			String t = ar.get(q);
			char a = '\n';
			t = t.replace("\\n", ""+a);			
			tv.setText(t);
			//tv.setText(ar.get(q)); // * a vari�vel tv carrega o enunciado na
									// tela
			String[] abcde = { "a) ", "b) ", "c) ", "d) ", "e) " }; // serve
																	// para
																	// adicionar
																	// os itens
																	// na tela
			int resposta = 0; // serve para armazenar a resposta
			ArrayList<String> auxAR = new ArrayList<String>();
			auxAR.addAll(ar);
			for (int i = 0; i < 5; i++) {
				String resp = auxAR.get(numbers.get(i)); // serve para verificar
														// qual quest�o � a
														// correta
				if (resp.indexOf("*") == 0) { // cada quest�o correta ter� um
												// asterisco no inicio
					auxAR.set(numbers.get(i), resp.substring(1)); // o m�todo substring limpa o asterisco
																//o m�todo set do arraylist faz a altera��o 
					
					resposta = numbers.get(i); // a vari�vel resposta armazena a quest�o correta
				}
				rd[i].setText(auxAR.get(numbers.get(i))); // * o array rd carrega
														// cada uma das
														// alternativas
				rd[i].setText(abcde[i] + rd[i].getText());
			}

			z = questao; // * a vari�vel z armazena o n�mero da quest�o para
							// verificar se ela est� correta

			txtBuscar.setText(String.valueOf(questao)); // * a vari�vel
														// txtBuscar carrega o
														// n�mero da quest�o na
														// tela

			auxiliarEmbaralharAlternativas = numbers;
			
			chrono.stop();			
			chrono.setBase(SystemClock.elapsedRealtime());			
			//chrono.clearAnimation();//.setText("00:00");
			chrono.start();
			
			setVisibleImage(ar.get(q));

			return resposta;
		} catch (Exception ex) {
			//return carregarQuestao(0);
			if (ar.isEmpty()) {
				Toast.makeText(Questao.this, "Selecione um arquivo para come�ar", Toast.LENGTH_SHORT).show();
				return 0;
			} else {
				System.out.println(ex);
				carregarDialogQuestoesErradas();
				return carregarQuestao(0);
				//return 0;
			}
		}

	}

	/*
	 * Serve para guardar as vari�veis atuais do app Permitindo que o app
	 * continue de onde parou
	 */
	public void guardarEstado() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putInt("z", z);
		editor.putString("path", pathFile);
		int i = 0;
		editor.putInt("qtdeQuestoesErradas", questoesErradas.size());
		if(questoesErradas.size()>0){
			for(Iterator<Integer> iterator = questoesErradas.iterator();iterator.hasNext();){			 
				String cat= "aux"+i;			
				editor.putInt(cat, iterator.next());
				i++;
			}
		}
		// for(int i=0;i<h;i++){
		// String cat="aux"+i;
		// editor.putInt(cat, w[i]);
		// }
		editor.commit();
	}

	/*
	 * Serve para restaurar uma vari�vel guardada pelo m�todo guardarEstado() A
	 * vari�vel que ele restaura � da �ltima quest�o que foi apresentada na tela
	 */
	public int restaurarEstado() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		int defaultValue = -1;
		int value = sharedPref.getInt("z", defaultValue);
		return value;
	}
	
	public String restaurarEstado2() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String defaultValue = "";
		String value = sharedPref.getString("path", defaultValue);
		return value;
	}
	
	/*
	 * Esse m�todo carrega novamente as questoes erradas
	 */
	public void carregarNovamenteQuestoesErradas(){
		int qtdeQuestoesErradas = restaurarQtdeQuestoesErradas();
		if(qtdeQuestoesErradas>0){
			for(int i=0;i<qtdeQuestoesErradas;i++){
				String key = "aux"+i;
				questoesErradas.add(restaurarQuestoesErradas(key));
			}			
		}		
	}
	
	public int restaurarQtdeQuestoesErradas(){
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		int defaultValue = -1;
		int value = sharedPref.getInt("qtdeQuestoesErradas", defaultValue);
		return value;
	}
	
	public int restaurarQuestoesErradas(String key){
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		int defaultValue = -1;
		int value = sharedPref.getInt(key, defaultValue);
		return value;
	}

	/*
	 * Serve para restaurar o array guardado pelo m�todo guardarEstado() O array
	 * que ele restaura � o utilizado para fazer o random
	 */
	//public int[] restaurarW() {
		// int[] aux=new int[h];
		//int[] aux = null;
		// SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		// for(int i=0;i<h;i++){
		// int defaultValue = i;
		// aux[i]=sharedPref.getInt("aux"+i, defaultValue);
		// }
		// return aux;
		//return aux;
	//}

	@Override
	public void onDestroy() {
		super.onDestroy();
		guardarEstado();
	}

	@Override
	protected void onStop() {
		super.onStop();
		guardarEstado();
		// The activity is no longer visible (it is now "stopped")
	}

	@Override
	protected void onPause() {
		super.onPause();
		guardarEstado();
		// Another activity is taking focus (this activity is about to be
		// "paused").
	}

	@Override
	protected void onResume() {
		super.onResume();
		// The activity has become visible (it is now "resumed").
	}
	///////////////////////////////////////// FIM DOS M�TODOS AUXILIARES
	///////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub

		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float velocityX, float velocityY) {
		// if (motionEvent1.getY() - motionEvent2.getY() > 50) {
		// Toast.makeText(this, "You Swiped up!", Toast.LENGTH_LONG).show();
		// return false;
		// }

		// if (motionEvent2.getY() - motionEvent1.getY() > 50) {
		// Toast.makeText(this, "You Swiped Down!", Toast.LENGTH_LONG).show();
		// return false;
		// }
		if(motionEvent1 == null){
			return false;
		}else{
			if (motionEvent1.getX() - motionEvent2.getX() > 50) {
				// Toast.makeText(this, "You Swiped Left!",
				// Toast.LENGTH_LONG).show();
				int aux = h - 1;
				if (z < aux) {
					try {
						resp = carregarQuestao(++z);
						if(verificarFimDeTema(ar,z,questoesErradas))
							carregarDialogQuestoesErradas();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mudarACorDosLayouts();
				return true;
			}

			if (motionEvent2.getX() - motionEvent1.getX() > 50) {
				// Toast.makeText(this, "You Swiped Right!",
				// Toast.LENGTH_LONG).show();
				if (z > 0) {
					try {
						resp = carregarQuestao(--z);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mudarACorDosLayouts();
				return true;
			} else {
				return true;
			}
		}
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		// TODO Auto-generated method stub
		if (z != -1) {
			salvarOuRemoverResposta(QuestaoAux.checked(resp, rd, Questao.this, auxiliarEmbaralharAlternativas));
		}
		return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Novas_Funcionalidades fo = new Novas_Funcionalidades();
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				zerarVariaveisGlobais();
				String FilePath = data.getData().getPath();
				pathFile = FilePath;
				try {
					InputStream is = new FileInputStream(FilePath);
					try {
						ar = fo.realizarLeituraDaQuestao(is); // RECEBE O ARRAYLIST DA FUN��O
						temas.clear();
						Temas tm = carregarTemas2(ar);
						temas = tm.getAr();
						//temas = carregarTemas2(ar).getAr();
						conectorAuxiliar = tm.getRefNumber(); 
						temas.add("TODOS OS TEMAS");
						alimentarSpinner();
						resp = carregarQuestao(0); // CARREGA QUEST�O NA TELA
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
	
	// esse m�todo serve para carregar o arquivo
		// ap�s uma parada
	public void carregarNovamente() {
		Novas_Funcionalidades fo = new Novas_Funcionalidades();
		String FilePath = restaurarEstado2();
		pathFile = FilePath;
		if (!FilePath.equals("")) {
			try {
				InputStream is = new FileInputStream(FilePath);
				try {
					ar = fo.realizarLeituraDaQuestao(is); // RECEBE O ARRAYLIST
															// DA FUN��O
					carregarTemas();
					//resp = carregarQuestao(z); // CARREGA QUEST�O NA TELA
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void carregarTemas(){
		// INSTRU��O PARA CARREGAR O SPINNER
		Novas_Funcionalidades nf = new Novas_Funcionalidades();
		temas = new ArrayList<String>();
		conectorAuxiliar = new ArrayList<Integer>();
				// temas.add("TODOS OS TEMAS");

				// enderecoDaQuestao = new ArrayList<Integer>();
		Temas t;

		if (ar != null) {
			t = nf.filtrarTemas(ar);
			temas.addAll(t.getAr());
			conectorAuxiliar = t.getRefNumber();
		}
		temas.add("TODOS OS TEMAS");
		carregarNoSpinner();
		//alimentarSpinner();
	}
	
	public void carregarNoSpinner() {
		btnSpinner.setOnItemSelectedListener((OnItemSelectedListener) this);

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
				temas);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		// attaching data adapter to spinner
		btnSpinner.setAdapter(dataAdapter);
		btnSpinner.setSelection(temas.size() - 1);
	}
	
	public Temas carregarTemas2(ArrayList<String> as){
		Novas_Funcionalidades nf = new Novas_Funcionalidades();
		//ArrayList<String> al = new ArrayList<String>();
		Temas te = new Temas();
		if(as!=null){
			te = nf.filtrarTemas(as);
		}
		return te;
	}
	
	public void alimentarSpinner(){
		// Creating adapter for spinner
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
						temas);

				// Drop down layout style - list view with radio button
				dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

				// attaching data adapter to spinner
				btnSpinner.setAdapter(dataAdapter);
				btnSpinner.setSelection(temas.size() - 1);
	}
	
	void salvarQuestaoErrada(List<Integer> questoesErradas, int index){
		if(questoesErradas.indexOf(index)==-1){
			questoesErradas.add(index);
		}
	}
	
	public String criarPastaAndMakeVisibleImage(String enunciado){
		Novas_Funcionalidades nf = new Novas_Funcionalidades();
		String imagem = nf.filtrarImagem(enunciado);
		getPublicAlbumStorageDir("QuestionationPictures");
		if(imagem.equals("")){
			imageView.setVisibility(ImageView.GONE);
			return "";
		}else{
			imageView.setVisibility(ImageView.VISIBLE);
			return imagem;
		}
	}
	
public void setVisibleImage(String enunciado){
		
		String imagem=criarPastaAndMakeVisibleImage(enunciado);
		if(!imagem.equals("")){

		// funciona!!!! imageView.setImageResource(R.drawable.blue96x96);	 
		
		//getPublicAlbumStorageDir("QuestionationPictures");
		//getPublicAlbumStorageDir("test1");
		
		Uri uri = null;		
		File f = getPublicFileStorageDir(imagem);
		if(f.exists()){
		//f = Context.
		uri = Uri.fromFile(f);
		final Uri uri2 = uri;
		imageView.setAdjustViewBounds(true);
		//imageView.setLongClickable(true);
		imageView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_EDIT);//.ACTION_VIEW);				
				intent.setData(uri2);							
				//intent.setType("image/png");
				intent.setDataAndType(uri2, "image/png");
				
				startActivityForResult(intent, 1);
				return false;
			}
		});
		imageView.setImageURI(uri);
		}
		}
	}

	public File getPublicAlbumStorageDir(String albumName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), albumName);
    
		if(!file.exists()){
			if (!file.mkdirs()) {
				Log.e("bla", "Directory not created");
			}
		}
		return file;
	}
	
	public File getPublicFileStorageDir(String picture){
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				"QuestionationPictures/"+picture);
		return file;
	}
	
	public int getVariavelZ(){
		return this.z;
	}
	
	boolean verificarFimDeTema(ArrayList<String> ar, int index, List<Integer> questoesErradas){	
		//verifica se h� mais de uma quest�o respondida
		boolean retorno = false;
		if(index>=1){
			index*=6;
			//verifica se h� respostas erradas
			if(!questoesErradas.isEmpty()){		
				//capturando a quest�o anterior
				String temaAnterior = ar.get(index-6);
				//capturando a quest�o atual
				String temaAtual = ar.get(index);
				//filtrando o tema da questao anterior
				temaAnterior = (String) temaAnterior.subSequence(temaAnterior.indexOf("[")+1, temaAnterior.indexOf("]"));
				//filtrando o tema da questao atual
				temaAtual = (String) temaAtual.subSequence(temaAtual.indexOf("[")+1, temaAtual.indexOf("]"));
				//verifica se os temas s�o diferentes
				if(!temaAnterior.equals(temaAtual)){
					retorno = true;
				}
			}
		}
		return retorno;
		
	}
	
	void carregarDialogQuestoesErradas(){
		if(!questoesErradas.isEmpty()){
			//criar o dialog
			AlertDialog.Builder builder = new AlertDialog.Builder(Questao.this);
			//definir a mensagem
			builder.setMessage("Voc� deseja carregar as quest�es erradas?")
			//definir o titulo
			.setTitle("Carregar Quest�es Anteriores");
			//m�todo para resposta positiva
			builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//habilita o botao
					btnQuestoesErradas.setEnabled(true);
					try {
						//armazena a posi��o atual do z numa vari�vel tempor�ria
						zTemp = z;
						//carrega a primeira questao errada
						resp = carregarQuestao(questoesErradas.get(indexQuestoesErradas));
						zQuestaoErradas = true;
						
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//zTemp = z;
					//arTemp = new ArrayList<String>();
					//arTemp = ar;
				}
			});
			builder.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					zQuestaoErradas = false;
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}		
	}
	
	void carregarDialogAcabouQuestoesErradas(){
		//criar o dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(Questao.this);
		//definir a mensagem
		builder.setMessage("Voc� deseja continuar de onde parou?")
		//definir o titulo
		.setTitle("Acabou quest�es erradas");
		//m�todo para resposta positiva
		builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//habilita o botao
				//btnQuestoesErradas.setEnabled(true);
				try {
					//armazena a posi��o atual do z numa vari�vel tempor�ria
					z=zTemp;
					//carrega a primeira questao errada
					resp = carregarQuestao(z);												
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//zTemp = z;
				//arTemp = new ArrayList<String>();
				//arTemp = ar;
			}
		});
		builder.setNegativeButton("N�o", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		zQuestaoErradas = false;
	}
	
	void zerarVariaveisGlobais(){
		z=-1;
		auxiliarTema = 0;
		questoesErradas.clear();
		resp = -1;
		indexQuestoesErradas=0;
		zQuestaoErradas = false;
	}
	
	public void salvarOuRemoverResposta(boolean gab){
		if(!gab){
			salvarQuestaoErrada(questoesErradas, z);				
		}else{
			if(!questoesErradas.isEmpty())
				if(questoesErradas.indexOf(questoesErradas.get(indexQuestoesErradas))!=-1){
					questoesErradas.remove(indexQuestoesErradas);
				}			
		}
		if(questoesErradas.isEmpty()){				
			btnQuestoesErradas.setEnabled(false);
			if(zQuestaoErradas){
				carregarDialogAcabouQuestoesErradas();
			}
		}
	}
	
	public Dialog onCreateDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(Questao.this);
		// Get the layout inflater
	    LayoutInflater inflater = Questao.this.getLayoutInflater();	  
	    final View layout = inflater.inflate(R.layout.buscarquest, null);
		alert.setView(layout)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int id) {            	                     
            	   try {         
            		   txtBuscar2 = (EditText) layout.findViewById(R.id.editTextBuscar);
            		   resp = carregarQuestao(Integer.parseInt(txtBuscar2.getText().toString()));
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               }
           })
           .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   //LoginDialogFragment.this.getDialog().cancel();
               }
           });
		return alert.create();
	}
}

///////////////////////////////////////// FIM DA CLASSE
///////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////////