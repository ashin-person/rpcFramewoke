package ljx.ashin.service;

/**
 * Created by Ashin Liang on 2017/12/28.
 */
public class StudentServiceImpl implements StudentService {
    public String getStudentInfoById(Integer id) {
        System.out.println("调用了业务方法获取学生信息");
        return "jsons"+id;
    }
}
