package lei.tqs.aeolus.cache;

public class SystemClock {
    public void sleep(long sec) throws InterruptedException {
        Thread.sleep(sec * 1000);
    }

    public static void whileSleep(long sec) {
        long current = System.currentTimeMillis();
        while (true) {
            if ((System.currentTimeMillis() - current) > (sec * 1000))
                break;
        }
    }
}
