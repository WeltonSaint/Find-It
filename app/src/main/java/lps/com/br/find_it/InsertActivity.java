package lps.com.br.find_it;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mysql.jdbc.PreparedStatement;

import java.sql.SQLException;
import java.util.Date;

public class InsertActivity extends AppCompatActivity {
    private EditText mItemName;
    private EditText mItemDescription;
    private EditText mLocation;
    private EditText mDate;
    private Spinner spinSituation;
    private Spinner spinCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItemName = (EditText) findViewById(R.id.name);
        mItemDescription = (EditText) findViewById(R.id.description);
        mLocation = (EditText) findViewById(R.id.position);
        mDate = (EditText) findViewById(R.id.data);

        spinSituation = (Spinner) findViewById(R.id.spin_situation);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.situation, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSituation.setAdapter(adapter);

        spinCategory = (Spinner) findViewById(R.id.spin_category);

        ArrayAdapter<CharSequence> adapterCategory = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCategory.setAdapter(adapterCategory);

        Button ButtonItem = (Button) findViewById(R.id.email_sign_in_button);
        ButtonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
            }
        });


    }

    public void insertItem(){
        // Reset errors.
        mItemName.setError(null);
        mItemDescription.setError(null);
        mLocation.setError(null);
        mDate.setError(null);


        // Store values at the time of the sign up attempt.
        String nameItem = mItemName.getText().toString();
        String description = mItemDescription.getText().toString();
        String location = mLocation.getText().toString();
        Date date = new java.sql.Date(2,3,4);
        String category = spinCategory.getSelectedItem().toString();
        String situation = spinSituation.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid item.
        if(TextUtils.isEmpty(nameItem)){
            mItemName.setError(getString(R.string.error_field_required));
            focusView = mItemName;
            cancel = true;
        } else if(TextUtils.isEmpty(description)){
            mItemDescription.setError(getString(R.string.error_field_required));
            focusView = mItemDescription;
            cancel = true;
        } else if(TextUtils.isEmpty(location)){
            mLocation.setError(getString(R.string.error_field_required));
            focusView = mLocation;
            cancel = true;
        } else if(date == null || TextUtils.isEmpty(date.toString())){
            mDate.setError(getString(R.string.error_field_required));
            focusView = mDate;
            cancel = true;
        }  else if(category.equals("Categoria")){
            Toast.makeText(this, "Escolha uma categoria", Toast.LENGTH_LONG).show();
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            //    focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
          /*  try {

                showProgress(true);
                mAuthTask = new CreateUserActivity.CreateUserTask(userName, contact, email.toUpperCase(), SHA1.SHA1(password));
                mAuthTask.execute((Void) null);

            } catch (Exception e) {
                //send error notification
            }*/

            try {
                int t = -1;
                double f = 0.0;
                String s = "as";

                ConnectionDB conDB = ConnectionDB.getInstance();
                PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("insert into findit.Item (nomeItem, descricaoItem, dataCadastro, latitudeItem, longitudeItem, raioItem, codigoCategoria, codigoStatus, codigoCliente) values (?,?,?,?,?,?,?,?,?)");

          /*      PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("" +
                        "insert into findit.Item (nomeItem, descricaoItem, dataCadastro, latitudeItem, longitudeItem, " +
                        "raioItem, codigoCategoria, codigoStatus, codigoCliente) values " +
                        "(s,s,date,f,f,f,t,t,t)");
*/
                stmt.setString(1, nameItem);
                stmt.setString(2, description);
                stmt.setDate(3, (java.sql.Date) date);
                stmt.setDouble(4, f);
                stmt.setDouble(5, f);
                stmt.setDouble(6, f);
                //    stmt.setDate(6, (java.sql.Date) date);
                stmt.setInt(7, t);
                stmt.setInt(8, t);
                stmt.setInt(9, t);

                if(stmt.execute()){
                    Toast.makeText(this, "Cadastro de item realizado com sucesso!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "Falha ao cadastrar novo item!", Toast.LENGTH_LONG).show();

                }

            } catch (SQLException e) {
                System.out.println("ERRO MENSAGEM: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
