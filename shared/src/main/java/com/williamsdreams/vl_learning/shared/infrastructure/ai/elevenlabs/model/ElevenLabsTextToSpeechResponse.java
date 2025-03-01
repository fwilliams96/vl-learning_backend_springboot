package com.williamsdreams.vl_learning.shared.infrastructure.ai.elevenlabs.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ElevenLabsTextToSpeechResponse {

    private String text;
    private String model_id;
    private ElevenLabsVoiceSettings voice_settings;

    @Data
    @Builder
    public static class ElevenLabsVoiceSettings {
        private Double stability;
        private Double similarity_boost;
    }

}
