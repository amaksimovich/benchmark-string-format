/* 
 * Copyright 2018 Aleh Maksimovich.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jroast.benchmark.stringformat;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.slf4j.helpers.MessageFormatter;
import org.stringtemplate.v4.ST;

import java.text.MessageFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Benchmarking different approaches to concatenating two strings separated by
 * hyphen.
 *
 * The pattern under test is {@code string1 + "-" + string2}.
 *
 * @author Aleh Maksimovich
 * @since 1.0
 */
public class ConcatTwoStringsViaHyphen {

    @Benchmark
    public String stringBuilderAppend(DataSource data) {
        return new StringBuilder()
                .append(data.getFirstString())
                .append("-")
                .append(data.getSecondString())
                .toString();
    }

    @Benchmark
    public String stringBufferAppend(DataSource data) {
        return new StringBuffer()
                .append(data.getFirstString())
                .append("-")
                .append(data.getSecondString())
                .toString();
    }

    @Benchmark
    public String stringPlus(DataSource data) {
        return data.getFirstString() + "-" + data.getSecondString();
    }

    @Benchmark
    public String stringConcat(DataSource data) {
        return data.getFirstString()
                .concat("-")
                .concat(data.getSecondString());
    }

    @Benchmark
    public String stringStaticFormat(DataSource data) {
        return String.format("%s-%s", data.getFirstString(), data.getSecondString());
    }

    @Benchmark
    public String formatterFormat(DataSource data) {
        StringBuilder buffer = new StringBuilder();
        Formatter formatter = new Formatter(buffer);
        formatter.format("%s-%s", data.getFirstString(), data.getSecondString());
        return buffer.toString();
    }

    @Benchmark
    public String messageFormatStaticFormat(DataSource data) {
        return MessageFormat.format("{0}-{1}", data.getFirstString(), data.getSecondString());
    }

    @Benchmark
    public String messageFormatCachedFormat(DataSource data, MessageFormatCache state) {
        MessageFormat messageFormat = state.getCachedFormat();
        return messageFormat.format(new Object[]{data.getFirstString(), data.getSecondString()});
    }

    @Benchmark
    public String strSubstitutorFormat(DataSource data) {
        Map<String, String> valuesMap = new HashMap();
        valuesMap.put("string1", data.getFirstString());
        valuesMap.put("string2", data.getSecondString());

        StrSubstitutor formatter = new StrSubstitutor(valuesMap);
        String template = "${string1}-${string2}";
        return formatter.replace(template);
    }

    @Benchmark
    public String stringTemplateRender(DataSource data) {
        ST template = new ST("<string1>-<string2>");
        template.add("string1", data.getFirstString());
        template.add("string2", data.getSecondString());
        return template.render();
    }

    @Benchmark
    public String messageFormatterStaticFormat(DataSource data) {
        return MessageFormatter
                .format("{}-{}", data.getFirstString(), data.getSecondString())
                .getMessage();
    }

    @State(Scope.Benchmark)
    public static class DataSource {

        private final String firstString = "first";
        private final String secondString = "second";

        public String getFirstString() {
            return firstString;
        }

        public String getSecondString() {
            return secondString;
        }

    }

    @State(Scope.Benchmark)
    public static class MessageFormatCache {

        private MessageFormat cachedFormat;

        @Setup(Level.Iteration)
        public void doSetup() {
            cachedFormat = new MessageFormat("{0}-{1}");
        }

        public MessageFormat getCachedFormat() {
            return cachedFormat;
        }

    }

}
