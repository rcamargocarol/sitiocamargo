package br.com.sitio.camargo.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.sitio.camargo.dao.AnimalDAO;
import br.com.sitio.camargo.dao.DatabaseHelper;
import br.com.sitio.camargo.dao.DespesaDAO;
import br.com.sitio.camargo.dao.PessoaDAO;
import br.com.sitio.camargo.util.MascaraMonetaria;
import br.com.sitio.camargo.util.Mask;
import br.com.sitio.camargo.vo.PessoaVO;

import com.example.sitiocamargo.R;

public class CadastroLancamentoDespesaActivity extends Activity implements OnItemSelectedListener {

	
	private DatabaseHelper helper;
	private EditText editText1;
	private TextView textView1;
	private TextView textView2;
	private EditText editText2;
	private TextView textView3;
	private Spinner spinner1;
	private TextView textView4;
	private EditText editText3;
	private Button button1;

	/**
	 * Find the Views in the layout<br />
	 * <br />
	 * Auto-created on 2015-12-18 19:34:55 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private void findViews() {
		editText1 = (EditText)findViewById( R.id.editText1 );
		textView1 = (TextView)findViewById( R.id.textView1 );
		textView2 = (TextView)findViewById( R.id.textView2 );
		editText2 = (EditText)findViewById( R.id.editText2 );
		textView3 = (TextView)findViewById( R.id.textView3 );
		spinner1 = (Spinner)findViewById( R.id.spinner1 );
		textView4 = (TextView)findViewById( R.id.textView4 );
		editText3 = (EditText)findViewById( R.id.editText3 );
		button1 = (Button)findViewById( R.id.button1 );

//		button1.setOnClickListener( this );
	}

	
	public void resetCampos(){
		editText1.setText("");
		editText2.setText("");
		editText3.setText("");;
	}
	
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_cadastro_lancamento_despesa);
		helper= new DatabaseHelper(getApplicationContext());
		findViews();
		loadAgenteDespesa();
		editText2.addTextChangedListener(new MascaraMonetaria(editText2));
		
		editText3.addTextChangedListener(Mask.insert(Mask.MES_ANO,editText3));
		
		 button1.setOnClickListener(new View.OnClickListener() {
        	 
	            @Override
	            public void onClick(View arg0) {
	            	final DespesaDAO despesaDAO ;
	            	if(validaFormulario()){
	            		despesaDAO = new DespesaDAO(getApplicationContext(), helper);
	            		boolean registroDespesa = despesaDAO.insertTableDespesas(editText1.getText().toString(),Double.valueOf(editText2.getText().toString().replaceAll("[^\\d]","")),
	            				Long.valueOf(String.valueOf(spinner1.getSelectedItem()).split("-")[0].trim()).intValue(),
	            				editText3.getText().toString());
	            		if(registroDespesa){
	            			resetCampos();
	            			loadAgenteDespesa();
	            		}
	            		
	            	}
	           
	            }
		 });
   
	};
	
	
	private boolean validaFormulario() {
		
		boolean retorno = true;
		AlertDialog.Builder builder = new AlertDialog.Builder(CadastroLancamentoDespesaActivity.this);
		builder.setTitle("Dados Inválido :");
		StringBuffer sb = new StringBuffer();
		if(editText1.length() == 0){
			sb.append("Descricao Obrigatório \n");
			retorno = false ;
		}
		
		if(editText2.length() == 0){
			sb.append("Valor Obrigatório \n");
			retorno = false ;
		}
		
		
		if(editText3.length() == 0){
			sb.append("Período Obrigatório \n");
			retorno = false ;
			
		}else if(editText3.getText().toString().length() != 7 || Integer.valueOf(
																		editText3.getText().toString().substring(0, 2)) < 0 || 
																		Integer.valueOf(
																				editText3.getText().toString().substring(0, 2)) > 12
																		){
			sb.append("Período Inválido \n");
			retorno = false ;
			
		}
		
		if(String.valueOf(spinner1.getSelectedItem())
				.equalsIgnoreCase("Secione Agente")){
			sb.append("Agente realizador da despesa \n");
			retorno = false ;
			
		}
		
		if(!retorno){
			builder.setMessage(sb.toString());
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
		
		// TODO Auto-generated method stub
		return retorno;
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	
	 private void loadAgenteDespesa() {
    	PessoaDAO pessoaDAO = new PessoaDAO(getApplicationContext(),helper);
    	List lstPessoaVo = pessoaDAO.getAllAgente();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, lstPessoaVo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
	 }
	
	

}
