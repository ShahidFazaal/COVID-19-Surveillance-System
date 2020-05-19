package util;

import javafx.scene.control.Button;

public class AddUsers {
    private String usersName;
    private int usersContact;
    private String usersEmail;
    private String usersUserName;
    private String usersPassword;
    private String usersRole;
    private String usersDepartment;
    private Button button;

    public AddUsers(String usersName, int usersContact, String usersEmail, String usersUserName, String usersPassword, String usersRole, String usersDepartment, Button button) {
        this.usersName = usersName;
        this.usersContact = usersContact;
        this.usersEmail = usersEmail;
        this.usersUserName = usersUserName;
        this.usersPassword = usersPassword;
        this.usersRole = usersRole;
        this.usersDepartment = usersDepartment;
        this.button = button;
    }

    public AddUsers(String usersName, int usersContact, String usersEmail, String usersUserName, String usersPassword, String usersRole, String usersDepartment) {
        this.usersName = usersName;
        this.usersContact = usersContact;
        this.usersEmail = usersEmail;
        this.usersUserName = usersUserName;
        this.usersPassword = usersPassword;
        this.usersRole = usersRole;
        this.usersDepartment = usersDepartment;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public int getUsersContact() {
        return usersContact;
    }

    public void setUsersContact(int usersContact) {
        this.usersContact = usersContact;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUsersUserName() {
        return usersUserName;
    }

    public void setUsersUserName(String usersUserName) {
        this.usersUserName = usersUserName;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    public String getUsersRole() {
        return usersRole;
    }

    public void setUsersRole(String usersRole) {
        this.usersRole = usersRole;
    }

    public String getUsersDepartment() {
        return usersDepartment;
    }

    public void setUsersDepartment(String usersDepartment) {
        this.usersDepartment = usersDepartment;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }


}
