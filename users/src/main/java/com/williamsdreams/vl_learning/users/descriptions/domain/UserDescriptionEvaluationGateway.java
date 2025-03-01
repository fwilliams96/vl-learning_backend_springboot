package com.williamsdreams.vl_learning.users.descriptions.domain;

import com.williamsdreams.vl_learning.users.shared.domain.Image;

public interface UserDescriptionEvaluationGateway {

    UserDescriptionEvaluation evaluate(Image image, UserDescriptionProposal userDescriptionProposal);

}
