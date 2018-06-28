
-assumenosideeffects class android.util.Log {
     public static boolean isLogger(java.lang.String, int);
     public static int v(...);
     public static int d(...);
     public static int i(...);
     public static int w(...);
}


-assumenosideeffects class com.github.hualuomoli.logger.Logger {
     public static boolean isVerboseEnabled();
     public static boolean isDebugEnabled();
     public static boolean isInfoEnabled();
     public static boolean isWarnEnabled();
     public static void verbose(...);
     public static void debug(...);
     public static void info(...);
     public static void warn(...);
}