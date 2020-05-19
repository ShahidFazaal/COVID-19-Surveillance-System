package controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import sun.audio.AudioPlayer;
import util.AddCenters;
import util.AddHospital;
import util.AddUsers;
import util.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public class DashboardController {
    private static final AudioClip errorAlertSound = new AudioClip(AudioPlayer.class.getResource("/resource/sounds/windows_error.mp3").toString());
    private static final AudioClip success = new AudioClip(AudioPlayer.class.getResource("/resource/sounds/success.mp3").toString());
    private static final AudioClip informationAlertSound = new AudioClip(AudioPlayer.class.getResource("/resource/sounds/windows_10_notify.mp3").toString());
    static int manageHospitalClick = 0;
    static int manageQCClick = 0;
    static int manageUsersClick = 0;
    ArrayList<String> places = new ArrayList<>(Arrays.asList("Jaffna", "Kilinochchi", "Kilinochchi", "Mannar", "Mullaitivu", "Vavuniya", "Puttalam", "Kurunegala", "Gampaha", "Colombo", "Kalutara", "Anuradhapura", "Polonnaruwa", "Matale",
            "Kandy", "Nuwara", "Kegalle", "Ratnapura", "Trincomalee", "Batticaloa", "Ampara", "Badulla", "Monaragala", "Hambantota", "Matara", "Galle"));


    //============================  InterFace ===================================//
    public JFXButton btnGlobalCovid;
    public JFXButton btnManageHospital;
    public ImageView titleImage;
    public Label txtTitle;
    public JFXButton btnManageQC;
    public JFXButton btnManageUsers;
    public Pane pnGlobalCovid;
    public Pane manageHospital;
    public Pane manageQC;
    public Pane manageusers;
    //============================  Starts Global Covid ===================================//
    public Label lblUpdate;
    public Label lblConfirmedCases;
    public Label lblRecovered;
    public Label lblDeath;
    public DatePicker txtUpdateDate;
    public TextField txtConfirmedCases;
    public TextField txtRecovered;
    public TextField txtDeath;
    public Button btnUpdateGC;
    //============================  Starts manage Hospital===================================//
    public TextField txtSearch;
    public JFXButton btnNewHospital;
    public TextField txtHId;
    public TextField txtHName;

    public TextField txtCity;
    public TextField txtCapacity;
    public TextField txtDirector;
    public TextField txtDContactNo;
    public TextField txtHContactNo1;
    public TextField txtHContactNo2;
    public TextField txtHFax;
    public TextField txtHEmail;
    public ComboBox cmbDistrict;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public ListView<AddHospital> lstHospital;
    ArrayList<AddHospital> searchHospitals = new ArrayList<>();

    //============================  Starts manage Quarantine Centers===================================//
    public ListView <AddCenters>lstManageQC;
    public TextField txtQSearch;
    public JFXButton btnNewCenterQ;
    public TextField txtQId;
    public TextField txtQName;
    public TextField txtQCity;
    public TextField txtQHead;
    public TextField txtQHeadContact;
    public TextField txtQContact1;
    public TextField txtQContact2;
    public TextField txtQCapacity;
    public ComboBox cmbQDistrict;
    public JFXButton btnSaveQ;
    public JFXButton btnDeleteQ;
    ArrayList<AddCenters> searchCenters = new ArrayList<>();

    //============================  Starts manage Users Centers===================================//
    public TextField txtUsersName;
    public TextField txtUsersContact;
    public TextField txtUsersEmail;
    public TextField txtUsersUserName;
    public TextField txtUsersPassword;
    public ComboBox cmbUsersUserRole;
    public JFXButton btnSaveU;
    public TableView <AddUsers> tblUsers;
    public TextField txtSearchUsers;
    public ComboBox cmbUsersDepartment;
    static String tempUserRole;
    static String tempUserDepartment;


    public void initialize() throws SQLException {
        //=============================== Manage Hospital starts in Initialize method=========================================================
        txtHName.setDisable(true);
        txtCity.setDisable(true);
        cmbDistrict.setDisable(true);
        txtCapacity.setDisable(true);
        txtDirector.setDisable(true);
        txtDContactNo.setDisable(true);
        txtHContactNo1.setDisable(true);
        txtHContactNo2.setDisable(true);
        txtHFax.setDisable(true);
        txtHEmail.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setDisable(true);


        pnGlobalCovid.setVisible(true);
        btnGlobalCovid.setStyle("-fx-background-color: #2ECC71 ");
        loadData();

        txtConfirmedCases.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtConfirmedCases.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        txtRecovered.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtRecovered.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        txtDeath.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    txtDeath.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


        lstHospital.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AddHospital>() {
            @Override
            public void changed(ObservableValue<? extends AddHospital> observable, AddHospital oldValue, AddHospital newValue) {
                AddHospital selectedItem = lstHospital.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnSave.setText("Save");
                    btnDelete.setDisable(true);
                    txtHId.clear();
                    txtHName.clear();
                    txtCity.clear();
                    txtCapacity.clear();
                    txtDirector.clear();
                    txtDContactNo.clear();
                    txtHContactNo1.clear();
                    txtHContactNo2.clear();
                    txtHFax.clear();
                    txtHEmail.clear();
                    cmbDistrict.getSelectionModel().clearSelection();

                    txtHName.setDisable(true);
                    txtCity.setDisable(true);
                    cmbDistrict.setDisable(true);
                    txtCapacity.setDisable(true);
                    txtDirector.setDisable(true);
                    txtDContactNo.setDisable(true);
                    txtHContactNo1.setDisable(true);
                    txtHContactNo2.setDisable(true);
                    txtHFax.setDisable(true);
                    txtHEmail.setDisable(true);
                    btnDelete.setDisable(true);
                    btnSave.setDisable(true);

                    return;
                }


                txtHId.setText(newValue.getId());
                txtHName.setText(newValue.gethName());
                txtCity.setText(newValue.getCity());
                cmbDistrict.getSelectionModel().select(newValue.getDistrict());
                txtCapacity.setText(newValue.getCapacity() + "");
                txtDirector.setText(newValue.getDirector());
                txtDContactNo.setText(newValue.getdContactNo() + "");
                txtHContactNo1.setText(newValue.gethContactNo1() + "");
                txtHContactNo2.setText(newValue.gethContactNo2() + "");
                txtHFax.setText(newValue.gethFax() + "");
                txtHEmail.setText(newValue.gethEmail());

                txtHName.setDisable(false);
                txtCity.setDisable(false);
                cmbDistrict.setDisable(false);
                txtCapacity.setDisable(false);
                txtDirector.setDisable(false);
                txtDContactNo.setDisable(false);
                txtHContactNo1.setDisable(false);
                txtHContactNo2.setDisable(false);
                txtHFax.setDisable(false);
                txtHEmail.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setText("Update");
                btnSave.setDisable(false);
            }
        });


        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtSearch.getText().trim().equals("")) {
                    try {
                        loadHospitals();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                ObservableList<AddHospital> hospitals = lstHospital.getItems();
                hospitals.clear();
                for (AddHospital hospital : searchHospitals) {
                    if (hospital.getId().contains(newValue) || hospital.gethName().contains(newValue) || hospital.getDistrict().contains(newValue)) {
                        hospitals.add(hospital);
                    }
                }
            }
        });

        //=============================== Manage Quarantine center starts in Initialize method=========================================================

        txtQId.setDisable(true);
        txtQName.setDisable(true);
        txtQCity.setDisable(true);
        cmbQDistrict.setDisable(true);
        txtQHead.setDisable(true);
        txtQHeadContact.setDisable(true);
        txtQContact1.setDisable(true);
        txtQContact2.setDisable(true);
        txtQCapacity.setDisable(true);
        btnSaveQ.setDisable(true);
        btnDeleteQ.setDisable(true);

        lstManageQC.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AddCenters>() {
            @Override
            public void changed(ObservableValue<? extends AddCenters> observable, AddCenters oldValue, AddCenters newValue) {
                AddCenters selectedItem = lstManageQC.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnSaveQ.setText("Save");
                    btnDeleteQ.setDisable(true);
                    txtQId.clear();
                    txtQName.clear();
                    txtQCity.clear();
                    txtQHead.clear();
                    txtQHeadContact.clear();
                    txtQContact1.clear();
                    txtQContact2.clear();
                    txtQCapacity.clear();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            cmbQDistrict.getSelectionModel().clearSelection();
                        }
                    });


                    txtQId.setDisable(true);
                    txtQName.setDisable(true);
                    txtQCity.setDisable(true);
                    cmbQDistrict.setDisable(true);
                    txtQHead.setDisable(true);
                    txtQHeadContact.setDisable(true);
                    txtQContact1.setDisable(true);
                    txtQContact2.setDisable(true);
                    txtQCapacity.setDisable(true);
                    btnSaveQ.setDisable(true);
                    btnDeleteQ.setDisable(true);

                    return;
                }


                txtQId.setText(newValue.getqId());
                txtQName.setText(newValue.getqName());
                txtQCity.setText(newValue.getqCity());
                cmbQDistrict.getSelectionModel().select(newValue.getqDistrict());

                txtQHead.setText(newValue.getqHead());
                txtQHeadContact.setText(newValue.getqHeadContact() + "");
                txtQContact1.setText(newValue.getqContact1() + "");
                txtQContact2.setText(newValue.getqContact2() + "");
                txtQCapacity.setText(newValue.getqCapacity()+ "");


                txtQId.setDisable(false);
                txtQName.setDisable(false);
                txtQCity.setDisable(false);
                cmbQDistrict.setDisable(false);
                txtQHead.setDisable(false);
                txtQHeadContact.setDisable(false);
                txtQContact1.setDisable(false);
                txtQContact2.setDisable(false);
                txtQCapacity.setDisable(false);

                btnDeleteQ.setDisable(false);
                btnSaveQ.setText("Update");
                btnSaveQ.setDisable(false);

            }
        });

        txtQSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtQSearch.getText().trim().equals("")) {
                    try {
                        loadCenters();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                ObservableList<AddCenters> centers = lstManageQC.getItems();
                centers.clear();
                for (AddCenters center : searchCenters) {
                    if (center.getqId().contains(newValue) || center.getqName().contains(newValue) || center.getqDistrict().contains(newValue)) {
                        centers.add(center);
                    }
                }
            }
        });
  //============================ Manage Users starts from here in initialize method  ==============================================================//
        btnSaveU.setDisable(true);
        txtUsersName.setDisable(true);
        txtUsersContact.setDisable(true);
        txtUsersEmail.setDisable(true);
        txtUsersUserName.setDisable(true);
        txtUsersPassword.setDisable(true);
        cmbUsersUserRole.setDisable(true);

        cmbUsersDepartment.setVisible(false);
        ObservableList hospitalOrQc = cmbUsersDepartment.getItems();

        cmbUsersUserRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (cmbUsersUserRole.getSelectionModel().getSelectedItem() == null){
                    cmbUsersDepartment.setVisible(false);
                    return;
                }
                String selectedItem = cmbUsersUserRole.getSelectionModel().getSelectedItem().toString();
                if (selectedItem.equals("Hospital IT") || selectedItem.equals("Quarantine Center IT")){
                    cmbUsersDepartment.setVisible(true);
                    if (selectedItem.equals("Hospital IT")){
                        hospitalOrQc.clear();
                        try {
                            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT name FROM managehospitals WHERE itPerson='not assign'");
                            while (resultSet.next()){
                                hospitalOrQc.add(resultSet.getString(1));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    if (selectedItem.equals("Quarantine Center IT")){
                        hospitalOrQc.clear();
                        try {
                            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT name FROM managequarantinecenters WHERE itPerson='not assign'");
                            while (resultSet.next()){
                                hospitalOrQc.add(resultSet.getString(1));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }else{
                    cmbUsersDepartment.setVisible(false);
                }
            }
        });

        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("usersUserName"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("usersName"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("usersRole"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("button"));

        tblUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AddUsers>() {
            @Override
            public void changed(ObservableValue<? extends AddUsers> observable, AddUsers oldValue, AddUsers newValue) {
                AddUsers selectedItem = tblUsers.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnSaveU.setText("Update");
                    txtUsersName.clear();
                    txtUsersContact.clear();
                    txtUsersEmail.clear();
                    txtUsersUserName.clear();
                    txtUsersPassword.clear();



                    btnSaveU.setDisable(true);
                    txtUsersName.setDisable(true);
                    txtUsersContact.setDisable(true);
                    txtUsersEmail.setDisable(true);
                    txtUsersUserName.setDisable(true);
                    txtUsersPassword.setDisable(true);
                    cmbUsersUserRole.setDisable(true);

                    return;
                }
                btnSaveU.setDisable(false);
                txtUsersName.setDisable(false);
                txtUsersContact.setDisable(false);
                txtUsersEmail.setDisable(false);
                txtUsersUserName.setDisable(false);
                txtUsersPassword.setDisable(false);
                cmbUsersUserRole.setDisable(false);
                txtUsersUserName.setEditable(false);
                txtUsersPassword.setEditable(false);

                btnSaveU.setText("Update");
                txtUsersName.setText(newValue.getUsersName());
                txtUsersContact.setText(newValue.getUsersContact() +"");
                txtUsersEmail.setText(newValue.getUsersEmail());
                txtUsersUserName.setText(newValue.getUsersUserName());
                txtUsersPassword.setText(newValue.getUsersPassword());
                cmbUsersUserRole.getSelectionModel().select(newValue.getUsersRole());
                if (cmbUsersUserRole.getSelectionModel().getSelectedItem() == "Hospital IT" || cmbUsersUserRole.getSelectionModel().getSelectedItem() == "Quarantine Center IT" ){
                    cmbUsersDepartment.setVisible(true);
                    cmbUsersDepartment.getSelectionModel().select(newValue.getUsersDepartment());

                }else{
                    cmbUsersDepartment.setVisible(false);

                }
                tempUserRole = cmbUsersUserRole.getSelectionModel().getSelectedItem().toString();
                System.out.println(tempUserRole);
                if (tempUserRole.equals("Hospital IT") || tempUserRole.equals("Quarantine Center IT")) {
                    tempUserDepartment = cmbUsersDepartment.getSelectionModel().getSelectedItem().toString();
                    System.out.println(tempUserDepartment);
                }


            }
        });

    }


    //============================ Starts InterFace ===================================//


    public void btnGlobalCovid_OnAction(ActionEvent event) {
        pnGlobalCovid.setVisible(true);
        manageHospital.setVisible(false);
        manageQC.setVisible(false);
        manageusers.setVisible(false);
        Image image = new Image("/resource/icons8-earth-planet-512.png");
        titleImage.setImage(image);
        txtTitle.setText("Global COVID-19");
    }

    public void btnManageHospital_OnAction(ActionEvent event) throws SQLException {

        pnGlobalCovid.setVisible(false);
        manageHospital.setVisible(true);
        manageQC.setVisible(false);
        manageusers.setVisible(false);
        Image image = new Image("/resource/icons8-hospital-96.png");
        titleImage.setImage(image);
        txtTitle.setText("Manage Hospitals");


        ObservableList<String> districts = cmbDistrict.getItems();
        districts.clear();
        for (String district : this.places) {
            districts.add(district);
        }

        loadHospitals();


        manageHospitalClick++;


    }

    public void btnManageQC_OnAction(ActionEvent event) throws SQLException {
        pnGlobalCovid.setVisible(false);
        manageHospital.setVisible(false);
        manageQC.setVisible(true);
        manageusers.setVisible(false);
        ObservableList<String> districts = cmbQDistrict.getItems();
        districts.clear();
        for (String district : this.places) {
            districts.add(district);
        }
        loadCenters();




    }

    public void btnManageUsers_OnAction(ActionEvent event) throws SQLException {
        manageUsersClick++;
        pnGlobalCovid.setVisible(false);
        manageHospital.setVisible(false);
        manageQC.setVisible(false);
        manageusers.setVisible(true);

        if (manageUsersClick == 1) {

            ObservableList role = cmbUsersUserRole.getItems();
            role.add("Admin");
            role.add("P.S.T.F.Member");
            role.add("Hospital IT");
            role.add("Quarantine Center IT");
        }
        loadUsers();

    }

    public void onMouseClick(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() instanceof JFXButton) {
            JFXButton button = (JFXButton) mouseEvent.getSource();


            switch (button.getId()) {
                case "btnGlobalCovid":
                    btnGlobalCovid.setStyle("-fx-background-color: #2ECC71 ");
                    btnManageHospital.setStyle("-fx-background-color: #76D7C4 ");
                    btnManageQC.setStyle("-fx-background-color: #76D7C4 ");
                    btnManageUsers.setStyle("-fx-background-color: #76D7C4 ");
                    break;
                case "btnManageHospital":
                    btnManageHospital.setStyle("-fx-background-color: #2ECC71 ");
                    btnGlobalCovid.setStyle("-fx-background-color:  #76D7C4 ");
                    btnManageQC.setStyle("-fx-background-color: #76D7C4 ");
                    btnManageUsers.setStyle("-fx-background-color: #76D7C4 ");
                    break;
                case "btnManageQC":
                    btnManageHospital.setStyle("-fx-background-color: #76D7C4 ");
                    btnGlobalCovid.setStyle("-fx-background-color:  #76D7C4 ");
                    btnManageQC.setStyle("-fx-background-color: #2ECC71 ");
                    btnManageUsers.setStyle("-fx-background-color: #76D7C4 ");

                    break;
                case "btnManageUsers":
                    btnManageHospital.setStyle("-fx-background-color: #76D7C4 ");
                    btnGlobalCovid.setStyle("-fx-background-color:  #76D7C4 ");
                    btnManageQC.setStyle("-fx-background-color: #76D7C4 ");
                    btnManageUsers.setStyle("-fx-background-color: #2ECC71 ");
                    break;
            }

        }
    }
    //============================ Global COVID-19 starts from here ============================================================//

    private void loadData() throws SQLException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM globalcovid");
        while (resultSet.next()) {
            int a = resultSet.getInt(2);
            int b = resultSet.getInt(3);
            int c = resultSet.getInt(4);
            DecimalFormat formatter = new DecimalFormat("#,###");
            String confirmedCases = formatter.format(a);
            String recoveredCases = formatter.format(b);
            String deaths = formatter.format(c);
            lblUpdate.setText(resultSet.getDate(1).toString());
            lblConfirmedCases.setText(confirmedCases);
            lblRecovered.setText(recoveredCases);
            lblDeath.setText(deaths);

        }
    }

    public void btnUpdateGC_OnAction(ActionEvent event) throws SQLException {
        if (txtUpdateDate.getValue() == null || txtConfirmedCases.getText().trim().equals("") && txtRecovered.getText().trim().equals("") && txtDeath.getText().trim().equals("")) {
            errorAlertSound.play();
            new Alert(Alert.AlertType.ERROR, "Text Fields Cant Be Empty", ButtonType.OK).show();
            return;
        }

        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM globalcovid ORDER BY 1 DESC ");
        resultSet.next();
            int a = resultSet.getInt(2);
            int b = resultSet.getInt(3);
            int c = resultSet.getInt(4);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        LocalDate lastUpdate = txtUpdateDate.getValue();
        String tempConfirmedCases = txtConfirmedCases.getText();
        String tempRecoveredCases = txtRecovered.getText();
        String tempDeaths = txtDeath.getText();

        if (tempConfirmedCases.equals("")){
            tempConfirmedCases = String.valueOf(a);
        }
        if(tempRecoveredCases.equals("")){
            tempRecoveredCases = String.valueOf(b);
        }
        if(tempDeaths.equals("")){
            tempDeaths = String.valueOf(c);
        }

        System.out.println(tempConfirmedCases);
        System.out.println(tempRecoveredCases);
        System.out.println(tempDeaths);


        int confirmedCases = Integer.parseInt(tempConfirmedCases);
        int recoveredCases = Integer.parseInt(tempRecoveredCases);
        int deaths = Integer.parseInt(tempDeaths);

        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO globalcovid VALUES (?,?,?,?)");
        statement.setObject(1, lastUpdate);
        statement.setObject(2, confirmedCases);
        statement.setObject(3, recoveredCases);
        statement.setObject(4, deaths);
        success.play();
        new Alert(Alert.AlertType.INFORMATION, "Data Updated Successfully", ButtonType.OK).show();
        statement.executeUpdate();
        loadData();
    }

    //============================ Manage Hospital starts from here  ==============================================================//

    public void btnSave_OnAcrtion(ActionEvent event) throws SQLException {

        if (txtHName.getText().trim().equals("")) {
            errorAlertSound.play();
            txtHName.requestFocus();
            return;
        } else if (txtCity.getText().trim().equals("")) {
            errorAlertSound.play();
            txtCity.requestFocus();
            return;
        } else if (cmbDistrict.getSelectionModel().getSelectedItem() == null) {
            errorAlertSound.play();
            cmbDistrict.requestFocus();
            return;
        } else if (txtCapacity.getText().trim().equals("")) {
            errorAlertSound.play();
            txtCapacity.requestFocus();
            return;
        } else if (txtDirector.getText().trim().equals("")) {
            errorAlertSound.play();
            txtDirector.requestFocus();
            return;
        } else if (txtDContactNo.getText().trim().equals("")) {
            errorAlertSound.play();
            txtDContactNo.requestFocus();
            return;
        } else if (txtHContactNo1.getText().trim().equals("")) {
            errorAlertSound.play();
            txtHContactNo1.requestFocus();
            return;
        } else if (txtHContactNo2.getText().trim().equals("")) {
            errorAlertSound.play();
            txtHContactNo2.requestFocus();
            return;
        } else if (txtHFax.getText().trim().equals("")) {
            errorAlertSound.play();
            txtHFax.requestFocus();
            return;
        } else if (txtHEmail.getText().trim().equals("")) {
            errorAlertSound.play();
            txtHEmail.requestFocus();
            return;
        }

        String emailValidation = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{3}$";
        Pattern emailPattern = Pattern.compile(emailValidation);
        if (!txtHEmail.getText().trim().matches(String.valueOf(emailPattern))) {
            errorAlertSound.play();
            new Alert(Alert.AlertType.ERROR, "The Email address you entered is invalid", ButtonType.OK).show();
            txtHEmail.requestFocus();
            return;
        }

        //toDo Further development regarding mobile number validation

        String phoneValidation = "^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|5|6|7|8)\\d)\\d{6}$";
        Pattern phonePattern = Pattern.compile(phoneValidation);

        if (!txtDContactNo.getText().trim().matches(String.valueOf(phonePattern)) || !txtHContactNo1.getText().trim().matches(String.valueOf(phonePattern)) || !txtHContactNo2.getText().trim().matches(String.valueOf(phonePattern)) || !txtHFax.getText().trim().matches(String.valueOf(phonePattern))) {
            errorAlertSound.play();
            new Alert(Alert.AlertType.ERROR, "The contact details is invalid", ButtonType.OK).show();
            return;
        }
        String id = txtHId.getText();
        String hName = txtHName.getText();
        String city = txtCity.getText();

        int capacity = Integer.parseInt(txtCapacity.getText());
        String director = txtDirector.getText();
        int dContactNo = Integer.parseInt(txtDContactNo.getText());
        int hContactNo1 = Integer.parseInt(txtHContactNo1.getText());
        int hContactNo2 = Integer.parseInt(txtHContactNo2.getText());
        int hFax = Integer.parseInt(txtHFax.getText());
        String hEmail = txtHEmail.getText();
        String district = cmbDistrict.getSelectionModel().getSelectedItem().toString();

        if (btnSave.getText().equals("Save")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Press Ok to add the hospital", ButtonType.OK, ButtonType.CANCEL);
            informationAlertSound.play();
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.OK) {
                PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO managehospitals VALUES (?,?,?,?,?,?,?,?,?,?,?,?,DEFAULT )");
                statement.setObject(1, id);
                statement.setObject(2, hName);
                statement.setObject(3, city);
                statement.setObject(4, district);
                statement.setObject(5, capacity);
                statement.setObject(6, director);
                statement.setObject(7, dContactNo);
                statement.setObject(8, hContactNo1);
                statement.setObject(9, hContactNo2);
                statement.setObject(10, hFax);
                statement.setObject(11, hEmail);
                statement.setObject(12, "fazaal1234");
                statement.executeUpdate();
                loadHospitals();

            }
        } else {
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managehospitals SET name=?,city=?,district=?,capacity=?,director=?,directorContactNo=?,hospitalContactNo1=?,hospitalContactNo2=?,faxNo=?,email=? WHERE id =?");
            statement.setObject(1, hName);
            statement.setObject(2, city);
            statement.setObject(3, district);
            statement.setObject(4, capacity);
            statement.setObject(5, director);
            statement.setObject(6, dContactNo);
            statement.setObject(7, hContactNo1);
            statement.setObject(8, hContactNo2);
            statement.setObject(9, hFax);
            statement.setObject(10, hEmail);
            statement.setObject(11, id);
            statement.executeUpdate();
            loadHospitals();

        }




    }

    public void btnDelete_OnAction(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to delete?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            String id = lstHospital.getSelectionModel().getSelectedItem().getId();
            DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM managehospitals WHERE id ='" + id + "'");
            lstHospital.getSelectionModel().clearSelection();

            loadHospitals();
        }


    }

    public void btnNewHospital_OnAction(ActionEvent event) throws SQLException {
        lstHospital.getSelectionModel().clearSelection();
        txtHName.setDisable(false);
        txtCity.setDisable(false);
        cmbDistrict.setDisable(false);
        txtCapacity.setDisable(false);
        txtDirector.setDisable(false);
        txtDContactNo.setDisable(false);
        txtHContactNo1.setDisable(false);
        txtHContactNo2.setDisable(false);
        txtHFax.setDisable(false);
        txtHEmail.setDisable(false);
        btnDelete.setDisable(true);
        btnSave.setText("Save");
        btnSave.setDisable(false);
        genarateHospitalId();
        txtHName.requestFocus();


    }

    private void genarateHospitalId() throws SQLException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM managehospitals ORDER BY 1 DESC");
        if (resultSet.next()) {
            String hID = resultSet.getString(1);
            String number = hID.substring(1, 4);
            int newHId = Integer.parseInt(number) + 1;
            if (newHId < 10) {

                txtHId.setText("H00" + newHId);
            } else if (newHId < 100) {

                txtHId.setText("H0" + newHId);
            } else {

                txtHId.setText("H" + newHId);
            }
        } else {
            txtHId.setText("H001");
        }
    }

    private void loadHospitals() throws SQLException {
        ObservableList<AddHospital> hospital = lstHospital.getItems();
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT  * FROM managehospitals");
        hospital.clear();
        searchHospitals.clear();

        while (resultSet.next()) {
            hospital.add(new AddHospital(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10), resultSet.getString(11)));
            searchHospitals.add(new AddHospital(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9), resultSet.getInt(10), resultSet.getString(11)));

        }
    }

    //Manage Quarantine Centers starts from here ============================================================================>>

    public void btnNewCenterQ_OnAction(ActionEvent event) throws SQLException {
        lstManageQC.getSelectionModel().clearSelection();
        txtQId.setDisable(false);
        txtQName.setDisable(false);
        txtQCity.setDisable(false);
        cmbQDistrict.setDisable(false);
        txtQHead.setDisable(false);
        txtQHeadContact.setDisable(false);
        txtQContact1.setDisable(false);
        txtQContact2.setDisable(false);
        txtQCapacity.setDisable(false);
        btnSaveQ.setDisable(false);
        txtQName.requestFocus();
        generateCenterId();
    }

    public void btnSaveQ_OnAction(ActionEvent event) throws SQLException {
        if (txtQId.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQId.requestFocus();
            return;
        } else if (txtQName.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQName.requestFocus();
            return;
        } else if (txtQCity.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQCity.requestFocus();
            return;
        } else if (cmbQDistrict.getSelectionModel().getSelectedItem() == null) {
            errorAlertSound.play();
            cmbQDistrict.requestFocus();
            return;
        } else if (txtQHead.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQHead.requestFocus();
            return;
        } else if (txtQHeadContact.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQHeadContact.requestFocus();
            return;
        } else if (txtQContact1.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQContact1.requestFocus();
            return;
        } else if (txtQContact2.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQContact2.requestFocus();
            return;
        } else if (txtQCapacity.getText().trim().equals("")) {
            errorAlertSound.play();
            txtQCapacity.requestFocus();
            return;
        }

            //toDo Further development regarding mobile number validation

            String phoneValidation = "^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|5|6|7|8)\\d)\\d{6}$";
            Pattern phonePattern = Pattern.compile(phoneValidation);

            if (!txtQHeadContact.getText().trim().matches(String.valueOf(phonePattern)) || !txtQContact1.getText().trim().matches(String.valueOf(phonePattern)) || !txtQContact2.getText().trim().matches(String.valueOf(phonePattern))) {
                errorAlertSound.play();
                new Alert(Alert.AlertType.ERROR, "The contact details is invalid", ButtonType.OK).show();
                return;
            }
            String qId = txtQId.getText();
            String qName = txtQName.getText();
            String qCity = txtQCity.getText();
            String qDistrict = cmbQDistrict.getSelectionModel().getSelectedItem().toString();
            String qHead = txtQHead.getText();
            int qHeadContact = Integer.parseInt(txtQHeadContact.getText());
            int qContact1 = Integer.parseInt(txtQContact1.getText());
            int qContact2 = Integer.parseInt(txtQContact2.getText());
            int qCapacity = Integer.parseInt(txtQCapacity.getText());


            if (btnSaveQ.getText().equals("Save")) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Press Ok to add the Quarantine Center", ButtonType.OK, ButtonType.CANCEL);
                informationAlertSound.play();
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.OK) {
                    PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO managequarantinecenters VALUES (?,?,?,?,?,?,?,?,?,?,DEFAULT)");
                    statement.setObject(1, qId);
                    statement.setObject(2, qName);
                    statement.setObject(3, qCity);
                    statement.setObject(4, qDistrict);
                    statement.setObject(5, qHead);
                    statement.setObject(6, qHeadContact);
                    statement.setObject(7, qContact1);
                    statement.setObject(8, qContact2);
                    statement.setObject(9, qCapacity);
                    statement.setObject(10, "fazaal1234");
                    statement.executeUpdate();
                    loadCenters();

                }
            }else{
                PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managequarantinecenters SET name=?,city=?,district=?,head=?,headContactNo=?,centerContactNo1=?,centerContactNo2=?,capacity=? WHERE id =?");
                statement.setObject(1, qName);
                statement.setObject(2, qCity);
                statement.setObject(3, qDistrict);
                statement.setObject(4, qHead);
                statement.setObject(5, qHeadContact);
                statement.setObject(6, qContact1);
                statement.setObject(7, qContact2);
                statement.setObject(8, qCapacity);
                statement.setObject(9, qId);
                statement.executeUpdate();
                loadCenters();
            }
        }

    public void btnDeleteQ_OnAction(ActionEvent event) throws SQLException {
        informationAlertSound.play();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to delete?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            String qId = lstManageQC.getSelectionModel().getSelectedItem().getqId();
            DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM managequarantinecenters WHERE id ='" + qId + "'");
            lstManageQC.getSelectionModel().clearSelection();
            loadCenters();
        }

    }
    private void generateCenterId() throws SQLException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT id FROM managequarantinecenters ORDER BY 1 DESC");
        if (resultSet.next()) {
            String cID = resultSet.getString(1);
            String number = cID.substring(1, 4);
            int newHId = Integer.parseInt(number) + 1;
            if (newHId < 10) {

                txtQId.setText("C00" + newHId);
            } else if (newHId < 100) {

                txtQId.setText("C0" + newHId);
            } else {

                txtQId.setText("C" + newHId);
            }
        } else {
            txtQId.setText("C001");
        }
    }

    private void loadCenters() throws SQLException {
        ObservableList<AddCenters> centers = lstManageQC.getItems();
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT  * FROM managequarantinecenters");
        centers.clear();
        searchCenters.clear();

        while (resultSet.next()) {
            centers.add(new AddCenters(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)));
            searchCenters.add(new AddCenters(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getInt(9)));

        }
    }
    //============================ Manage Users starts from here  ==============================================================//
    public void btnSaveU_OnAction(ActionEvent event) throws SQLException {
        if (btnSaveU.getText().equals("Save")) {
            String usersName = txtUsersName.getText();
            int usersContact = Integer.parseInt(txtUsersContact.getText());
            String usersEmail = txtUsersEmail.getText();
            String usersUserName = txtUsersUserName.getText();
            String usersPassword = txtUsersPassword.getText();
            String usersRole = cmbUsersUserRole.getSelectionModel().getSelectedItem().toString();
            String usersDepartment = null;
            if (usersRole.equals("Hospital IT") || usersRole.equals("Quarantine Center IT")) {
                usersDepartment = cmbUsersDepartment.getSelectionModel().getSelectedItem().toString();
                if (usersRole.equals("Hospital IT")) {
                    PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managehospitals SET itPerson=? WHERE name=?");
                    statement.setObject(1, "assign");
                    statement.setObject(2, usersDepartment);
                    statement.executeUpdate();
                }
                if (usersRole.equals("Quarantine Center IT")) {
                    PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managequarantinecenters SET itPerson=? WHERE name=?");
                    statement.setObject(1, "assign");
                    statement.setObject(2, usersDepartment);
                    statement.executeUpdate();
                }

            }

            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO manageusers VALUES (?,?,?,?,?,?,?)");
            statement.setObject(1, usersName);
            statement.setObject(2, usersContact);
            statement.setObject(3, usersEmail);
            statement.setObject(4, usersUserName);
            statement.setObject(5, usersPassword);
            statement.setObject(6, usersRole);
            statement.setObject(7, usersDepartment);
            statement.executeUpdate();
            success.play();
            new Alert(Alert.AlertType.INFORMATION, "User added Successfully", ButtonType.OK).show();
            loadUsers();
        }else{
            String usersName = txtUsersName.getText();
            int usersContact = Integer.parseInt(txtUsersContact.getText());
            String usersEmail = txtUsersEmail.getText();
            String usersUserName = txtUsersUserName.getText();
            String usersPassword = txtUsersPassword.getText();
            String usersRole = cmbUsersUserRole.getSelectionModel().getSelectedItem().toString();
            String usersDepartment = null;
            if (usersRole.equals("Hospital IT") || usersRole.equals("Quarantine Center IT")) {
                usersDepartment = cmbUsersDepartment.getSelectionModel().getSelectedItem().toString();
            }
            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE manageusers set name=?,contactNo=?,email=?,userRole=?,department=? WHERE userName = ?");
            statement.setObject(1, usersName);
            statement.setObject(2, usersContact);
            statement.setObject(3, usersEmail);
            statement.setObject(4, usersRole);
            statement.setObject(5, usersDepartment);
            statement.setObject(6,usersUserName);
            statement.executeUpdate();

            if(tempUserRole.equals("Hospital IT") || tempUserRole.equals("Quarantine Center IT")){
                if (tempUserRole.equals("Hospital IT")) {
                    PreparedStatement statement2 = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managehospitals SET itPerson=? WHERE name=?");
                    statement2.setObject(1, "not assign");
                    statement2.setObject(2, tempUserDepartment);
                    statement2.executeUpdate();

                    if (usersRole.equals("Quarantine Center IT")){
                        PreparedStatement statement5 = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managequarantinecenters SET itPerson=? WHERE name=?");
                        statement5.setObject(1, "assign");
                        statement5.setObject(2, usersDepartment);
                        statement5.executeUpdate();
                    }

                }
                if (tempUserRole.equals("Quarantine Center IT")) {
                    PreparedStatement statement3 = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managequarantinecenters SET itPerson=? WHERE name=?");
                    statement3.setObject(1, "not assign");
                    statement3.setObject(2, tempUserDepartment);
                    statement3.executeUpdate();
                    if (usersRole.equals("Hospital IT")){
                        PreparedStatement statement6 = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managehospitals SET itPerson=? WHERE name=?");
                        statement6.setObject(1, "assign");
                        statement6.setObject(2, usersDepartment);
                        statement6.executeUpdate();
                    }
                }
            }else{
                if (usersRole.equals("Hospital IT")) {
                    PreparedStatement statement4 = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managehospitals SET itPerson=? WHERE name=?");
                    statement4.setObject(1, "assign");
                    statement4.setObject(2, usersDepartment);
                    statement4.executeUpdate();
                }
                if (usersRole.equals("Quarantine Center IT")) {
                    PreparedStatement statement4 = DBConnection.getInstance().getConnection().prepareStatement("UPDATE managequarantinecenters SET itPerson=? WHERE name=?");
                    statement4.setObject(1, "assign");
                    statement4.setObject(2, usersDepartment);
                    statement4.executeUpdate();
                }
            }

        }

        txtUsersName.clear();
        txtUsersContact.clear();
        txtUsersEmail.clear();
        txtUsersUserName.clear();
        txtUsersPassword.clear();
        cmbUsersUserRole.getSelectionModel().clearSelection();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cmbUsersDepartment.setVisible(false);
            }
        });
        loadUsers();


    }

    public void loadUsers() throws SQLException {
        ObservableList<AddUsers> users = tblUsers.getItems();
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT  * FROM manageusers");
        users.clear();
        while (resultSet.next()){
            String userName = resultSet.getString(4);
            Button btnRemove = new Button("Remove");
            btnRemove.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        informationAlertSound.play();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Do you want to delete the user?", ButtonType.OK, ButtonType.CANCEL);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.OK) {
                            PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM manageusers WHERE userName =?");
                            statement.setObject(1, userName);
                            statement.executeUpdate();
                            loadUsers();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });

            users.add(new AddUsers(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),btnRemove));
        }
    }


    public void OnKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            tblUsers.getSelectionModel().clearSelection();
            cmbUsersUserRole.getSelectionModel().clearSelection();
            btnSaveU.setDisable(false);
            txtUsersName.setDisable(false);
            txtUsersContact.setDisable(false);
            txtUsersEmail.setDisable(false);
            txtUsersUserName.setDisable(false);
            txtUsersPassword.setDisable(false);
            cmbUsersUserRole.setDisable(false);
            txtUsersUserName.setEditable(true);

            txtUsersPassword.setEditable(true);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    btnSaveU.setText("Save");
                    txtUsersName.requestFocus();
                }
            });
        }
    }
}
