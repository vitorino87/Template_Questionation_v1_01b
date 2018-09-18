package troque.me;

import java.util.ArrayList;

public class Temas {
	
	private ArrayList<String> ar = new ArrayList<String>();
	private ArrayList<Integer> refNumber = new ArrayList<Integer>();
	
	public ArrayList<String> getAr() {
		return ar;
	}
	public void setAr(ArrayList<String> ar) {
		this.ar = ar;
	}
	public ArrayList<Integer> getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(ArrayList<Integer> refNumber) {
		this.refNumber = refNumber;
	}
	
	public void adicionarItem(String item){
		this.ar.add(item);
	}
	
	public void adicionarNumRef(int item){
		this.refNumber.add(item);
	}
	
}
