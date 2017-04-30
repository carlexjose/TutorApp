package jose.carlex.friendface3;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<FriendStatusMessage> mMessageList = null;
    private ArrayAdapter<FriendStatusMessage> mAdapter = null;
    private String mUsername = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent receivedIntent = getIntent();

        mUsername = receivedIntent.getStringExtra("USERNAME");

        Button btnPost = (Button) findViewById(R.id.btn_post);
        btnPost.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        postMessage(mUsername);
                        return;
                    }
                }
        );

        TextView txvGreeting = (TextView) findViewById(R.id.greeting_text);
        txvGreeting.setText("What's up, " + mUsername + "?");

        FriendFaceApp app = (FriendFaceApp) getApplication();

        /* Attempt to load back old Friend List */
        boolean isFriendListLoaded = app.loadAppUserFriendList(mUsername);
        if (isFriendListLoaded == false) {
            /* If not loaded, then randomize a new list of friends */
            app.initializeFriendList();

            /* Save the latest friend list */
            app.saveAppUserFriendList(mUsername);
        }

        /* Attempt to load back old Status Message List */
        boolean isMsgListLoaded = app.loadAppUserMessageList(mUsername);
        if (isMsgListLoaded == false) {
            /* If not loaded, then randomize a new list of status messages */
           // mMessageList.clear();
            app.initializeMessageList();

            /* Save the latest message list */
            app.saveAppUserMessageList(mUsername);
        }

        ArrayList<Friend> friendList = app.getFriendList();
        mMessageList = app.getMessageList();

        mAdapter = new ArrayAdapter<FriendStatusMessage>(this,
                android.R.layout.simple_list_item_1, mMessageList);

        ListView lstMessages = (ListView) findViewById(R.id.list_messages);
        lstMessages.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent launchIntent = new Intent(MainActivity.this,
                                ProfileActivity.class);

                        FriendStatusMessage friendMessage = mMessageList.get(position);
                        String targetUsernameStr = friendMessage.getUsername();

                        launchIntent.putExtra("USERNAME", targetUsernameStr);
                        startActivity(launchIntent);

                        return;
                    }
                }
        );

        lstMessages.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //Toast.makeText(this, "This is a message", Toast.LENGTH_SHORT).show();

        return;
    }

    @Override
    protected void onDestroy() {
        FriendFaceApp app = (FriendFaceApp) getApplication();
        app.saveAppUserMessageList(mUsername);
        app.saveAppUserFriendList(mUsername);

        super.onDestroy();
        return;
    }

    private void postMessage(String userStr) {
        EditText edtStatusMessage = (EditText) findViewById(R.id.edit_status_message);

        String messageStr = edtStatusMessage.getText().toString();

        mMessageList.add(new FriendStatusMessage(userStr, messageStr));
        FriendFaceApp app = (FriendFaceApp) getApplication();
        app.saveAppUserMessageList(mUsername);
        app.saveAppUserFriendList(mUsername);
        mAdapter.notifyDataSetChanged();

        return;
    }
}
