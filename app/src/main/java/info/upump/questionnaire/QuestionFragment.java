package info.upump.questionnaire;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.adapter.QuestionAdapter;
import info.upump.questionnaire.db.DataBaseHelper;
import info.upump.questionnaire.db.MyLoader;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 09.10.2017.
 */

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Question>> {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private DataBaseHelper helper;
    private QuestionAdapter questionAdapter;
    private List<Question> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.listQuestion);
        progressBar = root.findViewById(R.id.progress);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        helper = DataBaseHelper.getHelper(getContext());
        helper.create_db();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        questionAdapter = new QuestionAdapter(getActivity(), list);
        recyclerView.setAdapter(questionAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<List<Question>> onCreateLoader(int i, Bundle bundle) {
        System.out.println(1);

        return new MyLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        System.out.println(2);
        System.out.println(data.size());
        list.addAll(data);
        questionAdapter.notifyDataSetChanged();
        progressBar.setVisibility(progressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {

    }
}
