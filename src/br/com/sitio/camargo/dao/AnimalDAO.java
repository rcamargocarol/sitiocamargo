package br.com.sitio.camargo.dao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import br.com.sitio.camargo.constantes.AnimalTipo;
import br.com.sitio.camargo.constantes.Pasto;
import br.com.sitio.camargo.util.DateUtil;
import br.com.sitio.camargo.vo.AnimalVO;

import com.example.sitiocamargo.R;

public class AnimalDAO {
	
		// Database fields
	
		private static final String databaseName = "sitiocamargo.db";
		private static final String tabelaAnimal = "ANIMAL";
		private DatabaseHelper dbHelper;
		private AnimalVO animalVO ;
		private SimpleDateFormat sd= new SimpleDateFormat("dd/MM/yyyy");
		private String[] ANIMAL_TABLE_COLUMNS = {animalVO.columnID, animalVO.colum_ID_PASTO,animalVO.column_ID_ANIMAL_TIPO, animalVO.column_ANIMAL_QUANTIDADES,animalVO.column_DT_INSERT};
		
		private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
		
		private SimpleDateFormat dateFormatSqlLite = new SimpleDateFormat("yyyy-MM-dd");
		
		private SQLiteDatabase database;
		
		private Context context ;
		
		public DatabaseHelper getDbHelper() {
			return dbHelper;
		}

		public void setDbHelper(DatabaseHelper dbHelper) {
			this.dbHelper = dbHelper;
		}



		public static AnimalDAO animalDao ;
		
		public static AnimalDAO getInstance(){
			
			if(animalDao == null){
				animalDao = new AnimalDAO();
			}
			return animalDao;
		}
		
		public AnimalDAO() {
			// TODO Auto-generated constructor stub
		}
		
		
		
		public AnimalDAO(Context context, DatabaseHelper databaseHelper) {
			dbHelper = new DatabaseHelper(context);
			animalVO = new AnimalVO();
			this.context = context;
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}
		
		private String getDateTime(Date date) {
	        return dateFormat.format(date);
		}

		
		
		public AnimalVO addAnimal(int pasto, int animalTipo, int quantidades) throws ParseException {

			
			open();
			ContentValues values = new ContentValues();
			
			Date date = new Date();

			values.put(animalVO.colum_ID_PASTO,pasto);

			values.put(animalVO.column_ID_ANIMAL_TIPO,animalTipo);
			
			values.put(animalVO.column_ANIMAL_QUANTIDADES,quantidades);
			
			values.put("DT_INSERT",dateFormatSqlLite.format(Calendar.getInstance().getTime()));
			
			long animalId = database.insert(tabelaAnimal, null, values);

			if (animalId != -1) {
				Toast.makeText(this.context,R.string.registro_salvo, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this.context, R.string.erro_salvar,Toast.LENGTH_SHORT).show();
			}
			
			// now that the student is created return it ...
			Cursor cursor = database.query(tabelaAnimal,ANIMAL_TABLE_COLUMNS, animalVO.columnID + " = "
							+ animalId, null, null, null, null);

			cursor.moveToFirst();

			AnimalVO animalVO = parseStudent(cursor);
			cursor.close();
			return animalVO;
		}



		private AnimalVO parseStudent(Cursor cursor) throws ParseException {
			AnimalVO animalVO  = new AnimalVO();
			animalVO.set_id((cursor.getInt(0)));
			animalVO.setAnimalTipo(AnimalTipo.getEAnimalTipo(cursor.getInt(1)));
			animalVO.setPasto(Pasto.getEPasto(cursor.getInt(2)));
			animalVO.setQuantidade(cursor.getInt(3));
			animalVO.setDateInsert(cursor.getString(4));
			
			return animalVO;
		}
		
		
		
