package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.UserUpdatePasswordPostReq;
import com.ssafy.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	
	User getUserByUserId(String userId);

    boolean checkIdDuplicated(String userId);

	boolean checkRRNDuplicated(String userRRN);

	public User updateUserInfo(String userId, UserRegisterPostReq updateInfo);

	void deleteUser(User user);
}