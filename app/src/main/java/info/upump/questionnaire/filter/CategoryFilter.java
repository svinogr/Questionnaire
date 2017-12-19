package info.upump.questionnaire.filter;

import android.os.Handler;
import android.os.Message;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.adapter.QuestionAdapter;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 15.10.2017.
 */

public class CategoryFilter extends Filter {
    private final List<Question> inList;
    private List<Question> outList;
    private QuestionAdapter questionAdapter;


    public CategoryFilter(List<Question> inList, QuestionAdapter questionAdapter) {
        this.inList = inList;
        this.outList = new ArrayList<>();
        this.questionAdapter = questionAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        outList = new ArrayList<>();

        FilterResults filterResults = new FilterResults();
        if (constraint.toString().trim().equals("")) {
            outList = inList;
        } else {

            for (Question question : inList) {
                if (question.getBody().toLowerCase().contains(constraint.toString().toLowerCase())) {
                    outList.add(question);
                }

            }
        }
        filterResults.values = outList;
        filterResults.count = outList.size();

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //  questionAdapter.setList(null);

        questionAdapter.setList(outList);
        questionAdapter.notifyDataSetChanged();
    }
}
