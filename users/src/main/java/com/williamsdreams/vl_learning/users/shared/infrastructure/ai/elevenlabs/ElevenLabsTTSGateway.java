package com.williamsdreams.vl_learning.users.shared.infrastructure.ai.elevenlabs;

import com.williamsdreams.vl_learning.users.shared.domain.Audio;
import com.williamsdreams.vl_learning.users.shared.domain.TextToSpeechGateway;
import com.williamsdreams.vl_learning.users.shared.infrastructure.ai.elevenlabs.model.ElevenLabsTextToSpeechRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class ElevenLabsTTSGateway implements TextToSpeechGateway {

    private final WebClient elevenLabsWebClient;

    private final String VOICE_ID = "EXAVITQu4vr4xnSDxMaL";
//    private final String VOICE_ID = "letlmoMovQ02JuDYh3CW";

    @Override
    public Audio textToSpeech(String text) {

        byte[] audioBytes = elevenLabsWebClient.post()
                .uri(String.format("/v1/text-to-speech/%s", VOICE_ID))
                .bodyValue(
                        ElevenLabsTextToSpeechRequest.builder()
                                .text(text)
                                .model_id("eleven_multilingual_v2")
                                .voice_settings(
                                        ElevenLabsTextToSpeechRequest.ElevenLabsVoiceSettings.builder()
                                                .stability(0.5)
                                                .similarity_boost(0.75)
                                                .build()
                                )
                                .build()
                )
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
        if (audioBytes == null) {
            throw new RuntimeException("Failed to convert text to speech");
        }
        return Audio.builder()
            .content(new String(Base64.getEncoder().encode(audioBytes), StandardCharsets.UTF_8))
            .build();
    }
}
