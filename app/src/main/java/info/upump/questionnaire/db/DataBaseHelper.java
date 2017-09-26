package info.upump.questionnaire.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by explo on 26.09.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "questionnaire";
    public static final String TABLE_QUESTION = "QUESTION";
    public static final String TABLE_ANSWER = "ANSWER";

    public static final String TABLE_KEY_ID = "_id";
    public static final String TABLE_KEY_BODY = "body";
    public static final String TABLE_KEY_IMG = "img";
    public static final String TABLE_KEY_CATEGORY = "category";
    public static final String TABLE_KEY_COMMENT = "comment";
    public static final String TABLE_KEY_RIGHT = "right";
    public static final String TABLE_KEY_ID_QUESTION = "id_question";

    private static DataBaseHelper instance;

    private static final String CREATE_QUESTION_TABLE =
            "CREATE TABLE " + TABLE_QUESTION +
                    "(" +
                    TABLE_KEY_ID + " integer NOT NULL primary key autoincrement, " +
                    TABLE_KEY_BODY + " text, " +
                    TABLE_KEY_CATEGORY + " text, " +
                    TABLE_KEY_IMG + " text, " +
                    TABLE_KEY_COMMENT + " text)";


    private static final String CREATE_ANSWER_TABLE =
            "CREATE TABLE " + TABLE_ANSWER +
                    "(" +
                    TABLE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_KEY_BODY + " text, " +
                    TABLE_KEY_RIGHT + " INTEGER, " +
                    TABLE_KEY_ID_QUESTION + " INTEGER , " +
                    "FOREIGN KEY(" + TABLE_KEY_ID_QUESTION + ") REFERENCES " + TABLE_QUESTION + "(_id))";


    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUESTION_TABLE);
        db.execSQL(CREATE_ANSWER_TABLE);
        System.err.println("Создание базы");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);

        onCreate(sqLiteDatabase);
    }
}
