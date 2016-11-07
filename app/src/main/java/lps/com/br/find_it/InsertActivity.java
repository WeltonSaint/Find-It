package lps.com.br.find_it;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mysql.jdbc.PreparedStatement;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class InsertActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText mItemName;
    private EditText mItemDescription;
    private Spinner spinSituation;
    private Spinner spinCategory;
    private SupportMapFragment mMap;

    private InsertActivity.InsertItem mInsertItemTask = null;

    private double lat;
    private double longit;
    private double distance;
    private int strokeColor = 0x33b5e6;
    private int shadeColor = 0x4433b5e6;
    private int mUserCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mUserCode = intent.getIntExtra("code", 0);

        setContentView(R.layout.activity_insert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mItemName = (EditText) findViewById(R.id.name);
        mItemDescription = (EditText) findViewById(R.id.description);

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
                if(KeepLogin.isConnected(InsertActivity.this))
                    insertItem();
                else
                    KeepLogin.showIsNotConected(InsertActivity.this);
            }
        });

        mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mMap.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        distance = 0;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            lat = location.getLatitude();
            longit = location.getLongitude();
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title("Marker")
                    .draggable(true)
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(174)));
        }
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                googleMap.clear();
                lat = latLng.latitude;
                longit = latLng.longitude;

                CircleOptions circleOptions = new CircleOptions().center(latLng).radius(distance).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(2);
                googleMap.addCircle(circleOptions);
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Marker")
                        .draggable(true)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(174)));
            }
        });
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                    googleMap.clear();

                    Location loc1 = new Location("");
                    Location loc2 = new Location("");

                    loc1.setLatitude(lat);
                    loc1.setLongitude(longit);
                    loc2.setLatitude(latLng.latitude);
                    loc2.setLongitude(latLng.longitude);

                    distance = loc1.distanceTo(loc2);

                    CircleOptions circleOptions = new CircleOptions().center(new LatLng(lat, longit)).radius(distance).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(2);
                    googleMap.addCircle(circleOptions);
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, longit))
                            .title("Marker")
                            .draggable(true)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(174)));
            }
        });

    }

    public void insertItem() {
        // Reset errors.
        mItemName.setError(null);
        mItemDescription.setError(null);

        // Store values at the time of the sign up attempt.
        String nameItem = mItemName.getText().toString();
        String description = mItemDescription.getText().toString();
        String category = spinCategory.getSelectedItem().toString();
        String situation = spinSituation.getSelectedItem().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid item.
        if (TextUtils.isEmpty(nameItem)) {
            mItemName.setError(getString(R.string.error_field_required));
            focusView = mItemName;
            cancel = true;
        } else if (TextUtils.isEmpty(description)) {
            mItemDescription.setError(getString(R.string.error_field_required));
            focusView = mItemDescription;
            cancel = true;
        } else if (category.equals("Categoria")) {
            Toast.makeText(this, "Escolha uma categoria", Toast.LENGTH_LONG).show();
            focusView = null;
            cancel = true;
        } else if( lat == 0 && longit == 0){
            Toast.makeText(this, "Insira um ponto de Referência onde o Item foi Encontrado!", Toast.LENGTH_LONG).show();
            focusView = null;
            cancel = true;
        } else if(distance == 0){
            Toast.makeText(this, "Insira um Raio de Localização onde o Item foi Encontrado!", Toast.LENGTH_LONG).show();
            focusView = null;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if(focusView != null)
                focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            try {
                Item item = new Item(nameItem, description, lat, longit, distance, category, situation, mUserCode);
                if(Match.match(item)){
                    showNotification(getUserContact(Match.melhorResultado.getCodigoUsuario()),nameItem);
                }
                mInsertItemTask = new InsertActivity.InsertItem(item);
                mInsertItemTask.execute((Void) null);

            } catch (Exception e) {
                //send error notification
            }

        }
    }

    private void showNotification(String contactUser, String itemName){
        if (whatsappInstalledOrNot("com.whatsapp")) {
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(contactUser)+"@s.whatsapp.net");
            new MyNotificationManager(this, "Match de Item", "O seu item \""+itemName+"\" teve um match, clique aqui para conversar com quem o encontrou", sendIntent);
        } else {
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        }
    }

    private String getUserContact(int mUserCode){
        NoName2 task = new NoName2(mUserCode);
        try {
            return task.execute((Void) null).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class InsertItem extends AsyncTask<Void, Void, Boolean> {

        private final Item mItem;

        InsertItem(Item item) {
            this.mItem = item;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                ConnectionDB conDB = ConnectionDB.getInstance();
                PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("INSERT INTO findit.Item (nomeItem, descricaoItem, latitudeItem, longitudeItem, raioItem, codigoCategoria, codigoStatus, codigoCliente) SELECT ? as nomeItem, ? as descricaoItem, ? as latitudeItem, ? as longitudeItem, ? as raioItem, (select codigoCategoria from Categoria where nomeCategoria = ?) as codigoCategoria, (select codigoStatus from Status where nomeStatus = ?) as codigoStatus, ? as codigoCliente");

                stmt.setString(1, mItem.getNomeItem());
                stmt.setString(2, mItem.getDescricao());
                stmt.setDouble(3, mItem.getLatitude());
                stmt.setDouble(4, mItem.getLongitude());
                stmt.setDouble(5, mItem.getRaio());
                stmt.setString(6, mItem.getCategoria());
                stmt.setString(7, mItem.getStatus());
                stmt.setInt(8, mItem.getCodigoUsuario());

                stmt.execute();
                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mInsertItemTask = null;
            AlertDialog.Builder dlg = new AlertDialog.Builder(InsertActivity.this);

            if (success) {

                dlg.setMessage("Item Cadastrado com Sucesso!");
                dlg.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InsertActivity.this.finish();
                    }
                });

            } else {
                dlg.setMessage("Não foi Possível Cadastrar o Item! Tente novamente...");
                dlg.setNeutralButton("Ok", null);
            }
            dlg.show();
        }

        @Override
        protected void onCancelled() {
            mInsertItemTask = null;
        }
    }
}
