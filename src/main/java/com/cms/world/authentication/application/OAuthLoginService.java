package com.cms.world.authentication.application;


import com.cms.world.authentication.domain.AuthTokens;
import com.cms.world.authentication.member.domain.MemberRepository;
import com.cms.world.authentication.domain.oauth.OAuthInfoResponse;
import com.cms.world.authentication.domain.oauth.OAuthLoginParams;
import com.cms.world.authentication.domain.oauth.RequestOAuthInfoService;
import com.cms.world.authentication.domain.AuthTokensGenerator;
import com.cms.world.authentication.member.domain.MemberDto;
import com.cms.world.utils.DateUtil;
import com.cms.world.utils.GlobalCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

//    public JwtTokens login(OAuthLoginParams params) {
//        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
//        Long memberId = findOrCreateMember(oAuthInfoResponse);
//        return jwtTokensGenerator.generate(memberId);
//    }

    public Map<String, Object> getMemberAndTokens(OAuthLoginParams params) {
        Map<String, Object> map = new HashMap<>();
        
        
        
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);

        System.out.println("oAuthInfoResponse.getNickname() = " + oAuthInfoResponse.getNickname());
        System.out.println("oAuthInfoResponse.getOAuthProvider() = " + oAuthInfoResponse.getOAuthProvider());
        System.out.println("oAuthInfoResponse.getProfileImg() = " + oAuthInfoResponse.getProfileImg());
        System.out.println("oAuthInfoResponse.getId() = " + oAuthInfoResponse.getId()); // uid가 될 예정인 값
//        Long memberId = findOrCreateMember(oAuthInfoResponse);
//
//        map.put("tokens", authTokensGenerator.generate(memberId));
//        map.put("memberId", memberId);
        return map;
    }

    /* 카카오와 네이버는 리퀘스트를 보내서, 트위터도 OAuthInfoReponse로 처리되면 좋겠다고 생각하낟.  */


    /* 리퀘스트를 때리는 부분이랑, 응답을 처리해서 토큰과 멤버를 가져오는 부분이랑 나눠야 겠다.
    *
    * twitter의 경우 혼자 별도의 구조로 가기 때문에 쉽지가 않다....
    *
    * */

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
//        String email = "jinvicky@naver.com";
//        return memberRepository.findByEmail(email)
//                .map(MemberDto::getId)
//                .orElseGet(() -> newMember(oAuthInfoResponse));
        return 0L;
    }

//    /* uid와 provider로 사용자 중복구분하기 */
//    private Long selectOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
//        return memberRepository.findByUidAndLoginTp(oAuthInfoResponse.getUid(), oAuthInfoResponse.getOAuthProvider().toString())
//                .map(MemberDto::getId)
//                .orElseGet(() -> newMember(oAuthInfoResponse));
//    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        MemberDto member  = new MemberDto();
        member.setNickName("user_" + UUID.randomUUID().toString().substring(0, 8));
//        member.setEmail(oAuthInfoResponse.getEmail());

        // 현재 존재하는 member id의 최댓값을 가져온다.
        //
//        return memberRepository.save(member).get();
        return 0L;
    }

    // ------------------------------------------------- 트위터 로그인 -------------------------------------------------

    /* 트위터 조회/가입 분기처리 */
    public long findOrCreateMember (TwitterProfile profile) {
        Optional<MemberDto> member = memberRepository.findByUidAndLoginType(profile.getId(), GlobalCode.OAUTH_TWITTER.getCode());
        return member.map(MemberDto::getId).orElseGet(() -> newMember(profile));
    }

    /* 트위터 신규 회원 가입 */
    public long newMember (TwitterProfile profile) {
        MemberDto member = new MemberDto();
        member.setUid(profile.getId());
        member.setNickName(profile.getName());
        member.setProfileImg(profile.getProfileImageUrl());
        member.setLoginType(GlobalCode.OAUTH_TWITTER.getCode());
        return memberRepository.save(member).getId();
    }

    /* 트위터 jwt token 생성 + refresh token 저장 + 로그인 시각 저장  => handleTokenAndLoginTime ? */
    @Transactional
    public Map<String, Object> handleTokenAndLoginTime (long memberId) {
        Map<String, Object> map = new HashMap<>();
        AuthTokens authTokens = authTokensGenerator.generate(memberId);

        MemberDto dto = memberRepository.findById(memberId).get();
        dto.setRefreshToken(authTokens.getRefreshToken()); // 리프레시 토큰 저장
        dto.setLastLoginTime(DateUtil.currentDateTime()); // 로그인 시각 저장
        memberRepository.save(dto);

        map.put("tokens", authTokens);
        map.put("nick", dto.getNickName());
        return map;
    }

    /* 로그인 (트위터) */
    public Map<String, Object> login (TwitterProfile profile) {
        Long id = findOrCreateMember(profile);
        return handleTokenAndLoginTime(id);
    }


}