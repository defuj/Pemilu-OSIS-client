package pemilu.com.pemiluosisversion1_0;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by WilmanRosadi on 1/14/2017.
 */

public class MyAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;

    public MyAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Pemilihan pemilihan = new Pemilihan();
        switch (position){
            case 0:
                Kandidat1 kandidat1 = new Kandidat1();
                Bundle data = new Bundle();
                data.putInt("current_page",position+1);
                kandidat1.setArguments(data);
                return kandidat1;
            case 1:
                return new Kandidat2();
            case 2:
                return new Kandidat3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}