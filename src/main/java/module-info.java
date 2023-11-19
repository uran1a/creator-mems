module programmingtechnology.creatormemes {
    requires javafx.controls;
    requires javafx.fxml;

    opens programmingtechnology.creatormemes.controllers to javafx.controls, javafx.fxml;
    opens programmingtechnology.creatormemes to javafx.fxml;
    exports programmingtechnology.creatormemes;
}