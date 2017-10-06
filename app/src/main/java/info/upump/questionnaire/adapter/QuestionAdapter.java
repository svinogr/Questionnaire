package info.upump.questionnaire.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.questionnaire.R;
import info.upump.questionnaire.entity.Answer;
import info.upump.questionnaire.entity.Question;
import info.upump.questionnaire.model.QuestionViewHolder;
import info.upump.questionnaire.task.TaskGetImg;

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
        holder.number.setText("Вопрос номер: " + String.valueOf(position + 1));
        holder.questionBody.setText("Вопрос: " + list.get(position).getBody());
        if(list.get(position).getComment()!=null) {
            holder.comment.setText("Коментарий: " + list.get(position).getComment());
        }
      /*  TaskGetImg taskGetImg = new TaskGetImg(activity);
        taskGetImg.execute(list.get(position).getImg()+"gggi");
      //  taskGetImg.execute("test");
       int bitmap=0;

        try {
            bitmap = taskGetImg.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/

        // holder.img.setImageBitmap(bitmap);
        String s = list.get(position).getImg();
     //   System.out.println("image "+s.length());
        if(s!=null) {
            holder.img.setImageResource(activity.getResources().getIdentifier("drawable/" + s, null, activity.getApplication().getApplicationContext().getPackageName()));

        } else holder.img.setImageDrawable(null);
        List<Answer>
                answers = list.get(position).getAnswers();
        for (Answer answer :
                answers) {
            TextView text = new TextView(activity.getApplicationContext());
            switch (answer.getRight()) {
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
