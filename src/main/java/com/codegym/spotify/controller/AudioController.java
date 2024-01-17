package com.codegym.spotify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("api/audios")
public class AudioController {
    private static final String AUDIO_FOLDER = "/resources/audio";

    @GetMapping("/load")
    public Map<String, String> loadAudio(@RequestParam String audioFile) {
        Map<String, String> response = new HashMap<>();
        response.put("audioUrl", AUDIO_FOLDER + audioFile);
        return response;
    }

//    @GetMapping("/next")
//    public Map<String, String> nextAudio(@RequestParam String audioFile) {
//        loadAudio(audioFile);
//    }
//
//    @GetMapping("/previous")
//    public Map<String, String> nextAudio(@RequestParam String audioFile) {
//        loadAudio(audioFile);
//    }
}
