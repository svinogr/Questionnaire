package info.upump.questionnaire.task;

import android.app.Activity;
import android.os.AsyncTask;


public class TaskGetImg extends AsyncTask<String,Void,Integer> {
    private Activity activity;

    public TaskGetImg(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Integer doInBackground(String... params) {
       int bitmap;
        System.out.println("image "+params[0]);
            bitmap  = activity.getResources().getIdentifier("drawable/" + params[0], null, activity.getApplication().getApplicationContext().getPackageName());

        return bitmap;
    }

    @Override
    protected void onPostExecute(Integer bitmap) {
        super.onPostExecute(bitmap);
    }
}
