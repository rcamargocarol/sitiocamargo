package br.com.sitio.camargo.dao;

import java.io.File;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	
private static final int DATABASE_VERSION = 3;

private static final String DATABASE_CREATE_ANIMAL = "CREATE TABLE IF NOT EXISTS ANIMAL (_id INTEGER PRIMARY KEY,ID_PASTO INT,ID_ANIMAL_TIPO INT,QUANTIDADES INTEGER, dt_insert DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY(ID_ANIMAL_TIPO) REFERENCES ANIMAL_TIPO(_id),FOREIGN KEY(ID_PASTO) REFERENCES PASTO(_id));";

private static final String DATABASE_CREATE_ANIMAL_TIPO = "CREATE TABLE IF NOT EXISTS ANIMAL_TIPO (_id INTEGER PRIMARY KEY,  DESCRICAO VARCHAR);";

private static final String DATABASE_CREATE_PASTO = "CREATE TABLE IF NOT EXISTS PASTO (_id INTEGER PRIMARY KEY,  DESCRICAO VARCHAR)";

private static final String DATABASE_CREATE_PESSOA = "CREATE TABLE PESSOA (_id INTEGER PRIMARY KEY , NOME VARCHAR(60), dt_insert DATETIME DEFAULT CURRENT_TIMESTAMP);";

private static final String DATABASE_CREATE_PESSOA_FISICA = "CREATE TABLE PESSOA_FISICA  (_id INTEGER PRIMARY KEY, CPF INTEGER, ID_PESSOA INTEGER, FOREIGN KEY(ID_PESSOA) REFERENCES PESSOA(_ID));";

private static final String DATABASE_CREATE_PESSOA_JURIDICA = "CREATE TABLE PESSOA_JURIDICA(_id INTEGER PRIMARY KEY, CNPJ INTEGER, DESCRICAO, ID_PESSOA INTEGER, FOREIGN KEY(ID_PESSOA) REFERENCES PESSOA(_ID));";

private static final String DATABASE_CREATE_ENDERECO = "CREATE TABLE ENDERECO (_id INTEGER PRIMARY KEY, LOGRADOURO VARCHAR(50), CEP NUMBER (10), COMPLEMENTO VARCHAR (30), ID_PESSOA INTEGER, FOREIGN KEY(ID_PESSOA) REFERENCES PESSOA(_ID));";

private static final String DATABASE_CREATE_TELEFONE = "CREATE TABLE TELEFONE(_id INTEGER PRIMARY KEY, NUMERO_DDD  NUMBER (4) , NUMERO_TELEFONE  NUMBER (12), ID_PESSOA INTEGER, FOREIGN KEY(ID_PESSOA) REFERENCES PESSOA(_ID) );";

private static final String DATABASE_CREATE_DESPESAS = "CREATE TABLE DESPESAS (_id INTEGER PRIMARY KEY, DESCRICAO VARCHAR(80), VALOR NUMBER , ID_PESSOA INTEGER, dt_insert DATETIME DEFAULT CURRENT_TIMESTAMP,  FOREIGN KEY(ID_PESSOA) REFERENCES PESSOA(_ID));";

public static final String DATABASE_NAME = "sitioCamargo.do";

public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
}
@Override
public void onCreate(SQLiteDatabase db) {

	db.execSQL(DATABASE_CREATE_PASTO);
	db.execSQL(DATABASE_CREATE_ANIMAL_TIPO);
	db.execSQL(DATABASE_CREATE_ANIMAL);
	db.execSQL(DATABASE_CREATE_PESSOA);
	db.execSQL(DATABASE_CREATE_PESSOA_FISICA);
	db.execSQL(DATABASE_CREATE_PESSOA_JURIDICA);
	db.execSQL(DATABASE_CREATE_ENDERECO);
	db.execSQL(DATABASE_CREATE_TELEFONE);
	db.execSQL(DATABASE_CREATE_DESPESAS);
	
	
	db.execSQL("INSERT INTO PASTO VALUES (0,'SELECIONE');");
	db.execSQL("INSERT INTO PASTO VALUES (1,'CARLINHOS');");
	db.execSQL("INSERT INTO PASTO VALUES (2,'TIMOTEO_MATA');");
	db.execSQL("INSERT INTO PASTO VALUES (3,'DORCILIO');");
	db.execSQL("INSERT INTO PASTO VALUES (4,'LIMA_SEDE');");
	db.execSQL("INSERT INTO PASTO VALUES (5,'PAULINHO');");
	db.execSQL("INSERT INTO PASTO VALUES (6,'DIOLA');");
	db.execSQL("INSERT INTO PASTO VALUES (7,'SITINHO_JOAQUIM');");
	db.execSQL("INSERT INTO PASTO VALUES (8,'PASTO_LEITEIRAS');");
	db.execSQL("INSERT INTO PASTO VALUES (9,'TODOS_OS_PASTOS');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (0,'SELECIONE');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (1,'VACA');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (2,'BOI');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (3,'BEZERRO');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (4,'BEZERRA');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (5,'NOVILHA');");
	db.execSQL("INSERT INTO ANIMAL_TIPO VALUES (6,'CAVALO');");	
	
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// TODO Auto-generated method stub
	
}
}

class DatabaseContext extends ContextWrapper {

private static final String DEBUG_CONTEXT = "DatabaseContext";

public DatabaseContext(Context base) {
    super(base);
}

@Override
public File getDatabasePath(String name) 
{
    File sdcard = Environment.getExternalStorageDirectory();    
    String dbfile = sdcard.getAbsolutePath() + File.separator+ "databases" + File.separator + name;

    if (!dbfile.endsWith(".db"))
    {
        dbfile += ".db" ;
    }

    File result = new File(dbfile);

    if (!result.getParentFile().exists())
    {
        result.getParentFile().mkdirs();
    }

    if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN))
    {
        Log.w(DEBUG_CONTEXT,
                "getDatabasePath(" + name + ") = " + result.getAbsolutePath());
    }

    return result;
}

@Override
public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) 
{
    SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
    // SQLiteDatabase result = super.openOrCreateDatabase(name, mode, factory);
    if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN))
    {
        Log.w(DEBUG_CONTEXT,
                "openOrCreateDatabase(" + name + ",,) = " + result.getPath());
    }
    return result;
}



}



