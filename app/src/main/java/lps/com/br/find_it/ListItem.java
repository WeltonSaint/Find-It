package lps.com.br.find_it;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListItem extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "option";
    private static final String ARG_PARAM2 = "userCode";

    // TODO: Rename and change types of parameters
    private int mUserCode;
    private int mOption;

    private ListItemTask mAuthTask = null;

    private ProgressBar mListItemProgress;
    private RelativeLayout mListItemView;

    public ListItem() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListItem.
     */
    // TODO: Rename and change types and number of parameters
    public static ListItem newInstance(int param1, int param2) {
        ListItem fragment = new ListItem();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mOption = getArguments().getInt(ARG_PARAM1);
            mUserCode = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_item, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // save views as variables in this method
        // "view" is the one returned from onCreateView
        String[] listItemDescriptions = getResources().getStringArray(R.array.list_item_descriptions);
        TextView lblItemDescription = (TextView) view.findViewById(R.id.lbl_list_item_desc);
        TextView lblNotResults = (TextView) view.findViewById(R.id.lbl_not_results);
        mListItemProgress = (ProgressBar) view.findViewById(R.id.list_item_progress);
        mListItemView = (RelativeLayout) view.findViewById(R.id.list_item_view);
        RecyclerView mListItems = (RecyclerView) view.findViewById(R.id.list_item);

        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mListItems.setLayoutManager(layoutManager);
            lblItemDescription.setText(listItemDescriptions[mOption]);
            showProgress(true);
            mAuthTask = new ListItemTask(mOption, mUserCode);
            ArrayList<Item> listItem = mAuthTask.execute((Void) null).get();
            if(listItem.size() == 0)
                lblNotResults.setVisibility(View.VISIBLE);
            else
                lblNotResults.setVisibility(View.GONE);
            ListItemAdapter adapter = new ListItemAdapter(getActivity(), listItem);
            mListItems.setAdapter(adapter);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        super.onSaveInstanceState(savedInstanceState);
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mListItemView.setVisibility(show ? View.GONE : View.VISIBLE);
            mListItemView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mListItemView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mListItemProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mListItemProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mListItemProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mListItemProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mListItemView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class ListItemTask extends AsyncTask<Void, Void, ArrayList<Item>> {

        private final int mOption;
        private int mUserCode;
        private String[] complementQuery = {"nomeStatus <> 'Devolvido'", "nomeStatus = 'Perdido'", "nomeStatus = 'Encontrado'", "nomeStatus = 'Devolvido'"};


        ListItemTask(int option, int userCode) {
            mOption = option;
            mUserCode = userCode;
        }

        @Override
        protected ArrayList<Item> doInBackground(Void... params) {

            try {
                ArrayList<Item> listItems = new ArrayList<>();
                ConnectionDB conDB = ConnectionDB.getInstance();
                PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("select nomeItem, dataCadastro, descricaoItem, latitudeItem, longitudeItem, raioItem, nomeCategoria, nomeStatus from findit.Item natural join findit.Categoria natural join findit.Status where codigoCliente = ? and " + complementQuery[mOption]);
                stmt.setInt(1, mUserCode);

                ResultSet rs = stmt.executeQuery();

                Item item;

                while (rs.next()) {
                    item = new Item(rs.getString("nomeItem"), rs.getString("descricaoItem"), rs.getDouble("latitudeItem"), rs.getDouble("longitudeItem"), rs.getDouble("raioItem"), rs.getString("nomeCategoria"), rs.getString("nomeStatus"), mUserCode);
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
            mAuthTask = null;
            showProgress(false);

            if (list == null) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ListItem.this.getActivity());
                dlg.setMessage("Ocorreu um problema ao tentar recuperar os itens!");
                dlg.show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
