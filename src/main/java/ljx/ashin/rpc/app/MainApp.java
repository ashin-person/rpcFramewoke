package ljx.ashin.rpc.app;

import ljx.ashin.rpc.server.RpcFramework;
import ljx.ashin.service.StudentService;
import ljx.ashin.service.StudentServiceImpl;

/**
 * Created by Ashin Liang on 2017/12/28.
 */
public class MainApp {
    public static void main(String[] args) {
        try {
            StudentService service = new StudentServiceImpl();
            RpcFramework.exportService(service,8899);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
