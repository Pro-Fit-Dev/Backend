package com.profit.ProFit.Controller;

import com.profit.ProFit.DTO.CommunityResponseDTO;
import com.profit.ProFit.DTO.CommunitySaveDTO;
import com.profit.ProFit.DTO.CommunityUpdateDTO;
import com.profit.ProFit.Model.Participation;
import com.profit.ProFit.Service.CommunityService;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {

    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * 커뮤니티 글을 생성합니다.
     *
     * @param communityDTO 클라이언트로부터 받은 CommunityDTO
     * @return 성공 시 CommunityResponse 객체, 실패 시 에러 메시지
     */
    @PostMapping("/save")
    public ResponseEntity<?> createCommunity(@RequestBody CommunitySaveDTO communityDTO) {
        try {
            CommunityResponseDTO response = communityService.saveCommunity(communityDTO);
            return ResponseEntity.ok(response); // 성공 시 DTO 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage())); // 에러 메시지 반환
        }
    }


    /**
     * 커뮤니티 글을 업데이트합니다.
     *
     * @param communityUpdateDTO 클라이언트로부터 받은 업데이트 정보를 담은 DTO
     * @return 성공 시 CommunityResponseDTO, 실패 시 에러 메시지
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateCommunity(@RequestBody CommunityUpdateDTO communityUpdateDTO) {
        try {
            CommunityResponseDTO response = communityService.updateCommunity(communityUpdateDTO);
            return ResponseEntity.ok(response); // 수정된 정보 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    /**
     * 커뮤니티 글 삭제를 처리하는 컨트롤러
     */
    @DeleteMapping("/{communityId}")
    public ResponseEntity<?> deleteCommunity(@PathVariable Integer communityId) {
        try {
            communityService.deleteCommunityById(communityId);
            return ResponseEntity.ok(Map.of("message", "커뮤니티 글이 성공적으로 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    /**
     * 모든 커뮤니티 글을 반환합니다.
     *
     * @return CommunityResponseDTO 리스트
     */
    @GetMapping("/allCommunities")
    public ResponseEntity<List<CommunityResponseDTO>> getAllCommunities() {
        List<CommunityResponseDTO> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    /**
     * 커뮤니티 글에 댓글을 작성합니다.
     *
     * @param communityId 커뮤니티 글 ID
     * @param userId 사용자 개인 ID
     * @param contents 댓글 내용
     * @return 작성된 댓글 객체 또는 에러 메시지
     */
    @PostMapping("/{communityId}/comment")
    public ResponseEntity<?> saveComment(
        @PathVariable Integer communityId,
        @RequestParam Integer userId,
        @RequestParam String contents) {
        try {
            // 작성된 댓글 객체를 반환
            Participation comment = communityService.saveComment(communityId, userId, contents);
            return ResponseEntity.ok(comment); // 댓글 객체 반환
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * 특정 커뮤니티의 모든 댓글을 조회합니다.
     *
     * @param communityId 커뮤니티 ID
     * @return 댓글 리스트
     */
    @GetMapping("/{communityId}/comments")
    public ResponseEntity<?> getComments(@PathVariable Integer communityId) {
        try {
            List<Participation> comments = communityService.getCommentsByCommunityId(communityId);
            return ResponseEntity.ok(comments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


}
