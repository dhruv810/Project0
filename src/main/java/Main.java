import Controller.Controller;
import Util.ConnectionUtil;
import io.javalin.Javalin;


public class Main {

    //    ConnectionUtil
    public static void main(String[] args) {
        Controller  controller = new Controller();

        Javalin app = controller.startAPI();
        app.start(8080);

    }

}

