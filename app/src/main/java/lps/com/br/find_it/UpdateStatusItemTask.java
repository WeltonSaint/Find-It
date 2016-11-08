package lps.com.br.find_it;

import android.os.AsyncTask;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe Nova
 *
 * @author PC.RW
 * @version 1.0 - 07/11/2016.
 */
class UpdateStatusItemTask extends AsyncTask<Void, Void, Boolean> {
    private final int mItemCode;

        UpdateStatusItemTask(int itemCode){
            mItemCode = itemCode;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {

            ConnectionDB conDB = ConnectionDB.getInstance();
            PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("UPDATE findit.Item SET codigoStatus = 2 where codigoItem = ?");
            stmt.setInt(1, mItemCode);

            stmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}