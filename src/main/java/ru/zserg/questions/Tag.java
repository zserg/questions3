package ru.zserg.questions;

import lombok.Getter;

@Getter
public class Tag {
    private final String tag;
    private final boolean selected;

    public Tag(String tag, boolean selected) {
        this.tag = tag;
        this.selected = selected;
    }
}