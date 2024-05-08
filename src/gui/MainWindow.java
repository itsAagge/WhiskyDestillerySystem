package gui;

import application.controller.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
    private Controller controller;

    @Override
    public void init() {
        controller = new Controller();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Whisky Destillery System");
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane);

        initContent(pane);
        stage.setScene(scene);
        stage.setHeight(500);
        stage.setWidth(800);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabFade = new Tab("Fade");
        Tab tabDestillater = new Tab("Destillater");
        Tab tabPåfyldninger = new Tab("Påfyldninger");

        FadPane fadPane = new FadPane();
        tabFade.setContent(fadPane);
        DestillatPane destillatPane = new DestillatPane();
        tabDestillater.setContent(destillatPane);
        PåfyldningPane påfyldningPane = new PåfyldningPane();
        tabPåfyldninger.setContent(påfyldningPane);

        tabPane.getTabs().add(tabFade);
        tabPane.getTabs().add(tabDestillater);
        tabPane.getTabs().add(tabPåfyldninger);

        //tabFade.setOnSelectionChanged(event -> fadPane.updateControls());
        //tabDestillater.setOnSelectionChanged(event -> destillatPane.updateControls());
    }
}
