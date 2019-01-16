package pemilu.com.pemiluosisversion1_0;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Pemilihan extends AppCompatActivity {

    private String tag = this.getClass().getSimpleName();

    private TextView smk2t,smk3t,nama1t,kelas1t,tahun2t,nama2t,kelas2t,smk1t,nama3t,kelas3t;
    public String JSON_STRING;
    private String JS;
    public Typeface typeface;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(tag, "onCreate");
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemilihan);

        petunjuk();

        final ViewPager pager = (ViewPager) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        MyAdapter myAdapter = new MyAdapter(fm);
        pager.setAdapter(myAdapter);
        pager.setCurrentItem(0);

        typeface = Typeface.createFromAsset(getAssets(),"font/arialbd.ttf");

        getJSON1();

}
    private void petunjuk() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Petunjuk");
        alertDialogBuilder.setMessage("Geser untuk melihat kandidat lain.");
        alertDialogBuilder.setPositiveButton("Mengerti",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });

        // Tampilkan alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Pemilihan.this , "Silahkan PILIH terlebih dahulu.",Toast.LENGTH_LONG).show();
    }

    public void getJSON1() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JS =s;

                Tampil_Data1();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.KANDIDAT_URL+"1");
                return s;
            }
        }
        GetJSON g = new GetJSON();
        g.execute();
    }

    private void Tampil_Data1() {
        JSONObject J0 = null;
        ArrayList<HashMap<String,String>> dataUser = new ArrayList<HashMap<String, String>>();
        try {
            J0 = new JSONObject(JS);
            JSONArray result = J0.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i< result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String nama = jo.getString(Config.NAMA_SHARED_PREF_1);
                String tahun = jo.getString(Config.TAHUN_SHARED_PREF_1);
                String kelas = jo.getString(Config.KELAS_SHARED_PREF_1);
                String ft = jo.getString(Config.FOTO_ADDRESS_SHARED_PREF_1);

                HashMap<String,String> userData =new HashMap<>();
                userData.put(Config.NAMA_SHARED_PREF_1, nama);
                userData.put(Config.TAHUN_SHARED_PREF_1, tahun);
                userData.put(Config.KELAS_SHARED_PREF_1, kelas);
                userData.put(Config.FOTO_ADDRESS_SHARED_PREF_1, ft);

                dataUser.add(userData);
                SharedPreferences sharedPreferences = Pemilihan.this.getSharedPreferences(Config.SHARED_PREF_NAME_DATA, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putBoolean(Config.SAVED_SHARED_PREF, true);
                editor.putString(Config.NAMA_SHARED_PREF_1, nama);
                editor.putString(Config.TAHUN_SHARED_PREF_1, tahun);
                editor.putString(Config.KELAS_SHARED_PREF_1, kelas);
                editor.putString(Config.FOTO_ADDRESS_SHARED_PREF_1, ft);

                //Saving values to editor
                editor.commit();


                nama1t = (TextView)findViewById(R.id.textNama1);
                kelas1t = (TextView)findViewById(R.id.textKelas1);

                try {

                    ImageView imageView = (ImageView)findViewById(R.id.imageView1);
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                    if (ft != null){
                        String img = SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+ft;
                        imageView.setImageDrawable(Drawable.createFromPath(img));
                    }else{

                    }
                    //tahun2t = (TextView)findViewById(R.id.textTahun2);

//                    tahun2t.setText("PEMILU IFSU"+tahun);

                    nama1t.setTypeface(typeface);
                    nama1t.setText(nama);

                    kelas1t.setTypeface(typeface);
                    kelas1t.setText(kelas);

                }catch (SQLException ex){
                    Toast.makeText(Pemilihan.this,"Koneksi lemah",Toast.LENGTH_LONG).show();
                }

            }
        }catch (JSONException e) {
            Toast.makeText(Pemilihan.this,"Koneksi lemah",Toast.LENGTH_LONG).show();
        }
    }
    public String u;
/*    public void play1_texture(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            Toast.makeText(Pemilihan.this,video1,Toast.LENGTH_LONG).show();
            File SDCard = Environment.getExternalStorageDirectory();
            final TextureVideoView texture = (TextureVideoView)findViewById(R.id.textureview1);
            texture.setScaleType(TextureVideoView.ScaleType.CENTER_CROP);
            texture.setDataSource(SDCard+"/Android/data/"+getPackageName()+"/files/video/"+video1);
            texture.play();
            u = "p";
            texture.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (u == "p"){
                        u = "s";
                        texture.pause();
                        return false;
                    }
                    else if (u == "s"){
                        u = "p";
                        texture.play();
                        return false;
                    }
                    return false;
                }
            });
        }else{
            toKoneksi();
        }


    }
*/
    public void ClickVote1(View view) {
        ImageButton button = (ImageButton) findViewById(R.id.vote1);
        final Animation animation = AnimationUtils.loadAnimation(Pemilihan.this, R.anim.anim_alpha);
        button.startAnimation(animation);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Vote(1);
        } else {
            toKoneksi();
        }
    }
