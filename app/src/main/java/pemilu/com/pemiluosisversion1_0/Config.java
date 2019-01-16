package pemilu.com.pemiluosisversion1_0;

/**
 * Created by WilmanRosadi on 1/9/2017.
 */

public class Config {
    //URL to our login.php file
    public static final String SERVER_URL = "http://192.168.173.2/";
    public static final String LOGIN_URL = SERVER_URL+"pemilu/user/login.php";
    public static final String VOTE_URL = SERVER_URL+"pemilu/user/vote_kandidat.php";
    public static final String KANDIDAT_URL = SERVER_URL+"pemilu/user/select_data.php?id=";
    public static final String VIDEO_URL = SERVER_URL+"pemilu/user/video-kandidat/";
    public static final String URL_PHOTOS_USERS = SERVER_URL+"pemilu/user/img/" ;


    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_SUREL = "surel";
    public static final String KEY_PIN = "pin";
    //data kandidat
    public static final String FOTO_USER_1 = "foto";
    public static final String FOTO_USER_2 = "foto";
    public static final String FOTO_USER_3 = "foto";

    public static final String KEY_TAHUN = "tahun";
    public static final String KEY_NAMA = "nama";
    public static final String KEY_KELAS = "kelas";
    public static final String KEY_VISI = "visi";
    public static final String KEY_MISI = "misi";
    public static final String JSON_ARRAY = "result";
    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
    public static final String LOGIN_GAGAL = "gagal";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    public static final String SHARED_PREF_NAME_DATA = "mydataapp";

    //This would be used to store the email of current logged in user
    public static final String SUREL_SHARED_PREF = "surel";
    public static final String TAHUN_SHARED_PREF_1 = "tahun";
    public static final String NAMA_SHARED_PREF_1 = "nama";
    public static final String KELAS_SHARED_PREF_1 = "kelas";
    public static final String VISI_SHARED_PREF_1 = "visi";
    public static final String MISI_SHARED_PREF_1 = "misi";
    public static final String FOTO_ADDRESS_SHARED_PREF_1 = "foto";
    public static final String VIDEO_ADDRESS_SHARED_PREF_1 = "video";


    public static final String TAHUN_SHARED_PREF_2 = "tahun";
    public static final String NAMA_SHARED_PREF_2 = "nama";
    public static final String KELAS_SHARED_PREF_2 = "kelas";
    public static final String VISI_SHARED_PREF_2 = "visi";
    public static final String MISI_SHARED_PREF_2 = "misi";
    public static final String FOTO_ADDRESS_SHARED_PREF_2 = "foto";
    public static final String VIDEO_ADDRESS_SHARED_PREF_2 = "video";


    public static final String TAHUN_SHARED_PREF_3 = "tahun";
    public static final String NAMA_SHARED_PREF_3 = "nama";
    public static final String KELAS_SHARED_PREF_3 = "kelas";
    public static final String VISI_SHARED_PREF_3 = "visi";
    public static final String MISI_SHARED_PREF_3 = "misi";
    public static final String FOTO_ADDRESS_SHARED_PREF_3 = "foto";
    public static final String VIDEO_ADDRESS_SHARED_PREF_3 = "video";


    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";
    public static final String SAVED_SHARED_PREF = "saving";

    public static final String SHARED_VIDE_1 = "";

}
