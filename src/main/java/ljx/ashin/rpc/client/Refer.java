package ljx.ashin.rpc.client;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * 引用
 * Created by AshinLiang on 2017/12/27.
 */
public class Refer {

    public static <T> T proxyRefer(Class<T> interfaceClass, final int port, final String address){

        validateParams(port,address);

        T t = (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), interfaceClass.getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
                System.out.println("开始调用反射中的invoke里的方法");
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();

                Socket socket = new Socket(address,port);
                OutputStream outputStream = socket.getOutputStream();
                InputStream inputStream = socket.getInputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(parameterTypes);
                objectOutputStream.writeObject(params);

                Object result = objectInputStream.readObject();

                return result;
            }
        });

        return t;
    }

    public static void validateParams(int port,String address){
        if (0<=port||port>6533){
            throw new IllegalArgumentException("port:"+port+"不正确");
        }
        if (StringUtils.isBlank(address)){
            throw new IllegalArgumentException("address:"+address+"不正确");
        }
    }
}
