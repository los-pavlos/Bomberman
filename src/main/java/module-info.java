module cz.vsb.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens cz.vsb to javafx.fxml;
    exports cz.vsb;
}