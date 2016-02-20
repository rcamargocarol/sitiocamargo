package br.com.sitio.camargo.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.sitiocamargo.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import br.com.sitio.camargo.util.DateUtil;
import br.com.sitio.camargo.vo.AnimalVO;
import br.com.sitio.camargo.vo.DespesaVO;
import br.com.sitio.camargo.vo.PessoaVO;

public class DespesaDAO {

	
	private DatabaseHelper dbHelper;
	private static final String tabelaDespesas = "DESPESAS";
	
	private SimpleDateFormat dateFormatSqlLite = new SimpleDateFormat("yyyy-MM-dd");
	
	
	private SQLiteDatabase database;
	
	
	private Context context ;
	
	public DespesaDAO(Context context, DatabaseHelper databaseHelper) {
		dbHelper = new DatabaseHelper(context);
		this.context = context;
	}
	
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	
	
	public boolean insertTableDespesas(String descricao, double valor, int identificacaoAgente, String mesAnoLancamento){
		open();
		ContentValues initialValues = new ContentValues();
		initialValues.put("DESCRICAO", descricao);
		initialValues.put("VALOR", valor/100);
		initialValues.put("ID_PESSOA",identificacaoAgente);
		initialValues.put("dt_insert" , dateFormatSqlLite.format(DateUtil.obterdiaAtualByMesAno(mesAnoLancamento)));
		
		long despesaID = database.insert(tabelaDespesas, null, initialValues);
	
		if(despesaID != -1){
			Toast.makeText(this.context,R.string.registro_salvo, Toast.LENGTH_SHORT).show();
			return true;
		}else{
			Toast.makeText(this.context,R.string.erro_salvar, Toast.LENGTH_SHORT).show();
			return false;
		}
	}
	
	
	public List<DespesaVO> getLancamentoDespesaByPeriodo(String mesAnoInicial,String mesAnoFinal){
		
		Date dataPesquisaInicial, dataPesquisaFinal;
		dataPesquisaInicial = DateUtil.obterPeriodoInicioMes(mesAnoInicial);
		dataPesquisaFinal = DateUtil.obterPeriodoFinalMes(mesAnoFinal);
		
		

		String query = " SELECT  DESPESAS._ID , PESSOA.NOME,  DESPESAS.DESCRICAO, DESPESAS.VALOR, DESPESAS.DT_INSERT  FROM DESPESAS "
				+ " INNER JOIN PESSOA  ON PESSOA._ID = DESPESAS.ID_PESSOA "
				+ " WHERE  DESPESAS.DT_INSERT BETWEEN '"
				+ dateFormatSqlLite.format(dataPesquisaInicial)
				+ "' AND '"
				+ dateFormatSqlLite.format(dataPesquisaFinal) + "'";

		SQLiteDatabase db = null;

		List<DespesaVO> despesaVOList = new ArrayList<DespesaVO>();

		double somatoriaDespesas = 0;

		Cursor cursor = null;
		try {
			db = dbHelper.getReadableDatabase();
			cursor = db.rawQuery(query, null);
			String[] columnNames = cursor.getColumnNames();

			DespesaVO despesaVO = null; // looping through all rows and adding
										// to list
			if (cursor.moveToFirst()) {
				do {

					despesaVO = new DespesaVO();
					despesaVO.set_id(cursor.getInt(cursor
							.getColumnIndex(columnNames[0])));
					despesaVO.setNomePessoa(cursor.getString(cursor
							.getColumnIndex(columnNames[1])));
					despesaVO.setDescricao(cursor.getString(cursor
							.getColumnIndex(columnNames[2])));
					despesaVO.setValorDespesa(cursor.getDouble(cursor
							.getColumnIndex(columnNames[3])));
					try {
						Date date = dateFormatSqlLite.parse(cursor.getString(4));
						despesaVO.setDataInsercao(dateFormatSqlLite.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					somatoriaDespesas+= despesaVO.getValorDespesa();
					despesaVO.setValorTotalDespesaPeriodo(somatoriaDespesas);
					despesaVOList.add(despesaVO);
				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			cursor.close();
			db.close();
		}

		// closing connection
		return despesaVOList;
	
		
	}

	public void removeItemDespesa(String item) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String where[] = new String[] { item };
		db.delete("DESPESAS", "_id = ?", where);
	}
	
	
	
}
