package eu.crushedpixel.sponge.masquerade.utils;

public class PacketMath {

    public static byte rotationToByte(double rotation) {
        return (byte) (int) (rotation * 256f / 360f);
    }

    public static short deltaPositionToShort(double oldPosition, double newPosition) {
        return (short) ((newPosition * 32 - oldPosition * 32) * 128);
    }

    public static short velocityToShort(double velocity) {
        return (short) (Math.min(3.9, Math.max(-3.9, velocity)) * 8000);
    }

}
