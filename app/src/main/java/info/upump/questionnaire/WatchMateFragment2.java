package info.upump.questionnaire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by explo on 20.12.2017.
 */

public class WatchMateFragment2 extends CaptainFragment {
    public static String TAG="watch";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CATEGORY = "watch2";
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
