/*
 * Shroud
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
package blue.lapis.nocturne.mapping.model;

import blue.lapis.nocturne.mapping.model.attribute.Type;

/**
 * Represents a {@link Mapping} for a field.
 */
public class FieldMapping extends Mapping implements ClassComponent {

    private final ClassMapping parent;
    private final Type type; //TODO: not necessary (or possible) for SRG mappings so we won't enforce it

    /**
     * Constructs a new {@link FieldMapping} with the given parameters.
     *
     * @param parent The parent {@link ClassMapping}
     * @param obfName The obfuscated name of the field
     * @param deobfName The deobfuscated name of the field
     * @param type The (obfuscated) {@link Type} of the field
     */
    public FieldMapping(ClassMapping parent, String obfName, String deobfName, Type type) {
        super(obfName, deobfName);
        this.parent = parent;
        this.type = type;

        parent.addFieldMapping(this);
    }

    @Override
    public ClassMapping getParent() {
        return parent;
    }

    /**
     * Returns the {@link Type} of this field.
     *
     * @return The {@link Type} of this field
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the deobfuscated {@link Type} of this field.
     *
     * @return The deobfuscated {@link Type} of this field
     */
    public Type getDeobfuscatedType() {
        return getType().deobfuscate();
    }

}