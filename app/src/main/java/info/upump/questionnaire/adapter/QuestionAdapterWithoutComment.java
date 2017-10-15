package info.upump.questionnaire.adapter;

import android.app.Activity;
import android.view.View;

import java.util.List;

import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 15.10.2017.
 */

public class QuestionAdapterWithoutComment extends QuestionAdapter {
    public QuestionAdapterWithoutComment(Activity activity, List<Question> list) {
        super(activity, list);
    }

    @Override
    protected void setComment(int position) {
          holder.comment.setVisibility(View.GONE);

    }
}
