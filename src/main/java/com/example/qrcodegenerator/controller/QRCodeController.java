package com.example.qrcodegenerator.controller;

import com.example.qrcodegenerator.service.QRGeneratorService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class QRCodeController {

    final private QRGeneratorService service;

    @Autowired
    public QRCodeController(QRGeneratorService service) {
        this.service = service;
    }
    @GetMapping("/generateQR/{data}")
    public ResponseEntity<byte[]> generate(
            @PathVariable String data,
            @RequestParam(defaultValue = "300") int width,
            @RequestParam(defaultValue = "300") int height,
            @RequestParam(defaultValue = "PNG") String format) {

        try {
            byte[] imageData = service.generateImage(data, width, height, format);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData(
                    "attachment",
                    "qrcode."+ format.toLowerCase());

            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException | WriterException e) {
            e.getLocalizedMessage();
            return ResponseEntity.badRequest().build();
        }
    }
}
