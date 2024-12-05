package yung.dongnae_fit.global.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    private String defaultUrl = "https://s3.amazonaws.com/";

    public String upload(MultipartFile file, String folder) throws IOException {

        String contentType = "application/octet-stream";
        String fileName = folder + "/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(contentType);
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new IOException("Failed to upload file to S3", e);
        }
    }

    public void delete(String filePath) throws IOException {
        try {
            amazonS3.deleteObject(bucketName, filePath);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new IOException("Failed to delete file from S3", e);
        }
    }
}
