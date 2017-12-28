package ljx.ashin.rpc.server;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.net.SocketServer;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Ashin Liang on 2017/12/27.
 */
public class RpcFramework {
    /**
     * 暴露服务
     *
     * @param service 一个服务类的对象
     * @param port    端口号
     */
    public static void exportService(final Object service, int port) throws Exception {
        //1、参数校验
        if (null == service) {
            throw new IllegalArgumentException("service is null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("port:" + port + "is illegal");
        }
        //2、获取socket连接
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println(socket);


            new Thread(new Runnable() {//开启一个线程
                public void run() {
                    try {
                        //3、根据socket请求参数，通过反射调用本地的方法
                        InputStream inputStream = socket.getInputStream();
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        String methodName = objectInputStream.readUTF();
                        Class<?>[] paramerTypes = (Class<?>[]) objectInputStream.readObject();
                        Object[] params = (Object[]) objectInputStream.readObject();

                        Method method = service.getClass().getMethod(methodName, paramerTypes);
                        Object invokeResult = method.invoke(service, params);

                        //4、通过socket返回给客户端
                        OutputStream outputStream = socket.getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(invokeResult);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            }).start();
        }


    }
}
