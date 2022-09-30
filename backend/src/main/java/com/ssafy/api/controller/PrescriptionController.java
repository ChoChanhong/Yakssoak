package com.ssafy.api.controller;


import com.ssafy.api.request.*;
import com.ssafy.api.service.HospitalService;
import com.ssafy.api.service.PrescriptionService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.customObject.HospitalInfo;
import com.ssafy.common.customObject.PharmInfo;
import com.ssafy.db.entity.Hospital;
import com.ssafy.db.entity.Prescription;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 처방전 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "처방전 API", tags = {"Prescription"})
@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

	@Autowired  // 의존성 주입
	PrescriptionService	prescriptionService;

	@Autowired
	HospitalService hospitalService;

	@PostMapping("/regist")
	@ApiOperation(value = "처방전 생성", notes = "처방전을 생성한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<?> createPrescription(
			@ApiIgnore Authentication authentication,
			@RequestBody @ApiParam(value="처방전 정보", required = true) CreatePrescriptionPostReq createPrescriptionPostReq) {

			SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
			long userSeq = userDetails.getUser().getUserSeq();
			HospitalInfo hospitalInfo = hospitalService.getHospitalInfo(userSeq);

			if (hospitalInfo.getUser().getUserIdx() != 1) {
				return new ResponseEntity<>("처방전 생성 권한이 없습니다.", HttpStatus.valueOf(401));
			}

			prescriptionService.createPrescription(createPrescriptionPostReq);
			return new ResponseEntity<>("처방전 발행이 완료되었습니다", HttpStatus.valueOf(200));

//		return new ResponseEntity<>("잘못된 요청입니다", HttpStatus.valueOf(400));
	}

	/**
	 * 처방전 약국 정보 추가
	 */
	@PutMapping(value="updatePharm")
	@ApiOperation(value = "처방전 약국 정보 추가", notes = "처방전에 약국 정보를 추가한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "잘못된 요청"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 403, message = "토큰 없음"),
			@ApiResponse(code = 404, message = "바디 정보 오류"),
			@ApiResponse(code = 405, message = "무결성 오류"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> updatePrescriptionPharm(@ApiIgnore Authentication authentication,
											   @RequestBody @ApiParam(value="처방전 약국 정보 추가", required = true) UpdatePrescriptionPharmPostReq updatePrescriptionPharmPostReq) {

		if (authentication == null) {
			return new ResponseEntity<>("토큰이 없습니다", HttpStatus.valueOf(403));
		}
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		long userSeq = userDetails.getUser().getUserSeq();

		Prescription prescription = prescriptionService.updatePrescriptionPharm(updatePrescriptionPharmPostReq.getPrescriptionSeq(), updatePrescriptionPharmPostReq.getPharmUserSeq());

		if (prescription != null) {
			return new ResponseEntity<>(prescription, HttpStatus.valueOf(200));
		}

		return new ResponseEntity<>("잘못된 요청입니다", HttpStatus.valueOf(400));
	}




	/**
	 * 처방전 사용 완료
	 */
	@PutMapping(value="updateCompletion")
	@ApiOperation(value = "처방전 사용 완료", notes = "처방전에 약국 정보를 추가한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 400, message = "잘못된 요청"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 403, message = "토큰 없음"),
			@ApiResponse(code = 404, message = "바디 정보 오류"),
			@ApiResponse(code = 405, message = "무결성 오류"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> updatePrescriptionCompletion(@ApiIgnore Authentication authentication,
														  @RequestBody @ApiParam(value="처방전 사용 정보 추가", required = true)UpdatePrescriptionCompletionPostReq updatePrescriptionCompletionPostReq) {

		if (authentication == null) {
			return new ResponseEntity<>("토큰이 없습니다", HttpStatus.valueOf(403));
		}
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		String userId = userDetails.getUsername();
		long userSeq = userDetails.getUser().getUserSeq();

		Prescription prescription = prescriptionService.updatePrescriptionCompletion(updatePrescriptionCompletionPostReq.getPrescriptionSeq());

		if (prescription != null) {
			return new ResponseEntity<>(prescription, HttpStatus.valueOf(200));
		}

		return new ResponseEntity<>("잘못된 요청입니다", HttpStatus.valueOf(400));
	}
}
