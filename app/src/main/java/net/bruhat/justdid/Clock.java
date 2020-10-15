package net.bruhat.justdid;

public interface Clock {
    public long currentTimeMillis();

    public class System implements Clock {
        public long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }
    }

    public class Fake implements Clock {
        public long millis = 0;

        public long currentTimeMillis() {
            return millis;
        }
    }
}
