package net.bruhat.justdid;

import java.util.TimeZone;

public interface Clock {
    public long currentTimeMillis();
    public TimeZone timeZone();

    public class System implements Clock {
        public long currentTimeMillis() {
            return java.lang.System.currentTimeMillis();
        }
        public TimeZone timeZone() { return TimeZone.getDefault(); }
    }

    public class Fake implements Clock {
        public long millis = 0;
        public TimeZone timeZone = null;

        public long currentTimeMillis() {
            return millis;
        }

         public TimeZone timeZone() { return timeZone; }
    }
}
