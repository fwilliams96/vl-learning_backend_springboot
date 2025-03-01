package com.williamsdreams.vl_learning.users.listenings.infrastructure.ai.openai;

import com.williamsdreams.vl_learning.users.listenings.domain.Listening;
import com.williamsdreams.vl_learning.users.listenings.domain.UserListeningGeneratorGateway;
import com.williamsdreams.vl_learning.users.shared.domain.Question;
import com.williamsdreams.vl_learning.users.shared.domain.QuestionOption;
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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatGPTUserListeningGeneratorGateway implements UserListeningGeneratorGateway {

    private final OpenAiChatModel chatModel;

    @Value("classpath:/prompts/listening_generator.st")
    private Resource listeningGeneratorPrompt;

    @Override
    public Listening generateListening(String topic) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(listeningGeneratorPrompt);
        var outputConverter = new BeanOutputConverter<>(ListeningGeneration.class);
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
        );
        ChatResponse call = chatModel.call(prompt);
        String response = call.getResult().getOutput().getContent();
        ListeningGeneration listeningGeneration = outputConverter.convert(response);
        if (listeningGeneration == null) {
            throw new RuntimeException("Failed to convert response to ListeningGeneration");
        }
        return Listening.builder()
                .title(listeningGeneration.getTitle())
                .text(listeningGeneration.getText())
                .questions(mapQuestions(listeningGeneration.getQuestions()))
                .build();
    }

    private List<Question> mapQuestions(List<ListeningGeneration.ListeningGenerationQuestion> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(this::mapQuestion)
                .toList();
    }

    private Question mapQuestion(ListeningGeneration.ListeningGenerationQuestion question) {
        return Question.builder()
                .question(question.getQuestion())
                .options(mapQuestionOptions(question.getOptions()))
                .build();
    }

    private List<QuestionOption> mapQuestionOptions(List<ListeningGeneration.ListeningGenerationOption> options) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(this::mapQuestionOption)
                .toList();
    }

    private QuestionOption mapQuestionOption(ListeningGeneration.ListeningGenerationOption option) {
        return QuestionOption.builder()
                .text(option.getText())
                .correct(option.isCorrect())
                .build();
    }

}
