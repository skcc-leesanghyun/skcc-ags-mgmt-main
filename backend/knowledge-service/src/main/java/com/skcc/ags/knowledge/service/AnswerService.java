package com.skcc.ags.knowledge.service;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.dto.AnswerDTO;
import com.skcc.ags.knowledge.exception.AnswerNotFoundException;
import com.skcc.ags.knowledge.exception.QuestionNotFoundException;
import com.skcc.ags.knowledge.repository.AnswerRepository;
import com.skcc.ags.knowledge.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 답변 관리를 위한 서비스 인터페이스
 */
public interface AnswerService {

    /**
     * 새로운 답변을 생성합니다.
     *
     * @param questionId 답변이 달릴 질문의 ID
     * @param answerDTO 생성할 답변 정보
     * @param username 답변 작성자의 사용자명
     * @return 생성된 답변 정보
     */
    AnswerDTO createAnswer(Long questionId, AnswerDTO answerDTO, String username);

    /**
     * 기존 답변을 수정합니다.
     *
     * @param id 수정할 답변의 ID
     * @param answerDTO 수정할 답변 정보
     * @param username 수정 요청자의 사용자명
     * @return 수정된 답변 정보
     */
    AnswerDTO updateAnswer(Long id, AnswerDTO answerDTO, String username);

    /**
     * 답변을 삭제합니다.
     *
     * @param id 삭제할 답변의 ID
     * @param username 삭제 요청자의 사용자명
     */
    void deleteAnswer(Long id, String username);

    /**
     * ID로 답변을 조회합니다.
     *
     * @param id 조회할 답변의 ID
     * @return 조회된 답변 정보
     */
    AnswerDTO getAnswer(Long id);

    /**
     * 특정 질문에 대한 답변 목록을 조회합니다.
     *
     * @param questionId 질문 ID
     * @return 답변 목록
     */
    List<AnswerDTO> getAnswersByQuestion(Long questionId);

    /**
     * 특정 사용자가 작성한 답변 목록을 조회합니다.
     *
     * @param username 사용자명
     * @return 답변 목록
     */
    List<AnswerDTO> getAnswersByUser(String username);

    /**
     * 답변을 채택합니다.
     *
     * @param id 채택할 답변의 ID
     * @param username 채택 요청자의 사용자명
     * @return 채택된 답변 정보
     */
    AnswerDTO acceptAnswer(Long id, String username);

    /**
     * 답변 채택을 취소합니다.
     *
     * @param id 채택 취소할 답변의 ID
     * @param username 채택 취소 요청자의 사용자명
     * @return 채택 취소된 답변 정보
     */
    AnswerDTO unacceptAnswer(Long id, String username);

    /**
     * 답변을 추천합니다.
     *
     * @param id 추천할 답변의 ID
     * @param username 추천 요청자의 사용자명
     */
    void upvoteAnswer(Long id, String username);

    /**
     * 답변 추천을 취소합니다.
     *
     * @param id 추천 취소할 답변의 ID
     * @param username 추천 취소 요청자의 사용자명
     */
    void removeUpvote(Long id, String username);

    /**
     * 답변을 비추천합니다.
     *
     * @param id 비추천할 답변의 ID
     * @param username 비추천 요청자의 사용자명
     */
    void downvoteAnswer(Long id, String username);

    /**
     * 답변 비추천을 취소합니다.
     *
     * @param id 비추천 취소할 답변의 ID
     * @param username 비추천 취소 요청자의 사용자명
     */
    void removeDownvote(Long id, String username);

    /**
     * 최근 답변 목록을 조회합니다.
     *
     * @param pageable 페이지네이션 정보
     * @return 답변 목록
     */
    Page<AnswerDTO> getRecentAnswers(Pageable pageable);

    /**
     * 추천수가 높은 상위 N개의 답변을 조회합니다.
     *
     * @param limit 조회할 답변 수
     * @return 답변 목록
     */
    List<AnswerDTO> getTopVotedAnswers(int limit);

    /**
     * 특정 사용자의 채택된 답변 수를 조회합니다.
     *
     * @param username 사용자명
     * @return 채택된 답변 수
     */
    long countAcceptedAnswersByUser(String username);

    /**
     * 특정 질문의 답변 수를 조회합니다.
     *
     * @param questionId 질문 ID
     * @return 답변 수
     */
    long countAnswersByQuestion(Long questionId);
} 