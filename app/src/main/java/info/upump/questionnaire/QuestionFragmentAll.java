package info.upump.questionnaire;

import android.os.Bundle;
import android.support.v4.content.Loader;

import java.util.List;

import info.upump.questionnaire.db.MyLoader;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 20.12.2017.
 */

public class QuestionFragmentAll extends QuestionFragmentWithComment {
    @Override
    public Loader<List<Question>> onCreateLoader(int i, Bundle bundle) {
        System.out.println(1);

        return new MyLoader(getContext(),null,false);
    }
}
