package pemilu.com.pemiluosisversion1_0;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Splashscreen extends AppCompatActivity {

    String JS , foto1,foto2,foto3;


    ProgressBar pb;
    Dialog dialog;
    String Directory;
    int downloadedSize = 0;
    int totalSize = 0;
    TextView cur_val;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearMulai);
        final Button button = (Button)findViewById(R.id.btn_mulai);
        File SDCard = Environment.getExternalStorageDirectory();
        File images = new File(SDCard+"/Android/data/"+getPackageName()+"/files/images");

        if(!images.isDirectory()){
            images.mkdirs();
        }
        new CountDownTimer(0, 0) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    File SDCards = Environment.getExternalStorageDirectory();
                    File img = new File(SDCards+"/Android/data/"+getPackageName()+"/files/images");
                    File[] content = img.listFiles();
                    if(content.length < 3){
                        progressDialog = new ProgressDialog(Splashscreen.this);
                        progressDialog.setMessage("Mohon Tunggu...");
                        progressDialog.setIndeterminate(false);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        getJSON1();
                    }
                }else {
                    Intent intent = new Intent(Splashscreen.this, Koneksi.class);
                    startActivity(intent);
                }
            }
        }.start();


                linearLayout.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation animation = AnimationUtils.loadAnimation(Splashscreen.this, R.anim.anim_alpha);
                button.startAnimation(animation);
                Intent intent = new Intent(Splashscreen.this, Wizard.class);
                startActivity(intent);
            }
        });
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

                String ft = jo.getString(Config.FOTO_ADDRESS_SHARED_PREF_1);

                HashMap<String,String> userData =new HashMap<>();
                userData.put(Config.FOTO_ADDRESS_SHARED_PREF_1, ft);

                dataUser.add(userData);
                SharedPreferences sharedPreferences = Splashscreen.this.getSharedPreferences(Config.SHARED_PREF_NAME_DATA, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Config.FOTO_ADDRESS_SHARED_PREF_1, ft);

                //Saving values to editor
                editor.commit();

                try{
                    progressDialog.hide();
                    foto1 = ft;
                    if (ft == ""){
                        getJSON1();
                    }else{
                        video_download(4);
                    }
                }catch (SQLException e){
                    getJSON1();
                }

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

                String ft = jo.getString(Config.FOTO_ADDRESS_SHARED_PREF_2);

                HashMap<String,String> userData =new HashMap<>();
                userData.put(Config.FOTO_ADDRESS_SHARED_PREF_2, ft);

                dataUser.add(userData);
                SharedPreferences sharedPreferences = Splashscreen.this.getSharedPreferences(Config.SHARED_PREF_NAME_DATA, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Config.FOTO_ADDRESS_SHARED_PREF_2, ft);

                //Saving values to editor
                editor.commit();

                try{
                    foto2 = ft;
                    if (ft == ""){
                        getJSON2();
                    }else{
                        video_download(5);
                    }
                }catch (SQLException e){
                    getJSON2();
                }

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

                String ft = jo.getString(Config.FOTO_ADDRESS_SHARED_PREF_3);

                HashMap<String,String> userData =new HashMap<>();
                userData.put(Config.FOTO_ADDRESS_SHARED_PREF_3, ft);

                dataUser.add(userData);
                SharedPreferences sharedPreferences = Splashscreen.this.getSharedPreferences(Config.SHARED_PREF_NAME_DATA, Context.MODE_PRIVATE);

                //Creating editor to store values to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Config.FOTO_ADDRESS_SHARED_PREF_3, ft);

                //Saving values to editor
                editor.commit();

                try{
                    foto3 = ft;
                    if (ft == ""){
                        getJSON3();
                    }else{
                        video_download(6);
                    }
                }catch (SQLException e){
                    getJSON3();
                }
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Splashscreen.this , "Terima Kasih telah Berkunjung" , Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    String dwnload_file_path_4;
    String dwnload_file_path_5;
    String dwnload_file_path_6;

    void video_download(int i){
        File SDCardRoot = Environment.getExternalStorageDirectory();
        File gambar1 = new File(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto1);
        File gambar2 = new File(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto2);
        File gambar3 = new File(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto3);

        dwnload_file_path_4 = Config.SERVER_URL+"pemilu/user/img/"+foto1;
        dwnload_file_path_5 = Config.SERVER_URL+"pemilu/user/img/"+foto2;
        dwnload_file_path_6 = Config.SERVER_URL+"pemilu/user/img/"+foto3;

        if(i == 4 && !gambar1.exists()) {
            showProgress_4(dwnload_file_path_4);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    downloadFile_4();
                }
            }).start();
        }
        else if (i == 4 && gambar1.exists()){
            getJSON2();
        }
        else if(i == 5 && !gambar2.exists()) {
            showProgress_5(dwnload_file_path_5);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    downloadFile_5();
                }
            }).start();
        }
        else if (i == 5 && gambar2.exists()){
            getJSON3();
        }
        else if(i == 6 && !gambar3.exists()) {
            showProgress_6(dwnload_file_path_6);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    downloadFile_6();
                }
            }).start();
        }
        else {

        }
        }
    //data foto untuk kandidat 1
    void downloadFile_4(){

        dwnload_file_path_4 = Config.SERVER_URL+"pemilu/user/img/"+foto1;
        dwnload_file_path_5 = Config.SERVER_URL+"pemilu/user/img/"+foto2;
        dwnload_file_path_6 = Config.SERVER_URL+"pemilu/user/img/"+foto3;

        try {
            URL url = new URL(dwnload_file_path_4);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto1);
            Directory = String.valueOf(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto1);

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Pengunduhan... " + downloadedSize/1024 + "KB / " + totalSize/1024 + "KB (" + (int)per + "%)" );

                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.hide();// if you want close it..
                    downloadedSize = 0;
                    totalSize = 0;
                    getJSON2();
                    //Toast.makeText(Splashscreen.this,Directory, Toast.LENGTH_LONG).show();
                }
            });

        } catch (final MalformedURLException e) {
            showError_4("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError_4("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError_4("Error : Please check your internet connection " + e);
        }
    }

    void showError_4(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Splashscreen.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress_4(String file_path){
        dialog = new Dialog(Splashscreen.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setTitle("Download Progress");

        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        //text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Mulai mengunduh...");
        dialog.show();

        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
    }

    //data foto untuk kandidat 2
    void downloadFile_5(){

        dwnload_file_path_4 = Config.SERVER_URL+"pemilu/user/img/"+foto1;
        dwnload_file_path_5 = Config.SERVER_URL+"pemilu/user/img/"+foto2;
        dwnload_file_path_6 = Config.SERVER_URL+"pemilu/user/img/"+foto3;

        try {
            URL url = new URL(dwnload_file_path_5);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto2);
            Directory = String.valueOf(SDCardRoot+"/Android/data/"+getPackageName()+"files/images/"+foto2);

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Pengunduhan... " + downloadedSize/1024 + "KB / " + totalSize/1024 + "KB (" + (int)per + "%)" );
                                            }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.hide();// if you want close it..
                    downloadedSize = 0;
                    totalSize = 0;
                    getJSON3();
                    //Toast.makeText(Splashscreen.this,Directory, Toast.LENGTH_LONG).show();
                }
            });

        } catch (final MalformedURLException e) {
            showError_5("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError_5("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError_5("Error : Please check your internet connection " + e);
        }
    }

    void showError_5(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Splashscreen.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress_5(String file_path){
        dialog = new Dialog(Splashscreen.this);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setTitle("Download Progress");

        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        //text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Mulai mengunduh...");
        dialog.show();

        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
    }

    //data foto untuk kandidat 3
    void downloadFile_6(){

        dwnload_file_path_4 = Config.SERVER_URL+"pemilu/user/img/"+foto1;
        dwnload_file_path_5 = Config.SERVER_URL+"pemilu/user/img/"+foto2;
        dwnload_file_path_6 = Config.SERVER_URL+"pemilu/user/img/"+foto3;

        try {
            URL url = new URL(dwnload_file_path_6);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File file = new File(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto3);
            Directory = String.valueOf(SDCardRoot+"/Android/data/"+getPackageName()+"/files/images/"+foto3);

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                    pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                        pb.setProgress(downloadedSize);
                        float per = ((float)downloadedSize/totalSize) * 100;
                        cur_val.setText("Pengunduhan... " + downloadedSize/1024 + "KB / " + totalSize/1024 + "KB (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    dialog.hide();// if you want close it..
                    
                    Toast.makeText(Splashscreen.this,"Data berhasil diunduh", Toast.LENGTH_LONG).show();
                }
            });

        } catch (final MalformedURLException e) {
            showError_6("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError_6("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError_6("Error : Please check your internet connection " + e);
        }
    }

    void showError_6(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Splashscreen.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }

    void showProgress_6(String file_path){
        dialog = new Dialog(Splashscreen.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setTitle("Download Progress");
        dialog.setCancelable(false);

        TextView text = (TextView) dialog.findViewById(R.id.tv1);
        //text.setText("Downloading file from ... " + file_path);
        cur_val = (TextView) dialog.findViewById(R.id.cur_pg_tv);
        cur_val.setText("Mulai mengunduh...");
        dialog.show();

        pb = (ProgressBar)dialog.findViewById(R.id.progress_bar);
        pb.setProgress(0);
        pb.setProgressDrawable(getResources().getDrawable(R.drawable.green_progress));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
