package java_modules.globalThis;

public class console {
    public static void log(Object... objects) {
        for (Object obj : objects) {
            System.out.print(obj);
            System.out.print(" ");
        }
        System.out.println(); // 换行
    }
}