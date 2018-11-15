public class Main {

    public static void main(String[] args) {

        byte [] key = {(byte)0x80,0,0,0,0,0,0,0,0,0};
        byte [] IV = {0,0,0,0,0,0,0,0,0,0};

        Trivium t = new Trivium(key,IV);
        for(int i=0;i<16;i++)
            System.out.print(t.getNextString().toUpperCase());
    }
}
