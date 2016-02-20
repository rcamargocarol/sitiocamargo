package br.com.sitio.camargo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.sitio.camargo.adaptador.ExpandableListAdapter;
import br.com.sitio.camargo.dao.DatabaseHelper;
import br.com.sitio.camargo.dao.PessoaDAO;
import br.com.sitio.camargo.util.Mask;
import br.com.sitio.camargo.vo.PessoaVO;

import com.example.sitiocamargo.R;

public class ListaCadastroPessoaActivity extends Activity implements OnItemSelectedListener{
	
	private DatabaseHelper helper;
	private CheckBox chekcbox;
	private TextView text;
	private CheckBox checkBox1;
	private TextView text1;
	private EditText editText1;
	private EditText editText2;
	private ImageButton imageButton1;
	ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    PessoaDAO pessoaDAO ;
    List<PessoaVO> lstPessoaVo ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main_consulta_pessoa);
		
		findViews();
		
        imageButton1.setOnClickListener(new View.OnClickListener() {
    	 
            @Override
            public void onClick(View arg0) {
            	
            	if(editText1.getText().toString().length()>0){
            		pessoaDAO = new PessoaDAO(getApplicationContext(), helper);
            		lstPessoaVo = pessoaDAO.getContatoByNome(editText1.getText().toString());
                    if(lstPessoaVo != null && lstPessoaVo.size() >0){
                    	prepareListData(lstPessoaVo);
                    }else{
                    	//TODO RETORNO CONTATO NAO ENCONTRADO
                    }
            	}
            	
            	
            }
        });
        
        
        
        expListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					final int groupPosition, final int childPosition, long id) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(ListaCadastroPessoaActivity.this);
				builder.setTitle("Deseja Atualizar dados ?");
				builder.setPositiveButton(R.string.sim,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								  final EditText input = new EditText(ListaCadastroPessoaActivity.this);  
								  LinearLayout ll = new LinearLayout(ListaCadastroPessoaActivity.this);
								  ll.setOrientation(LinearLayout.VERTICAL);

								  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
								       LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								  
								if (listDataChild
										.get(listDataHeader.get(groupPosition))
										.get(childPosition).contains("Cpf") || listDataChild
										.get(listDataHeader.get(groupPosition))
										.get(childPosition).contains("Cep") || listDataChild
										.get(listDataHeader.get(groupPosition))
										.get(childPosition)
										.contains("Telefone")) {
									input.setInputType(InputType.TYPE_CLASS_NUMBER);
								}								  
								  
								  ll.addView(input);
								  layoutParams.setMargins(30, 20, 30, 0);

								  Button okButton=new Button(ListaCadastroPessoaActivity.this);
								  okButton.setText("Gravar");
								  ll.addView(okButton, layoutParams);
								  
								  AlertDialog.Builder builder1 = new AlertDialog.Builder(ListaCadastroPessoaActivity.this);
								  builder1.setTitle("Entre com o valor");
								  builder1.setView(ll);
								  final AlertDialog dialog2 = builder1.create();
								  dialog2.show();
								  
								  okButton.setOnClickListener(new View.OnClickListener() {
								    	 
							            @Override
							            public void onClick(View arg0) {
							            	
							            	if(input.getText().toString().length()>0){
							            		pessoaDAO = new PessoaDAO(getApplicationContext(), helper);

							            		if(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contains("Cpf")){
							            			pessoaDAO.atualizaCpfByIdPessoa(input.getText().toString(),
							            					listDataHeader.get(groupPosition).split("-")[0].trim());
							            		}else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contains("Endereço")){
							            			pessoaDAO.atualizaEnderecoByIdPessoa(input.getText().toString(),
							            					listDataHeader.get(groupPosition).split("-")[0].trim());
							            		}else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contains("Cep")){
							            			pessoaDAO.atualizaCepByIdPessoa(input.getText().toString(),
							            					listDataHeader.get(groupPosition).split("-")[0].trim());
							            		}else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contains("Complemento")){
							            			pessoaDAO.atualizaComplementoByIdPessoa(input.getText().toString(),
							            					listDataHeader.get(groupPosition).split("-")[0].trim()); 
							            		}else if (listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).contains("Telefone")){
							            			pessoaDAO.atualizaTelefoneByIdPessoa(input.getText().toString(),
							            					listDataHeader.get(groupPosition).split("-")[0].trim()); 
							            		}
							            		lstPessoaVo = pessoaDAO.getContatoByNome(editText1.getText().toString());
							            		prepareListData(lstPessoaVo);
							            		dialog2.dismiss();
							            		Toast.makeText(
							            				getApplicationContext(),
							            				" Dados : "+input.getText().toString() + "\nAtualizado com sucesso"
							            				, Toast.LENGTH_SHORT)
							            				.show();
							            		listAdapter.notifyDataSetChanged();
							            	}
							            	
							            }
							        });
								  
								  
								
								  
								  
								  
							}
						});
				builder.setNegativeButton(R.string.nao,new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								return;
							}
						});
				
				AlertDialog dialog = builder.create();
				dialog.show();
				
				
				
                return false;
			}
		});
        
	}
	
	
	/**
	 * Find the Views in the layout<br />
	 * <br />
	 * Auto-created on 2015-12-08 19:57:37 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private void findViews() {
		text = (TextView)findViewById( R.id.text );
		text1 = (TextView)findViewById( R.id.text1 );
		editText1 = (EditText)findViewById( R.id.editText1 );
		imageButton1 = (ImageButton)findViewById( R.id.imageButton1 );
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView1);

	}

	/**
	 * Handle button click events<br />
	 * <br />
	 * Auto-created on 2015-12-08 19:57:37 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	public void onClick(View v) {
		if ( v == imageButton1 ) {
			// Handle clicks for imageButton1
		}
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
	
	
		private void prepareListData(List<PessoaVO> lstPessoaVo2) {
	        List<String> lstPessoAuxiliar ;
	        
	        listDataHeader = new ArrayList<String>();
	        listDataChild = new HashMap<String, List<String>>();
	        int i = 0;
	        for(PessoaVO pessoaVO : lstPessoaVo2){
	        	listDataHeader.add(pessoaVO.get_id()+"-"+ pessoaVO.getNome());
	        	lstPessoAuxiliar = new ArrayList<String>();
	        	lstPessoAuxiliar.add(null != pessoaVO.getCpf() && pessoaVO.getCpf().length() >0? "Cpf : "+Mask.mask(Mask.CPF_MASK,pessoaVO.getCpf()):"Cnpj : "+Mask.mask(Mask.CNPJ_MASK,pessoaVO.getCnpj()));
	        	lstPessoAuxiliar.add("Endereço : "+pessoaVO.getLogradouro());
	        	lstPessoAuxiliar.add("Cep : "+Mask.mask(Mask.CPF_MASK,String.valueOf(0 != pessoaVO.getCep()?""+pessoaVO.getCep():"0")));
	        	lstPessoAuxiliar.add("Complemento : "+ pessoaVO.getComplemento());
	        	lstPessoAuxiliar.add("Telefone : "+String.valueOf(pessoaVO.getNumeroTelefoneDDD())+"-"+pessoaVO.getNumeroTelefone());
	        	listDataChild.put(listDataHeader.get(i++),lstPessoAuxiliar);
	        }
	        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
	        expListView.setAdapter(listAdapter);
	       
	    }
		
		
		public void setNewItems(List<String> listDataHeader,HashMap<String, List<String>> listChildData) {
		    this.listDataHeader = listDataHeader;
		    this.listDataChild = listChildData;
		    listAdapter.notifyDataSetChanged();
		 }

}
