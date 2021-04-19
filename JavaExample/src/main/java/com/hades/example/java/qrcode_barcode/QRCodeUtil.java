package com.hades.example.java.qrcode_barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hades.example.java.lib.FileUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, createEncodeHints());
        FileUtils.checkDestDirIsExist(filePath.substring(0, filePath.lastIndexOf("/")));
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", FileSystems.getDefault().getPath(filePath));
    }

    private static Map<EncodeHintType, ?> createEncodeHints() {
        // Create the ByteMatrix for the QR-Code that encodes the given String
        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        // 设置容错率 L>M>Q>H 等级越高扫描时间越长,准确率越高
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //设置字符集
        hintMap.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置外边距
        hintMap.put(EncodeHintType.MARGIN, 1);
        return hintMap;
    }

    private static Map<DecodeHintType, ?> createDecodeHints() {
        Map<DecodeHintType, Object> hintMap = new HashMap<>();
        //设置字符集
        hintMap.put(DecodeHintType.CHARACTER_SET, "utf-8");
        hintMap.put(DecodeHintType.TRY_HARDER, true);
        Collection<BarcodeFormat> barcodeFormats= new ArrayList<>();
        barcodeFormats.add(BarcodeFormat.QR_CODE);
        hintMap.put(DecodeHintType.POSSIBLE_FORMATS, barcodeFormats);
        return hintMap;
    }

    public static byte[] generateQRCodeByte(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, createEncodeHints());
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

    public static void generateQRCodeImage2(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, createEncodeHints());

        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.GREEN);  // bg color
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.RED);  // code color

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        FileUtils.checkDestDirIsExist(filePath.substring(0, filePath.lastIndexOf("/")));
        File file = new File(filePath);
        ImageIO.write(image, "PNG", file);
    }

    public static String decodeQRCode(File qrCodeImage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap, createDecodeHints());
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

    public static void whetherImageContain2DBarCode(File qrCodeImage, Map<DecodeHintType, ?> hints) throws IOException, NotFoundException, FormatException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeImage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiDetector detector = new MultiDetector(bitmap.getBlackMatrix());
        DetectorResult result = detector.detect(hints);
    }

}
