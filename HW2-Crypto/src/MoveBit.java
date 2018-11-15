public class MoveBit {

    private boolean hasNeighbor;
    private MoveBit right;
    private byte value;

    public MoveBit(){
        value = 0;
        hasNeighbor=false;
    }

    public MoveBit(MoveBit neighbor){
        value=0;
        hasNeighbor=true;
        this.right=neighbor;
    }

    public void move(byte value){
        if(hasNeighbor)
            right.move(this.value);

        this.value=value;
    }

    public byte getValue(){
        return value;
    }
}
