package Controller;

import io.javalin.Javalin;

public class Controller {
    public Javalin startAPI() {
        Javalin app = Javalin.create();
//        app.post("/url", this::function);
        return app;
    }
}
