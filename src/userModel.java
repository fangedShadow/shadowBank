
public class userModel {
	
	private String UserName;
	private String Password;
	
	private static userModel instance;
	
	private userModel() {
        // private constructor to enforce singleton pattern
    }

    public static userModel getInstance() {
        if (instance == null) {
            instance = new userModel();
        }
        return instance;
    }
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	

}
