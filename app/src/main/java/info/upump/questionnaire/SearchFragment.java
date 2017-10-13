package info.upump.questionnaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.adapter.QuestionAdapter;
import info.upump.questionnaire.adapter.SearchQuestionAdapter;
import info.upump.questionnaire.db.MyLoader;
import info.upump.questionnaire.entity.Question;
import info.upump.questionnaire.model.DelayAutoCompleteTextView;

/**
 * Created by explo on 11.10.2017.
 */

public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener,
        LoaderManager.LoaderCallbacks<List<Question>>,
        TextView.OnEditorActionListener{
    private DelayAutoCompleteTextView delayAutoCompleteTextView;
    private ImageButton btn;
    private List<Question> list = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        btn = root.findViewById(R.id.btn_search);
        btn.setOnClickListener(this);

        delayAutoCompleteTextView = root.findViewById(R.id.autoCompleteTextView);
        delayAutoCompleteTextView.setThreshold(4);
        SearchQuestionAdapter searchQuestionAdapter = new SearchQuestionAdapter(getContext());
        delayAutoCompleteTextView.setAdapter(searchQuestionAdapter);
        delayAutoCompleteTextView.setLoadingIndicator((ProgressBar) root.findViewById(R.id.progress_bar));
        delayAutoCompleteTextView.setOnItemClickListener(this);
        delayAutoCompleteTextView.setOnEditorActionListener(this);

        progressBar = root.findViewById(R.id.progressSearch);
        progressBar.setVisibility(progressBar.GONE);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = root.findViewById(R.id.listQuestionSearch);
        recyclerView.setLayoutManager(linearLayoutManager);
        questionAdapter = new QuestionAdapter(getActivity(), list);
        recyclerView.setAdapter(questionAdapter);
        return root;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Question question = (Question) adapterView.getItemAtPosition(i);
        delayAutoCompleteTextView.setText(question.getBody());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           // case android.R.id.:
            case R.id.btn_search:
                progressBar.setVisibility(progressBar.VISIBLE);
                System.out.println("click");;
                String text = String.valueOf(delayAutoCompleteTextView.getText());
                Bundle bundle = new Bundle();
                bundle.putString("request",text);
                getLoaderManager().restartLoader(0, bundle, this);
                getLoaderManager().initLoader(0, bundle, this);
                break;

        }

    }

    @Override
    public Loader<List<Question>> onCreateLoader(int id, Bundle args) {
        return new MyLoader(getContext(),args.getString("request"));
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        System.out.println(2);
        System.out.println(data.size());
        list.clear();
        list.addAll(data);
        questionAdapter.notifyDataSetChanged();
        progressBar.setVisibility(progressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_SEARCH){
            onClick(btn);
            return true;
        }
        return false;
    }
}
