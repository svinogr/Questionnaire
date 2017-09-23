package info.upump.questionnaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.upump.questionnaire.adapter.QuestionAdapter;
import info.upump.questionnaire.entity.Question;

import static android.R.id.list;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionFragment extends Fragment {

    private final String TAG = "QuestionFragment";
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> list=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("OnCreate", TAG);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.listQuestion);
        Reader reader = new Reader(getActivity());
        try {
            list =  reader.startReade();

        } catch (IOException e) {
            e.printStackTrace();
        }


        questionAdapter = new QuestionAdapter(getActivity(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(questionAdapter);
        return root;
    }
}
