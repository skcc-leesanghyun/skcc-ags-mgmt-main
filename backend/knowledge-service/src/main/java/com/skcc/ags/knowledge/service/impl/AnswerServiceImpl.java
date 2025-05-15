package com.skcc.ags.knowledge.service.impl;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Question;
import com.skcc.ags.knowledge.domain.Vote;
import com.skcc.ags.knowledge.domain.VoteType;
import com.skcc.ags.knowledge.dto.AnswerDTO;
import com.skcc.ags.knowledge.exception.AnswerNotFoundException;
import com.skcc.ags.knowledge.exception.DuplicateVoteException;
import com.skcc.ags.knowledge.exception.QuestionNotFoundException;
import com.skcc.ags.knowledge.exception.UnauthorizedAccessException;
import com.skcc.ags.knowledge.repository.AnswerRepository;
import com.skcc.ags.knowledge.repository.QuestionRepository;
import com.skcc.ags.knowledge.repository.VoteRepository;
import com.skcc.ags.knowledge.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final VoteRepository voteRepository;

    @Override
    @Transactional
    public AnswerDTO createAnswer(Long questionId, AnswerDTO answerDTO, String username) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + questionId));

        // 자신의 질문에 답변하는 것을 방지
        if (question.getCreatedBy().equals(username)) {
            throw new UnauthorizedAccessException("You cannot answer your own question");
        }

        // 이미 답변했는지 확인
        if (answerRepository.existsByQuestionIdAndCreatedBy(questionId, username)) {
            throw new DuplicateVoteException("You have already answered this question");
        }

        Answer answer = convertToEntity(answerDTO);
        answer.setQuestion(question);
        answer.setCreatedBy(username);
        answer.setCreatedAt(LocalDateTime.now());
        answer.setAccepted(false);
        answer.setUpvoteCount(0);
        answer.setDownvoteCount(0);

        Answer savedAnswer = answerRepository.save(answer);
        return convertToDTO(savedAnswer);
    }

    @Override
    @Transactional
    public AnswerDTO updateAnswer(Long id, AnswerDTO answerDTO, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        validateOwnership(answer, username);

        answer.setContent(answerDTO.getContent());
        answer.setUpdatedBy(username);
        answer.setUpdatedAt(LocalDateTime.now());

        Answer updatedAnswer = answerRepository.save(answer);
        return convertToDTO(updatedAnswer);
    }

    @Override
    @Transactional
    public void deleteAnswer(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        validateOwnership(answer, username);

        // 답변에 대한 투표 기록 삭제
        voteRepository.deleteByAnswerId(id);
        answerRepository.delete(answer);
    }

    @Override
    public AnswerDTO getAnswer(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));
        return convertToDTO(answer);
    }

    @Override
    public List<AnswerDTO> getAnswersByQuestion(Long questionId) {
        return answerRepository.findByQuestionIdOrderByCreatedAtDesc(questionId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AnswerDTO> getAnswersByUser(String username) {
        return answerRepository.findByCreatedByOrderByCreatedAtDesc(username)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AnswerDTO acceptAnswer(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        Question question = answer.getQuestion();
        validateQuestionOwnership(question, username);

        // 이미 채택된 답변이 있는 경우 채택 해제
        answerRepository.resetAcceptedStatusForQuestion(question.getId());

        answer.setAccepted(true);
        Answer acceptedAnswer = answerRepository.save(answer);
        return convertToDTO(acceptedAnswer);
    }

    @Override
    @Transactional
    public AnswerDTO unacceptAnswer(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        Question question = answer.getQuestion();
        validateQuestionOwnership(question, username);

        answer.setAccepted(false);
        Answer unacceptedAnswer = answerRepository.save(answer);
        return convertToDTO(unacceptedAnswer);
    }

    @Override
    @Transactional
    public void upvoteAnswer(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        // 자신의 답변에 투표하는 것을 방지
        if (answer.getCreatedBy().equals(username)) {
            throw new UnauthorizedAccessException("You cannot vote for your own answer");
        }

        // 이미 추천한 경우 예외 발생
        if (voteRepository.existsByAnswerIdAndUsernameAndType(id, username, VoteType.UPVOTE)) {
            throw new DuplicateVoteException("You have already upvoted this answer");
        }

        // 비추천을 한 경우 비추천 취소
        voteRepository.findByAnswerIdAndUsernameAndType(id, username, VoteType.DOWNVOTE)
                .ifPresent(vote -> {
                    voteRepository.delete(vote);
                    answer.decrementDownvoteCount();
                });

        Vote vote = new Vote();
        vote.setAnswer(answer);
        vote.setUsername(username);
        vote.setType(VoteType.UPVOTE);
        vote.setCreatedAt(LocalDateTime.now());

        voteRepository.save(vote);
        answer.incrementUpvoteCount();
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void removeUpvote(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        voteRepository.findByAnswerIdAndUsernameAndType(id, username, VoteType.UPVOTE)
                .ifPresent(vote -> {
                    voteRepository.delete(vote);
                    answer.decrementUpvoteCount();
                    answerRepository.save(answer);
                });
    }

    @Override
    @Transactional
    public void downvoteAnswer(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        // 자신의 답변에 투표하는 것을 방지
        if (answer.getCreatedBy().equals(username)) {
            throw new UnauthorizedAccessException("You cannot vote for your own answer");
        }

        // 이미 비추천한 경우 예외 발생
        if (voteRepository.existsByAnswerIdAndUsernameAndType(id, username, VoteType.DOWNVOTE)) {
            throw new DuplicateVoteException("You have already downvoted this answer");
        }

        // 추천을 한 경우 추천 취소
        voteRepository.findByAnswerIdAndUsernameAndType(id, username, VoteType.UPVOTE)
                .ifPresent(vote -> {
                    voteRepository.delete(vote);
                    answer.decrementUpvoteCount();
                });

        Vote vote = new Vote();
        vote.setAnswer(answer);
        vote.setUsername(username);
        vote.setType(VoteType.DOWNVOTE);
        vote.setCreatedAt(LocalDateTime.now());

        voteRepository.save(vote);
        answer.incrementDownvoteCount();
        answerRepository.save(answer);
    }

    @Override
    @Transactional
    public void removeDownvote(Long id, String username) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found with id: " + id));

        voteRepository.findByAnswerIdAndUsernameAndType(id, username, VoteType.DOWNVOTE)
                .ifPresent(vote -> {
                    voteRepository.delete(vote);
                    answer.decrementDownvoteCount();
                    answerRepository.save(answer);
                });
    }

    @Override
    public Page<AnswerDTO> getRecentAnswers(Pageable pageable) {
        return answerRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::convertToDTO);
    }

    @Override
    public List<AnswerDTO> getTopVotedAnswers(int limit) {
        return answerRepository.findTopByUpvoteCount(limit)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countAcceptedAnswersByUser(String username) {
        return answerRepository.countByCreatedByAndAcceptedTrue(username);
    }

    @Override
    public long countAnswersByQuestion(Long questionId) {
        return answerRepository.countByQuestionId(questionId);
    }

    private void validateOwnership(Answer answer, String username) {
        if (!answer.getCreatedBy().equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to modify this answer");
        }
    }

    private void validateQuestionOwnership(Question question, String username) {
        if (!question.getCreatedBy().equals(username)) {
            throw new UnauthorizedAccessException("Only the question author can manage answer acceptance");
        }
    }

    private AnswerDTO convertToDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .accepted(answer.isAccepted())
                .upvoteCount(answer.getUpvoteCount())
                .downvoteCount(answer.getDownvoteCount())
                .questionId(answer.getQuestion().getId())
                .createdBy(answer.getCreatedBy())
                .createdAt(answer.getCreatedAt())
                .updatedBy(answer.getUpdatedBy())
                .updatedAt(answer.getUpdatedAt())
                .build();
    }

    private Answer convertToEntity(AnswerDTO answerDTO) {
        return Answer.builder()
                .content(answerDTO.getContent())
                .build();
    }
} 