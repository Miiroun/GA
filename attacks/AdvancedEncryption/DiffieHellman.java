package attacks.AdvancedEncryption;
import Other.Interfaces.ByteEncrytionInterface;

public class DiffieHellman implements ByteEncrytionInterface {
    
    // private should not know the other
    double myKey;
    double otherKey;

    // public knolage
    double g;
    double n;

    public byte[] message;

    public DiffieHellman() {
        myKey = generateKey();
        otherKey = getOtherKey();
    }

    private double generateKey() {
        double key = 0;

        key = Math.random();

        return key;
    }

    private double getOtherKey() {
        return generateKey();
    }

    public byte[] enc(byte[] data) {
        message = data;

        // one person only does the first or second

        // the basic math calcultation, send them to eachother, make them public
        long me = Math.floorMod((long) Math.pow(g, myKey), (long) n);
        long other = Math.floorMod((long) Math.pow(g, otherKey), (long) n);

        // shared a known number, the same
        long knownNumber;
        knownNumber = Math.floorMod((long) Math.pow(Math.pow(g, myKey), otherKey), (long) n);
        knownNumber = Math.floorMod((long) Math.pow(Math.pow(g, otherKey), myKey), (long) n);
        // this is not 100% correvt, somthing wrong should use: me & other, for second calculation

        //
        me = me + other + knownNumber; //just so no error
        return message;
    }

    //

    //

    //

    //

    public byte[] dec(byte[] data) {
        return null;
    }
    
    public void setKey(byte[] data) {
        throw new UnsupportedOperationException("Unimplemented method 'setKey'");
    }

}
