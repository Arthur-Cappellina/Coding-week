module eu.telecomnancy.codingweek {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires jbcrypt;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens eu.telecomnancy.codingweek to javafx.fxml;
    opens eu.telecomnancy.codingweek.Controllers to javafx.fxml;
    exports eu.telecomnancy.codingweek;
    exports eu.telecomnancy.codingweek.Models;
    opens eu.telecomnancy.codingweek.Models to javafx.fxml;
    exports eu.telecomnancy.codingweek.Controllers;
}