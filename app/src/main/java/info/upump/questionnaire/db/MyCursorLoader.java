package info.upump.questionnaire.db;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.util.Log;

/**
 * Created by Сергей on 24.08.2017.
 */

public  class MyCursorLoader extends CursorLoader {
    private DB db ;
    private String table;
    private QuestionDAO questionDAO;
    public MyCursorLoader(Context context) {
        super(context);
        //this.db =db;
        questionDAO = new QuestionDAO(getContext());
     //   this.db = new DB(getContext());

    }

 /*   @Override
    protected Cursor onLoadInBackground() {
        Log.d("DB", "getAllBACK");
        Cursor cursor = null;
        db.open();
        if(db!=null){
            Log.d("DB", "getAllBACK   2");
            cursor = db.getAllData(table);
        }
        Log.d("DB", String.valueOf(cursor.getColumnCount()));
        return cursor;

    }*/
 @Override
 protected Cursor onLoadInBackground() {
     Log.d("DB", "getAllBACK");
     Cursor cursor = questionDAO.getCursorQuestion();

     return cursor;

 }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
