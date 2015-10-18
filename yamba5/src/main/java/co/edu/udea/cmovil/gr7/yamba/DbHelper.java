package co.edu.udea.cmovil.gr7.yamba;

/**
 * Created by johnj_000 on 15/10/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG=DbHelper.class.getSimpleName();

    public DbHelper(Context context){
        super(context, StatusContract.DB_NAME,null, StatusContract.DB_VERSION);
        //
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String sql=String
                .format("create table %s(%s int primary key, %s text, %s text, %s int)",
                        StatusContract.TABLE, StatusContract.Column.ID,
                        StatusContract.Column.USER,
                        StatusContract.Column.MESSAGE,
                        StatusContract.Column.CREATED_AT);
        //Sentencia para crear tabla
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);//Ejecución de la sentencia
    }

    //Se llama cada que el schema cambie(nueva versión)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int NewVersion){
        db.execSQL("drop table if exists "+ StatusContract.TABLE);//Borrar datos
        onCreate(db);//Crear Tabla de nuevo
    }

}
