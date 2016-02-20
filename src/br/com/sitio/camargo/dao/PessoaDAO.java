package br.com.sitio.camargo.dao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import br.com.sitio.camargo.vo.PessoaVO;

import com.example.sitiocamargo.R;

public class PessoaDAO {


			private static final String tabelaPessoa = "PESSOA";
			private static final String tabelaPessoaFisica = "PESSOA_FISICA";
			
			private static final String tabelaPessoaJuridica = "PESSOA_JURIDICA";
			private static final String tabelaPessoaEndereco = "ENDERECO";
			private static final String tabelaTelefone = "TELEFONE" ;
			
			private DatabaseHelper dbHelper;
			private PessoaVO pessoaVO;
			
			private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
			
			private SQLiteDatabase database;
			
			private Context context ;
			
			public DatabaseHelper getDbHelper() {
				return dbHelper;
			}

			public void setDbHelper(DatabaseHelper dbHelper) {
				this.dbHelper = dbHelper;
			}



			public static PessoaDAO pessoaDao ;
			
			public static PessoaDAO getInstance(){
				
				if(pessoaDao == null){
					pessoaDao = new PessoaDAO();
				}
				return pessoaDao;
			}
			
			public PessoaDAO() {
				// TODO Auto-generated constructor stub
			}
			
			
			
