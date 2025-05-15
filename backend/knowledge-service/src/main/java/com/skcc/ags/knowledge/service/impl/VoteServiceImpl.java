package com.skcc.ags.knowledge.service.impl;

import com.skcc.ags.knowledge.domain.Answer;
import com.skcc.ags.knowledge.domain.Vote;
import com.skcc.ags.knowledge.domain.VoteType;
import com.skcc.ags.knowledge.dto.VoteDTO;
import com.skcc.ags.knowledge.exception.AnswerNotFoundException;
import com.skcc.ags.knowledge.exception.DuplicateVoteException;
import com.skcc.ags.knowledge.exception.VoteNotFoundException;
import com.skcc.ags.knowledge.mapper.VoteMapper;
import com.skcc.ags.knowledge.repository.AnswerRepository;
import com.skcc.ags.knowledge.repository.VoteRepository;
import com.skcc.ags.knowledge.repository.VoteRepository.VoteStats;
import com.skcc.ags.knowledge.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * VoteService 인터페이스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final AnswerRepository answerRepository;
    private final VoteMapper voteMapper;

    @Override
    @Transactional
    public VoteDTO createVote(Long answerId, VoteType type, String username) {
        log.debug("Creating vote for answer: {}, type: {}, username: {}", answerId, type, username);

        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("답변을 찾을 수 없습니다. ID: " + answerId));

        if (voteRepository.existsByAnswerIdAndUsernameAndType(answerId, username, type)) {
            throw new DuplicateVoteException("이미 투표한 답변입니다.");
        }

        Vote vote = Vote.builder()
                .answer(answer)
                .username(username)
                .type(type)
                .build();

        Vote savedVote = voteRepository.save(vote);
        return voteMapper.toDTO(savedVote);
    }

    @Override
    @Transactional
    public void deleteVote(Long answerId, VoteType type, String username) {
        log.debug("Deleting vote for answer: {}, type: {}, username: {}", answerId, type, username);

        Vote vote = voteRepository.findByAnswerIdAndUsernameAndType(answerId, username, type)
                .orElseThrow(() -> new VoteNotFoundException("투표를 찾을 수 없습니다."));

        voteRepository.delete(vote);
    }

    @Override
    public List<VoteStats> getVoteStats(Long answerId) {
        log.debug("Getting vote stats for answer: {}", answerId);
        return voteRepository.getVoteStatsByAnswerId(answerId);
    }

    @Override
    public boolean hasUserVoted(Long answerId, String username, VoteType type) {
        log.debug("Checking if user {} has voted on answer: {} with type: {}", username, answerId, type);
        return voteRepository.existsByAnswerIdAndUsernameAndType(answerId, username, type);
    }

    @Override
    public Map<VoteType, Long> getVoteStatsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.debug("Getting vote stats for date range: {} to {}", startDate, endDate);
        
        List<Object[]> stats = voteRepository.getVoteStatsByDateRange(startDate, endDate);
        Map<VoteType, Long> result = new HashMap<>();
        
        for (Object[] stat : stats) {
            result.put((VoteType) stat[0], (Long) stat[1]);
        }
        
        return result;
    }

    @Override
    public List<VoteDTO> getUserVoteHistory(String username) {
        log.debug("Getting vote history for user: {}", username);
        List<Vote> votes = voteRepository.findByUsername(username);
        return voteMapper.toDTOList(votes);
    }

    @Override
    public List<VoteDTO> getVotesByAnswer(Long answerId) {
        log.debug("Getting votes for answer: {}", answerId);
        List<Vote> votes = voteRepository.findByAnswerIdOrderByCreatedAtDesc(answerId);
        return voteMapper.toDTOList(votes);
    }

    @Override
    public Map<VoteType, Long> getUserVoteStats(String username) {
        log.debug("Getting vote stats for user: {}", username);
        
        Map<VoteType, Long> stats = new HashMap<>();
        stats.put(VoteType.UPVOTE, voteRepository.countUpvotesByUsername(username));
        stats.put(VoteType.DOWNVOTE, voteRepository.countDownvotesByUsername(username));
        
        return stats;
    }

    @Override
    public Map<String, Long> getVoteSummary(Long answerId) {
        log.debug("Getting vote summary for answer: {}", answerId);
        
        List<Object[]> summary = voteRepository.getVoteSummaryByAnswerId(answerId);
        Map<String, Long> result = new HashMap<>();
        result.put("upvotes", 0L);
        result.put("downvotes", 0L);
        
        for (Object[] stat : summary) {
            VoteType type = (VoteType) stat[0];
            Long count = (Long) stat[1];
            
            if (type == VoteType.UPVOTE) {
                result.put("upvotes", count);
            } else {
                result.put("downvotes", count);
            }
        }
        
        return result;
    }

    @Override
    public List<Long> getVotedAnswerIds(String username, VoteType type) {
        log.debug("Getting voted answer IDs for user: {} and type: {}", username, type);
        return voteRepository.findAnswerIdsByUsernameAndType(username, type);
    }
} 