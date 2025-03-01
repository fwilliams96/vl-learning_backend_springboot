package com.williamsdreams.vl_learning.users.completable_listenings.infrastructure.ai.openai;

import com.williamsdreams.vl_learning.users.completable_listenings.domain.CompletableListening;
import com.williamsdreams.vl_learning.users.completable_listenings.domain.UserCompletableListeningGeneratorGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatGPTUserCompletableListeningGeneratorGateway implements UserCompletableListeningGeneratorGateway {

    private final OpenAiChatModel chatModel;

    @Value("classpath:/prompts/completable_listening_generator.st")
    private Resource completableListeningGeneratorPrompt;


    @Override
    public CompletableListening generateCompletableListening(String topic) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(completableListeningGeneratorPrompt);
        var outputConverter = new BeanOutputConverter<>(CompletableListeningGeneration.class);
        var format = outputConverter.getFormat();

        Message message = systemPromptTemplate.createMessage(
                Map.of("topic", topic, "format", format)
        );
        log.debug("Message sent to chatgpt: {}", message);
        Prompt prompt = new Prompt(
                message,
                OpenAiChatOptions.builder()
                        .withModel("gpt-4o-mini")
                        .withTemperature(0.8f)
                        .withTopP(0.7f)
//                        .withMaxTokens(500)
                        .build()
        );        ChatResponse call = chatModel.call(prompt);
        String response = call.getResult().getOutput().getContent();
        CompletableListeningGeneration completableListeningGeneration = outputConverter.convert(response);
        if (completableListeningGeneration == null) {
            throw new RuntimeException("Failed to convert response to CompletableListeningGeneration");
        }
        return CompletableListening.builder()
                .title(completableListeningGeneration.getTitle())
                .text(completableListeningGeneration.getText())
                .build();
    }
}
