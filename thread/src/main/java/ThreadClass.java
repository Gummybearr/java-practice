public class ThreadClass extends Thread{
    int threadValue;

    public ThreadClass(int val){
        this.threadValue = val;
    }

    public void run(){
        System.out.println(Thread.currentThread().getName());
    }
}
