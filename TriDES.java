public class TriDES implements EncrytionInterface {
    public byte[] enc(byte[] data) {
        byte[] message = data;

        DES des = new DES();
        message = des.enc(message);
        message = des.enc(message);
        message = des.enc(message);

        return message;

    }

    public byte[] dec(byte[] data) {
        byte[] message = data;

        DES des = new DES();
        message = des.dec(message);
        message = des.dec(message);
        message = des.dec(message);

        return message;

    }
}
