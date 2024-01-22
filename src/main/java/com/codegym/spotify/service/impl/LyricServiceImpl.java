package com.codegym.spotify.service.impl;

import com.codegym.spotify.service.LyricService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LyricServiceImpl implements LyricService {
    @Override
    public List<String> getSongLyrics(String band, String songTitle) {
        String songLyricUrl = "http://www.songlyrics.com";
        List<String> lyrics = new ArrayList<>();
        try {
            Document document = Jsoup.connect(
                    songLyricUrl + "/"
                            + band.replace(" ", "-").toLowerCase()
                            + "/" + songTitle.replace(" ", "-").toLowerCase()
                            + "-lyrics/").get();
            String title = document.title();
            System.out.println(title);
            Element p =document.select("p.songLyricsV14").get(0);
            for(Node e: p.childNodes()){
                if(e instanceof TextNode) {
                    lyrics.add(((TextNode)e).getWholeText());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lyrics;
    }
}
