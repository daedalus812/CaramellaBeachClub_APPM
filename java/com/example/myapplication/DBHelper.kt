import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, NOME_DATABASE, null, VERSIONE_DATABASE) {

    companion object {
        private const val VERSIONE_DATABASE = 1
        private const val NOME_DATABASE = "CredenzialiUtenti.db"
        private const val NOME_TABELLA = "utenti"
        private const val COLONNA_USERNAME = "username"
        private const val COLONNA_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreazioneTabella = "CREATE TABLE $NOME_TABELLA ($COLONNA_USERNAME TEXT PRIMARY KEY, $COLONNA_PASSWORD TEXT)"
        db?.execSQL(queryCreazioneTabella)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $NOME_TABELLA")
        onCreate(db)
    }

    fun inserisciUtente(username: String, password: String): Long {
        val db = this.writableDatabase
        val valori = ContentValues()
        valori.put(COLONNA_USERNAME, username)
        valori.put(COLONNA_PASSWORD, password)
        val risultato = db.insert(NOME_TABELLA, null, valori)
        db.close()
        return risultato
    }

    @SuppressLint("Range")
    fun ottieniPassword(username: String): String? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $NOME_TABELLA WHERE $COLONNA_USERNAME = ?"
        val cursore = db.rawQuery(query, arrayOf(username))
        var password: String? = null
        if (cursore.moveToFirst()) {
            password = cursore.getString(cursore.getColumnIndex(COLONNA_PASSWORD))
        }
        cursore.close()
        db.close()
        return password
    }
}
