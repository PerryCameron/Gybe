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

    public static void main(String[] args) {
        String html = "<p>As your Commodore for 2024, I'm excited to steer us into another great year of sailing. We are lucky to have such an amazing lake right in our backyard, and we are even luckier to have such a strong community and organization that allows us to sail on her every year.</p>"
                + "<p>Some could say that I joined ECSC back in 1992 when I was born (my parents are members), however, I like to think that my membership started in 2018 when I moved back to Indiana to make it my home.  ECSC was one of the things I missed most while living away. I was never more land-locked than when I lived in California (I never sailed that summer). Sailing on the Purdue Sailing Team in college was fun (especially traveling to all the Big 10 Universities) but the team practiced on a small river which left a lot to be desired... While living in Dallas, I got connected into a great Laser sailing community and even raced in some regattas down there but none of it was to the scale of our club here in Indiana. With 275 active memberships (just about 600 total headcount including family members) and a facility that can welcome 300 boats (173 in the water), Eagle Creek Sailing Club is one of the most impressive sailing organizations of its kind.</p>"
                + "<p>My roles & responsibilities at ECSC started when I was quite young. I was the go-to DJ for social events back in the early 2000's. I helped run the Summer Sailing Camp from 2005 to 2011. As an ECSC Junior Race Team member, I traveled all over the country representing ECSC in youth regattas. But the job I'll never forget is helping my dad label and stamp 250 physical newsletters back in the 90's when the Telltales were still paper and mailed out to everyone. (Child labor??). More recently, I've served as the club's publicity chair for the last 5 years where I had the pleasure of keeping everyone informed of club activities and events.</p>"
                + "<p>Now in 2024 I have the opportunity to serve as ECSC's Commodore.  I'm honored to be in this position, and I'm excited at what we are going to accomplish. A huge thank you to all the Committee heads, assistants, and volunteers that put in long hours to make our club run smoothly every year. You all are the reason we can have a wonderful summer full of sailing!</p>";

        String[] result = extractTextBetweenPTags(html);
        for (String paragraph : result) {
            System.out.println(paragraph);
        }
    }
}
