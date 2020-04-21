package lab2.typecheck;

public class PrintPiglet {
    public static int tab_cnt = 0;

    public static void print(String s){
        for(int i = 0;i < tab_cnt;i ++){
            System.out.print("  ");
        }
        System.out.print(s);
    }

    public static void printmain(){
        println("MAIN");
        tab_cnt ++;
    }

    public static void println(String s){
        print(s);
        System.out.println();
    }

    public static void printbegin(){
        println("BEGIN");
        tab_cnt ++;
    }

    public static void printend(){
        tab_cnt --;
        println("END");
    }
}
