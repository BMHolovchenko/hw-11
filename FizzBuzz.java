import java.util.StringJoiner;

public class FizzBuzz {

    private static volatile int maxValue;
    private static volatile int initValue = 1;
    static StringJoiner result = new StringJoiner (", ");

    public static void setMaxValue(int maxValue) {
        FizzBuzz.maxValue = maxValue;
    }

    static class Replacer {

        public synchronized void fizz() throws InterruptedException {

            while (initValue <= maxValue) {
                if (initValue % 3 == 0 && initValue % 5 != 0) {
                    result.add ("fizz");
                    initValue++;
                    notifyAll();
                } else {
                    wait ();
                }
            }
        }

        public synchronized void buzz() throws InterruptedException {

            while (initValue <= maxValue) {
                if (initValue % 3 != 0 && initValue % 5 == 0) {
                    result.add ("buzz");
                    initValue++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }

        public synchronized void fizzbuzz() throws InterruptedException {

            while (initValue <= maxValue) {
                if (initValue % 15 == 0) {
                    result.add ("fizzbuzz");
                    initValue++;
                    notifyAll();
                } else {
                    wait();
                }
            }
        }

        public synchronized void digit() throws InterruptedException {

            while (initValue <= maxValue) {
                if (initValue % 3 != 0 && initValue % 5 != 0) {
                    result.add (String.valueOf(initValue));
                    initValue++;
                    notifyAll();
                } else {
                    wait();
                }
            }
            System.out.println (result);
        }
    }

    public void digitReplacer(int maxValue) {
        setMaxValue(maxValue);

        Replacer replacer = new Replacer ();

        Thread A = new Thread (() -> {
            try {
                replacer.fizz();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        });

        Thread B = new Thread(() -> {
            try {
                replacer.buzz();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        });

        Thread C = new Thread(() -> {
            try {
                replacer.fizzbuzz();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        });

        Thread D = new Thread(() -> {
            try {
                replacer.digit();
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
        });

        Thread[] threads = new Thread[]{A, B, C, D};
        for(Thread thread: threads){
            thread.start();

        }
    }

    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz ();
        fizzBuzz.digitReplacer (30);
    }
}