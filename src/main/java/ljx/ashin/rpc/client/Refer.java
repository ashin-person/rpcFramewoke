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

        T t = (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {
                System.out.println("开始调用反射中的invoke里的方法");
                String methodName = method.getName();
                System.out.println("methodName="+methodName);
                Class<?>[] parameterTypes = method.getParameterTypes();

                Socket socket = new Socket(address,port);
                OutputStream outputStream = socket.getOutputStream();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(parameterTypes);
                objectOutputStream.writeObject(params);

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Object result = objectInputStream.readObject();

                return result;
            }
        });

        return t;
    }

    public static void validateParams(int port,String address){
       /* if (0<=port||port>625333){
            throw new IllegalArgumentException("port:"+port+"不正确");
        }*/
        if (StringUtils.isBlank(address)){
            throw new IllegalArgumentException("address:"+address+"不正确");
        }
    }
}
