package info.upump.questionnaire;


import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private SearchView searchView;
    private List<Question> list = new ArrayList<>();
    private EditText editText;
    public static String TAG="question";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_list, container, false);


        questionAdapter = new QuestionAdapter(getActivity(), list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView = (RecyclerView) root.findViewById(R.id.listQuestionSearch);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(questionAdapter);

        progressBar = root.findViewById(R.id.progressSearch);
        progressBar.setVisibility(ProgressBar.VISIBLE);

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



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
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