		/**
		 * Getting all labels
		 * returns list of labels
		 * */
		public List<String> getAllLabelPasto(){
		    List<String> labels = new ArrayList<String>();
		     
		    // Select All Query
		    String selectQuery = "SELECT  * FROM PASTO" ;
		  
		    SQLiteDatabase db = dbHelper.getReadableDatabase();
		   
		    Cursor cursor = db.rawQuery(selectQuery, null);
		  
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		            labels.add(cursor.getString(1));
		        } while (cursor.moveToNext());
		    }
		     
		    // closing connection
		    cursor.close();
		    db.close();
		     
		    // returning lables
		    return labels;
		}
		
		
		/**
		 * Getting all labels
		 * returns list of labels
		 * */
		public List<String> getAllLabelAnimalTipo(){
		    List<String> labels = new ArrayList<String>();
		     
		    // Select All Query
		    String selectQuery = "SELECT  * FROM ANIMAL_TIPO" ;
		  
		    SQLiteDatabase db = dbHelper.getReadableDatabase();
		   
		    Cursor cursor = db.rawQuery(selectQuery, null);
		  
		    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		            labels.add(cursor.getString(1));
		        } while (cursor.moveToNext());
		    }
		     
		    // closing connection
		    cursor.close();
		    db.close();
		     
		    // returning lables
		    return labels;
		}
		
		
		public List<AnimalVO> getAllByPasto(int pasto, String mesAno){
			
			Date dataPesquisaInicial, dataPesquisaFinal ;
			
			dataPesquisaInicial = DateUtil.obterPeriodoInicioMes(mesAno);
			dataPesquisaFinal = DateUtil.obterPeriodoFinalMes(mesAno);
			
			String query = " SELECT  ANIMAL._ID , ANIMAL_TIPO.DESCRICAO, PASTO.DESCRICAO, SUM(ANIMAL.QUANTIDADES), ANIMAL.DT_INSERT  FROM ANIMAL  " +
					" INNER JOIN ANIMAL_TIPO  ON ANIMAL_TIPO._ID  = ANIMAL.ID_ANIMAL_TIPO " +
					" INNER JOIN PASTO  ON PASTO._ID = ANIMAL.ID_PASTO  " +
					" WHERE ANIMAL.DT_INSERT BETWEEN '"+dateFormatSqlLite.format(dataPesquisaInicial) + "' AND '" + dateFormatSqlLite.format(dataPesquisaFinal) +"'"+  
					" AND PASTO._ID = " + pasto +
					" GROUP BY ANIMAL._ID , ANIMAL_TIPO.DESCRICAO, PASTO.DESCRICAO ; " ;
			
			
    		SQLiteDatabase db = dbHelper.getReadableDatabase();
					
    		List<AnimalVO> lstAnimalVO = new ArrayList<AnimalVO>();
    		
    		int somatoriaGado = 0 ;
    		
		    Cursor cursor = db.rawQuery(query, null);
		    
		   
				  
			AnimalVO animalVO = null ;	    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		            
		        	animalVO  = new AnimalVO();
					animalVO.set_id((cursor.getInt(0)));
					animalVO.setDescricaoAnimalTipo(cursor.getString(1));
					animalVO.setDescricaoPasto(cursor.getString(2));
					animalVO.setQuantidade(cursor.getInt(3));
					somatoriaGado+= cursor.getInt(3);
					animalVO.setTotalGeralPasto(somatoriaGado);
					try {
						Date date = dateFormatSqlLite.parse(cursor.getString(4));
						animalVO.setDateInsert(dateFormatSqlLite.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	lstAnimalVO.add(animalVO);
		        } while (cursor.moveToNext());
		    }
		     
		    // closing connection
		    cursor.close();
		    db.close();
			
		    return lstAnimalVO	;
		
			
		}
		
		
		public List<AnimalVO> getAllByPeriodo(String mesAno){
			
			Date dataPesquisaInicial, dataPesquisaFinal ;
			
			dataPesquisaInicial = DateUtil.obterPeriodoInicioMes(mesAno);
			dataPesquisaFinal = DateUtil.obterPeriodoFinalMes(mesAno);
			
			String query = " SELECT  ANIMAL._ID , ANIMAL_TIPO.DESCRICAO, PASTO.DESCRICAO, SUM(ANIMAL.QUANTIDADES), ANIMAL.DT_INSERT  FROM ANIMAL  " +
					" INNER JOIN ANIMAL_TIPO  ON ANIMAL_TIPO._ID  = ANIMAL.ID_ANIMAL_TIPO " +
					" INNER JOIN PASTO  ON PASTO._ID = ANIMAL.ID_PASTO  " +
					" WHERE  ANIMAL.DT_INSERT BETWEEN '"+dateFormatSqlLite.format(dataPesquisaInicial) + "' AND '" + dateFormatSqlLite.format(dataPesquisaFinal) +"'"+  
					" GROUP BY ANIMAL._ID , ANIMAL_TIPO.DESCRICAO, PASTO.DESCRICAO ; " ;
			
			
    		SQLiteDatabase db = dbHelper.getReadableDatabase();
					
    		List<AnimalVO> lstAnimalVO = new ArrayList<AnimalVO>();
    		
    		int somatoriaGado = 0 ;
    		
		    Cursor cursor = db.rawQuery(query, null);
				  
			AnimalVO animalVO = null ;	    // looping through all rows and adding to list
		    if (cursor.moveToFirst()) {
		        do {
		            
		        	animalVO  = new AnimalVO();
					animalVO.set_id((cursor.getInt(0)));
					animalVO.setDescricaoAnimalTipo(cursor.getString(1));
					animalVO.setDescricaoPasto(cursor.getString(2));
					animalVO.setQuantidade(cursor.getInt(3));
					somatoriaGado+= cursor.getInt(3);
					animalVO.setTotalGeralPasto(somatoriaGado);
					
					try {
						Date date = dateFormatSqlLite.parse(cursor.getString(4));
						animalVO.setDateInsert(dateFormatSqlLite.format(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		        	lstAnimalVO.add(animalVO);
		        } while (cursor.moveToNext());
		    }
		     
		    // closing connection
		    cursor.close();
		    db.close();
			
		    return lstAnimalVO	;
		
			
		}
		
		
		public void removeItemAnimal(String id){
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			String where [] = new String[]{ id };
			db.delete("ANIMAL", "_id = ?", where);
		}
		
	
}
	

