package jose.carlex.friendface3;

public class Friend {
    private String mUsername = "";
    private double mAge = 21.0;
    private String mLocation = "";
    private String mRole = "";
    private String mDescription = "";

    public Friend(String username, double age,
                  String location, String role,
                  String description) {
        mUsername = username;
        mAge = age;
        mLocation = location;
        mRole = role;
        mDescription = description;

        return;
    }

    public String getUsername() { return mUsername; }
    public double getAge() { return mAge; }
    public String getLocation() { return mLocation; }
    public String getRole() { return mRole; }
    public String getDescription() { return mDescription; }
}
