package com.profit.ProFit.Service;

import com.profit.ProFit.DTO.CommunityResponseDTO;
import com.profit.ProFit.DTO.CommunitySaveDTO;
import com.profit.ProFit.DTO.CommunityUpdateDTO;
import com.profit.ProFit.Model.Community;
import com.profit.ProFit.Model.Participation;
import com.profit.ProFit.Model.User;
import com.profit.ProFit.Repository.CommunityRepositoty;
import com.profit.ProFit.Repository.ParticipationRepository;
import com.profit.ProFit.Repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {

    private final CommunityRepositoty communityRepositoty;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    public CommunityService(CommunityRepositoty communityRepositoty, UserRepository userRepository,
        ParticipationRepository participationRepository) {
        this.communityRepositoty = communityRepositoty;
        this.userRepository = userRepository;
        this.participationRepository = participationRepository;
    }

    /**
     * 모든 커뮤니티 글을 조회합니다.
     *
     * @return CommunityResponseDTO 리스트
     */
    public List<CommunityResponseDTO> getAllCommunities() {
        // 1. 데이터베이스에서 모든 커뮤니티 글 조회
        List<Community> communities = communityRepositoty.findAll();

        // 2. CommunityResponseDTO로 변환하여 반환
        return communities.stream()
            .map(community -> new CommunityResponseDTO(
                community.getCommunityId(),
                community.getTag(),
                community.getTitle(),
                community.getContents(),
                community.getCommentCount()
            ))
            .collect(Collectors.toList());
    }

    /** 커뮤니티 글 저장(생성)
     *
     * @param communityDTO 클라이언트로부터 받은 CommunityDTO
     * @return 변환한 CommunityResponseDTO 객체
     * @throws IllegalArgumentException 필수 값 누락 또는 잘못된 데이터가 있는 경우
     */
    @Transactional
    public CommunityResponseDTO saveCommunity(CommunitySaveDTO communityDTO) {
        validateCommunityDTO(communityDTO); // DTO 검증
        User author = userRepository.findByUserId(communityDTO.getUserId()); // 작성자 확인
        Community community = createCommunityFromDTO(communityDTO, author); // Community 객체 생성
        Community savedCommunity = communityRepositoty.save(community); // 저장

        // CommunityResponseDTO로 변환
        return new CommunityResponseDTO(
            community.getCommunityId(),
            community.getTag(),
            community.getTitle(),
            community.getContents(),
            community.getCommentCount()
        );
    }

    /**
     * 커뮤니티 글을 업데이트합니다.
     *
     * @param communityUpdateDTO 업데이트할 정보를 담은 DTO
     * @return 수정된 커뮤니티 정보를 담은 CommunityResponseDTO
     * @throws IllegalArgumentException 해당 ID의 글이 없을 경우 예외 발생
     */
    @Transactional
    public CommunityResponseDTO updateCommunity(CommunityUpdateDTO communityUpdateDTO) {
        // 1. communityId가 null인지 확인
        if (communityUpdateDTO.getCommunityId() == null) {
            throw new IllegalArgumentException("해당 게시물의 ID 데이터가 공백입니다. 다시 요청해주세요.");
        }

        // 2. 해당 커뮤니티 글 조회
        Community community = communityRepositoty.findById(communityUpdateDTO.getCommunityId())
            .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));

        // 3. 변경 내용 업데이트
        if (communityUpdateDTO.getTag() != null) { // 태그가 존재하면 수정
            community.setTag(communityUpdateDTO.getTag());
        }
        if (communityUpdateDTO.getTitle() != null) { // 제목이 존재하면 수정
            community.setTitle(communityUpdateDTO.getTitle());
        }
        if (communityUpdateDTO.getContents() != null) { // 내용이 존재하면 수정
            community.setContents(communityUpdateDTO.getContents());
        }

        // 4. 저장
        Community updatedCommunity = communityRepositoty.save(community);

        // 5. 수정된 정보를 CommunityResponseDTO로 변환 후 반환
        return new CommunityResponseDTO(
            updatedCommunity.getCommunityId(),
            updatedCommunity.getTag(),
            updatedCommunity.getTitle(),
            updatedCommunity.getContents(),
            updatedCommunity.getCommentCount()
        );
    }


    /**
     * 커뮤니티 글 삭제를 처리하는 서비스
     * @param communityId 커뮤니티 글 ID
     */
    @Transactional
    public void deleteCommunityById(Integer communityId) {
        // 1. 커뮤니티 글 조회
        Community community = communityRepositoty.findById(communityId)
            .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        // 2. 커뮤니티 글 삭제
        communityRepositoty.delete(community);
    }

    @Transactional
    public Participation saveComment(Integer communityId, Integer userId, String contents) {
        // 1. 커뮤니티 글 조회
        Community community = communityRepositoty.findById(communityId)
            .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다."));

        // 2. 사용자 조회
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }

        // 3. 현재 댓글 수 조회 및 새로운 댓글 생성
        int currentCommentCount = participationRepository.countByCommunity(community);
        Participation comment = new Participation(community, user, contents, currentCommentCount + 1);

        // 4. 댓글 저장 및 댓글 수 증가
        participationRepository.save(comment);
        community.incrementCommentCount(); // 댓글 수 증가
        communityRepositoty.save(community); // 변경사항 저장

        return comment;
    }

    /**
     * 특정 커뮤니티의 모든 댓글을 조회합니다.
     *
     * @param communityId 커뮤니티 ID
     * @return 댓글 리스트
     */
    @Transactional
    public List<Participation> getCommentsByCommunityId(Integer communityId) {
        // 커뮤니티 존재 여부 확인
        if (!communityRepositoty.existsById(communityId)) {
            throw new IllegalArgumentException("해당 커뮤니티 글을 찾을 수 없습니다.");
        }

        // 댓글 조회
        return participationRepository.findByCommunity_CommunityId(communityId);
    }




    /**
     * CommunitySaveDTO의 필수 값을 검증합니다.
     * 필수 값 누락 시 IllegalArgumentException을 발생시킵니다.
     * @param communityDTO 클라이언트로부터 받은 CommunityDTO
     */
    private void validateCommunityDTO(CommunitySaveDTO communityDTO) {
        StringBuilder errors = new StringBuilder();

        if (isNullOrEmpty(communityDTO.getTag())) {
            errors.append("태그는 필수입니다. ");
        }
        if (isNullOrEmpty(communityDTO.getTitle())) {
            errors.append("제목은 필수입니다. ");
        }
        if (isNullOrEmpty(String.valueOf(communityDTO.getUserId()))) {
            errors.append("userId는 필수입니다. ");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }

    /**
     * CommunityDTO와 작성자를 사용하여 Community 객체를 생성합니다.
     * @param communityDTO 클라이언트로부터 받은 CommunityDTO
     * @param author 작성자(User 객체)
     * @return 생성된 Community 객체
     */
    private Community createCommunityFromDTO(CommunitySaveDTO communityDTO, User author) {
        return new Community(
            communityDTO.getTag(),
            communityDTO.getTitle(),
            communityDTO.getContents(),
            "recruitment".equalsIgnoreCase(communityDTO.getTag()) ? 1 : 0,
            author
        );
    }

    /**
     * 문자열이 null 또는 공백인지 확인합니다.
     * @param value 확인할 문자열
     * @return null 또는 공백일 경우 true
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}
