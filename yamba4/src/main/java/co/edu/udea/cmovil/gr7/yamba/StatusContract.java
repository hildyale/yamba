package co.edu.udea.cmovil.gr7.yamba;
import android.provider.BaseColumns;

/**
 * Created by johnj_000 on 15/10/2015.
 */
public class StatusContract {
    public static final String DB_NAME= "timeline.db";//Nombre de la DB
    public static final int DB_VERSION=1;//Versión de la DB
    public static final String TABLE="status";//Nombre de la Tabla
    public static final String DEFAULT_SORT=Column.CREATED_AT + "DESC";/* Para ordenar descendentemente
        por fecha de creación*/
    public class Column {//Columnas de la tabla
        public static final String ID=BaseColumns._ID;
        public static final String USER="user";
        public static final String MESSAGE="message";
        public static final String CREATED_AT="created_at";
    }
}
