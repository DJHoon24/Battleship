module dh2jeong.a3basic {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens dh2jeong.a3basic to javafx.fxml;
    exports dh2jeong.a3basic;
}