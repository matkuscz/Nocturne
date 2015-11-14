/*
 * Nocturne
 * Copyright (c) 2015, Lapis <https://github.com/LapisBlue>
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package blue.lapis.nocturne.test;

import blue.lapis.nocturne.mapping.MappingSet;
import blue.lapis.nocturne.mapping.io.reader.SrgReader;
import blue.lapis.nocturne.mapping.model.ClassMapping;

import jdk.nashorn.api.scripting.URLReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;

/**
 * Unit tests related to the {@link SrgReader}.
 */
public class ReaderTests {

    @Test
    public void classTest() {
        SrgReader reader =
                new SrgReader(new BufferedReader(new URLReader(ClassLoader.getSystemResource("example.srg"))));
        MappingSet mappings = reader.read();

        Assert.assertTrue(mappings.getMappings().containsKey("a"));
    }

    @Test
    public void innerClassTest() {
        SrgReader reader =
                new SrgReader(new BufferedReader(new URLReader(ClassLoader.getSystemResource("example.srg"))));
        MappingSet mappings = reader.read();

        ClassMapping mapping = mappings.getMappings().get("a");

        Assert.assertTrue(mapping.getInnerClassMappings().containsKey("b"));
    }
}