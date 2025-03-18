import java.util.Scanner;
public interface ConfigInterface {
    Scanner getScanner();

    String getDB_URL();

    String getDB_USER();

    String getDB_PASSWORD();
}
