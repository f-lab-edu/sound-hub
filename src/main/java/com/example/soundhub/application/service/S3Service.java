package com.example.soundhub.application.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {
	private final AmazonS3 amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String upload(MultipartFile multipartFile, String dirName) throws IOException {
		log.debug("Attempting to upload file [{}] to directory [{}]", multipartFile.getOriginalFilename(), dirName);

		File uploadFile = convert(multipartFile)
			.orElseThrow(() -> new IllegalArgumentException("Failed to convert MultipartFile to File"));

		try {
			String fileUrl = uploadToS3(uploadFile, dirName);
			if (!uploadFile.delete()) {
				log.error("Failed to delete temporary file [{}]", uploadFile.getPath());
			}
			return fileUrl;
		} catch (Exception e) {
			log.error("Error uploading file [{}] to S3 bucket [{}]", uploadFile.getName(), bucket, e);
			throw e;
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
}
