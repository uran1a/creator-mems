package programmingtechnology.creatormemes.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
import javafx.util.Duration;
import programmingtechnology.creatormemes.logics.ConcreteAggregate;
import programmingtechnology.creatormemes.logics.Iterator;
import programmingtechnology.creatormemes.logics.Meme;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    @FXML
    private Button startMemeButton;

    @FXML
    private Button stopMemeButton;

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

    private Ellipse el1;
    private Ellipse el2;
    private Ellipse el3;
    private List<Ellipse> ellipses;
    private Text text;
    private FileChooser fileChooser;
    private ConcreteAggregate conaggr;
    private Iterator iter = null;
    private ImageView imageView;
    private Timeline timeline;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageView = new ImageView();
        imageView.setFitHeight(298);
        imageView.setFitWidth(594);

        pane.getChildren().add(imageView);

        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\voron\\Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpeg")
        );

        el1 = new Ellipse(95, 60,80, 45);
        el1.setFill(Color.WHITE); el1.setStroke(Color.BLACK);

        el2 = new Ellipse(146, 97,10, 10);
        el2.setFill(Color.WHITE); el2.setStroke(Color.BLACK);

        el3 = new Ellipse(156, 107,6, 6);
        el3.setFill(Color.WHITE); el3.setStroke(Color.BLACK);
        ellipses = List.of(el1, el2, el3);

        text = new Text(25, 56, "");
        text.setFont(new Font(12));

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                var meme = new Meme.Builder(iter, imageView).Ellipses(ellipses).Text(text).build();
                meme.setMeme(pane);
            }
        }));

        this.nextImageButton.setOnMouseClicked(this::nextImageHandler);
        this.prevImageButton.setOnMouseClicked(this::prevImageHandler);

        this.startMemeButton.setOnMouseClicked(this::startMemeHandler);
        this.stopMemeButton.setOnMouseClicked(this::stopMemeHandler);

        this.openMenuItem.setOnAction(this::openFileHandler);
        this.saveMenuItem.setOnAction(this::saveFileHandler);
    }

    private void stopMemeHandler(MouseEvent event) {
        if(iter == null){
            displayWarningMessage("Выберите папку с картинками!");
            return;
        }
        timeline.stop();
    }

    private void startMemeHandler(MouseEvent event) {
        if(iter == null){
            displayWarningMessage("Выберите папку с картинками!");
            return;
        }
        timeline.play();
    }

    private void prevImageHandler(MouseEvent event) {
        if(iter == null){
            displayWarningMessage("Выберите папку с картинками!");
            return;
        }
        if(iter.hasNext()){
            var image = (Image) iter.preview();
            imageView.setImage(image);
            pane.getChildren().set(0, imageView);
        }
    }

    private void nextImageHandler(MouseEvent event) {
        if(iter == null){
            displayWarningMessage("Выберите папку с картинками!");
            return;
        }
        if(iter.hasNext()){
            var image = (Image) iter.next();
            imageView.setImage(image);
            pane.getChildren().set(0, imageView);
        }
    }
    private void saveFileHandler(ActionEvent actionEvent){
        int uniqueNumber =  String.valueOf(System.currentTimeMillis()).hashCode();
        fileChooser.setInitialFileName("mem_"+uniqueNumber+".png");
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());
    }
    private void openFileHandler(ActionEvent actionEvent) {
        var selectedDirectory = fileChooser.showOpenDialog(pane.getScene().getWindow()).getParent();
        var dir = new File(selectedDirectory);

        pathFolderLabel.setText(selectedDirectory);

        conaggr = new ConcreteAggregate(new File(selectedDirectory));
        iter = conaggr.getIterator();
    }
    private void displayWarningMessage(String text){
        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Сообщение");
        alert.setHeaderText("Предупреждение");
        alert.setContentText(text);
        alert.showAndWait();
    }
}
