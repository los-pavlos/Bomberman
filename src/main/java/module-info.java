module cz.vsb.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens cz.vsb to javafx.fxml;
    exports cz.vsb;
}