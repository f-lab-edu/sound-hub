package com.example.soundhub.config.exception;

public enum ErrorResponseStatus {
	// 2000 : Request 오류
	REQUEST_ERROR(false, 2000, "입력값을 확인 해주세요."),
	DUPLICATE_ERROR(false, 2001, "중복된 레코드 입니다."),
	INVALID_PWD(false, 2002, "비밀번호가 올바르지 않습니다."),

	// 3000 : Response 오류
	RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

	//4000 : Database, Server 오류
	DATABASE_ERROR(false, 4000, "데이터 베이스 접근 오류."),
	QUERY_TIMEOUT_ERROR(false, 4001, "쿼리 타임 아웃 에러."),
	DB_INSERT_ERROR(false, 4002, "DB에 값을 삽입 하는데 실패 했습니다."),
	NOT_FOUND_USER(false, 4003, "등록된 유저가 없습니다."),
	IMAGE_DELETE_ERROR(false, 4004, "이미지 삭제 실패"),
	IMAGE_UPLOAD_ERROR(false, 4005, "이미지 업로드 실패"),

	//5000 : Server connection 오류
	SERVER_ERROR(false, 5000, "서버와의 연결에 실패하였습니다."),

	;

	private final boolean isSuccess;
	private final int code;
	private final String message;

	private ErrorResponseStatus(boolean isSuccess, int code, String message) {
		this.isSuccess = isSuccess;
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return this.code;
	}

}
