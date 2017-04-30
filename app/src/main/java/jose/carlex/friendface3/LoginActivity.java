package jose.carlex.friendface3;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.io.FileInputStream;
        import java.io.FileOutputStream;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = (Button) findViewById(R.id.button_Login);
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /* Verify the user */
                        FriendFaceApp app = (FriendFaceApp) getApplication();
                        String validUsername = app.getAppUser();
                        String validPassword = app.getAppUserPassword();

                        EditText edtUsername = (EditText) findViewById(R.id.edit_username);
                        EditText edtPassword = (EditText) findViewById(R.id.edit_password);

                        String enteredUsername = edtUsername.getText().toString();
                        String enteredPassword = edtPassword.getText().toString();

                        if (enteredUsername.equals("")) {
                            return;
                        }

                        if ( enteredUsername.equals(validUsername) &&
                                enteredPassword.equals(validPassword) ) {

                            Intent launchIntent = new Intent(LoginActivity.this,
                                    MainActivity.class);

                            launchIntent.putExtra("USERNAME", edtUsername.getText().toString());
                            launchIntent.putExtra("PASSWORD", edtPassword.getText().toString());

                            startActivity(launchIntent);
                        }

                        else{
                            Toast.makeText(getApplicationContext(), "Invalid username or password.", Toast.LENGTH_LONG).show();
                        }

                        return;
                    }
                }
        );

        Button btnRegister = (Button) findViewById(R.id.button_Register);
        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edtUsername = (EditText) findViewById(R.id.edit_username);
                        EditText edtPassword = (EditText) findViewById(R.id.edit_password);

                        Intent launchIntent = new Intent(LoginActivity.this,
                                RegisterActivity.class); /* CAUSES AN ERROR */

                        launchIntent.putExtra("USERNAME", edtUsername.getText().toString());
                        launchIntent.putExtra("PASSWORD", edtPassword.getText().toString());

                        startActivityForResult(launchIntent, 0);

                        return;

                    }
                }
        );

        return;
    }
}
