# RPC_Example
## 一、RPC框架概述
RPC（Remote Procedure Call）叫作远程过程调用，它是利用网络从远程计算机上请求服务，可以理解为把程序的一部分放在其他远程计算机上执行。通过网络通信将调用请求发送至远程计算机后，利用远程计算机的系统资源执行这部分程序，最终返回远程计算机上的执行结果。

RPC的五个主要部分

- user（服务调用方）
- user-stub（调用方的本地存根）
- RPCRuntime（RPC通信者）
- server-stub（服务端的本地存根）
- server（服务端）

服务调用方、调用方的本地存根及其一个RPC通信包的实例存在于调用者的机器上；而服务提供方、服务提供方的存根及另一个RPC通信包的实例存在于被调用的机器上。



服务方的代码启动后要能够接受调用方的网络请求（如Netty、Tomcat）

从一个简单的案例分析

- Provider模块为服务方，提供服务（接口）的实现，接收调用方的调用（网络请求），并返回执行结果
- Consumer模块为调用方，发送请求给服务方，并接受执行结果
- Common模块为公共模块，提供服务（接口）的定义。供Provider和Consumer使用
- RPC模块为RPC架构的实现框架
    - 提供网络服务的启动（HttpServer）
    - 提供网络请求的处理（HttpServerHandler）
    - 提供接口名和实现类的映射，便于快速找到网络请求调用方法对应的实现类（LocalRegister，这里使用本地注册）
    - 提供网络请求中接口名，方法名，参数类型数组，参数数组的封装（Invocation，需要实现序列化接口）
    - 提供代理工厂类用于Consumer创建代理对象，通过代理对象直接调用服务中的方法（ProxyFactory，通过JDK中的动态代理类Proxy实现）
## 二、RPC框架的开发要点

### 服务发现

从远程注册中心（如Redis）获取接口名对应的ip地址和端口（URL列表）

```java
List<URL> urlList = RemoteRegister.get(interfaceClass.getName(), "1.0");
```

### 负载均衡

负载均衡（LoadBalance）提供很多种策略，这里实现随机策略

```java
public class LoadBalance {

	public static URL random(List<URL> urls){
		Random random = new Random();
		int randomIndex = random.nextInt(urls.size());
		return urls.get(randomIndex);
	}
}
```

### 服务容错

在可能会出现异常的位置进行异常的友好处理，例如在网络连接失败时返回具体的信息

也可以在catch中调用自定义的实现类HelloServiceErrorCallback调用具体的错误回调方法

```java
//服务调用：发送请求
String result = null;
try {
    HttpClient client = new HttpClient();
    result = client.send(url.getHostname(), url.getPort(), invocation);
}catch (Exception e){
    return "服务调用失败！";
}
```

### 服务重试

当某个服务调用失败后，可以尝试调用集群中的其他服务

### 服务mock

若服务方的业务代码没有开发完，调用方需要一个返回结果，就可以用mock实现。

在代理对象的调用逻辑中判断是否有mock参数的信息，如果有直接返回mock中的信息

```java
String mock = System.getProperty("mock");
if( mock != null && mock.startsWith("return:")){
    String result = mock.replace("return:", "");
    return result;
}
```



