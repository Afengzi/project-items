package com.afengzi.jeast.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: lixiuhai
 * Date: 14-10-27
 * Time: 下午2:51
 * 代理处理器
 */
public class DynamicProxyHandler implements InvocationHandler{
    private Object proxied ;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       System.out.println("**************proxy.class : "+proxy.getClass());
//       System.out.println("**************proxy.toString : "+proxy);
       System.out.println("**************method.class : "+method.getClass());
       System.out.println("**************method.name : "+method.getName());
       System.out.println("**************method.class.toString : "+method);
//       System.out.println("**************args.class : "+args.getClass());
      if (args!=null){
          for (Object arg : args){
              System.out.println("**************arg : "+arg);
          }
      }
       return method.invoke(proxied,args) ;
    }
}
