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

package blue.lapis.nocturne.mapping.io.writer;

import blue.lapis.nocturne.mapping.MappingContext;
import blue.lapis.nocturne.mapping.model.ClassMapping;
import blue.lapis.nocturne.mapping.model.FieldMapping;
import blue.lapis.nocturne.mapping.model.MethodMapping;
import blue.lapis.nocturne.mapping.model.TopLevelClassMapping;

import java.io.PrintWriter;

/**
 * The mappings writer, for the Enigma format.
 */
public class EnigmaWriter extends MappingsWriter {

    /**
     * Constructs a new {@link EnigmaWriter} which outputs to the given
     * {@link PrintWriter}.
     *
     * @param outputWriter The {@link PrintWriter} to output to
     */
    public EnigmaWriter(PrintWriter outputWriter) {
        super(outputWriter);
    }

    @Override
    public void write(MappingContext mappings) {
        for (TopLevelClassMapping classMapping : mappings.getMappings().values()) {
            this.writeClassMapping(classMapping, 0);
        }
    }

    protected void writeClassMapping(ClassMapping classMapping, int depth) {
        if (classMapping.getDeobfuscatedName().equals(classMapping.getObfuscatedName())) {
            out.println(getIndentForDepth(depth) + "CLASS " + classMapping.getObfuscatedName());
        } else {
            out.println(getIndentForDepth(depth) + "CLASS " + classMapping.getObfuscatedName() + " " + classMapping.getDeobfuscatedName());
        }

        for (ClassMapping innerClass : classMapping.getInnerClassMappings().values()) {
            this.writeClassMapping(innerClass, depth + 1);
        }

        for (FieldMapping fieldMapping : classMapping.getFieldMappings().values()) {
            this.writeFieldMapping(fieldMapping, depth + 1);
        }

        for (MethodMapping methodMapping : classMapping.getMethodMappings().values()) {
            this.writeMethodMapping(methodMapping, depth + 1);
        }
    }

    protected void writeFieldMapping(FieldMapping fieldMapping, int depth) {
        out.println(getIndentForDepth(depth) + "FIELD " + fieldMapping.getObfuscatedName() + " " + fieldMapping.getDeobfuscatedName() + " "
                + fieldMapping.getType().toString());
    }

    protected void writeMethodMapping(MethodMapping methodMapping, int depth) {
        if (methodMapping.getDeobfuscatedName().equals(methodMapping.getObfuscatedName())) {
            out.println(getIndentForDepth(depth) + "METHOD " + methodMapping.getObfuscatedName() + " "
                    + methodMapping.getObfuscatedDescriptor().toString());
        } else {
            out.println(getIndentForDepth(depth) + "METHOD " + methodMapping.getObfuscatedName() + " " + methodMapping.getDeobfuscatedName() + " "
                    + methodMapping.getObfuscatedDescriptor().toString());
        }
        // TODO: Support argument mappings
    }

    private String getIndentForDepth(int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append("\t");
        }
        return builder.toString();
    }
}
