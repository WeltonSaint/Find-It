package lps.com.br.find_it;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.mysql.jdbc.PreparedStatement;

import java.sql.SQLException;

public class CreateUserActivity extends AppCompatActivity {

    public AeSimpleSHA1 SHA1 = new AeSimpleSHA1();
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private CreateUserTask mAuthTask = null;

    // UI references.
    private EditText mUserNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mContact;
    private EditText mPasswordView;
    private EditText mRepeatPasswordView;
    private View mProgressView;
    private View mCreateUserFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mUserNameView = (EditText) findViewById(R.id.name);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email_sign_up);
        mContact = (EditText) findViewById(R.id.user_contact);
        mPasswordView = (EditText) findViewById(R.id.password_sign_up);
        mRepeatPasswordView = (EditText) findViewById(R.id.repeat_password_sign_up);

        Button mEmailSignInButton = (Button) findViewById(R.id.sign_up_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

        mCreateUserFormView = findViewById(R.id.create_user_form);
        mProgressView = findViewById(R.id.create_user_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignUp() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mEmailView.setError(null);
        mContact.setError(null);
        mPasswordView.setError(null);
        mRepeatPasswordView.setError(null);

        // Store values at the time of the sign up attempt.
        String userName = mUserNameView.getText().toString();
        String email = mEmailView.getText().toString();
        String contact = mContact.getText().toString();
        String password = mPasswordView.getText().toString();
        String repeatPassword = mRepeatPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a repeat password is valid, if the user entered one.
        if (!TextUtils.isEmpty(repeatPassword) && !repeatPassword.equals(password)) {
            mRepeatPasswordView.setError(getString(R.string.error_repeat_password));
            focusView = mRepeatPasswordView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid contact, if the user entered one.
        if (!TextUtils.isEmpty(contact) && !isPhoneNumberValid(contact)) {
            mContact.setError(getString(R.string.error_invalid_contact));
            focusView = mContact;
            cancel = true;
        }

        // Check for a valid user name, if the user entered one.
        if (!TextUtils.isEmpty(userName) && !isUserNameValid(userName)) {
            mUserNameView.setError(getString(R.string.error_invalid_username));
            focusView = mUserNameView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(userName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (TextUtils.isEmpty(contact)) {
            mContact.setError(getString(R.string.error_field_required));
            focusView = mContact;
            cancel = true;
        }  else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(repeatPassword)) {
            mRepeatPasswordView.setError(getString(R.string.error_field_required));
            focusView = mRepeatPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            try {

                showProgress(true);
                mAuthTask = new CreateUserTask(userName, contact, email.toUpperCase(), SHA1.SHA1(password));
                mAuthTask.execute((Void) null);

            } catch (Exception e) {
                //send error notification
            }
        }
    }

    private boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 8;
    }

    private boolean isUserNameValid(String userName) {
        //TODO: Replace this with your own logic
        return userName.length() > 8;
    }

    private static boolean isPhoneNumberValid(String phone) {
        return !(TextUtils.isEmpty(phone)) && Patterns.PHONE.matcher(phone).matches();
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

            mCreateUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mCreateUserFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCreateUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCreateUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mContact;
        private final String mPassword;
        private final String mUser;

        CreateUserTask(String userName, String contact, String email, String password) {
            mEmail = email;
            mContact = contact;
            mPassword = password;
            mUser = userName;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {

                ConnectionDB conDB = ConnectionDB.getInstance();
                PreparedStatement stmt = (PreparedStatement) conDB.getConnection().prepareStatement("insert into findit.Cliente (nomeCliente, contatoCliente, emailCliente, senhaCliente) values (?,?,?,?)");
                stmt.setString(1, mUser);
                stmt.setString(2, mContact);
                stmt.setString(3, mEmail);
                stmt.setString(4, mPassword);

                stmt.execute();

                return true;

            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            AlertDialog.Builder dlg = new AlertDialog.Builder(CreateUserActivity.this);

            if (success) {

                dlg.setMessage("Usuário Cadastrado com Sucesso!");
                dlg.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CreateUserActivity.this.finish();
                    }
                });

            } else {
                dlg.setMessage("Não foi Possível Cadastrar o Usuário! Tente novamente...");
                dlg.setNeutralButton("Ok", null);
            }
            dlg.show();
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}