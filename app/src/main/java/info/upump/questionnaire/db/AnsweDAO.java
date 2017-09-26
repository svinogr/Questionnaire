package info.upump.questionnaire.db;

import android.content.ContentValues;
import android.content.Context;

import info.upump.questionnaire.entity.Answer;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 27.09.2017.
 */

public class AnsweDAO extends DBDAO {
    private static final String WHERE_ID_EQUALS = DataBaseHelper.TABLE_KEY_ID
            + " =?";
    public AnsweDAO(Context context) {
        super(context);
    }

    public long save(Answer answer){
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_BODY,answer.getBody());
        cv.put(DataBaseHelper.TABLE_KEY_RIGHT,answer.getRight());
        cv.put(DataBaseHelper.TABLE_KEY_ID_QUESTION,answer.getQuestion().getId());
        return database.insert(DataBaseHelper.TABLE_ANSWER,null,cv);
    }
}
