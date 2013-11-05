package dvdcollectionapp.view;

import dvdcollectionapp.model.DVD;
import dvdcollectionapp.util.IOManager;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Josh Hanrahan
 */
public class DvdcollectionappfxmlController implements Initializable {
    //controller data... 

    private ObservableList<DVD> DVDList;
    private ArrayList<DVD> DVDDatabase;
    private String currentFileName = "dvd.dat";
    private ObservableList<String> genreList;

    @FXML
    private TableView<DVD> dvdTable;
    @FXML
    private TextField titleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField starsField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField blurbField;

    @FXML
    private Tab imageTab;
    @FXML
    private Tab detailsTab;
    @FXML
    private Tab genreTab;
    @FXML
    private ImageView dvdImage;
    @FXML
    private MenuButton fileMenuButton;
    @FXML
    private MenuItem openMenuItem;
    @FXML
    private MenuItem saveMenuItem;
    @FXML
    private MenuItem saveAsMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private TableColumn<DVD, String> titleColumn;

    @FXML
    private TextField messageField;
    @FXML
    private Button okButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button cancelButton;

    @FXML
    private MenuItem newMenuItem;
    @FXML
    private MenuItem editMenuItem;
    @FXML
    private TabPane DVDTabPane;

    private DVD dvd;

    @FXML
    private TextField imageURLField;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private ComboBox<String> genreChoiceBox;
    @FXML
    private Tab welcomeTab;

    @FXML
    private TableView<DVD> genreListView;
    @FXML
    private TableColumn<DVD, String> genreTitleColumn;

    /**
     * Initializes the controller class. Initialises the DVD and genre lists,
     * then loads in all DVD objects and the genre items. It then initialises
     * both the left side table view as well as the table in the genre tab. Then
     * it adds a listener to the dvd table on the left which listens for
     * changes, and if the image tab is selected, it will show the image for the
     * object that is currently selected. Additionally it listens to the genre
     * choice box and displays the relevant title of all dvd objects in that
     * genre into the genre table in the genre tab
     *
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {

        //Create the observable data structures
        DVDList = FXCollections.observableArrayList();
        genreList = FXCollections.observableArrayList("Action", "Drama", "Comedy", "Family", "Fantasy", "Sci-Fi");

        //Load default dvd data
        loadGenre();
        loadDVDs();

        // Initialize the dvd table
        titleColumn.setCellValueFactory(new PropertyValueFactory<DVD, String>("title"));
        genreTitleColumn.setCellValueFactory(new PropertyValueFactory<DVD, String>("title"));

        // Auto resize columns 
        dvdTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        genreListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //Select first entry
        dvdTable.getSelectionModel().selectFirst();

        // Listen for selection changes with a ChangeListener object (defined inline)
        dvdTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DVD>() {
            @Override
            public void changed(ObservableValue<? extends DVD> observable, DVD oldValue, DVD newValue) {
                System.out.println("DVD old val: " + oldValue);
                System.out.println("DVD new val: " + newValue);
                if (imageTab.isSelected()) {
                    displayDVDImage(newValue);
                } else {
                    displayDVDDetails(newValue);
                }
            }

        });
        genreChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Genre old val: " + oldValue);
                System.out.println("Genre new val: " + newValue);
                if (genreTab.isSelected()) {
                    displayGenreTitles(newValue);

                } else {
                    displayGenreTitles(oldValue);
                }
            }
        });
    }

    @FXML
    /**
     * Handles a string field and runs it through a regex to see that the
     * entered data is not numerical
     *
     * @param event
     */
    private void handleStringField(KeyEvent event) {
        TextField inputField = (TextField) event.getSource();
        if (inputField.isEditable() && event.getCharacter().matches("[0-9]*")) {
            inputField.getStyleClass().add("error");
            event.consume();
        } else {
            inputField.getStyleClass().remove("error");
        }
    }

    /**
     * Handles a digit field and runs it through a regex to see that the entered
     * data is not alphabetical
     *
     * @param event
     */
    @FXML
    private void handleDigitField(KeyEvent event) {
        if (yearField.isEditable() && event.getCharacter().matches("[a-zA-z\\s]*")) {
            yearField.getStyleClass().add("error");
            event.consume();
        } else {
            yearField.getStyleClass().remove("error");
        }
    }

