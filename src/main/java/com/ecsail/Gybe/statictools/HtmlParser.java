package com.ecsail.Gybe.statictools;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {

    public static String[] extractTextBetweenPTags(String html) {
        ArrayList<String> paragraphs = new ArrayList<>();
        Pattern pattern = Pattern.compile("<p>(.*?)</p>");
        Matcher matcher = pattern.matcher(html);

        while (matcher.find()) {
            paragraphs.add(matcher.group(1));
        }

        return paragraphs.toArray(new String[0]);
    }

}
