package jose.carlex.friendface3;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent receiveIntent = getIntent();
        String username = receiveIntent.getStringExtra("USERNAME");

        FriendFaceApp app = (FriendFaceApp) getApplication();
        ArrayList<Friend> friendList = app.getFriendList();

        //Toast.makeText(this, username, Toast.LENGTH_SHORT).show();

        Friend selectedFriend = null;
        for (int i = 0; i < friendList.size(); i++) {
            String tempUsername = friendList.get(i).getUsername();
            if ( username.equals( tempUsername ) == true ) {
                selectedFriend = friendList.get(i);
                break;
            }
        }

        if (selectedFriend != null) {
            loadProfile( selectedFriend );
        }
        else{
            //Toast.makeText(this, "selectedfriend is null", Toast.LENGTH_SHORT).show();
        }

        return;

    }

    private void loadProfile(Friend friendProfile) {
        TextView txvName = (TextView) findViewById(R.id.txv_name);
        TextView txvDet1 = (TextView) findViewById(R.id.txv_det1);
        TextView txvDet2 = (TextView) findViewById(R.id.txv_det2);
        TextView txvDet3 = (TextView) findViewById(R.id.txv_det3);

        txvName.setText( friendProfile.getUsername() );
        txvDet1.setText( friendProfile.getAge() + " / " + friendProfile.getLocation());
        txvDet2.setText( friendProfile.getRole());
        txvDet3.setText( friendProfile.getDescription());

        return;
    }
}

