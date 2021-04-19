package com.hades.example.java.qrcode_barcode;

import com.google.zxing.WriterException;
import com.hades.example.java.lib.FileUtils;

import java.io.File;
import java.io.IOException;

public class Test {
    private static final String QR_CODE_IMAGE_PATH = "./tmp/QRCode_1.png";
    private static final String QR_CODE_IMAGE_PATH_2 = "https://gitee.com/YingVickyCao/QRCode_BarCode_Example/raw/main/JavaExample/src/main/resources/qrcode_example.png";

    public static void main(String[] args) {
        generateQRCodeImage();
//        generateQRCodeImage2();
//        generateQRCodeByte();
        readQRCode();
    }

    private static void generateQRCodeImage() {
        try {
            QRCodeUtil.generateQRCodeImage("QR Code Example 苹果梨hellopgfh@123.com", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

    private static void generateQRCodeImage2() {
        try {
            QRCodeUtil.generateQRCodeImage2("QR Code Example 苹果梨hellopgfh@123.com", 350, 350, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
    }

    private static void generateQRCodeByte() {
        try {
            byte[] bytes = QRCodeUtil.generateQRCodeByte("QR Code Example 苹果梨hellopgfh@123.com", 350, 350);
            FileUtils.saveFile(bytes, QR_CODE_IMAGE_PATH);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException: " + e.getMessage());
        }
    }

    private static void readQRCode() {
        try {
            File file = new File(QR_CODE_IMAGE_PATH);
            String decodedText = QRCodeUtil.decodeQRCode(file);
            if (decodedText == null) {
                System.out.println("No QR Code found in the image");
            } else {
                // TODO: 2020/10/28 decode not support Chinese
                // QR Code Example ???hellopgfh@123.com
                System.err.println(decodedText);
            }
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
    }
}