package khalilbhijazi.gmail.com.gethip;

public class User {

    private String email;
    private String password;

    //default constructor

    public User() {

    }

    //constructor that takes in two String arguments, the user's email and password, and then
    //initializes the private variables with those values

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //returns user's email

    public String getEmail() {
        return email;
    }

    //changes the value of user's email to equal the parameter value

    public void setEmail(String email) {
        this.email = email;
    }

    //changes the value of user's password to equal the parameter value

    public void setPassword(String password) {
        this.password = password;
    }

    //returns user's password

    public String getPassword() {

        return password;
    }
}
