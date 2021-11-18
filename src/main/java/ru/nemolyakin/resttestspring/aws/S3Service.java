package ru.nemolyakin.resttestspring.aws;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
    public final String bucketName = "resttest";
    private final AmazonS3 s3client;

    public void createBucket() {
        if (s3client.doesBucketExistV2(bucketName)) {
            log.info("Bucket {} already exists, use a different name", bucketName);
            return;
        }

        s3client.createBucket(bucketName);
    }

    public URL uploadFile(File file) {
        createBucket();
        s3client.putObject(bucketName, file.getName(), file);
        return s3client.getUrl(bucketName, file.getName());
    }

    public void deleteFile(String name){
        s3client.deleteObject(bucketName, name);
    }
}