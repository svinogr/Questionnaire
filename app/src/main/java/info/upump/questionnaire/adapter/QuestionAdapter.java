package info.upump.questionnaire.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.R;
import info.upump.questionnaire.entity.Answer;
import info.upump.questionnaire.entity.Question;
import info.upump.questionnaire.model.QuestionViewHolder;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private Activity activity;
    private List<Question> list;

    public QuestionAdapter(Activity activity, List<Question> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card_item, parent, false);

        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.linearLayoutAnswer.removeAllViews();
        holder.number.setText("Вопрос номер: "+String.valueOf(position + 1));
        holder.questionBody.setText("Вопрос: "+list.get(position).getBody());
        holder.comment.setText("Коментарий: "+list.get(position).getComment());

        List<Answer>
                answers = list.get(position).getAnswers();
        for (Answer answer :
                answers) {
            TextView text = new TextView(activity.getApplicationContext());
            switch (answer.getRight()){
                case 1:
                    text.setTextColor(Color.GREEN);
                    break;
                case 0:
                    text.setTextColor(Color.YELLOW);
                    break;
                case -1:
                    text.setTextColor(Color.RED);
                    break;
            }

            text.setText(answer.getBody());
            holder.linearLayoutAnswer.addView(text);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
