package jose.carlex.friendface3;

        import android.app.Application;
        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Environment;
        import android.util.Log;

        import java.io.File;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Random;

public class FriendFaceApp extends Application {
    private ArrayList<Friend> mFriendList = new ArrayList<>();
    private ArrayList<FriendStatusMessage> mMessageList = new ArrayList<>();

    public void initializeFriendList() {
        String nameList[] = { "Anna", "Bill", "Catherine",
                "Dan", "Eunice", "Francis",
                "Jean", "Kate"};
        String locationList[] = { "Ateneo", "UP", "Unknown" };
        String roleList[] = { "Student", "Salesperson", "CEO",
                "Professor", "President", "Admin",
                "Mobile Developer", "Web Developer",
                "Corporate Zombie"
        };
        String descList[] = { "I like food. Food is good.",
                "Sleep is for the weak",
                "YOLO",
                "I hate swimming pools",
                "AAFDDRG%$T%$#@FFH___!r",
                "*mic drop*" };

        /* Insantiate a new Random object */
        Random rand = new Random();

        /* Create 3 random imaginary friends */
        for (int i = 0; i < 3; i++) {
            /* Roll random indices for each list */
            int randomNameIdx = rand.nextInt(nameList.length);
            int randomLocIdx = rand.nextInt(locationList.length);
            int randomRoleIdx = rand.nextInt(roleList.length);
            int randomDescIdx = rand.nextInt(descList.length);
            int randomAge = rand.nextInt(50);

            /* Create the new friend */
            Friend newFriend =
                    new Friend( nameList[randomNameIdx] + Integer.toString(i),
                            randomAge,
                            locationList[randomLocIdx],
                            roleList[randomRoleIdx],
                            descList[randomDescIdx]);

            /* Add the friend object to the list */
            mFriendList.add( newFriend );
        }

        return;
    }

    public void initializeMessageList() {
        String msgChoices[] = {
                "Hey",
                "Jude, don't make it bad",
                "I was doing just fine before I met you",
                "I drink too much and that's an issue",
                "Tell your friends it was nice to meet them",
                "I just met you?",
                "And this is crazy",
                "Here's my number: _",
                "Take a sad song and make it better",
                "Na na na na-na-na na",
                "BATMAN.",
                "Moved to the city in a broke down car.",
                "I remember when we were driving",
                "Driving in your car",
                "Had a feeling that I could be someone, be someone, be someone"
        };

        Random rand = new Random();

        int countAssignedFriends = 0;
        int randMessage = rand.nextInt(3);
        while (countAssignedFriends < randMessage) {
            /* Assign status message to Friend */
            int indexFriend = rand.nextInt(mFriendList.size()-1);
            int indexMessage = rand.nextInt(msgChoices.length);

            String username = mFriendList.get(indexFriend).getUsername();
            String statusMessage = msgChoices[indexMessage];

            FriendStatusMessage msg = new FriendStatusMessage(username, statusMessage);
            mMessageList.add(msg);

            countAssignedFriends += 1;
        }

        return;
    }

    public ArrayList<Friend> getFriendList() { return mFriendList; }
    public ArrayList<FriendStatusMessage> getMessageList() { return mMessageList; }

    public void saveUserData(String username, String password,
                             String role, String desc) {
        SharedPreferences prefs =
                getSharedPreferences("edu.ateneo.cie199.friendface", Context.MODE_PRIVATE);

        SharedPreferences.Editor edt = prefs.edit();

        edt.putString("USERNAME", username);
        edt.putString("PASSWORD", password);
        edt.putString("ROLE", role);
        edt.putString("DESC", desc);
        mFriendList.add(new Friend(username, 20, "ADMU", role, desc));

        edt.commit();

        return;
    }

    /* Methods for reloading data from the app's SharedPrefs */
    public String getAppUser() {
        SharedPreferences prefs =
                getSharedPreferences("edu.ateneo.cie199.friendface", Context.MODE_PRIVATE);

        return prefs.getString("USERNAME", "");
    }

    public String getAppUserPassword() {
        SharedPreferences prefs =
                getSharedPreferences("edu.ateneo.cie199.friendface", Context.MODE_PRIVATE);

        return prefs.getString("PASSWORD", "");
    }
    /* ********************************************* */
    /* Public Methods for updating message list data */
    /* ********************************************* */
    public boolean addStatusMessage(String username, String message){
        FriendStatusMessage fsm = new FriendStatusMessage(username, message);
        mMessageList.add( fsm );
        return true;
    }

    /* ************************************************************** */
    /* Public Methods for saving user data to files on the filesystem */
    /* ************************************************************** */
    public boolean saveAppUserFriendList(String username) {
        String filename = username + "_friendlist.txt";

        String fileContents = "";
        for (int i = 0; i < mFriendList.size(); i++) {
            Friend fr = mFriendList.get(i);

            fileContents += fr.getUsername() + "," +
                    fr.getLocation()  + "," +
                    fr.getRole() + "," +
                    fr.getAge() + "," +
                    fr.getDescription() + "\n";
        }

        return writeFile(filename, fileContents);
    }

    public boolean saveAppUserMessageList(String username) {
        String filename = username + "_messagelist.txt";

        String fileContents = "";
        for (int i = 0; i < mMessageList.size(); i++) {
            FriendStatusMessage msg = mMessageList.get(i);

            fileContents += msg.getUsername() + "," +
                    msg.getMessage()  + "\n";
        }

        return writeFile(filename, fileContents);
    }


