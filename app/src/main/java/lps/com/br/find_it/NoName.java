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

    private final String mCategory;
    private final String mItemDescription;
    private final String mItemStatus;
    private final int mUserCode;

    NoName(String category, String itemDescription, String itemStatus, int userCode){
        mCategory = category;
        mItemDescription = itemDescription;
        mItemStatus = itemStatus;
        mUserCode = userCode;
    }

    @Override
    protected ArrayList doInBackground(Void... params) {
        try {
            ArrayList<Item> listItems = new ArrayList<>();
            ConnectionDB conDB = ConnectionDB.getInstance();
            PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("SELECT nomeItem, dataCadastro, descricaoItem, latitudeItem, longitudeItem, raioItem, nomeCategoria, nomeStatus, codigoCliente from findit.Item natural join findit.Categoria natural join findit.Status WHERE nomeCategoria = ? AND descricaoItem LIKE ? and nomeStatus <> 'Devolvido' and nomeStatus <> ? and codigoCliente <> ? ORDER BY nomeItem");
            stmt.setString(1, mCategory);
            stmt.setString(2, "%" + mItemDescription + "%");
            stmt.setString(3, mItemStatus);
            stmt.setInt(4,mUserCode);

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
