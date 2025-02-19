package ru.kpfu.itis.test2.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ru.kpfu.itis.test2.model.UserConfig;

public class UserConfigView extends BaseView {

    private AnchorPane pane;
    private VBox box;
    private TextField username;
    private TextField host;
    private TextField port;
    private Button start;

    @Override
    public Parent getView() {
        if (pane == null) {
            createView();
        }
        return pane;
    }

    private void createView() {
        pane = new AnchorPane();
        box = new VBox(10);

        Label usernameLabel = new Label("Username:");
        username = new TextField();
        Label hostLabel = new Label("Host:");
        host = new TextField();
        host.setText("127.0.0.1");
        Label portLabel = new Label("Port:");
        port = new TextField();
        port.setText("5555");
        start = new Button("Start");

        start.setOnAction(actionEvent -> {
            if (actionEvent.getSource() == start) {
                UserConfig userConfig = new UserConfig();
                userConfig.setUsername(username.getText());
                userConfig.setHost(host.getText());
                userConfig.setPort(Integer.parseInt(port.getText()));

                getApplication().setUserConfig(userConfig);

                getApplication().startChat();

                getApplication().setView(getApplication().getChatView());
            }
        });
        box.getChildren().addAll(
                usernameLabel, username, hostLabel,
                host, portLabel, port, start
        );
        pane.getChildren().addAll(box);
    }
}
