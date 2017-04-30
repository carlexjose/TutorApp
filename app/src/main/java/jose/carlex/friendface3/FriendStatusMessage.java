package jose.carlex.friendface3;

public class FriendStatusMessage {
    private String mUsername = "";
    private String mMessage = "";
    private Friend mUser = null;

    public FriendStatusMessage(String username, String statusMessage) {
        mUsername = username;
        mMessage = statusMessage;
        return;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getMessage() {
        return mMessage;
    }

    public String toString() {
        return getUsername() + ": " + getMessage() + "\n";
    }
}