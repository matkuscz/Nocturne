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
package blue.lapis.nocturne.mapping.io.reader;

import blue.lapis.nocturne.Main;

import com.google.common.collect.Maps;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Map;

public enum MappingReaderType {

    SRG(new FileChooser.ExtensionFilter(Main.getResourceBundle().getString("filechooser.type_srg"), "*.srg"),
            SrgReader.class),
    ENIGMA(new FileChooser.ExtensionFilter(Main.getResourceBundle().getString("filechooser.type_enigma"), "*.*"),
            EnigmaReader.class);

    private static Map<FileChooser.ExtensionFilter, MappingReaderType> filterToType = Maps.newHashMap();

    static {
        Arrays.asList(values()).forEach(t -> filterToType.put(t.getExtensionFilter(), t));
    }

    private final FileChooser.ExtensionFilter extensionFilter;
    private final Class readerClass;

    MappingReaderType(FileChooser.ExtensionFilter extensionFilter, Class readerClass) {
        this.extensionFilter = extensionFilter;
        this.readerClass = readerClass;
    }

    public FileChooser.ExtensionFilter getExtensionFilter() {
        return this.extensionFilter;
    }

    public MappingsReader constructReader(BufferedReader reader) {
        try {
            return (MappingsReader) this.readerClass.getConstructor(BufferedReader.class).newInstance(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static MappingReaderType fromExtensionFilter(FileChooser.ExtensionFilter filter) {
        return filterToType.get(filter);
    }
}
