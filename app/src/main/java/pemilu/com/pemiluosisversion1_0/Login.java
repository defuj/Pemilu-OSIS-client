package pemilu.com.pemiluosisversion1_0;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    ProgressDialog pDialog;
    public static EditText editTextSurel;
    private EditText editTextPin;
    private Button buttonLogin;

    //boolean variable to check user is logged in or not
    //initially it is false
//    private boolean loggedIn = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mennginisiasi
        editTextSurel = (EditText)findViewById(R.id.editTextSurel);
        editTextPin = (EditText)findViewById(R.id.editTextPin);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        login();
            }
        });
    }



    private void login(){

        final Animation animation = AnimationUtils.loadAnimation(Login.this, R.anim.anim_alpha);
        buttonLogin.startAnimation(animation);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        if ("".equals(editTextSurel.getText().toString()) || editTextSurel.getText().toString().length() < 8) {
            editTextSurel.setError("Isi SUREL min 8-karakter");
        }
        else if ("".equals(editTextPin.getText().toString()) || editTextPin.getText().toString().length() < 4){
            editTextPin.setError("Isi PIN 4-digit");

        }
        else if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            if (editTextPin.getText().toString().length() >= 4 || editTextSurel.getText().toString().length() >= 12){
                pDialog = new ProgressDialog(Login.this);
                pDialog.setMessage("Mohon Tunggu...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

                //Getting values from edit texts
                final String surel = editTextSurel.getText().toString().trim();
                final String pin = editTextPin.getText().toString().trim();

                //Creating a string request
                StringRequest stringRequest = new StringRequest(Request.Method.POST,Config.LOGIN_URL +"?surel="+surel+"&pin="+pin ,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(final String response) {
                                //If we are getting success from server
                                Toast.makeText(Login.this,response , Toast.LENGTH_LONG).show();
                                if(response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                                    Intent intent = new Intent(Login.this, Pemilihan.class);
                                    String email = editTextSurel.getText().toString();
                                    intent.putExtra("Surel", email);
                                    startActivity(intent);
                                    Toast.makeText(Login.this,"Selamat datang" , Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                }else {
                                    Toast.makeText(Login.this,"Periksa kembali data yang anda masukan" , Toast.LENGTH_LONG).show();
                                    pDialog.dismiss();
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                              pDialog.dismiss();
                              Toast.makeText(Login.this, "Koneksi lemah",Toast.LENGTH_LONG).show();
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        //Adding parameters to request
                        //params.put(Config.KEY_SUREL, surel);
                        //params.put(Config.KEY_PIN, pin);

                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        }
        else {
            Intent i = new Intent(Login.this, Koneksi.class);
            startActivity(i);
        }

    }

    @Override
    public void onClick(View view) {
        login();
    }
}
