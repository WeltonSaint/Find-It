package lps.com.br.find_it;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by PC.RW on 14/10/2016.
 */

public class KeepLogin {

    private static final String PREFS_PRIVATE = "PREFS_PRIVATE";

    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static void showIsNotConected(Activity activity){
        Snackbar.make(activity.findViewById(android.R.id.content), "Sem Conexão com a Internet", Snackbar.LENGTH_LONG).show();
    }

    public static boolean isPasswordSave(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        String senha = sharedPreferences.getString("senha","");

        return ( senha.length() != 0 && senha != null);
    }

    public static String getPasswordSave(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        return sharedPreferences.getString("senha","");
    }

    public static String getLoginSave(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);
        return sharedPreferences.getString("login","");
    }

    public static void savePreferences( String login, String senha, Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_PRIVATE, Context.MODE_PRIVATE);

        SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();

        prefsPrivateEditor.putString("login", login);
        prefsPrivateEditor.putString("senha", senha);

        prefsPrivateEditor.commit();

    }

}
