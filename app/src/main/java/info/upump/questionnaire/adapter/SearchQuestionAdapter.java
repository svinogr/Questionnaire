package info.upump.questionnaire.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.R;
import info.upump.questionnaire.db.QuestionDAO;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 11.10.2017.
 */

public class SearchQuestionAdapter extends BaseAdapter implements Filterable {
    private static final int TOTAL = 3;
    private final Context context;
    private List<Question> list;

    public SearchQuestionAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Question getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.delay_item, viewGroup, false);
        }
        Question question = getItem(i);
        ((TextView) view.findViewById(R.id.text)).setText((question.getBody()));
        return view;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence != null) {
                    List<Question> questions = findQuation(context, charSequence.toString());
                    // Assign the data to the FilterResults
                   filterResults.values = questions;
                  filterResults.count = questions.size();
                }
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    list = (List<Question>) filterResults.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    private List<Question> findQuation(Context context, String s) {
        /// здесь навного нужен асинк
        QuestionDAO questionDAO = new QuestionDAO(context);
        List<Question> resList = new ArrayList<>();
        Cursor cursor = questionDAO.searchByString(s);
        System.out.println("66"+cursor.moveToFirst());
        if (cursor.moveToFirst()) {

            do{
                Question question = new Question();
                question.setId(cursor.getInt(0));
                question.setBody(cursor.getString(1));
                question.setCategory(cursor.getString(2));
                question.setImg(cursor.getString(3));
                question.setComment((cursor.getString(4)));
                System.out.println(question.toString());
                resList.add(question);

            }
                while(cursor.moveToNext());

            }
            cursor.close();
        return resList;
        }


    }

