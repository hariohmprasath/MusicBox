package com.personal.music.parser;

import com.personal.music.pojo.SplitterType;

import java.util.Map;

/**
 * Created by hrajagopal on 5/19/15.
 */
public class SplitterConfiguration {
    private SplitterType splitterType;
    private String splitterValue;
    private Map<String, String> splitterOptions;

    public SplitterType getSplitterType() {
        return splitterType;
    }

    public void setSplitterType(SplitterType splitterType) {
        this.splitterType = splitterType;
    }

    public String getSplitterValue() {
        return splitterValue;
    }

    public void setSplitterValue(String splitterValue) {
        this.splitterValue = splitterValue;
    }

    public Map<String, String> getSplitterOptions() {
        return splitterOptions;
    }

    public void setSplitterOptions(Map<String, String> splitterOptions) {
        this.splitterOptions = splitterOptions;
    }
}
