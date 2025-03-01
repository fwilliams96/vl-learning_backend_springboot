package com.williamsdreams.vl_learning.users.descriptions.application.evaluate;

import com.williamsdreams.vl_learning.users.descriptions.application.find.UserDescriptionFinder;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescription;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionEvaluation;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionEvaluationGateway;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionProposal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDescriptionEvaluator {

    private final UserDescriptionFinder userDescriptionFinder;
    private final UserDescriptionEvaluationGateway userDescriptionEvaluationGateway;

    public UserDescriptionEvaluation evaluate(UUID userId, UUID descriptionId, UserDescriptionProposal userDescriptionProposal) {
        Optional<UserDescription> userDescriptionOpt = userDescriptionFinder.find(userId, descriptionId);
        if (userDescriptionOpt.isEmpty()) {
            throw new RuntimeException("User description not found");
        }
        UserDescription userDescription = userDescriptionOpt.get();
        return userDescriptionEvaluationGateway.evaluate(userDescription.getImage(), userDescriptionProposal);
    }

}
