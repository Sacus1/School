public class RegisterWindows {
  private static RegisterWindows instance;
  public String name;

  private RegisterWindows() {}

  public static RegisterWindows getInstance() {
    if (instance == null) {
      synchronized (RegisterWindows.class) {
        if (instance == null)
          ;
        instance = new RegisterWindows();
      }
    }
    return instance;
  }
}
