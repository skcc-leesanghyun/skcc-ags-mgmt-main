package com.skcc.ra.common.util;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.HashMap;
import java.util.Map;

public class HtmlCharacterEscapes extends CharacterEscapes {

    private final int[] asciiEscapes;
    private final CharSequenceTranslator translator;

    public HtmlCharacterEscapes() {
        // XSS 방지 처리할 특수문자 지정
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

        // XSS 방지 처리 특수문자 인코딩 값 지정
        Map<CharSequence, CharSequence> CUSTOM_ESCAPE = new HashMap<>();
        CUSTOM_ESCAPE.put("(", "&#40;");
        CUSTOM_ESCAPE.put(")", "&#41;");
        CUSTOM_ESCAPE.put("#", "&#35;");
        CUSTOM_ESCAPE.put("\'", "&#39;");

        translator = new AggregateTranslator(
                new LookupTranslator(EntityArrays.BASIC_ESCAPE),  // " & < >
                new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
                new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE),
                new LookupTranslator(CUSTOM_ESCAPE)
        );
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
//        return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
        return new SerializedString(translator.translate(Character.toString((char) ch)));
    }
}