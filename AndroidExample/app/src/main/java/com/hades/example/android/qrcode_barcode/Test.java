package com.hades.example.android.qrcode_barcode;

import com.google.zxing.WriterException;

import java.io.IOException;

public class Test {
    private static final String QR_CODE_IMAGE_PATH = "./MyQRCode.png";

    public static void main(String[] args) {
        try {
            new QRCodeUtil().generateQRCode("This is my first QR Code", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }
}
