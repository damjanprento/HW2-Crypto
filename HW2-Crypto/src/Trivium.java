public class Trivium {

    private Register r1;
    private Register r2;
    private Register r3;

    public Trivium(byte[] key, byte[] IV) {
        r1 = new Register(93);
        r2 = new Register(84);
        r3 = new Register(111);

        set288(key, IV);
        rotate4Cycles();
    }

    private void set288(byte[] key, byte[] IV) {
        for (int i = 0; i < key.length ; i++) {
            byte temp;
            for (int j = 0; j < 8; j++) {
                // >> vrshi shiftanje 011001 >> 2 -> 001100
                temp = (byte) (key[i] >> j);
                // temp & 1 pokazuva dali posledniot bit vo temp e 1 ili 0
                temp = (byte) (temp & 1);
                r1.load(temp);
            }
        }

        for (int i = 0; i < IV.length; i++) {
            byte temp;
            for (int j = 0; j < 8; j++) {
                temp = (byte) (IV[i] >> j);
                temp = (byte) (temp & 1);
                r2.load(temp);
            }
        }

        // se vrshi za poslednite tri vrednosti da se 1
        r3.load((byte) 0x01);
        r3.load((byte) 0x01);
        r3.load((byte) 0x01);

        for (int i = 0; i < r3.size() - 3; i++) {
            r3.load((byte) 0x00);
        }

    }

    public byte next() {

        byte result;

        byte[] r1Bits = r1.getBits(65, 90, 91, 92, 68);
        byte[] r2Bits = r2.getBits(68, 81, 82, 83, 77);
        byte[] r3Bits = r3.getBits(65, 108, 109, 110, 86);

        byte t1, t2, t3;

        result = (byte) (r1Bits[0] ^ r1Bits[3] ^ r2Bits[0] ^ r2Bits[3] ^ r3Bits[0] ^ r3Bits[3]);

        t1 = (byte) (r1Bits[1] & r1Bits[2]);
        t1 ^= r2Bits[4] ^ r1Bits[0] ^ r1Bits[3];

        t2 = (byte) (r2Bits[1] & r2Bits[2]);
        t2 ^= r3Bits[4] ^ r2Bits[0] ^ r2Bits[3];

        t3 = (byte) (r3Bits[1] & r3Bits[2]);
        t3 ^= r1Bits[4] ^ r3Bits[0] ^ r3Bits[3];

        r1.load(t3);
        r2.load(t1);
        r3.load(t2);

        return result;
    }

    private void rotate4Cycles() {
        for (int i = 0; i < (4 * 288); i++) {
            this.next();
        }
    }

    // gi reversnuva i go prai baraniot output (little endian)
    public String getNextString() {
        StringBuilder sb1 = new StringBuilder();
        for(int i = 0;i<4;i++)
            sb1.append(this.next());

        String second = sb1.reverse().toString();

        StringBuilder sb2 = new StringBuilder();
        for(int i = 0;i<4;i++)
            sb2.append(this.next());

        String first = sb2.reverse().toString();

        return Long.toHexString(Long.parseLong(first,2)) + Long.toHexString(Long.parseLong(second,2));
    }
}
