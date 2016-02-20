package br.com.sitio.camargo.activity;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import br.com.sitio.camargo.dao.AnimalDAO;
import br.com.sitio.camargo.dao.DatabaseHelper;
import br.com.sitio.camargo.dao.DespesaDAO;
import br.com.sitio.camargo.util.Mask;
import br.com.sitio.camargo.vo.AnimalVO;
import br.com.sitio.camargo.vo.DespesaVO;

import com.example.sitiocamargo.R;

public class ListaConsultaLancamentoDespesaActivity extends Activity implements OnItemSelectedListener{
	
	private DatabaseHelper helper;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private Spinner spinner1;
	private ImageButton btnBuscar;
	private ListView listView1;
	private SimpleAdapter dataAdapter;
	private EditText editTextMesAnoInicial, editTextMesAnoFinal;
	private DespesaDAO despesaDAO ;
	private List<DespesaVO> lstDespesaVOs;
	private NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_consulta_lancamento_despesa);
		helper= new DatabaseHelper(getApplicationContext());
		findViews();
		
		
		
		btnBuscar.setOnClickListener(new View.OnClickListener() {
	    	 
	            @Override
	            public void onClick(View arg0) {
	            	
	            		try {
	            			despesaDAO = new DespesaDAO(getApplicationContext(), helper);
	            			
	            			lstDespesaVOs = despesaDAO.getLancamentoDespesaByPeriodo(editTextMesAnoInicial.getText().toString(), editTextMesAnoFinal.getText().toString());
	            			if(lstDespesaVOs != null && lstDespesaVOs.size() >0){
	            				exibeLancamentoDespesa(lstDespesaVOs);
	                    		Object o = lstDespesaVOs.get(lstDespesaVOs.size()-1);
	                   			textView3.setText(String.valueOf(nf.format(((DespesaVO)o).getValorTotalDespesaPeriodo())));
	                    			
	            			}else{
	            				//TODO RETORNO CONTATO NAO ENCONTRADO
	            			}
							
						} catch (Exception e) {
							// TODO: handle exception
						}
	            	
	            	}
	        });
		
		
	}

	/**
	 * Find the Views in the layout<br />
	 * <br />
	 * Auto-created on 2015-12-24 10:25:18 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private void findViews() {
		textView1 = (TextView)findViewById( R.id.textView1 );
		textView2 = (TextView)findViewById( R.id.textView2 );
		textView3 = (TextView)findViewById( R.id.textView3 );
		spinner1 = (Spinner)findViewById( R.id.spinner1 );
		btnBuscar = (ImageButton)findViewById( R.id.btnBuscar );
		listView1 = (ListView)findViewById( R.id.listView1 );
		editTextMesAnoInicial = (EditText)findViewById(R.id.input_Mes_Ano_Inicial);
		editTextMesAnoInicial.addTextChangedListener(Mask.insert(Mask.MES_ANO,editTextMesAnoInicial));
		editTextMesAnoFinal = (EditText)findViewById(R.id.input_Mes_Ano_Final);
		editTextMesAnoFinal.addTextChangedListener(Mask.insert(Mask.MES_ANO,editTextMesAnoFinal));
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
	
	
	
	public void exibeLancamentoDespesa(List<DespesaVO> despesaVO){
		
		String []from ;
		int [] to ;
		final List<HashMap<String,String>> lstHashMaps = converteArrayListToHashMap(despesaVO);
		
		
		from = new String[]{"NOME","DESCRICAO", "VALOR","DT_INSERT"};
		to = new int []{R.id.AGENTE,  R.id.DESCRICAO_DESPESA, R.id.VALOR_DESPESA, R.id.DT_INSERT};
		dataAdapter = new SimpleAdapter(this,lstHashMaps,R.layout.row_despesas, from, to);
			
		listView1.setAdapter(dataAdapter);
		
		listView1.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
	             
	            	final HashMap<String, String> hashMapElementoAdapter = lstHashMaps.get(position);
	            	final String item = hashMapElementoAdapter.get("_ID");
	            	AlertDialog.Builder builder = new AlertDialog.Builder(ListaConsultaLancamentoDespesaActivity.this);
	            	builder.setTitle("Deletar ?");

	            	builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
            	           public void onClick(DialogInterface dialog, int id) {
            	        	   
            	        	   lstHashMaps.remove(position);
            	        	   removerItenAdapter(item);
            	        	   dataAdapter.notifyDataSetChanged();
            	        	   BigDecimal totalTextView = new BigDecimal(textView3.getText().toString().replaceAll("[^\\d]",""));
            	        	   BigDecimal valorItemRemovido = new BigDecimal(hashMapElementoAdapter.get("VALOR").replaceAll("[^\\d]",""));
            	       		   textView3.setText(nf.format(totalTextView.subtract(valorItemRemovido).divide(new BigDecimal(100)).doubleValue()));
            	        	   	
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
		DespesaDAO despesaDAO = new DespesaDAO(getApplicationContext(),helper);
		despesaDAO.removeItemDespesa(item);
	}
	
	
	private List<HashMap<String,String>> converteArrayListToHashMap(List<DespesaVO> despesaVOs) {
		List<HashMap<String,String>> lhm = new ArrayList<HashMap<String,String>>();
		for(DespesaVO despesaVO : despesaVOs){
			HashMap<String,String> hm = new HashMap<String, String>(); 
			hm.put("_ID",String.valueOf(despesaVO.get_id()));
			hm.put("NOME",despesaVO.getNomePessoa());
			hm.put("DESCRICAO",despesaVO.getDescricao());
			hm.put("VALOR",String.valueOf(nf.format(despesaVO.getValorDespesa())));
			hm.put("DT_INSERT",despesaVO.getDataInsercao());
			lhm.add(hm);
			
		}
		
		return lhm ;
		
	}
	
	

}
