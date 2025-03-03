package com.williamsdreams.vl_learning.app.controllers;

import com.williamsdreams.vl_learning.users.descriptions.application.evaluate.UserDescriptionEvaluator;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionEvaluation;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionProposal;
import com.williamsdreams.vl_learning.app.api.UserDescriptionEvaluationPostV1Api;
import com.williamsdreams.vl_learning.app.api.dto.UserDescriptionEvaluationDto;
import com.williamsdreams.vl_learning.app.api.dto.UserDescriptionProposalDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserDescriptionEvaluationPostV1Controller implements UserDescriptionEvaluationPostV1Api {

    private final UserDescriptionEvaluator userDescriptionEvaluator;

    @Override
    public ResponseEntity<UserDescriptionEvaluationDto> createUserDescriptionEvaluation(UUID userId, UUID descriptionId, UserDescriptionProposalDto userDescriptionProposalDto) {
        UserDescriptionEvaluation evaluate = userDescriptionEvaluator.evaluate(userId, descriptionId, mapToUserDescriptionProposal(userDescriptionProposalDto));
        return ResponseEntity.ok(mapToUserDescriptionEvaluationDto(evaluate));
    }

    private UserDescriptionEvaluationDto mapToUserDescriptionEvaluationDto(UserDescriptionEvaluation evaluate) {
        UserDescriptionEvaluationDto userDescriptionEvaluationDto = new UserDescriptionEvaluationDto();
        userDescriptionEvaluationDto.setFeedback(evaluate.getFeedback());
        userDescriptionEvaluationDto.setScore(evaluate.getScore());
        userDescriptionEvaluationDto.setProposedDescription(evaluate.getProposedDescription());
        return userDescriptionEvaluationDto;
    }

    private UserDescriptionProposal mapToUserDescriptionProposal(UserDescriptionProposalDto userDescriptionProposalDto) {
        return UserDescriptionProposal.builder()
                .description(userDescriptionProposalDto.getDescription())
                .build();
    }
}
