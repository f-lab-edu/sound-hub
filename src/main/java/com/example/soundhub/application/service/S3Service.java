package com.example.soundhub.application.service;

import static com.example.soundhub.config.exception.ErrorResponseStatus.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.soundhub.config.exception.AwsS3Exception;
import com.example.soundhub.config.exception.DatabaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {
	private final AmazonS3 amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String upload(MultipartFile multipartFile, String dirName) {
		log.debug("Attempting to upload file [{}] to directory [{}]", multipartFile.getOriginalFilename(), dirName);

		File uploadFile = null;
		try {
			uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("Failed to convert MultipartFile to File"));

			return uploadToS3(uploadFile, dirName);
		} catch (IOException e) {
			log.error("Error uploading file to S3", e);
			throw new AwsS3Exception(IMAGE_UPLOAD_ERROR);
		} finally {
			if (uploadFile != null && !uploadFile.delete()) {
				log.error("Failed to delete temporary file [{}]", uploadFile.getPath());
			}
		}
	}

	private String uploadToS3(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		log.debug("Uploading file [{}] to S3 bucket [{}]", fileName, bucket);
		amazonS3Client.putObject(
			new PutObjectRequest(bucket, fileName, uploadFile)
				.withCannedAcl(CannedAccessControlList.PublicRead)
		);
		String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
		log.info("File [{}] uploaded successfully to URL [{}]", fileName, fileUrl);
		return fileUrl;
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
		log.debug("Creating temporary file [{}] for conversion", convertFile.getPath());

		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
				log.info("Temporary file [{}] created successfully", convertFile.getPath());
				return Optional.of(convertFile);
			} catch (IOException e) {
				log.error("Failed to write to temporary file [{}]", convertFile.getPath(), e);
				throw e;
			}
		} else {
			log.error("Failed to create new file [{}]", convertFile.getPath());
			return Optional.empty();
		}
	}

	public void deleteImageFromS3(String imageAddress) {
		String key = getKeyFromImageAddress(imageAddress);
		try {
			amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
		} catch (Exception e) {
			throw new DatabaseException(IMAGE_DELETE_ERROR);
		}
	}

	private String getKeyFromImageAddress(String imageAddress) {
		try {
			URL url = new URL(imageAddress);
			String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
			return decodingKey.substring(1); // 맨 앞의 '/' 제거
		} catch (MalformedURLException | UnsupportedEncodingException e) {
			throw new DatabaseException(IMAGE_DELETE_ERROR);
		}
	}
}