    /**
     * Takes the selected object in the dvd table and if it is not null it
     * displays the image for that dvd
     *
     * @param event
     */
    private void showImageView(Event event) {
        int selectedIndex = dvdTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            DVD dvd = DVDList.get(selectedIndex);
            if (dvd != null) {
                displayDVDImage(dvd);
            }
        }
    }

    /**
     * Handles opening a file through the menu up the top. It calls all DVD's
     * from that file to then be loaded
     *
     * @param event
     */
    @FXML
    private void handleOpenFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.dat)", "*.dat");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show open file dialog and load contents of selected file
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            currentFileName = file.getName();
            loadDVDs();
        }
    }

    /**
     * Handles saving a file by invoking the write DVD database method
     *
     * @param event
     */
    @FXML
    private void handleSaveFile(Event event) {
        File file = new File(currentFileName);
        writeDVDDatabase(file);
    }

    /**
     * Handles a save as function by letting the user choose the save location
     * and then running the writeDVDDatabase method
     *
     * @param event
     */
    @FXML
    private void handleSaveAsFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            writeDVDDatabase(file);
        }
    }

    /**
     * Allows the application to be quit
     *
     * @param event
     */
    @FXML
    private void handleExitFile(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Loads all DVD's by calling the read dvd database method, then setting all
     * objects into the observable list, then finally setting the list to the
     * dvd table view
     */
    private void loadDVDs() {
        readDVDDatabase();
        DVDList.setAll(DVDDatabase);
        dvdTable.setItems(DVDList);
    }

    /**
     * Loads the genre observable list into the genre choice box
     */
    private void loadGenre() {
        genreChoiceBox.setItems(genreList);
    }

    /**
     * Reads a dvd database by checking if the file is a .csv or .dat file
     */
    private void readDVDDatabase() {
        if (currentFileName.endsWith(".dat") || currentFileName.endsWith(".csv")) {
            DVDDatabase = IOManager.readTextDatabase(new File(currentFileName));
        }
    }

    /**
     * displays the titles returned when a user chooses a genre from the genre
     * choice combo box. It does this by creating a new observable list, then
     * looping though all DVD objects in the DVD list which contains all dvd
     * that were read in as well as what the user has created or edited. It then
     * checks the genre passed to it from the initialisation and compares it to
     * each dvd object and if the genres match, it adds the object to the
     * obeservable list.
     *
     * @param genre the genre passed into the method from the choice box
     */
    private void displayGenreTitles(String genre) {
        ObservableList<DVD> genreOL;
        genreOL = FXCollections.observableArrayList();

        for (DVD a_dvd : DVDList) {
            if (genre.equals(a_dvd.getGenre())) {
                genreOL.add(a_dvd);
            } else {
            }

        }
        genreListView.setItems(genreOL);

    }

    /**
     * Sets each field in the details tab to the attributes for the dvd selected
     *
     * @param dvd the dvd selected
     */
    private void displayDVDDetails(DVD dvd) {
        if (dvd != null) {
            titleField.setText(dvd.getTitle());
            genreField.setText(dvd.getGenre());
            ratingField.setText(dvd.getRating());
            starsField.setText(dvd.getStars());
            yearField.setText(dvd.getYear());
            blurbField.setText(dvd.getBlurb());
            imageURLField.setText(dvd.getImageURL());

        } else {
            clearDVDDetails();
        }
    }

    /**
     * clears all fields in the details tab
     */
    private void clearDVDDetails() {
        titleField.setText("");
        genreField.setText("");
        ratingField.setText("");
        starsField.setText("");
        yearField.setText("");
        blurbField.setText("");
        imageURLField.setText("");
    }

    /**
     * Displays the image for a selected dvd by getting the imageurl, if none is
     * specified it grabs the default 'dvdlogo.jpg'
     *
     * @param dvd the dvd object selected
     */
    private void displayDVDImage(DVD dvd) {
        Image image;
        if (dvd != null) {
            image = new Image(getClass().getResourceAsStream(dvd.getImageURL()));
        } else {
            image = new Image(getClass().getResourceAsStream("images/dvdLogo.jpg"));
        }
        dvdImage.setImage(image);
    }

    /**
     * Writes a file to either .dat or .xml
     *
     * @param file the file specified
     */
    private void writeDVDDatabase(File file) {

        if (file.getName().endsWith(".dat")) {
            IOManager.writeTextDatabase(file, DVDList);
        } else if (file.getName().endsWith(".xml")) {
            IOManager.writeXMLDatabase(file.getName(), DVDList);
        }
    }

    /**
     * When an edit is complete it tells the user it's done and makes controls
     * not visible again
     *
     * @param event
     */
    @FXML
    private void handleEditDone(ActionEvent event) {
        messageField.setText("Edit DVD complete");
        getDVDDetails();
        int selectedIndex = dvdTable.getSelectionModel().getSelectedIndex();
        DVDList.set(selectedIndex, dvd);
        refreshDVDTable();
        setDVDDetailFieldsEditable(false);
        setEditControlsVisible(false);
    }

    /**
     * When an edit is cancelled is sets the controls back to not being visible
     *
     * @param event
     */
    @FXML
    private void handleEditCancel(ActionEvent event) {
        dvdTable.getSelectionModel().selectLast();
        int index = dvdTable.getSelectionModel().getSelectedIndex();
        dvdTable.getItems().remove(index);
        setDVDDetailFieldsEditable(false);
        setEditControlsVisible(false);
        DVDTabPane.getSelectionModel().select(detailsTab);
    }

    /**
     * When the details tab is selected, if a dvd is not null it display the
     * details of the dvd
     *
     * @param event
     */
    @FXML
    private void handleDVDDetailsTabSelection(Event event) {
        dvd = dvdTable.getSelectionModel().getSelectedItem();
        if (dvd != null) {
            displayDVDDetails(dvd);
        }
    }

    /**
     * When a dvd is selected and it is not null, it displays the image of the
     * dvd
     *
     * @param event
     */
    @FXML

    private void handleDVDImageSelection(Event event
            ) {
        dvd = dvdTable.getSelectionModel().getSelectedItem();
        if (dvd != null) {
            displayDVDImage(dvd);
        }
    }

    /**
     * clears the dvd details
     *
     * @param event
     */
    @FXML
    private void handleResetDVD(ActionEvent event) {
        clearDVDDetails();
    }

    /**
     * Handles creating a new dvd by setting the controls to visible and the
     * fields to editable. It then creates a new dvd object and adds it to the
     * dvdlist
     *
     * @param event
     */
    @FXML
    private void handleNewDVD(ActionEvent event
            ) {
        setEditControlsVisible(true);
        messageField.setText("Adding New DVD");
        DVDTabPane.getSelectionModel().select(detailsTab);
        DVD dvd = new DVD();
        displayDVDDetails(dvd);
        setDVDDetailFieldsEditable(true);
        DVDList.add(dvd);
        dvdTable.getSelectionModel().selectLast();
    }

    /**
     * When an edit needs to be done it sets the controls to visible, then sets
     * the fields to editable
     *
     * @param event
     */
    @FXML
    private void handleEditDVD(ActionEvent event
            ) {
        setEditControlsVisible(true);
        messageField.setText("Editing Selected DVD");
        DVDTabPane.getSelectionModel().select(detailsTab);
        dvd = dvdTable.getSelectionModel().getSelectedItem();
        setDVDDetailFieldsEditable(true);
    }

    /**
     * removes the selected dvd from the dvd table, if no dvd is selected it
     * informs the user to select a dvd
     *
     * @param event
     */
    @FXML
    private void handleDeleteDVD(ActionEvent event
            ) {
        messageField.setText("DVD deleted");
        int selectedIndex = dvdTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            dvdTable.getItems().remove(selectedIndex);
        } else {
            System.out.println("Please select a DVD!!!");
        }
    }

    /**
     * Sets the fields to be editable
     *
     * @param state
     */
    private void setDVDDetailFieldsEditable(boolean state) {
        titleField.setEditable(state);
        genreField.setEditable(state);
        ratingField.setEditable(state);
        starsField.setEditable(state);
        yearField.setEditable(state);
        blurbField.setEditable(state);
        imageURLField.setEditable(state);
    }

    /**
     * Sets the edit controls to visible
     *
     * @param state
     */
    private void setEditControlsVisible(boolean state) {
        messageField.setVisible(state);
        okButton.setVisible(state);
        cancelButton.setVisible(state);
        resetButton.setVisible(state);
    }

    /**
     * gets the fields for a dvd object
     */
    private void getDVDDetails() {
        dvd = new DVD(titleField.getText(), genreField.getText(),
                ratingField.getText(), starsField.getText(),
                yearField.getText(), blurbField.getText(), imageURLField.getText());
    }

    /**
     * refreshes the dvd table by reloading the dvdlist back into memory
     */
    private void refreshDVDTable() {
        int selectedIndex = dvdTable.getSelectionModel().getSelectedIndex();
        dvdTable.setItems(null);
        dvdTable.layout();
        dvdTable.setItems(DVDList);
        dvdTable.getSelectionModel().select(selectedIndex);
    }
}
