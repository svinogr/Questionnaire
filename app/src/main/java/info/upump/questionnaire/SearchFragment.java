package info.upump.questionnaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import info.upump.questionnaire.adapter.SearchQuestionAdapter;
import info.upump.questionnaire.entity.Question;
import info.upump.questionnaire.model.DelayAutoCompleteTextView;

/**
 * Created by explo on 11.10.2017.
 */

public class SearchFragment extends Fragment implements  AdapterView.OnItemClickListener{
    private DelayAutoCompleteTextView delayAutoCompleteTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        delayAutoCompleteTextView = root.findViewById(R.id.autoCompleteTextView);
        delayAutoCompleteTextView.setThreshold(4);
        SearchQuestionAdapter searchQuestionAdapter = new SearchQuestionAdapter(getContext());
        delayAutoCompleteTextView.setAdapter(searchQuestionAdapter);
        delayAutoCompleteTextView.setLoadingIndicator((ProgressBar) root.findViewById(R.id.progress_bar));
        delayAutoCompleteTextView.setOnItemClickListener(this);
        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Question question = (Question) adapterView.getItemAtPosition(i);
        System.out.println("click "+question.getBody());
        delayAutoCompleteTextView.setText(question.getBody());
    }
}
