public class InvokerDemo implements Runnable {
    public static void main(String[] args) {
        new InvokerDemo().test();
    }
    public void test() {
        InvokerDemo.staticMethod();              // invokestatic
        InvokerDemo demo = new InvokerDemo();    // invokespecial
        demo.instanceMethod();                  // invokespecial
        super.equals(null);                     // invokespecial
        this.run();                             // invokevirtual
        ((Runnable) demo).run();                // invokeinterface
    }
    public static void staticMethod() {}
    private void instanceMethod() {}
    @Override public void run() {}
}