package jose.carlex.friendface3;

        import android.app.Application;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private String mUsername = "";
    private String mPassword = "";
    private String mRole = "";
    private String mDescription = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent recvdIntent = getIntent();

        mUsername = recvdIntent.getStringExtra("USERNAME");
        mPassword = recvdIntent.getStringExtra("PASSWORD");

        Button btnSubmit = (Button) findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitUserData();
                        return;
                    }
                }
        );

        return;
    }

    private void submitUserData() {
        EditText edtUsername = (EditText) findViewById(R.id.edit_username);
        EditText edtPassword = (EditText) findViewById(R.id.edit_password);
        EditText edtRole = (EditText) findViewById(R.id.edit_role);
        EditText edtDesc = (EditText) findViewById(R.id.edit_desc);

        FriendFaceApp app = (FriendFaceApp) getApplication();
        app.saveUserData(edtUsername.getText().toString(),
                edtPassword.getText().toString(),
                edtRole.getText().toString(),
                edtDesc.getText().toString() );
        finish();
        return;
    }
}
