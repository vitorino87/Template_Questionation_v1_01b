package troque.me;



import java.util.List;
import java.util.Random;
import android.widget.RadioButton;
import android.widget.Toast;
import android.content.Context;

public class QuestaoAux {
	//esse m�todo serve para embaralhar o array v[]
	
	/**
	 * Serve para embaralhar o array adicionado
	 * @param v
	 */
		public static void embaralhar(int [] v) 
		{
				
			Random random = new Random();
				
			for (int i=0; i < (v.length - 1); i++) 
			{

				//sorteia um �ndice
				int j = random.nextInt(v.length); 
					
				//troca o conte�do dos �ndices i e j do vetor
				int temp = v[i];
				v[i] = v[j];
				v[j] = temp;
			}
		}
		
		/**
		 * Serve para alimentar o array com n�meros de 0 at� o tamanho do array
		 * @param w
		 */
		public static void alimentador(int[] w){
			for(int i=0;i<w.length;i++)
			{
				w[i]=i;
			}
		}
		
		//m�todo para verificar se a resposta selecionada est� correta
		//possui 3 assinaturas a primeira � o n�mero da quest�o
		//a segunda � o array
		//a terceira � o objeto radio
		/**
		 * Serve para checar a resposta selecionada
		 * @param c resposta
		 * @param b array
		 * @param rd objeto r�dio
		 * @param Tela classe
		 */
		public static void checked(int c, RadioButton[] rd, Context Tela, List<Integer> l)
		{
			for(int i=0;i<5;i++)
			{
				if(rd[i].isChecked())
				{					
					if(l.get(i)==c)
					{
						Toast.makeText(Tela, "Correto! Resp.: "+rd[i].getText(), Toast.LENGTH_SHORT).show();						
					}
					else
					{
						Toast.makeText(Tela, "Errado!", Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}
		}		
}
