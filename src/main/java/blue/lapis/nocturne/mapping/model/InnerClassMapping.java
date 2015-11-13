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

/**
 * Represents a {@link Mapping} for an inner class, i.e. a class parented by
 * another class.
 */
public class InnerClassMapping extends ClassMapping implements ClassComponent {

    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';

    private final ClassMapping parent;

    /**
     * Constructs a new {@link InnerClassMapping} with the given parameters.
     *
     * <p>The name should not include the parent class(es), just the name of the
     * inner class itself.</p>
     *
     * @param parent The parent {@link ClassMapping}
     * @param obfName The obfuscated name of the inner class
     * @param deobfName The deobfuscated name of the inner class
     */
    public InnerClassMapping(ClassMapping parent, String obfName, String deobfName) {
        super(obfName, deobfName);
        this.parent = parent;

        parent.addInnerClassMapping(this);
    }

    @Override
    public ClassMapping getParent() {
        return parent;
    }

    /**
     * Returns the full obfuscated name of this inner class.
     *
     * @return The full obfuscated name of this inner class
     */
    public String getFullObfuscatedName() {
        return (parent instanceof InnerClassMapping
                ? ((InnerClassMapping) parent).getFullObfuscatedName() + INNER_CLASS_SEPARATOR_CHAR : "")
                + getObfuscatedName();
    }

    /**
     * Returns the full deobfuscated name of this inner class.
     *
     * @return The full deobfuscated name of this inner class
     */
    public String getFullDeobfuscatedName() {
        return (parent instanceof InnerClassMapping
                ? ((InnerClassMapping) parent).getFullDeobfuscatedName() + INNER_CLASS_SEPARATOR_CHAR : "")
                + getDeobfuscatedName();
    }

}