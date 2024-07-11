import Controller.Controller;
import Util.ConnectionUtil;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        ConnectionUtil.startDatabase();

        Controller controller = new Controller();

        Javalin app = controller.startAPI();
        app.start(8000);


    }
}
