import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperOmbrelloni(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "OmbrelloniDB"
        private const val TABLE_OMBRELLONI = "ombrelloni"
        private const val KEY_ID = "id"
        private const val KEY_IS_OCCUPIED = "isOccupied"
        private const val KEY_INFO = "info"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = ("CREATE TABLE $TABLE_OMBRELLONI($KEY_ID INTEGER PRIMARY KEY, $KEY_IS_OCCUPIED INTEGER, $KEY_INFO TEXT)")
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_OMBRELLONI")
        onCreate(db)
    }

    fun addOrUpdateOmbrellone(id: Int, isOccupied: Boolean, info: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ID, id)
        values.put(KEY_IS_OCCUPIED, if (isOccupied) 1 else 0)
        values.put(KEY_INFO, info)

        db.insertWithOnConflict(TABLE_OMBRELLONI, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    @SuppressLint("Range")
    fun getOmbrellone(id: Int): Pair<Boolean, String> {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT $KEY_IS_OCCUPIED, $KEY_INFO FROM $TABLE_OMBRELLONI WHERE $KEY_ID = $id", null)
        cursor.moveToFirst()
        val isOccupied = cursor.getInt(cursor.getColumnIndex(KEY_IS_OCCUPIED)) == 1
        val info = cursor.getString(cursor.getColumnIndex(KEY_INFO))
        cursor.close()
        return Pair(isOccupied, info)
    }

    @SuppressLint("Range")
    fun getAllOmbrelloni(): HashMap<Int, Pair<Boolean, String>> {
        val ombrelloniMap = HashMap<Int, Pair<Boolean, String>>()
        val selectQuery = "SELECT * FROM $TABLE_OMBRELLONI"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val isOccupied = cursor.getInt(cursor.getColumnIndex(KEY_IS_OCCUPIED)) == 1
                val info = cursor.getString(cursor.getColumnIndex(KEY_INFO))
                ombrelloniMap[id] = Pair(isOccupied, info)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return ombrelloniMap
    }

    fun deleteOmbrellone(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_OMBRELLONI, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
    }
}