//kadua
    public void getJSON2() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JS =s;

                Tampil_Data2();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.KANDIDAT_URL+"2");
                return s;
            }
        }
        GetJSON g = new GetJSON();
        g.execute();
    }

    private void Tampil_Data2() {
        JSONObject J0 = null;
        ArrayList<HashMap<String,String>> dataUser = new ArrayList<HashMap<String, String>>();
        try {
            J0 = new JSONObject(JS);
            JSONArray result = J0.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i< result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String nama = jo.getString(Config.NAMA_SHARED_PREF_2);
                String tahun = jo.getString(Config.TAHUN_SHARED_PREF_2);
                String kelas = jo.getString(Config.KELAS_SHARED_PREF_2);
                String ft = jo.getString(Config.FOTO_ADDRESS_SHARED_PREF_2);
                String video = jo.getString(Config.VIDEO_ADDRESS_SHARED_PREF_2);


                HashMap<String,String> userData =new HashMap<>();
                userData.put(Config.NAMA_SHARED_PREF_2, nama);
                userData.put(Config.TAHUN_SHARED_PREF_2, tahun);
                userData.put(Config.KELAS_SHARED_PREF_2, kelas);
                userData.put(Config.FOTO_ADDRESS_SHARED_PREF_2, ft);

                dataUser.add(userData);
                SharedPreferences sharedPreferences = Pemilihan.this.getSharedPreferences(Config.SHARED_PREF_NAME_DATA, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putBoolean(Config.SAVED_SHARED_PREF, true);
                editor.putString(Config.NAMA_SHARED_PREF_2, nama);
                editor.putString(Config.TAHUN_SHARED_PREF_2, tahun);
                editor.putString(Config.KELAS_SHARED_PREF_2, kelas);
                editor.putString(Config.FOTO_ADDRESS_SHARED_PREF_2, ft);

                //Saving values to editor
                editor.commit();


                nama2t = (TextView)findViewById(R.id.textNama2);
                kelas2t = (TextView)findViewById(R.id.textKelas2);

                try {

                    ImageView imageView = (ImageView)findViewById(R.id.imageView2);
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                    if (ft != null){
                        String img = SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+ft;
                        imageView.setImageDrawable(Drawable.createFromPath(img));
                    }else{

                    }
                    //tahun2t = (TextView)findViewById(R.id.textTahun2);

//                    tahun2t.setText("PEMILU IFSU"+tahun);

                    nama2t.setTypeface(typeface);
                    nama2t.setText(nama);

                    kelas2t.setTypeface(typeface);
                    kelas2t.setText(kelas);

                }catch (SQLException ex){
                    Toast.makeText(Pemilihan.this,"Koneksi lemah",Toast.LENGTH_LONG).show();
                }

            }
        }catch (JSONException e) {
            Toast.makeText(Pemilihan.this,"Koneksi lemah",Toast.LENGTH_LONG).show();
        }
    }
    /*
    public void play2_texture(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            File SDCard = Environment.getExternalStorageDirectory();
            final TextureVideoView texture = (TextureVideoView)findViewById(R.id.textureview2);
            texture.setScaleType(TextureVideoView.ScaleType.CENTER_CROP);
            texture.setDataSource(SDCard+"/Android/data/"+getPackageName()+"/files/video/"+video2);
            texture.play();
            u = "p";
            texture.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (u == "p"){
                        u = "s";
                        texture.pause();
                        return false;
                    }
                    else if (u == "s"){
                        u = "p";
                        texture.play();
                        return false;
                    }
                    return false;
                }
            });
        }else{
            toKoneksi();
        }
    }
*/
    public void ClickVote2(View view) {
        ImageButton button = (ImageButton) findViewById(R.id.vote2);
        final Animation animation = AnimationUtils.loadAnimation(Pemilihan.this, R.anim.anim_alpha);
        button.startAnimation(animation);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Vote(2);
        } else {
            toKoneksi();
        }
    }

    //katilu
    public void getJSON3() {
        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JS =s;
                Tampil_Data3();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.KANDIDAT_URL+"3");
                return s;
            }
        }
        GetJSON g = new GetJSON();
        g.execute();
    }

    private void Tampil_Data3() {
        JSONObject J0 = null;
        ArrayList<HashMap<String,String>> dataUser = new ArrayList<HashMap<String, String>>();
        try {
            J0 = new JSONObject(JS);
            JSONArray result = J0.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i< result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                String nama = jo.getString(Config.NAMA_SHARED_PREF_3);
                String tahun = jo.getString(Config.TAHUN_SHARED_PREF_3);
                String kelas = jo.getString(Config.KELAS_SHARED_PREF_3);
                String ft = jo.getString(Config.FOTO_ADDRESS_SHARED_PREF_3);


                HashMap<String,String> userData =new HashMap<>();
                userData.put(Config.NAMA_SHARED_PREF_3, nama);
                userData.put(Config.TAHUN_SHARED_PREF_3, tahun);
                userData.put(Config.KELAS_SHARED_PREF_3, kelas);
                userData.put(Config.FOTO_ADDRESS_SHARED_PREF_3, ft);

                dataUser.add(userData);
                SharedPreferences sharedPreferences = Pemilihan.this.getSharedPreferences(Config.SHARED_PREF_NAME_DATA, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                //Adding values to editor
                editor.putBoolean(Config.SAVED_SHARED_PREF, true);
                editor.putString(Config.NAMA_SHARED_PREF_3, nama);
                editor.putString(Config.TAHUN_SHARED_PREF_3, tahun);
                editor.putString(Config.KELAS_SHARED_PREF_3, kelas);
                editor.putString(Config.FOTO_ADDRESS_SHARED_PREF_3, ft);

                //Saving values to editor
                editor.commit();


                nama3t = (TextView)findViewById(R.id.textNama3);
                kelas3t = (TextView)findViewById(R.id.textKelas3);

                try {

                    ImageView imageView = (ImageView)findViewById(R.id.imageView3);
                    File SDCardRoot = Environment.getExternalStorageDirectory();
                    if (ft != null){
                        String img = SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+ft;
                        try {
                            imageView.setImageDrawable(Drawable.createFromPath(img));
                        }catch (SQLException e){

                        }
                    }else{

                    }
                    //tahun2t = (TextView)findViewById(R.id.textTahun2);

//                    tahun2t.setText("PEMILU IFSU"+tahun);
                    nama3t.setTypeface(typeface);
                    nama3t.setText(nama);

                    kelas3t.setTypeface(typeface);
                    kelas3t.setText(kelas);

                }catch (SQLException ex){
                    Toast.makeText(Pemilihan.this,"Koneksi lemah",Toast.LENGTH_LONG).show();
                }

            }
        }catch (JSONException e) {
            Toast.makeText(Pemilihan.this,"Koneksi lemah",Toast.LENGTH_LONG).show();
        }
    }
    /*
    public void play3_texture(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            File SDCard = Environment.getExternalStorageDirectory();
            final TextureVideoView texture = (TextureVideoView)findViewById(R.id.textureview3);
            texture.setScaleType(TextureVideoView.ScaleType.CENTER_CROP);
            texture.setDataSource(SDCard+"/Android/data/"+getPackageName()+"/files/video/"+video3);
            texture.play();
            u = "p";
            texture.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (u == "p"){
                        u = "s";
                        texture.pause();
                        return false;
                    }
                    else if (u == "s"){
                        u = "p";
                        texture.play();
                        return false;
                    }
                    return false;
                }
            });
        }else{
            toKoneksi();
        }


    }
    */

    public void ClickVote3(View view) {
        ImageButton button = (ImageButton) findViewById(R.id.vote3);
        final Animation animation = AnimationUtils.loadAnimation(Pemilihan.this, R.anim.anim_alpha);
        button.startAnimation(animation);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Vote(3);
        } else {
            toKoneksi();
        }
    }

    public void Vote(final int i) {
        final Button back, yes;
        final Context context = this;
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Konfirmasi");
        dialog.setCancelable(false);

        back = (Button)dialog.findViewById(R.id.back_dialog);
        yes = (Button)dialog.findViewById(R.id.yes_dialog);
        ImageView image_dialog = (ImageView)dialog.findViewById(R.id.imageView_dialog);
        TextView text_dialog = (TextView)dialog.findViewById(R.id.txt_dialog);

        text_dialog.setText("Apakah Anda yakin ?");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animation = AnimationUtils.loadAnimation(Pemilihan.this, R.anim.anim_alpha);
                back.startAnimation(animation);

                dialog.dismiss();
                Toast.makeText(Pemilihan.this , "Silahkan pilih kembali." , Toast.LENGTH_LONG).show();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    final Animation animation = AnimationUtils.loadAnimation(Pemilihan.this, R.anim.anim_alpha);
                    yes.startAnimation(animation);

                    String surel = null;
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        surel = bundle.getString("Surel");
                    }

                    final int pilih = i;

                    final String finalSurel = surel;
                    class GetJSON extends AsyncTask<Void, Void, String> {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            JSON_STRING = s;
                            // Panggil method tampil data
                            //CekHasil();
                        }

                        @Override
                        protected String doInBackground(Void... params) {
                            RequestHandler rh = new RequestHandler();
                            String s = rh.sendGetRequest(Config.VOTE_URL + "?pilih=" + pilih + "&surel=" + finalSurel + "");
                            return s;
                        }

                    }
                    GetJSON gj = new GetJSON();
                    gj.execute();

                    delete_data_login();
                    Toast.makeText(Pemilihan.this, "Terima Kasih Telah BERPARTISIPASI.", Toast.LENGTH_LONG).show();
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {

                            Intent i = new Intent(Pemilihan.this, Splashscreen.class);
                            startActivity(i);
                        }
                    }.start();
                }else{
                    toKoneksi();
                }

            }
        });
        dialog.show();

    }
    public void delete_data_login() {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        editor.putString(Config.SUREL_SHARED_PREF, "");
        editor.putString(Config.KEY_PIN, "");

        editor.commit();
    }

    public void toKoneksi() {
        Intent i = new Intent(Pemilihan.this , Koneksi.class);
        startActivity(i);
    }

}
