package info.upump.questionnaire.model;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import info.upump.questionnaire.R;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    public TextView number;
    public TextView questionBody;
    public ImageView img;
    public TextView comment;
    public LinearLayout linearLayoutAnswer;


    public QuestionViewHolder(View itemView) {
        super(itemView);
        linearLayoutAnswer = itemView.findViewById(R.id.list_answer);
        number = itemView.findViewById(R.id.number);
        questionBody = itemView.findViewById(R.id.question);
        img = itemView.findViewById(R.id.img);

        // img = itemView.findViewById(R.id.img);

        comment = itemView.findViewById(R.id.comment);
    }
}
