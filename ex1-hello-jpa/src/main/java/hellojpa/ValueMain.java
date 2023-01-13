package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        String a = "내용1";
        String b = a;

        a = "내용2";

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
