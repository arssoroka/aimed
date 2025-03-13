package com.ai_omed.smart_care.service;

import com.ai_omed.smart_care.service.dto.RecurringPattern;
import com.ai_omed.smart_care.service.util.DateUtil;
import com.ai_omed.smart_care.service.util.RecurringPatternParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class RecurringPatternParserTest {

    @Test
    public void itShouldParseOneDay(){
        String str = "every day at 08:00";
        LocalDateTime with = LocalDateTime.now().with(LocalDateTime.MIN);

        List<RecurringPattern> parseResult = RecurringPatternParser.parse(str);

        Assertions.assertEquals(1, parseResult.size());
        Assertions.assertEquals(0, parseResult.get(0).getDayOfWeek());
        Assertions.assertEquals( parseResult.get(0).getStartTime(), DateUtil.parseTime("08:00"));
        Assertions.assertEquals( parseResult.get(0).getEndTime(), DateUtil.parseTime("08:00"));
    }

    @Test
    public void itShouldParseTwoDays(){
        String str = "every Monday at 08:00 and 18:00";

        List<RecurringPattern> parseResult = RecurringPatternParser.parse(str);

        Assertions.assertEquals(2, parseResult.size());
        Assertions.assertEquals(1, parseResult.get(0).getDayOfWeek());
        Assertions.assertEquals( parseResult.get(0).getStartTime(), DateUtil.parseTime("08:00"));
        Assertions.assertEquals( parseResult.get(0).getEndTime(), DateUtil.parseTime("08:00"));
        Assertions.assertEquals( parseResult.get(1).getStartTime(), DateUtil.parseTime("18:00"));
        Assertions.assertEquals( parseResult.get(1).getEndTime(), DateUtil.parseTime("18:00"));
    }

    @Test
    public void itShouldParseOneDayRange(){
        String str = "every day from 08:00 to 18:00";

        List<RecurringPattern> parseResult = RecurringPatternParser.parse(str);

        Assertions.assertEquals(1, parseResult.size());
        Assertions.assertEquals(0, parseResult.get(0).getDayOfWeek());
        Assertions.assertEquals( parseResult.get(0).getStartTime(), DateUtil.parseTime("08:00"));
        Assertions.assertEquals( parseResult.get(0).getEndTime(), DateUtil.parseTime("18:00"));
    }

    @Test
    public void itShouldParseTwoDaysRange(){
        String str = "every Friday from 08:00 to 18:00 and from 20:00 to 21:00";

        List<RecurringPattern> parseResult = RecurringPatternParser.parse(str);

        Assertions.assertEquals(2, parseResult.size());
        Assertions.assertEquals(5, parseResult.get(0).getDayOfWeek());
        Assertions.assertEquals( parseResult.get(0).getStartTime(), DateUtil.parseTime("08:00"));
        Assertions.assertEquals( parseResult.get(0).getEndTime(), DateUtil.parseTime("18:00"));

        Assertions.assertEquals( parseResult.get(1).getStartTime(), DateUtil.parseTime("20:00"));
        Assertions.assertEquals( parseResult.get(1).getEndTime(), DateUtil.parseTime("21:00"));
    }

}