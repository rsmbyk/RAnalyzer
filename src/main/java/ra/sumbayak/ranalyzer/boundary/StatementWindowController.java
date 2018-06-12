package ra.sumbayak.ranalyzer.boundary;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import ra.sumbayak.ranalyzer.controller.StatementController;
import ra.sumbayak.ranalyzer.entity.Project;
import ra.sumbayak.ranalyzer.entity.Statement;

public class StatementWindowController {
    
    @FXML private JFXListView<Statement> statementListView;
    private ObservableList<Statement> statementItems;
    private StatementController controller;
    private Project project;
    
    void setProject (Project project) {
        this.project = project;
        updateStatementList ();
    }
    
    @FXML
    private void initialize () {
        controller = new StatementController ();
        statementItems = FXCollections.observableArrayList ();
        statementListView.setCellFactory (param -> new StatementCell ());
        statementListView.setItems (statementItems);
    }
    
    @FXML
    private void addStatement () {
        if (project == null)
            return;
        controller.addStatement (project);
        updateStatementList ();
    }
    
    private void editStatement (int index) {
        controller.editStatement (project, index);
        updateStatementList ();
    }
    
    private void deleteStatement (int index) {
        controller.deleteStatement (project, index);
        updateStatementList ();
    }
    
    private void updateStatementList () {
        if (project == null)
            return;
        statementItems.setAll (project.getStatements ());
    }
    
    public class StatementCell extends JFXListCell<Statement> {
        
        @Override
        protected void updateItem (Statement item, boolean empty) {
            super.updateItem (item, empty);
            
            if (empty) {
                setText (null);
                setGraphic (null);
            }
            else {
                GridPane grid = new GridPane ();
                grid.setHgap (15);
                grid.setVgap (4);
                grid.setPadding (new Insets (10, 10, 10, 10));
                
                Label id = new Label ("R" + String.valueOf (getIndex ()));
                Label s = new Label (item.getValue ());
                Separator separator = new Separator (Orientation.VERTICAL);
                
                JFXButton editStatementButton = new JFXButton ("Edit");
                editStatementButton.setOnMouseClicked (event -> editStatement (getIndex ()));
                
                JFXButton deleteStatementButton = new JFXButton ("Remove");
                deleteStatementButton.setOnMouseClicked (event -> deleteStatement (getIndex ()));
                
                HBox hBox = new HBox ();
                hBox.getChildren ().addAll (editStatementButton, deleteStatementButton);
                hBox.setAlignment (Pos.CENTER_RIGHT);
                
                grid.addRow (0, id, s, separator, hBox);
    
                ColumnConstraints c = new ColumnConstraints ();
                c.setHgrow (Priority.ALWAYS);
                grid.getColumnConstraints ().addAll (new ColumnConstraints (), c);
                
                setGraphic (grid);
                setText (null);
            }
        }
    }
}
