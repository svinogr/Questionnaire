package info.upump.questionnaire;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
import info.upump.questionnaire.db.DB;
import info.upump.questionnaire.db.DataBaseHelper;
import info.upump.questionnaire.db.MyCursorLoader;
import info.upump.questionnaire.db.QuestionDAO;
import info.upump.questionnaire.entity.Question;

import static android.R.id.list;

/**
 * Created by explo on 23.09.2017.
 */

public class QuestionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private final String TAG = "QuestionFragment";
    private RecyclerView recyclerView;
    private QuestionAdapter questionAdapter;
    private List<Question> list=new ArrayList<>();
    Cursor cursor;
    DataBaseHelper helper;
    DB db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Log.d("OnCreate", TAG);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.listQuestion);
        helper = DataBaseHelper.getHelper(getContext());
        Reader reader = new Reader(getActivity());
        try {
            reader.startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

      //   helper = DataBaseHelper.getHelper(getContext());
      //  QuestionDAO questionDAO = new QuestionDAO(getContext());

     /*
        db = new DB(getContext());
        db.open();*/
/*
        for (Question question:list){
          *//*  ContentValues contentValues = new ContentValues();
            contentValues.put(DB.TABLE_KEY_BODY, question.getBody());
            contentValues.put(DB.TABLE_KEY_CATEGORY, question.getCategory());
            contentValues.put(DB.TABLE_KEY_COMMENT, question.getComment());
            contentValues.put(DB.TABLE_KEY_IMG, question.getImg());*//*
           Log.d("id", String.valueOf(questionDAO.save(question)));
        }*/
        /*db.close();*/


     //   questionAdapter = new QuestionAdapter(getActivity(), list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(questionAdapter);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        MyCursorLoader myCursorLoader = new MyCursorLoader(getContext());
    //    myCursorLoader.setTable(DB.TABLE_MAIN_QUESTION);
        return myCursorLoader;

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("onLoadFinishedColCount", String.valueOf(data.getColumnCount()));
        list=new ArrayList<>();

        cursor = data;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();

                    question.setId(cursor.getInt(0));
                    question.setBody(cursor.getString(1));
                    question.setCategory(cursor.getString(2));
                    question.setImg(cursor.getString(3));
                    question.setComment(cursor.getString(4));
                    list.add(question);

                } while (cursor.moveToNext());
            }
        }
        Log.d("onLoadFinishedSizeList", String.valueOf(list.size()));
        QuestionAdapter questionAdapter= new QuestionAdapter(getActivity(),list);
        recyclerView.setAdapter(questionAdapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
