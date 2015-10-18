package co.edu.udea.cmovil.gr7.yamba;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by johnj_000 on 15/10/2015.
 */
public class StatusContract {
    public static final String DB_NAME= "timeline.db";//Nombre de la DB
    public static final int DB_VERSION=1;//Versión de la DB
    public static final String TABLE="status";//Nombre de la Tabla
    public static final String DEFAULT_SORT=Column.CREATED_AT + "DESC"; // Para ordenar descendentemente por fecha de creación*/
   //provider constans
    public static final String AUTHORITY = "co.edu.udea.cmovil.gr7.yamba.StatusProvider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int STATUS_ITEM = 1;
    public static final int STATUS_DIR = 2;
    public static final String STATUS_TYPE_ITEM =
            "vnd.android.cursor.item/vnd.co.edu.udea.cmovil.gr7.yamba.providers.status";
    public static final String STATUS_TYPE_DIR =
            "vnd.android.cursor.dir/vnd.co.edu.udea.cmovil.gr7.yamba.providers.status";


    public class Column {//Columnas de la tabla
        public static final String ID=BaseColumns._ID;
        public static final String USER="user";
        public static final String MESSAGE="message";
        public static final String CREATED_AT="created_at";
    }
}
