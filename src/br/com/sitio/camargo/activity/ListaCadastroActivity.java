package br.com.sitio.camargo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.sitio.camargo.constantes.Pasto;
import br.com.sitio.camargo.dao.AnimalDAO;
import br.com.sitio.camargo.dao.DatabaseHelper;
import br.com.sitio.camargo.util.Mask;
import br.com.sitio.camargo.vo.AnimalVO;

import com.example.sitiocamargo.R;

public class ListaCadastroActivity extends Activity implements OnItemSelectedListener{
	
	
	private DatabaseHelper helper;
	private ListView listView ;
	Spinner spinner ;
	private SimpleAdapter dataAdapter;
	TextView textView ,descricaoPastoView, descricaoPastoRow , quantidade ;
	private EditText editTextMesAno;
	ImageButton btnAdd;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	setContentView(R.layout.activity_main_consulta);
	helper= new DatabaseHelper(getApplicationContext());
	spinner = (Spinner) findViewById(R.id.spinner);
	
	spinner.setOnItemSelectedListener(this);
	loadSpinnerPasto();
	
	editTextMesAno = (EditText)findViewById(R.id.input_Mes_Ano);
	editTextMesAno.addTextChangedListener(Mask.insert(Mask.MES_ANO,editTextMesAno));
	
	btnAdd = (ImageButton) findViewById(R.id.btn_add);
	
	
	btnAdd.setOnClickListener(new View.OnClickListener() {
    	 
            @Override
            public void onClick(View arg0) {
            	
            	boolean exibe = false ;
            	AnimalDAO animalDAO = new AnimalDAO(getApplicationContext(), helper);
            	
            	listView = (ListView)findViewById(R.id.lista);
            	
            	List<AnimalVO> lstAnimalVOs = null ;
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(ListaCadastroActivity.this);
            	
            	if (!String.valueOf(spinner.getSelectedItem())
						.equalsIgnoreCase("SELECIONE")
					 && editTextMesAno.getText().toString().length() == 7) {

            		if(Pasto.getEPasto(String.valueOf(spinner.getSelectedItem())) != 9 ){
            			lstAnimalVOs = animalDAO.getAllByPasto(Pasto.getEPasto(String.valueOf(spinner.getSelectedItem())),editTextMesAno.getText().toString());
            			
            		}else{
            			lstAnimalVOs = animalDAO.getAllByPeriodo(editTextMesAno.getText().toString());
            			exibe = true ;
            		}
            		
            		
            		exibeListaPasto(lstAnimalVOs,exibe);
            		
            		textView = (TextView)findViewById(R.id.textViewTotal);
            		
            		if(lstAnimalVOs != null && lstAnimalVOs.size() > 0){
            			
            			Object o = lstAnimalVOs.get(lstAnimalVOs.size()-1);
            			
            			textView.setText("Total : " +String.valueOf(((AnimalVO)o).getTotalGeralPasto()));
            			
            		}else{
            			textView.setText("");
            		}
            		
            	}else{
            		builder.setTitle("Dados Inválido :");
        			builder.setMessage("Favor Preencher Corretamente os Campos do Cadastro.\nPasto, mês e ano (MM/YYYY)");
        			AlertDialog dialog = builder.create();
        			dialog.show();
            		
            	}
            	
            	
            }
     });
	}

	
	
	
	public void exibeListaPasto(List<AnimalVO> lstAnimalVOs, final boolean exibeAll){
		
		String []from ;
		int [] to ;
		final List<HashMap<String,String>> lstHashMaps = converteArrayListToHashMap(lstAnimalVOs);
		
		
		from = new String[]{"_ID","descricaoPastoRow","DESCRICAO_ANIMAL", "QUANTIDADES","DT_INSERT"};
		to = new int []{R.id._ID, R.id.descricaoPastoView,  R.id.DESCRICAO_ANIMAL,  R.id.QUANTIDADES, R.id.DT_INSERT};
		dataAdapter = new SimpleAdapter(this,lstHashMaps,R.layout.row, from, to);
			
		listView.setAdapter(dataAdapter);
		
		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
	             
	            	final HashMap<String, String> hashMapElementoAdapter = lstHashMaps.get(position);
	            	final String item = hashMapElementoAdapter.get("_ID");
	            	AlertDialog.Builder builder = new AlertDialog.Builder(ListaCadastroActivity.this);
	            	builder.setTitle("Deletar ?");

	            	builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            	           public void onClick(DialogInterface dialog, int id) {
            	        	   
            	        	   lstHashMaps.remove(position);
            	        	   
            	        	   removerItenAdapter(item);
            	        	   
            	        	   dataAdapter.notifyDataSetChanged();
            	        	   
            	        	   Pattern p = Pattern.compile("\\d");
            	        	   
            	        	   Matcher matcher = p.matcher(textView.getText().toString());
            	        	   
            	        	   int totalTextView = 0 ;
            	        	   
            	       		   if(matcher.find()){
            	       			  totalTextView = Integer.valueOf(textView.getText().toString().substring(matcher.start()));
            	       			  int valorItemRemovido = Integer.valueOf(hashMapElementoAdapter.get("QUANTIDADES"));
            	       			  textView.setText("Total : "+String.valueOf(totalTextView - valorItemRemovido));
            	       		   }

            	           }
	            	     });
	            	builder.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
	            	           public void onClick(DialogInterface dialog, int id) {
	            	        	   return;
	            	           }
	            	       });
	            	
	            	
	            	AlertDialog dialog = builder.create();
	            	dialog.show();
	            	
	            	
	            }
				
	        });
		
		

		
	}
	
	private void removerItenAdapter(String item) {
		AnimalDAO animalDAO = new AnimalDAO(getApplicationContext(),helper);
		animalDAO.removeItemAnimal(item);
	}
	
	
	private List<HashMap<String,String>> converteArrayListToHashMap(List<AnimalVO> lstAnimalVOs) {

		List<HashMap<String,String>> lhm = new ArrayList<HashMap<String,String>>();
		for(AnimalVO animalVO : lstAnimalVOs){
			HashMap<String,String> hm = new HashMap<String, String>(); 
			hm.put("_ID",String.valueOf(animalVO.get_id()));
			hm.put("DESCRICAO_ANIMAL",animalVO.getDescricaoAnimal());
			hm.put("QUANTIDADES",String.valueOf(animalVO.getQuantidade()));
			hm.put("DT_INSERT",animalVO.getDateInsert());
			if(animalVO.getDescricaoPasto() != null){
				hm.put("descricaoPastoRow",animalVO.getDescricaoPasto());
			}
			
			lhm.add(hm);
			
		}
		
		return lhm ;
		
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
	
	
    private void loadSpinnerPasto() {
    	
    	AnimalDAO animalDAO = new AnimalDAO(getApplicationContext(),helper);
    	List<String> lables = animalDAO.getAllLabelPasto();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    
    


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
	
	

}
