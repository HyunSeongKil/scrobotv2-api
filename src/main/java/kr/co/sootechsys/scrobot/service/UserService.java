package kr.co.sootechsys.scrobot.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.data.domain.Pageable;
import kr.co.sootechsys.scrobot.domain.PageableResult;
import kr.co.sootechsys.scrobot.domain.SearchUserDto;
import kr.co.sootechsys.scrobot.domain.UserDto;

public interface UserService {
    Map<String, Object> signin(UserDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException;

    void join(UserDto dto) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException;

    UserDto findById(String userId) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException;

    /**
     * 토큰 재발급
     * 
     * @param refreshToken refresh token
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    String reIssueToken(String refreshToken) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException;

    /**
     * 프로젝트아이디로 사용자 목록 조회
     * 
     * @param prjctId 프로젝트아이디
     * @return 사용자 목록
     */
    List<UserDto> findAllByPrjctId(String prjctId);

    /**
     * id 찾기
     * 
     * @param userNm 사용자명
     * @param telno 전화번호
     * @return
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    String findIdByUserNmAndTelno(String userNm, String telno) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException;

    /**
     * 목록 조회
     * 
     * @param searchDto
     * @param pageable
     * @return
     */
    PageableResult findAll(SearchUserDto searchDto, Pageable pageable);
}
