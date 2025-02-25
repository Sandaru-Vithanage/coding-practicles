public class ThreadGroups {
    public static void main(String[] args) {
        System.out.println("Thread Name: "+ Thread.currentThread().getName());
        System.out.println("Thread Group Name: "+ Thread.currentThread().getThreadGroup().getName());
        System.out.println("The Parent Thread Group Name: "+ Thread.currentThread().getThreadGroup().getParent()
                .getName());
        System.out.println("The Parent Thread Group Parent Name: "+ Thread.currentThread().getThreadGroup().getParent()
                .getParent());

        ThreadGroup threadGroup1 = new ThreadGroup("Thread Group 1");

        System.out.println("Thread Group1 Parent's Name: "+ threadGroup1.getParent().getName());

        Thread t1 = new Thread(() -> { //run method starts here when you use lambda function
            System.out.println("t1's Thread Group Name: "+ Thread.currentThread().getThreadGroup().getName());
            for (int i = 0; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
            //run method ends here when you use lambda function
        }, "Thread 1");

        Thread t2 = new Thread(threadGroup1, () -> {
            //t2 belongs to threadGroup1
            //why?
            //because we passed threadGroup1 as the first argument to the Thread constructor
            System.out.println("t2's Thread Group Name: "+ Thread.currentThread().getThreadGroup().getName());

            Thread t3 = new Thread(() -> {
                System.out.println("t3's Thread Group Name: "+ Thread.currentThread().getThreadGroup().getName());
                System.out.println("t3's Parent Thread Group Name: "+ Thread.currentThread().getThreadGroup()
                        .getParent().getName());

                ThreadGroup threadGroup2 = new ThreadGroup("Thread Group 2");

                Thread t4 = new Thread(threadGroup2,() -> {
                    System.out.println("t4's Thread Group Name: "+ Thread.currentThread().getThreadGroup().getName());
                    System.out.println("t4's Parent Thread Group Name: "+ Thread.currentThread().getThreadGroup()
                            .getParent().getName());

                    for (int i = 0; i <= 10; i++) {
                        System.out.println(Thread.currentThread().getName()+" "+i);
                    }

                }, "Thread 4");

                for (int i = 0; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName()+" "+i);
                }

                t4.start();
            }, "Thread 3");

            for (int i = 0; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }
            t3.start();
        }, "Thread 2");

        t1.start();
        t2.start();

       Thread [] threads = new Thread[Thread.currentThread().getThreadGroup().activeCount()*2];
       ThreadGroup[] threadGroups = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()*2];

       Thread.currentThread().getThreadGroup().enumerate(threadGroups, true);
    }
}
