package info.upump.questionnaire.model;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    TextureView number;
    TextureView questionBody;
    TextureView answer;
    Bitmap img;


    public QuestionViewHolder(View itemView) {
        super(itemView);
    }
}
