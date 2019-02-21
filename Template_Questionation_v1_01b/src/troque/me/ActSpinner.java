package troque.me;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class ActSpinner {

	public void carregarNoSpinner(Spinner btnSpinner, Activity ac, List<String> temas) {						
		//btnSpinner.setOnItemSelectedListener((OnItemSelectedListener) ac);
		
		btnSpinner.setOnItemSelectedListener(te);

		// Creating adapter for spinner
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ac, android.R.layout.simple_dropdown_item_1line,
				temas);

		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		// attaching data adapter to spinner
		btnSpinner.setAdapter(dataAdapter);
		btnSpinner.setSelection(temas.size() - 1);
	}
	
	private AdapterView.OnItemSelectedListener te =
			new AdapterView.OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			};
	
}
