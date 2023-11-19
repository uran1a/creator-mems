package programmingtechnology.creatormemes.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import programmingtechnology.creatormemes.logics.ConcreteAggregate;
import programmingtechnology.creatormemes.logics.Iterator;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button addTextButton;

    @FXML
    private Button createMemButton;


    @FXML
    private Pane pane;

    @FXML
    private Button nextImageButton;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private Button prevImageButton;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private Label pathFolderLabel;

    private FileChooser fileChooser;
    private ConcreteAggregate conaggr;
    private Iterator iter;
    private ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView = new ImageView();
        imageView.setFitHeight(298);
        imageView.setFitWidth(594);

        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\voron\\Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg")
        );

        this.nextImageButton.setOnMouseClicked(this::nextImageHandler);
        this.prevImageButton.setOnMouseClicked(this::prevImageHandler);
        this.createMemButton.setOnMouseClicked(this::createMemHandler);

        this.openMenuItem.setOnAction(this::openFileHandler);
        this.saveMenuItem.setOnAction(this::saveFileHandler);
    }

    private void createMemHandler(MouseEvent event) {
        Ellipse start = new Ellipse(286, 50,41, 22);
        start.setFill(Color.BLUE);
    }

    private void prevImageHandler(MouseEvent event) {
        if(iter.hasNext()){
            var image = (Image) iter.preview();
            imageView.setImage(image);
            pane.getChildren().add(imageView);
        }
    }

    private void nextImageHandler(MouseEvent event) {
        if(iter.hasNext())
            imageView.setImage((Image) iter.next());
    }
    private void saveFileHandler(ActionEvent actionEvent){
        int uniqueNumber =  String.valueOf(System.currentTimeMillis()).hashCode();
        fileChooser.setInitialFileName("mem_"+uniqueNumber+".txt");
       /* File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null)
            FileParser.parseToString(shapes, file);
        else
            displayWarningMessage("Неверный файл");*/
    }
    private void openFileHandler(ActionEvent actionEvent) {
        var selectedDirectory = fileChooser.showOpenDialog(pane.getScene().getWindow()).getParent();
        var dir = new File(selectedDirectory);

        pathFolderLabel.setText(selectedDirectory);

        conaggr = new ConcreteAggregate(new File(selectedDirectory));
        iter = conaggr.getIterator();
    /*    Deque<Shape> fileShapes = FileParser.parseToShapes(selectedFile);
        if(fileShapes.size() != 0){
            this.shapes = fileShapes;
            drawShapesOnCleanCanvas();
        }
        else
            displayWarningMessage("Файл пустой или с некоррестными данными");*/
    }
    private void displayWarningMessage(String text){
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Сообщение");
        alert.setHeaderText("Предупреждение");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
