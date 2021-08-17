public class Timer extends Thread {

    private int initTime;
    private final int executionTime;

    public Timer(int executionTime) {
        this.executionTime = executionTime;
    }

    @Override
    public void run() {
        while(!currentThread().isInterrupted()){
            try{
                sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            synchronized (this){
                initTime++;
            }
            System.out.println("Прошло " + initTime + " секунд.");
            if (initTime == executionTime){
                currentThread().interrupt();
                System.out.println("Поток счётчика прерван");
            }

        }
    }


    public static class SecondsPrinter extends Thread {

        private final Timer timer;

        public SecondsPrinter(Timer timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            while (!currentThread().isInterrupted()) {
                try {
                    int number = timer.initTime;
                    if (number % 5 == 0 && number != 0) {
                        System.out.println("Оповещение! Прошло 5 секунд");
                        sleep(1000L);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (timer.initTime == timer.executionTime){
                    currentThread().interrupt();
                    System.out.println("Поток оповещений прерван");
                }
            }
        }

        public static void main(String[] args) {
            Timer timer = new Timer(6);
            SecondsPrinter secondsPrinter = new SecondsPrinter(timer);
            timer.start();
            secondsPrinter.start();
        }
    }
}
