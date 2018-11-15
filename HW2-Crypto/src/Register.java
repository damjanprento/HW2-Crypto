public class Register {

    private MoveBit[] moveBits;

    public Register(int sizeReg){
        moveBits = new MoveBit[sizeReg];
        moveBits[sizeReg -1] = new MoveBit();

        for(int i=moveBits.length-2; i >= 0; i--){
            moveBits[i] = new MoveBit(moveBits[i+1]);
        }
    }

    public int size(){
        return this.moveBits.length;
    }

    //Ova se koristi za da funkcionira move i za da se dodade vrednost vo registarot
    public void load(byte value){
        moveBits[0].move(value);
    }

    public byte[] getBits(int... positions){
        byte[] result = new byte[positions.length];

        for(int i=0; i < result.length; i++){
            result[i] = moveBits[positions[i]].getValue();
        }
        return result;
    }
}
