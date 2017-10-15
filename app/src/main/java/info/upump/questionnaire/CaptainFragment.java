package info.upump.questionnaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.adapter.QuestionAdapter;
import info.upump.questionnaire.adapter.QuestionAdapterWithoutComment;
import info.upump.questionnaire.db.MyLoader;
import info.upump.questionnaire.entity.Question;

/**
 * Created by explo on 11.10.2017.
 */

public class CaptainFragment extends Fragment implements AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<List<Question>> {
    private List<Question> list = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private SearchView searchView;
    protected  String CATEGORY = "капитаны";
    public   static String TAG = "cap";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        View root = inflater.inflate(R.layout.fragment_serch_category, container, false);

        searchView = root.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {

                    int number = Integer.parseInt(newText);
                    if(number>recyclerView.getAdapter().getItemCount()){
                        number = recyclerView.getAdapter().getItemCount();
                    }
                    if(number<1){
                        number = 1;
                    }
                    recyclerView.scrollToPosition(number-1);
                }catch (NumberFormatException e) {
                    questionAdapter.filter(newText);
                    return true;
                }catch (IndexOutOfBoundsException e){
                    recyclerView.stopScroll();
                }

                return true;
            }
        });

        progressBar = root.findViewById(R.id.progressSearchCategory);
        progressBar.setVisibility(progressBar.VISIBLE);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView = root.findViewById(R.id.listQuestionSearchCategory);
        recyclerView.setLayoutManager(linearLayoutManager);
        questionAdapter = new QuestionAdapterWithoutComment(getActivity(), list);
        recyclerView.setAdapter(questionAdapter);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle.putString("category", CATEGORY);
        getLoaderManager().initLoader(1, bundle, this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Question question = (Question) adapterView.getItemAtPosition(i);
    }


    @Override
    public Loader<List<Question>> onCreateLoader(int id, Bundle args) {
        return new MyLoader(getContext(), args.getString("category"));
    }

    @Override
    public void onLoadFinished(Loader<List<Question>> loader, List<Question> data) {
        System.out.println(2);
        System.out.println(data.size());
        list.clear();
        list.addAll(data);
        System.out.println(list.size());
        questionAdapter.notifyDataSetChanged();
        progressBar.setVisibility(progressBar.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<Question>> loader) {

    }

    public String getTAG() {
        return TAG;
    }
}
