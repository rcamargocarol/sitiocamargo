package br.com.sitio.camargo.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.sitio.camargo.constantes.AnimalTipo;
import br.com.sitio.camargo.constantes.Pasto;
import br.com.sitio.camargo.dao.AnimalDAO;
import br.com.sitio.camargo.dao.DatabaseHelper;

import com.example.sitiocamargo.R;


public class CadastroActivity extends Activity implements OnItemSelectedListener{
	
	private DatabaseHelper helper;

	Spinner spinner, spinner1;
	EditText editText;
 
    // Add button
    Button btnAdd, btnContador;
 
    // Input text
    EditText inputLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_cadastro);
		
		helper= new DatabaseHelper(getApplicationContext());
       
		spinner = (Spinner) findViewById(R.id.spinner);
 
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnContador = (Button) findViewById(R.id.btn_cont);

        loadSpinnerPasto();
        
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        
        loadSpinnerAnimalTipo();
        
        btnContador.setOnClickListener(new View.OnClickListener() {
       	 
            @Override
            public void onClick(View arg0) {
            	
            	editText = (EditText)findViewById(R.id.input_label);
            
            	int acumulador = 0 ;
            	
            	if( null!=editText.getText().toString() && editText.getText().length() > 0){
            		acumulador =Integer.valueOf(editText.getText().toString()) ;
            	}
            	
            	acumulador++;
            	
            	editText.setText(String.valueOf(acumulador));
            }
        });

        
        btnAdd.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View arg0) {
            	
            	final AnimalDAO animalDAO = new AnimalDAO(getApplicationContext(), helper);
            	editText = (EditText)findViewById(R.id.input_label);
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
            	builder.setTitle("Deseja Cadastrar ?");

            	
				if (!String.valueOf(spinner.getSelectedItem())
						.equalsIgnoreCase("SELECIONE")
						&& !String.valueOf(spinner1.getSelectedItem())
								.equalsIgnoreCase("SELECIONE") && editText.getText().toString() != null && editText.getText().toString().length() > 0) {
	
					builder.setPositiveButton(R.string.sim,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									try {
										animalDAO.addAnimal(
												Pasto.getEPasto(String.valueOf(spinner
														.getSelectedItem())),
												AnimalTipo.getEAnimalTipo(String.valueOf(spinner1
														.getSelectedItem())),
												Integer.valueOf(editText
														.getText().toString()));
										editText.setText("");
										loadSpinnerPasto();
										loadSpinnerAnimalTipo();

									} catch (Exception e) {
										e.printStackTrace();
									}

								}
							});
					builder.setNegativeButton(R.string.nao,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									return;
								}
							});            		
            		
        		}else{
        			builder.setTitle("Favor Preencher Os Campos do Cadastro");
        			
        		}
        		AlertDialog dialog = builder.create();
    			dialog.show();
            	
 
            }
        });
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_cadastro ) {
		      Intent show = new Intent(this,CadastroActivity.class);
		      startActivity(show);
		      return true;
		}else if(id == R.id.action_cadastroPessoaFornecedor){
			 Intent show = new Intent(this,CadastroPessoaActivity.class);
		     startActivity(show);
		     return true;
		}else if(id == R.id.action_listaCadastros){
			  Intent show = new Intent(this,ListaCadastroActivity.class);
		      startActivity(show);
		      return true;
		}else if(id == R.id.action_consultaPessoaFornecedor){
			  Intent show = new Intent(this,ListaCadastroPessoaActivity.class);
		      startActivity(show);
		      return true;
		
		}else if(id == R.id.action_lancamentoDespesas){
			  Intent show = new Intent(this,CadastroLancamentoDespesaActivity.class);
		      startActivity(show);
		      return true;
		
		}else if(id == R.id.action_consultaLancamentoDespesas){
			  Intent show = new Intent(this,ListaConsultaLancamentoDespesaActivity.class);
		      startActivity(show);
		      return true;
		
		}

		return false;
	}
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		Toast.makeText(parent.getContext(), 
				"OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
				Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	
	 /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerPasto() {
        // database handler
    	
    	AnimalDAO animalDAO = new AnimalDAO(getApplicationContext(),helper);
 
        // Spinner Drop down elements
        
    	List<String> lables = animalDAO.getAllLabelPasto();
 
        // Creating adapter for spinner
    	 // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
	
   
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerAnimalTipo() {
        // database handler
       
    	AnimalDAO animalDAO = new AnimalDAO(getApplicationContext(), helper);
 
        // Spinner Drop down elements
        List<String> lables = animalDAO.getAllLabelAnimalTipo();
        
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);
 
        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 
        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter);
    }
	
    
	
}
