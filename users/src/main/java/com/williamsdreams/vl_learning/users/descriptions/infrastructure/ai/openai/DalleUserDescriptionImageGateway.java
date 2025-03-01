package com.williamsdreams.vl_learning.users.descriptions.infrastructure.ai.openai;

import com.williamsdreams.vl_learning.shared.domain.Image;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionImageGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@Slf4j
public class DalleUserDescriptionImageGateway implements UserDescriptionImageGateway {

    private final ImageModel imageModel;
    private final WebClient webClient;

    public DalleUserDescriptionImageGateway(ImageModel imageModel, WebClient.Builder webClientBuilder) {
        this.imageModel = imageModel;
        this.webClient = webClientBuilder.build();
    }

//    @Value("classpath:/prompts/image_generator.st")
//    private Resource imageGenerationPrompt;

    private static final String IMAGE_GENERATION_PROMPT = "Generate an image based on the following topic: %s";

    @Override
    public Image generateImage(String topic) {
        String message = String.format(IMAGE_GENERATION_PROMPT, topic);
        log.debug("Message sent to chatgpt: {}", message);
        ImagePrompt imagePrompt = new ImagePrompt(
                message,
                OpenAiImageOptions.builder()
                        .withModel("dall-e-3")
                        .withWidth(1024)
                        .withHeight(1024)
                        .withN(1)
                        .withResponseFormat("b64_json")
                        .build()
        );
        ImageResponse imageResponse = imageModel.call(imagePrompt);
        String imageb64 = resolveImageContent(imageResponse);
        /*byte[] imageBytes = webClient.get()
                .uri(imageUrl)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        if (imageBytes == null) {
            throw new RuntimeException("Failed to generate image");
        }*/
        // TODO store image in S3
        //String imageb64 = new String(Base64.getEncoder().encode(imageBytes), StandardCharsets.UTF_8);
        return Image.builder()
                .content(imageb64)
                .build();
    }

    private String resolveImageContent(ImageResponse imageResponse) {
        org.springframework.ai.image.Image output = imageResponse.getResult().getOutput();
        return Optional
                .ofNullable(output.getUrl())
                .orElseGet(output::getB64Json);
    }
}
