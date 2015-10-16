package co.edu.udea.cmovil.gr7.yamba;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class StatusProvider extends ContentProvider {
    private static final String TAG = StatusProvider.class.getSimpleName();
    private DbHelper dbhelper;

    public StatusProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)){
            case StatusContract.STATUS_DIR:
                //Se elminaran varios registros
                where = (selection == null) ? "1" : selection;
                break;
            case StatusContract.STATUS_ITEM:
                //Se eliminara un solo registro
                long id = ContentUris.parseId(uri);
                where = StatusContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( "
                        + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int ret = db.delete(StatusContract.TABLE, where, selectionArgs); // Elimina
        if (ret>0) {
            // Informa que la informacion para esta URI ha cambiado
            getContext().getContentResolver().notifyChange(uri,null);
        }
        Log.d(TAG, "deleted records: " + ret);
        return ret;
    }

    @Override
    public String getType(Uri uri) {
        switch (sURIMatcher.match(uri)){
            case StatusContract.STATUS_DIR: Log.d(TAG,"goType: "+StatusContract.STATUS_TYPE_DIR);
                return  StatusContract.STATUS_TYPE_DIR;
            case StatusContract.STATUS_ITEM: Log.d(TAG,"goType: "+StatusContract.STATUS_TYPE_ITEM);
                return  StatusContract.STATUS_TYPE_ITEM;
            default:
            throw new UnsupportedOperationException("ilegal uri: " + uri);
        }
    } //retorna el MIME type apropiado que se definiò en StatusContract

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri ret = null;

        if (sURIMatcher.match(uri) != StatusContract.STATUS_DIR){
            //verifica que no sea el directorio completo
            throw new IllegalArgumentException("Ilegal uri: " + uri);
        }
        SQLiteDatabase db = dbhelper.getWritableDatabase(); //Obtione instancia de la BD
        long rowId = db.insertWithOnConflict(StatusContract.TABLE,null,values,SQLiteDatabase.CONFLICT_IGNORE); //inserta

        if(rowId != -1) {
            long id = values.getAsLong(StatusContract.Column.ID);
            ret = ContentUris.withAppendedId(uri, id);
            Log.d(TAG,"inserted uri: "+ ret);
            //notifica que la informacion para la uri ha cambiado
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return ret;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreated");
        dbhelper = new DbHelper(getContext());
        return true;
    }

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(StatusContract.AUTHORITY,StatusContract.TABLE,StatusContract.STATUS_DIR);
        //si la uri no termina en un numero se refiere a la coleccion
        sURIMatcher.addURI(StatusContract.AUTHORITY,StatusContract.TABLE+"/#",StatusContract.STATUS_ITEM);
        //si la uri termina en numero se refiere a un estado
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        qb.setTables(StatusContract.TABLE);

        switch (sURIMatcher.match(uri)){
            case StatusContract.STATUS_DIR:
                break;
            case StatusContract.STATUS_ITEM:
                qb.appendWhere(StatusContract.Column.ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
        String orderBy = (TextUtils.isEmpty(sortOrder)) ? StatusContract.DEFAULT_SORT
                :sortOrder; //Para ordenar los registros a obtener
        SQLiteDatabase db= dbhelper.getReadableDatabase(); //Obtener instancia de la BD (para leer)
        Cursor cursor = qb.query(db, projection, selection, selectionArgs,
                null, null, orderBy);//Leer
        //Registrar cambios en las URI
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d(TAG, "queried records: " + cursor.getCount() );
        return cursor;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String where;
        switch (sURIMatcher.match(uri)){
            case StatusContract.STATUS_DIR:
                where = selection; //se actualizaran de varios registros (la URI no tiene un ID).
                break;
            case StatusContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = StatusContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "": "and ( "
                        + selection + ")" );
                break;
            default:
                throw new IllegalArgumentException("ilegal uri: " + uri);
        }
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        int ret = db.update(StatusContract.TABLE, values,
                where, selectionArgs);// Actualiza
        if(ret>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "update records: " +  ret);
        return ret;

    }
}