			public PessoaDAO(Context context, DatabaseHelper databaseHelper) {
				dbHelper = new DatabaseHelper(context);
				pessoaVO = new PessoaVO();
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

			public long insertTablePessoa(String nome){
				ContentValues initialValues = new ContentValues();
				initialValues.put(pessoaVO.columnNome, nome);
				initialValues.put(pessoaVO.columndtInsert, dateFormat.format(Calendar.getInstance().getTime()));
				return database.insert(tabelaPessoa, null, initialValues);
			}
			
			public long insertTablePessoaFisica(long cpf, long idPessoa){
					ContentValues initialValues = new ContentValues();
					initialValues.put(pessoaVO.columncpf,cpf);
					initialValues.put(pessoaVO.columID_Pessoa,String.valueOf(idPessoa));
					return database.insert(tabelaPessoaFisica, null, initialValues);
			}
			
			public long insertTableEndereco(String logradouro, long cep, String complemento, long idPessoa){
				ContentValues initialValues = new ContentValues();
				initialValues = new ContentValues();
				initialValues.put(pessoaVO.columnlogradouro,logradouro);
				initialValues.put(pessoaVO.columnCEP,cep);
				initialValues.put(pessoaVO.columnComplemento,complemento);
				initialValues.put(pessoaVO.columID_Pessoa,String.valueOf(idPessoa));
				return database.insert(tabelaPessoaEndereco, null, initialValues);
			}
			
			public long insertTableTelefone(String telefone,long idPessoa){
				ContentValues initialValues = new ContentValues();
				initialValues = new ContentValues();
				initialValues.put(pessoaVO.columnTelefoneDD,String.valueOf(telefone).substring(0,2));
				initialValues.put(pessoaVO.columnTelefoneNumero,String.valueOf(telefone).substring(2,String.valueOf(telefone).length()));
				initialValues.put(pessoaVO.columID_Pessoa,idPessoa);
				
				return database.insert(tabelaTelefone, null, initialValues);
			}
			
			public long insertTablePessoaJuridica(long cnpj, String nome,long idPessoa){
				ContentValues initialValues = new ContentValues();
				initialValues = new ContentValues();
				initialValues.put(pessoaVO.columncnpj,cnpj);
				initialValues.put(pessoaVO.columnRazaoSocial,nome);
				initialValues.put(pessoaVO.columID_Pessoa,idPessoa);
				return database.insert(tabelaPessoaJuridica, null, initialValues);
			}
			
			
			
			public void addPessoa(String nome, long cpf , long cnpj, long telefone, String logradouro, long cep, String complemento) throws ParseException {
				
				open();

				try {
					
					long IdPessoa = insertTablePessoa(nome);
					if (IdPessoa != -1) {
						if(cpf != 0){
							long idPessoaFisica = insertTablePessoaFisica(cpf, IdPessoa);
							if(idPessoaFisica !=-1){
								long idEnderecoPessoaFisica = insertTableEndereco(logradouro,cep,complemento,IdPessoa);
								if(idEnderecoPessoaFisica != -1){
									long idTelefone = insertTableTelefone(String.valueOf(telefone),IdPessoa);
									if(idTelefone != -1){
										Toast.makeText(this.context,R.string.registro_salvo, Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(this.context,R.string.erro_salvar, Toast.LENGTH_SHORT).show();
									}
							
								}else{
									Toast.makeText(this.context,R.string.erro_salvar, Toast.LENGTH_SHORT).show();
								}
								
							}
							
						}else{
							
							long idPessoaJuridica = insertTablePessoaJuridica(cnpj, nome, IdPessoa);
							if(idPessoaJuridica!=1){
								long idEnderecoPessoaJuridica = insertTableEndereco(logradouro,cep,complemento,IdPessoa);
								if(idEnderecoPessoaJuridica != -1){
									long idTelefone = insertTableTelefone(String.valueOf(telefone),IdPessoa);
									if(idTelefone != -1){
										Toast.makeText(this.context,R.string.registro_salvo, Toast.LENGTH_SHORT).show();
									}else{
										Toast.makeText(this.context,R.string.erro_salvar, Toast.LENGTH_SHORT).show();
									}
								}else{
									Toast.makeText(this.context,R.string.erro_salvar, Toast.LENGTH_SHORT).show();
								}
								
							}
							
						}
						
					} else {
						Toast.makeText(this.context, R.string.erro_salvar,Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			
			public void removeItemAnimal(String id){
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				String where [] = new String[]{ id };
				db.delete("ANIMAL", "_id = ?", where);
			}
			
			
			public List<PessoaVO> getContatoByNome(String nome){
				
				
				String query = "SELECT PESSOA._ID, PESSOA.NOME, PESSOA.DT_INSERT, PESSOA_FISICA.CPF, PESSOA_JURIDICA.CNPJ , " +
						" ENDERECO.LOGRADOURO , ENDERECO.CEP, ENDERECO.COMPLEMENTO, TELEFONE.NUMERO_DDD, TELEFONE.NUMERO_TELEFONE " +
						" FROM PESSOA LEFT JOIN PESSOA_FISICA ON PESSOA_FISICA.ID_PESSOA = PESSOA._ID  " +
						" LEFT JOIN PESSOA_JURIDICA ON PESSOA_JURIDICA.ID_PESSOA = PESSOA._ID LEFT JOIN ENDERECO ON ENDERECO.ID_PESSOA = PESSOA._ID "+  
						" LEFT JOIN TELEFONE ON TELEFONE.ID_PESSOA = PESSOA._ID "  +
						" WHERE UPPER(PESSOA.NOME) LIKE UPPER('"+ nome + "%');";
				
	    		SQLiteDatabase db = dbHelper.getReadableDatabase();
						
	    		List<PessoaVO> lstPessoaVO = new ArrayList<PessoaVO>();
	    		
	    		int somatoriaGado = 0 ;
	    		
			    Cursor cursor = db.rawQuery(query, null);
			    
			    String [] columnNames = cursor.getColumnNames();
					  
				PessoaVO pessoaVO = null ;	    
			    if (cursor.moveToFirst()) {
			        do {
			        	pessoaVO  = new PessoaVO();
			        	pessoaVO.set_id(cursor.getInt(cursor.getColumnIndex(columnNames[0])));
			        	pessoaVO.setNome(cursor.getString(cursor.getColumnIndex(columnNames[1])));
			        	pessoaVO.setDateYYYYMMdd(cursor.getString(cursor.getColumnIndex(columnNames[2])));
			        	pessoaVO.setCpf(cursor.getString(cursor.getColumnIndex(columnNames[3])));
			        	pessoaVO.setCnpj(cursor.getString(cursor.getColumnIndex(columnNames[4])));
			        	pessoaVO.setLogradouro(cursor.getString(cursor.getColumnIndex(columnNames[5])));
			        	pessoaVO.setCep(cursor.getInt(cursor.getColumnIndex(columnNames[6])));
			        	pessoaVO.setComplemento(cursor.getString(cursor.getColumnIndex(columnNames[7])));
			        	pessoaVO.setNumeroTelefoneDDD(cursor.getInt(cursor.getColumnIndex(columnNames[8])));
			        	pessoaVO.setNumeroTelefone(cursor.getInt(cursor.getColumnIndex(columnNames[9])));
			        	lstPessoaVO.add(pessoaVO);
			        } while (cursor.moveToNext());
			    }

			    cursor.close();
			    db.close();
				
			    return lstPessoaVO	;
			
				
			}
			
			
			
			
			public List<String> getAllAgente(){
				
				String query = "SELECT PESSOA._ID, PESSOA.NOME FROM PESSOA LEFT JOIN PESSOA_FISICA ON PESSOA_FISICA.ID_PESSOA = PESSOA._ID  " +
						" LEFT JOIN PESSOA_JURIDICA ON PESSOA_JURIDICA.ID_PESSOA = PESSOA._ID "  ;
				
	    		SQLiteDatabase db = dbHelper.getReadableDatabase();
						
	    		List<String> lstPessoaVO = new ArrayList<String>();
	    		
			    Cursor cursor = db.rawQuery(query, null);
			    
			    String [] columnNames = cursor.getColumnNames();
					  
			    if (cursor.moveToFirst()) {
			    	lstPessoaVO.add("Secione Agente");
			        do {
			        	lstPessoaVO.add(" "+cursor.getInt(cursor.getColumnIndex(columnNames[0]))+"-"+cursor.getString(cursor.getColumnIndex(columnNames[1])));
			        } while (cursor.moveToNext());
			    }

			    cursor.close();
			    db.close();

			    return lstPessoaVO	;
			
				
			}
			
			
			public void atualizaCpfByIdPessoa(String valor,String idPessoa) {

				ContentValues cv = new ContentValues();
				cv.put("CPF",valor);
				
	    		SQLiteDatabase db = dbHelper.getWritableDatabase();
				
	    		db.update("PESSOA_FISICA", cv, "_id = ?",new String[]{ idPessoa });
	    	}

			public void atualizaEnderecoByIdPessoa(String valor, String IdPessoa) {

				ContentValues cv = new ContentValues();
				cv.put("LOGRADOURO",valor);
				
	    		SQLiteDatabase db = dbHelper.getWritableDatabase();
				
	    		db.update("ENDERECO", cv, "_id = ?",new String[]{ IdPessoa });
				
			}

			public void atualizaCepByIdPessoa(String valor, String IdPessoa) {

				
				ContentValues cv = new ContentValues();
				cv.put("CEP",valor);
				
	    		SQLiteDatabase db = dbHelper.getWritableDatabase();
				
	    		db.update("ENDERECO", cv, "_id = ?",new String[]{ IdPessoa });
				
			}

			public void atualizaComplementoByIdPessoa(String valor, String idPessoa) {
				ContentValues cv = new ContentValues();
				cv.put("COMPLEMENTO",valor);
				
	    		SQLiteDatabase db = dbHelper.getWritableDatabase();
				
	    		db.update("ENDERECO", cv, "_id = ?",new String[]{ idPessoa });
				
				
			}

			public void atualizaTelefoneByIdPessoa(String valor, String idPessoa) {

				ContentValues cv = new ContentValues();
				cv.put(pessoaVO.columnTelefoneDD,String.valueOf(valor).substring(0,2));
				cv.put(pessoaVO.columnTelefoneNumero,String.valueOf(valor).substring(2,String.valueOf(valor).length()));
				
	    		SQLiteDatabase db = dbHelper.getWritableDatabase();
				
	    		db.update("TELEFONE", cv, "_id = ?",new String[]{ idPessoa });
				
			}
			
		
	}
		


	
	
