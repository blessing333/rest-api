package com.blessing333.restapi.domain.model.common;

import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.util.UUID;

@UtilityClass
public class UUIDUtils {
    public static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static byte[] toBinary(UUID id) {
        String uuidToHexString = id.toString().replace("-", "");
        return hexStringToByteArray(uuidToHexString);
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

}
