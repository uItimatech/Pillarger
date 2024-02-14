package net.ultimatech.pillarger;

public class PLDependencyManager {
    public static boolean isEchoingWildsInstalled() {
        try {
            Class.forName("net.ultimatech.echoingwilds.EchoingWilds");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
