package executor.service;

public class App {

  public static void main(String[] args) {

    private static String getPackageName() {
        Package currentPackage = App.class.getPackage();
        return currentPackage.getName();
    }
}
