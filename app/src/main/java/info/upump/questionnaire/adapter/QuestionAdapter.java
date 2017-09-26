package info.upump.questionnaire.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import info.upump.questionnaire.R;
import info.upump.questionnaire.entity.Answer;
import info.upump.questionnaire.entity.Question;
import info.upump.questionnaire.model.QuestionViewHolder;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder>  {
    private Activity activity;
    private List<Question> list;

    public QuestionAdapter(Activity activity, List<Question> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card_item,parent,false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.number.setText(String.valueOf(position+1)+")");
        holder.questionBody.setText(list.get(position).getBody());
        holder.comment.setText(list.get(position).getComment());

        List<Answer> answers = list.get(position).getAnswers();
        Log.d("кол-во атветов ", String.valueOf(answers.size()));

        for (Answer answer:answers) {
            TextView textView = new TextView(activity.getApplicationContext());
            textView.setText(answer.getBody());
            holder.linearLayoutAnswer.addView(textView);
        }
        answers.clear();


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
