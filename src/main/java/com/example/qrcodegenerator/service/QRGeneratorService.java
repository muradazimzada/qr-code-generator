package com.example.qrcodegenerator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRGeneratorService {

    private BitMatrix generate(String data, int width, int height)
            throws WriterException {

        var writer = new QRCodeWriter();
        return writer.encode(data, BarcodeFormat.QR_CODE, width, height);
    }

    public byte[] generateImage(String data, int width , int height, String format) throws IOException, WriterException {

        BitMatrix bitMatrix = generate(data, width, height);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, format, baos);

        return baos.toByteArray();
    }
}
