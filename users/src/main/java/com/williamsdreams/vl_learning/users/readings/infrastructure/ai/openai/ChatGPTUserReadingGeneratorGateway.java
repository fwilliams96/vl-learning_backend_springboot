package com.williamsdreams.vl_learning.users.readings.infrastructure.ai.openai;

import com.williamsdreams.vl_learning.users.readings.domain.Reading;
import com.williamsdreams.vl_learning.users.readings.domain.UserReadingGeneratorGateway;
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
public class ChatGPTUserReadingGeneratorGateway implements UserReadingGeneratorGateway {

    private final OpenAiChatModel chatModel;

    @Value("classpath:/prompts/reading_generator.st")
    private Resource readingGeneratorPrompt;

    @Override
    public Reading generate(String topic) {
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(readingGeneratorPrompt);
        var outputConverter = new BeanOutputConverter<>(ReadingGeneration.class);
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
        ReadingGeneration readingGeneration = outputConverter.convert(response);
        if (readingGeneration == null) {
            throw new RuntimeException("Failed to convert response to ReadingGeneration");
        }
        return Reading.builder()
                .title(readingGeneration.getTitle())
                .text(readingGeneration.getText())
                .questions(mapQuestions(readingGeneration.getQuestions()))
                .build();
    }

    private List<Question> mapQuestions(List<ReadingGeneration.ReadingGenerationQuestion> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            return Collections.emptyList();
        }
        return questions.stream()
                .map(this::mapQuestion)
                .toList();
    }

    private Question mapQuestion(ReadingGeneration.ReadingGenerationQuestion question) {
        return Question.builder()
                .question(question.getQuestion())
                .options(mapQuestionOptions(question.getOptions()))
                .build();
    }

    private List<QuestionOption> mapQuestionOptions(List<ReadingGeneration.ReadingGenerationOption> options) {
        if (CollectionUtils.isEmpty(options)) {
            return Collections.emptyList();
        }
        return options.stream()
                .map(this::mapQuestionOption)
                .toList();
    }

    private QuestionOption mapQuestionOption(ReadingGeneration.ReadingGenerationOption option) {
        return QuestionOption.builder()
            .text(option.getText())
            .correct(option.isCorrect())
            .build();
    }
}
