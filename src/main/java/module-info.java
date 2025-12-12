module io.jfxdevelop.lab6_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.jfxdevelop.lab6_2 to javafx.fxml;
    exports io.jfxdevelop.lab6_2;
}