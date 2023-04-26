package se211.V2;

public class BMain {

    public static void main (String [] args) {
        se211.V2.Athread a = new se211.V2.Athread("Monday");
        a.start ();

        se211.V2.Athread b = new se211.V2.Athread("Tuesday");
        b.start ();
    }
}
