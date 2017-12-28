package ljx.ashin.rpc.app;

import ljx.ashin.rpc.client.Refer;
import ljx.ashin.service.StudentService;

/**
 * Created by Ashin Liang on 2017/12/28.
 */
public class ClientApp {
    public static void main(String[] args) {
        StudentService studentService = Refer.proxyRefer(StudentService.class,8899,"127.0.0.1");
        for (int i = 0; i < 10; i++) {
            String studentInfo = studentService.getStudentInfoById(23+i);
            System.out.println(studentInfo);
        }

    }
}
