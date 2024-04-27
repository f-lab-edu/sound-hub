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

	// MultipartFile to File conversion and upload to S3
	public String upload(MultipartFile multipartFile, String dirName) throws IOException {
		log.debug("Attempting to upload file [{}] to directory [{}]", multipartFile.getOriginalFilename(), dirName);

		File uploadFile = convert(multipartFile)
			.orElseThrow(() -> new IllegalArgumentException("Failed to convert MultipartFile to File"));

		try {
			return upload(uploadFile, dirName);
		} catch (Exception e) {
			log.error("Error uploading file [{}] to S3 bucket [{}]", uploadFile.getName(), bucket, e);
			throw e;
		}
	}

	private String upload(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		try {
			String uploadImageUrl = putS3(uploadFile, fileName);
			removeNewFile(uploadFile);
			return uploadImageUrl;
		} catch (Exception e) {
			log.error("Failed to upload file [{}] to S3 under directory [{}]", uploadFile.getName(), dirName, e);
			throw e;
		}
	}

	private String putS3(File uploadFile, String fileName) {
		try {
			amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, uploadFile)
					.withCannedAcl(CannedAccessControlList.PublicRead) // Upload file with PublicRead access
			);
			String fileUrl = amazonS3Client.getUrl(bucket, fileName).toString();
			log.info("File [{}] uploaded successfully to [{}]", fileName, fileUrl);
			return fileUrl;
		} catch (Exception e) {
			log.error("Exception uploading file [{}] to S3", fileName, e);
			throw e;
		}
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info("Successfully deleted local file [{}]", targetFile.getName());
		} else {
			log.error("Failed to delete local file [{}]", targetFile.getName());
		}
	}

	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(System.getProperty("java.io.tmpdir") + "/"
			+ file.getOriginalFilename()); // Store file in temporary directory
		log.debug("Converting multipart file [{}] to file [{}]", file.getOriginalFilename(), convertFile.getPath());

		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
				log.info("File [{}] created successfully", convertFile.getPath());
				return Optional.of(convertFile);
			} catch (IOException e) {
				log.error("Failed to write to file [{}]", convertFile.getPath(), e);
				throw e;
			}
		} else {
			log.error("Failed to create new file [{}]", convertFile.getPath());
			return Optional.empty();
		}
	}
}