    /* ***************************************************************** */
    /* Public Methods for loading user data from files on the filesystem */
    /* ***************************************************************** */
    public boolean loadAppUserFriendList(String username) {
        /* Get storage path */
        String loadPath = getStoragePath();

        /* Check if file exists */
        File loadFile = new File(loadPath, username + "_friendlist.txt");
        if (loadFile.exists() == false) {
            Log.e("FriendFaceApp", "File not loaded because it does not exist");
            return false;
        }

        String contents = "";
        try {
            /* Get file input stream */
            FileInputStream fis = new FileInputStream( loadFile );

            /* Read from file input stream */
            while (fis.available() > 0) {
                byte buf[] = new byte[32];
                int bytesRead = fis.read(buf, 0, 32);
                contents += new String(buf, 0, bytesRead);
            }

            /* close file input stream */
            fis.close();
        } catch (Exception e) {
            Log.e("FriendFaceApp", "Exception occurred: " + e.getMessage());
            return false;
        }

        /* Display the contents in Android Monitor */
        Log.d("FriendFaceApp", "File Read Done:");
        Log.d("FriendFaceApp", "    " + contents );

        /* Parse the file contents */
        boolean result = parseFriendsToList(contents);

        return result;
    }

    public boolean loadAppUserMessageList(String username) {
        /* Get storage path */
        String loadPath = getStoragePath();

        /* Check if file exists */
        File loadFile = new File(loadPath, username + "_messagelist.txt");
        if (loadFile.exists() == false) {
            Log.e("FriendFaceApp", "File not loaded because it does not exist");
            return false;
        }

        String contents = "";
        try {
            /* Get file input stream */
            FileInputStream fis = new FileInputStream( loadFile );

            /* Read from file input stream */
            while (fis.available() > 0) {
                byte buf[] = new byte[32];
                int bytesRead = fis.read(buf, 0, 32);
                contents += new String(buf, 0, bytesRead);
            }

            /* close file input stream */
            fis.close();
        } catch (Exception e) {
            Log.e("FriendFaceApp", "Exception occurred: " + e.getMessage());
            return false;
        }

        /* Display the contents in Android Monitor */
        Log.d("FriendFaceApp", "File Read Done:");
        Log.d("FriendFaceApp", "    " + contents );

        /* Parse the file contents */
        boolean result = parseMessagesToList(contents);

        return result;
    }


    /* ******************************************************** */
    /* Private Methods for parsing data files on the filesystem */
    /* ******************************************************** */
    private boolean parseFriendsToList(String contentsFriendFile) {
        /* Clear the existing friend list */
        mFriendList.clear();

        /* Divide the contents of the friend list file by newlines (\n)
         *  to obtain each individual line in the file */
        String lines[] = contentsFriendFile.split("\n");

        /* Cycle through each line */
        for (int i = 0; i < lines.length; i++) {
            /* Split the current line by commas (, ) to separate the saved pieces of info */
            String friendInfo[] = lines[i].split(",");

            /* Store each piece of information in a temporary variable */
            String username = friendInfo[0];
            String location = friendInfo[1];
            String role = friendInfo[2];
            double age = Double.parseDouble(friendInfo[3]);
            String description = friendInfo[4];

            /* Create a Friend object from the info */
            Friend newFriend = new Friend( username, age, location, role, description );

            /* Add the new Friend object to the Friend list */
            mFriendList.add(newFriend);
        }
        //mFriendList.add();

        return true;
    }


    /* *********************** */
    /* Private Utility Methods */
    /* *********************** */
    private boolean parseMessagesToList(String contentsMessageFile) {
        /* Clear the existing friend list */
        mMessageList.clear();

        /* Divide the contents of the friend list file by newlines (\n)
         *  to obtain each individual line in the file */
        String lines[] = contentsMessageFile.split("\n");

        /* Cycle through each line */
        for (int i = 0; i < lines.length; i++) {
            /* Split the current line by commas (, ) to separate the saved pieces of info */
            String friendMessageInfo[] = lines[i].split(",");

            /* Store each piece of information in a temporary variable */
            String username = friendMessageInfo[0];
            String statusMessage = friendMessageInfo[1];

            /* Create a Friend object from the info */
            FriendStatusMessage newMsg = new FriendStatusMessage(username,statusMessage);

            /* Add the new Friend object to the Friend list */
            mMessageList.add(newMsg);
        }

        return true;
    }

    private String getStoragePath() {
        String storagePath = getFilesDir().toString();
        return storagePath;
    }

    private boolean writeFile(String fileName, String data) {
        /* Get storage path */
        String savePath = getStoragePath();

        /* Check if path exists */
        File pathStorage = new File(savePath);
        if (pathStorage.exists() == false) {
            /* Create the directory */
            pathStorage.mkdirs();
        }

        /* Check if the file exists */
        File saveFile = new File(savePath, fileName);
        if (saveFile.exists() == false) {
            try {
                saveFile.createNewFile();
            } catch (Exception e) {
                Log.e("FriendFaceApp", "Exception occurred: " + e.getMessage());
                return false;
            }
        }

        try {
            /* Get file output stream */
            FileOutputStream fos = new FileOutputStream(saveFile, false);

            /* Write to file */
            fos.write(data.getBytes());

            /* close the output stream */
            fos.close();
        } catch (Exception e) {
            Log.e("FriendFaceApp", "Exception occurred: " + e.getMessage());
            return false;
        }

        return true;
    }
}
