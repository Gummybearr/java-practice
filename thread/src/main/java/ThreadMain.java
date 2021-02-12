public class ThreadMain {
    public static void main(String[] args){
        Thread1 th1 = new Thread1();
        Thread2 th2 = new Thread2();
        th1.start();
        th2.start();

        try{
            Thread1.sleep(2000);
        } catch (InterruptedException e) { }
        System.out.println("<<main 종료>>");
    }
}

class Thread1 extends Thread{
    public void run(){
        for(int i = 0;i<300;i++){
            System.out.printf("-");
        }
        System.out.println("<<th1 종료>>");
    }
}

class Thread2 extends Thread{
    public void run(){
        for(int i = 0;i<300;i++){
            System.out.printf("|");
        }
        System.out.println("<<th2 종료>>");
    }
}