package br.com.sitio.camargo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.sitiocamargo.R;

public class MainActivity extends Activity {
	
	ImageView image;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
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
	
}
