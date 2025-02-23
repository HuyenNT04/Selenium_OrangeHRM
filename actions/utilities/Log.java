package utilities;

public class Log {
    public class LogPrint {
        public static void error(String message) {
            System.err.println("[ERROR] " + message);
        }

        public static void info(String message) {
            System.out.println("[INFO] " + message);
        }
    }
}
