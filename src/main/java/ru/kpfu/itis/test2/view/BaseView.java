package ru.kpfu.itis.test2.view;

import javafx.scene.Parent;
import ru.kpfu.itis.test2.ChatApplication;

public abstract class BaseView {

    private static ChatApplication application;

    public static ChatApplication getApplication() {
        if (application != null) {
            return application;
        }
        throw new RuntimeException("application is null");
    }

    public static void setApplication(ChatApplication application) {
        BaseView.application = application;
    }

    public abstract Parent getView();
}
