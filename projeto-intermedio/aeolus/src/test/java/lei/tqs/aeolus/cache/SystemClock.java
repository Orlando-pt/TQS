package lei.tqs.aeolus.cache;

public class SystemClock {
    public void sleep(long sec) throws InterruptedException {
        Thread.sleep(sec * 1000);
    }
}
