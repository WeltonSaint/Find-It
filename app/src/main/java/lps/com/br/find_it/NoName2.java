package lps.com.br.find_it;

import android.os.AsyncTask;

import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by PC.RW on 04/11/2016.
 */

public class NoName2 extends AsyncTask<Void, Void, String> {

    private final int mUserCode;

    NoName2(int userCode){
        mUserCode = userCode;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            ArrayList<Item> listItems = new ArrayList<>();
            ConnectionDB conDB = ConnectionDB.getInstance();
            PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("SELECT contatoCliente from findit.Cliente where codigoCliente = ?");
            stmt.setInt(1, mUserCode);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("contatoCliente");
            } else {
               return null;
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
