package lps.com.br.find_it;

import android.os.AsyncTask;

import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by PC.RW on 04/11/2016.
 */

public class NoName extends AsyncTask<Void, Void, ArrayList <Item>> {

    private final int mCategoryCode;
    private final String mItemDescription;


    NoName(int categoryCode, String itemDescription){
        mCategoryCode = categoryCode;
        mItemDescription = itemDescription;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        try {
            ArrayList<Item> listItems = new ArrayList<>();
            ConnectionDB conDB = ConnectionDB.getInstance();
            PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("SELECT nomeItem, dataCadastro, descricaoItem, latitudeItem, longitudeItem, raioItem, nomeCategoria, nomeStatus, codigoCliente from findit.Item natural join findit.Categoria natural join findit.Status WHERE codigoCategoria = ? AND descricaoItem LIKE ? ORDER BY nomeItem");
            stmt.setInt(1, mCategoryCode);
            stmt.setString(2, "%" + mItemDescription + "%");

            ResultSet rs = stmt.executeQuery();

            Item item;

            while (rs.next()) {
                item = new Item(rs.getString("nomeItem"), rs.getString("descricaoItem"), rs.getDouble("latitudeItem"), rs.getDouble("longitudeItem"), rs.getDouble("raioItem"), rs.getString("nomeCategoria"), rs.getString("nomeStatus"),  rs.getInt("codigoCliente"));
                item.setData(rs.getString("dataCadastro"));
                listItems.add(item);
            }

            return listItems;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(final ArrayList<Item> list) {

    }

}
