package org.example;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        System.out.println("Hello world!");
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("https://minio.alfabank.tecman.ru")
                        .credentials("minioadmin", "minioadmin")
                        .build();

        FileInputStream f = new FileInputStream("./readme.txt");
        InputStream is = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        } ;

        System.out.println("Put to MINIO");

        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket("alfaresearch")
                .object("readme.txt")
                .stream(f,  f.getChannel().size(), 5242880)
                        .build());

        try (InputStream stream =
                     minioClient.getObject(GetObjectArgs
                             .builder()
                             .bucket("alfaresearch")
                             .object("readme.txt")
                             .build())) {
            byte[] bytes = stream.readAllBytes();
            System.out.println("Read from MINIO readme.txt bytes=" + bytes.length);
            System.out.println("OK");
        }
    }
}