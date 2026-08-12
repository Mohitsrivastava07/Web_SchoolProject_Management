package com.school.schoolproject.handler;

public class DetectContentTypeHandler {

    public static String detectContentType(byte[] data) {

        if (data == null || data.length < 4) {
            return "application/octet-stream";
        }

        // PDF
        if (data[0] == 0x25 &&
                data[1] == 0x50 &&
                data[2] == 0x44 &&
                data[3] == 0x46) {

            return "application/pdf";
        }

        // DOCX / XLSX / PPTX are ZIP-based
        if (data[0] == 0x50 &&
                data[1] == 0x4B &&
                data[2] == 0x03 &&
                data[3] == 0x04) {

            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }

        // Old DOC
        if (data[0] == (byte) 0xD0 &&
                data[1] == (byte) 0xCF &&
                data[2] == (byte) 0x11 &&
                data[3] == (byte) 0xE0) {

            return "application/msword";
        }

        // PNG
        if (data[0] == (byte) 0x89 &&
                data[1] == 0x50 &&
                data[2] == 0x4E &&
                data[3] == 0x47) {

            return "image/png";
        }

        // JPG
        if (data[0] == (byte) 0xFF &&
                data[1] == (byte) 0xD8 &&
                data[2] == (byte) 0xFF) {

            return "image/jpeg";
        }

        return "application/octet-stream";
    }
}
