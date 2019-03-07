package webServices;

public class HelloClient {
    public static void main(String[] args) {
        System.out.println(new HelloService().getHelloPort().sayHello("Ars"));
    }
}
