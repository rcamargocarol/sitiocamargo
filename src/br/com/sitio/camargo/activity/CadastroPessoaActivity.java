package br.com.sitio.camargo.activity;

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
import android.widget.TextView;
import br.com.sitio.camargo.dao.DatabaseHelper;
import br.com.sitio.camargo.dao.PessoaDAO;
import br.com.sitio.camargo.util.Mask;

import com.example.sitiocamargo.R;

public class CadastroPessoaActivity extends Activity implements OnItemSelectedListener {
	
	
	private DatabaseHelper helper;

	private TextView text1;
	private Spinner spinner;
	private TextView cpf;
	private EditText campoCpf;
	private TextView cnpj;
	private EditText campoCnpj;
	private TextView text2;
	private EditText campoNome;
	private TextView text3;
	private EditText campoEndereco;
	private TextView text4;
	private EditText campoCep;
	private TextView text5;
	private EditText campoTelefone;
	private TextView text6;
	private EditText campoComplemento;
	private Button button1;

	/**
	 * Find the Views in the layout<br />
	 * <br />
	 * Auto-created on 2015-12-03 09:01:15 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private void findViews() {
		text1 = (TextView)findViewById( R.id.text1 );
		spinner = (Spinner)findViewById( R.id.spinner );
		cpf = (TextView)findViewById( R.id.cpf );
		campoCpf = (EditText)findViewById( R.id.campoCpf );
		cnpj = (TextView)findViewById( R.id.cnpj );
		campoCnpj = (EditText)findViewById( R.id.campoCnpj );
		text2 = (TextView)findViewById( R.id.text2 );
		campoNome = (EditText)findViewById( R.id.campoNome );
		text3 = (TextView)findViewById( R.id.text3 );
		campoEndereco = (EditText)findViewById( R.id.campoEndereco );
		text4 = (TextView)findViewById( R.id.text4 );
		campoCep = (EditText)findViewById( R.id.campoCep );
		text5 = (TextView)findViewById( R.id.text5 );
		campoTelefone = (EditText)findViewById( R.id.campoTelefone );
		text6 = (TextView)findViewById( R.id.text6 );
		campoComplemento = (EditText)findViewById( R.id.campoComplemento );
		button1 = (Button)findViewById( R.id.button1 );
		
	}

	
	
	public void resetCampos(){
		campoCpf.setText("");
		campoCnpj.setText("");
		campoNome.setText("");;
		campoEndereco.setText("");;
		campoCep.setText("");
		campoTelefone.setText("");
		campoComplemento.setText("");;
	}
	
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_cadastro_pessoa);
		helper= new DatabaseHelper(getApplicationContext());
		findViews();
		loadPessoaTipo();
		if(campoTelefone.length() >0){
			campoTelefone.addTextChangedListener(Mask.insert(Mask.CELULAR_MASK,campoTelefone));
		}
		
		
		if(campoCep.length() > 0){
			campoCep.addTextChangedListener(Mask.insert(Mask.CEP_MASK,campoCep));
		}
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
	            @Override
	            public void onItemSelected(AdapterView<?> arg0, View arg1,
	                    int pos, long id) {
	
	                String workRequestType = arg0.getItemAtPosition(pos)
	                        .toString();
	                
	                if(workRequestType.equalsIgnoreCase("Pessoa Fisica")){
	                	cpf.setVisibility(View.VISIBLE);
	                	campoCpf.setVisibility(View.VISIBLE);
	                	cnpj.setVisibility(View.GONE);
	                	campoCnpj.setVisibility(View.GONE);  
	                	campoCpf.addTextChangedListener(Mask.insert(Mask.CPF_MASK,campoCpf));
	                	campoCpf.requestFocus();

	                }else if (workRequestType.equalsIgnoreCase("Pessoa Juridica")){
	                	cpf.setVisibility(View.GONE);
	                	campoCpf.setVisibility(View.GONE);
	                	cnpj.setVisibility(View.VISIBLE);
	                	campoCnpj.setVisibility(View.VISIBLE);
	                	campoCnpj.addTextChangedListener(Mask.insert(Mask.CNPJ_MASK,campoCnpj));
	                	campoCnpj.requestFocus();
	                }else{
	                	cpf.setVisibility(View.GONE);
	                	campoCpf.setVisibility(View.GONE);
	                	cnpj.setVisibility(View.GONE);
	                	campoCnpj.setVisibility(View.GONE);
	                	
	               
	                }
	
	            }
	
	            @Override
	            public void onNothingSelected(AdapterView<?> arg0) {
	                // TODO Auto-generated method stub
	
	            }
        });
				
		
		button1.setOnClickListener(new View.OnClickListener() {
	       	 
	            @Override
	            public void onClick(View arg0) {
	            	
	            		AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPessoaActivity.this);
	            		StringBuilder sb = new StringBuilder();
	            		boolean msg = false ;
	            		if (String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("SELECIONE")){
	            			sb.append("TIPO DE PESSOA - Obrigatório\n ");
	            		}
	            		
	            		if(campoNome.getText().toString().length() == 0){
							sb.append("NOME - Obrigatório \n");
							msg = true;
						}
	            		if (campoEndereco.getText().toString().length() == 0){
							sb.append("ENDEREÇO - Obrigatório \n");
							msg = true;
						}
	            		
	            		if(campoTelefone.getText().toString().length() == 0){
							sb.append("TELEFONE - Obrigatório \n");
							msg = true;
						}
	            		
	            		
	            		
	            		if (!String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("SELECIONE") && 
								String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Pessoa Fisica") && campoCpf.getText().length() == 0){
							sb.append("CPF - Obrigatório \n ");
							msg = true;
						}
	            		
	            		if (!String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("SELECIONE") && 
								String.valueOf(spinner.getSelectedItem()).equalsIgnoreCase("Pessoa Juridica") && campoCnpj.getText().toString().length() == 0){
							sb.append("CNPJ - Obrigatório \n ");
							msg = true;
						}
	            		
	            		
	           
						if(msg){
							builder.setTitle("Alerta ");
							builder.setMessage(sb.toString());
						}else{
							builder.setTitle("Deseja realizar o cadastro !");
						}
						
	            		builder.setPositiveButton(R.string.sim,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {

										try {
											
											PessoaDAO pessoaDAO = new PessoaDAO(getApplicationContext(),helper);
															
											pessoaDAO.addPessoa(campoNome.getText().toString(),
														(null != campoCpf.getText().toString()&& campoCpf.getText().length() > 0)?Long.valueOf(Mask.unmask(campoCpf.getText().toString())):0,
														(null != campoCnpj.getText().toString() && campoCnpj.getText().length() > 0)?Long.valueOf(Mask.unmask(campoCnpj.getText().toString())):0,
														Long.valueOf(null != campoTelefone.getText().toString()&& campoTelefone.getText().toString().length() > 0 ? Long.valueOf(Mask.unmask(campoTelefone.getText().toString())):0),
														null != campoEndereco.getText().toString() && campoEndereco.getText().toString().length() > 0?campoEndereco.getText().toString():"",
														Long.valueOf(null !=campoCep.getText().toString() && campoCep.getText().length() > 0?Long.valueOf(Mask.unmask(campoCep.getText().toString())):0),
														campoComplemento.getText().toString());
											resetCampos();
											loadPessoaTipo();
										} catch (Exception e) {
											e.printStackTrace();
										}

									}
								});
						builder.setNegativeButton(R.string.nao,new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int id) {
										return;
									}
								});
						
						AlertDialog dialog = builder.create();
						dialog.show();
						
	            		
	            	}
	            	
	            	
	        });
		
    }
    
    
    public void loadPessoaTipo(){
    	
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.pessoa_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
    	
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
		
		System.out.println("Entro Select");
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
