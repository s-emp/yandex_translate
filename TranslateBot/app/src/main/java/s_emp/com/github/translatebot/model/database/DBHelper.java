package s_emp.com.github.translatebot.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;

import s_emp.com.github.translatebot.model.Message;

public class DBHelper extends SQLiteOpenHelper implements IHistory, IMark {



    private static final String DATABASE_NAME = "translateEmp";
    private static final int DATABASE_VERSION = 1;
    // region TableHistory
    private static final String TABLE_HISTORY = "history";
    private static final String COL_HIST_ID = "id";
    private static final String COL_HIST_BOT = "is_bot";
    private static final String COL_HIST_MESSAGE = "message";
    // endregion

    // region TableMark
    private static final String TABLE_MARK = "mark";
    private static final String COL_MARK_ID = "id";
    private static final String COL_MARK_BOT = "is_bot";
    private static final String COL_MARK_MESSAGE = "message";
    // endregion

    // region SQLite

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableHist = "CREATE TABLE " + TABLE_HISTORY + " (" + COL_HIST_ID + " INTEGER PRIMARY KEY, " +
                COL_HIST_BOT + " INTEGER NOT NULL, " + COL_HIST_MESSAGE + " TEXT)";
        db.execSQL(tableHist);
        String tableMark = "CREATE TABLE " + TABLE_MARK + " (" + COL_MARK_ID + " INTEGER PRIMARY KEY, " +
                COL_MARK_BOT + " INTEGER NOT NULL, " + COL_MARK_MESSAGE + " TEXT)";
        db.execSQL(tableMark);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MARK);
        onCreate(db);
    }

    // endregion

    // region History

    @Override
    public void addHist(MessageDB obj) throws IOException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_HIST_BOT, obj.getIsBot());
        contentValues.put(COL_HIST_MESSAGE, obj.getMessage());
        database.insert(TABLE_HISTORY, null, contentValues);
        database.close();
    }

    @Override
    public ArrayList<MessageDB> getHist(int count) {

        ArrayList<MessageDB> hists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr;
        if (count > 0) {
            cr = db.query(TABLE_HISTORY, null, null, null, null, null,
                    COL_HIST_ID + " DESC ", Integer.toString(count));
        } else {
            cr = db.query(TABLE_HISTORY, null, null, null, null, null,
                    COL_HIST_ID + " DESC ", null);
        }

        if (cr.moveToFirst()) {
            do {
                hists.add(new MessageDB(cr.getInt(1), cr.getString(2)));
            } while (cr.moveToNext());
        }
        db.close();
        return hists;
    }

    @Override
    public ArrayList<MessageDB> getHist(int from, int limit) {
        ArrayList<MessageDB> hists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cr = db.query(TABLE_HISTORY, null, COL_HIST_ID + " >= ?",
                new String[]{Integer.toString(from)}, null, null, COL_HIST_ID + " DESC ", null);
        if (cr.moveToFirst()) {
            do {
                hists.add(new MessageDB(cr.getInt(1), cr.getString(2)));
            } while (cr.moveToNext());
        }
        db.close();
        return hists;
    }

    @Override
    public void deleteHist(int index) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, COL_HIST_ID + " = ?", new String[]{Integer.toString(index)});
        db.close();
    }

    @Override
    public void deleteHist() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, null, null);
        db.close();
    }


    // endregion

    // region Mark

    @Override
    public ArrayList<MessageDB> getMark() {
        return null;
    }

    @Override
    public void deleteMark(int index) {

    }

    @Override
    public void addMark(MessageDB mark) {

    }


    // endregion

}
