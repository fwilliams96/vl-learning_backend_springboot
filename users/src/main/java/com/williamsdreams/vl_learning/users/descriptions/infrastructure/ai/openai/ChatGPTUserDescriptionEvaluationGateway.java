package com.williamsdreams.vl_learning.users.descriptions.infrastructure.ai.openai;

import com.williamsdreams.vl_learning.shared.domain.Image;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionEvaluation;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionEvaluationGateway;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionProposal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatGPTUserDescriptionEvaluationGateway implements UserDescriptionEvaluationGateway {

    private final OpenAiChatModel chatModel;

    @Value("classpath:/prompts/description_evaluator.st")
    private Resource descriptionEvaluatorPrompt;

    private static final String IMAGE_MESSAGE = "Here is the image link: %s";
    private static final String USER_MESSAGE = "Please evaluate the following description taking into the account the image sent: %s";

    @Override
    public UserDescriptionEvaluation evaluate(Image image, UserDescriptionProposal userDescriptionProposal) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(descriptionEvaluatorPrompt);
        var outputConverter = new BeanOutputConverter<>(DescriptionEvaluation.class);
        var format = outputConverter.getFormat();

        Message systemMessage = systemPromptTemplate.createMessage(
                Map.of("format", format)
        );

        Resource imageResource;
        try {
            imageResource = saveBase64ImageToTmp(image.getContent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image to /tmp");
        }

        Message imageMessage = new UserMessage(String.format(IMAGE_MESSAGE, new Media(MimeTypeUtils.IMAGE_PNG, imageResource)));
        Message userMessage = new UserMessage(String.format(USER_MESSAGE, userDescriptionProposal.getDescription()));
        log.debug("Message sent to chatgpt: {}", systemMessage);
        Prompt prompt = new Prompt(
                List.of(systemMessage, imageMessage, userMessage),
                OpenAiChatOptions.builder()
                        .withModel("gpt-4o-mini")
                        .withTemperature(0.8f)
                        .withTopP(0.7f)
//                        .withMaxTokens(500)
                        .build()
        );
        ChatResponse call = chatModel.call(prompt);
        String response = call.getResult().getOutput().getContent();
        DescriptionEvaluation descriptionEvaluation = outputConverter.convert(response);
        if (descriptionEvaluation == null) {
            throw new RuntimeException("Failed to convert response to DescriptionEvaluation");
        }
        return UserDescriptionEvaluation.builder()
                .feedback(descriptionEvaluation.getFeedback())
                .score(descriptionEvaluation.getScore())
                .proposedDescription(descriptionEvaluation.getProposedDescription())
                .build();
    }

    public Resource saveBase64ImageToTmp(String base64Image) throws IOException {
        // Generar un nombre de archivo único usando la hora actual y un UUID
        String uniqueFileName = "image_" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ".png";
        File tempFile = new File("/tmp/" + uniqueFileName);

        // Decodificar la imagen en Base64 a un arreglo de bytes
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        // Guardar los bytes en el archivo
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(imageBytes);
        }

        // Retornar el recurso que apunta al archivo guardado
        return new FileSystemResource(tempFile);
    }
}
