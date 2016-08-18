/*
 * Nocturne
 * Copyright (c) 2015-2016, Lapis <https://github.com/LapisBlue>
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

package blue.lapis.nocturne.mapping.io.reader;

import blue.lapis.nocturne.mapping.MappingContext;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * The mappings reader for the POMF format.
 */
public class PomfReader extends EnigmaReader {

    public PomfReader(Path mappingsPath) throws IOException {
        super(readPomfMappings(mappingsPath));
    }

    @Override
    public MappingContext read(MappingContext mappings) {
        return super.read(mappings);
    }

    private static BufferedReader readPomfMappings(Path mappingsPath) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(out);

        Files.walk(mappingsPath).filter(Files::isRegularFile).forEach(path -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
                reader.lines().collect(Collectors.toList()).forEach(writer::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
        writer.close();
        out.close();
        return new BufferedReader(new InputStreamReader(input));
    }
}