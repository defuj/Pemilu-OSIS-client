package pemilu.com.pemiluosisversion1_0;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Kandidat2 extends Fragment {

    String tag = this.getClass().getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(tag,"onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Pemilihan pemilihan = (Pemilihan)getActivity();

        Log.i(tag,"onCreateView");
        View view = inflater.inflate(R.layout.activity_kandidat2,container,false);
        pemilihan.getJSON2();
        return view;

    }
}
