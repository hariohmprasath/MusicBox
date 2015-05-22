package com.personal.music.test;

import com.personal.music.Crawler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hrajagopal on 5/18/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-appContext.xml"})
public class TestCrawler {

    @Autowired
    private Crawler crawler;

    @Test
    public void testCrawler() {
        crawler.parseAndIndexData();
    }

}
