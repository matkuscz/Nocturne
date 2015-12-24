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
package blue.lapis.nocturne.util.helper;

import static blue.lapis.nocturne.util.Constants.INNER_CLASS_SEPARATOR_CHAR;
import static blue.lapis.nocturne.util.Constants.INNER_CLASS_SEPARATOR_PATTERN;

import blue.lapis.nocturne.Main;
import blue.lapis.nocturne.jar.model.attribute.MethodDescriptor;
import blue.lapis.nocturne.mapping.MappingContext;
import blue.lapis.nocturne.mapping.model.ClassMapping;
import blue.lapis.nocturne.mapping.model.FieldMapping;
import blue.lapis.nocturne.mapping.model.InnerClassMapping;
import blue.lapis.nocturne.mapping.model.MethodMapping;
import blue.lapis.nocturne.mapping.model.TopLevelClassMapping;
import blue.lapis.nocturne.util.Constants;

import java.util.Optional;

/**
 * Static utility class for assisting with mapping retrieval and creation.
 */
public class MappingsHelper {

    public static void genClassMapping(MappingContext context, String obf, String deobf) {
        if (obf.contains(INNER_CLASS_SEPARATOR_CHAR + "")) {
            String[] obfSplit = INNER_CLASS_SEPARATOR_PATTERN.split(obf);
            String[] deobfSplit = INNER_CLASS_SEPARATOR_PATTERN.split(deobf);
            if (obfSplit.length != deobfSplit.length) { // non-inner mapped to inner or vice versa
                Main.getLogger().warning("Unsupported mapping: " + obf + " <-> " + deobf);
                return; // ignore it
            }

            // get the direct parent class to this inner class
            ClassMapping parent = getOrCreateClassMapping(context,
                    obf.substring(0, obf.lastIndexOf(INNER_CLASS_SEPARATOR_CHAR)));

            String baseObfName = obfSplit[obfSplit.length - 1];
            String baseDeobfname = deobfSplit[deobfSplit.length - 1];
            if (parent.getInnerClassMappings().containsKey(baseObfName)) {
                parent.getInnerClassMappings().get(obf).setDeobfuscatedName(baseDeobfname);
            } else {
                new InnerClassMapping(parent, baseObfName, baseDeobfname);
            }
        } else {
            if (context.getMappings().containsKey(obf)) {
                context.getMappings().get(obf).setDeobfuscatedName(deobf);
            } else {
                context.addMapping(new TopLevelClassMapping(context, obf, deobf));
            }
        }
    }

    public static void genFieldMapping(MappingContext context, String obf, String deobf) {
        int lastIndex = obf.lastIndexOf(Constants.CLASS_PATH_SEPARATOR_CHAR);
        String owningClass = obf.substring(0, lastIndex);
        String obfName = obf.substring(lastIndex + 1);

        String deobfName = deobf.substring(deobf.lastIndexOf(Constants.CLASS_PATH_SEPARATOR_CHAR) + 1);

        ClassMapping parent = getOrCreateClassMapping(context, owningClass);
        if (parent.getFieldMappings().containsKey(obfName)) {
            parent.getFieldMappings().get(obfName).setDeobfuscatedName(deobfName);
        } else {
            new FieldMapping(parent, obfName, deobfName, null);
        }
    }

    public static void genMethodMapping(MappingContext context, String obf, String obfSig, String deobf,
                                        String deobfSig) {
        int lastIndex = obf.lastIndexOf(Constants.CLASS_PATH_SEPARATOR_CHAR);
        String owningClass = obf.substring(0, lastIndex);
        String obfName = obf.substring(lastIndex + 1);

        String deobfName = deobf.substring(deobf.lastIndexOf(Constants.CLASS_PATH_SEPARATOR_CHAR) + 1);

        ClassMapping parent = getOrCreateClassMapping(context, owningClass);
        if (parent.getMethodMappings().containsKey(obfName)) {
            parent.getMethodMappings().get(obfName).setDeobfuscatedName(deobfName);
        } else {
            new MethodMapping(parent, obfName, deobfName, MethodDescriptor.fromString(obfSig));
        }
    }

    private static Optional<ClassMapping> getClassMapping(MappingContext context, String qualifiedName,
                                                          boolean create) {
        String[] arr = INNER_CLASS_SEPARATOR_PATTERN.split(qualifiedName);

        ClassMapping mapping = context.getMappings().get(arr[0]);
        if (mapping == null) {
            if (create) {
                mapping = new TopLevelClassMapping(context, arr[0], arr[0]);
                context.addMapping((TopLevelClassMapping) mapping);
            } else {
                return Optional.empty();
            }
        }

        for (int i = 1; i < arr.length; i++) {
            ClassMapping child = mapping.getInnerClassMappings().get(arr[i]);
            if (child == null) {
                if (create) {
                    child = new InnerClassMapping(mapping, arr[i], arr[i]);
                } else {
                    return Optional.empty();
                }
            }
            mapping = child;
        }

        return Optional.of(mapping);
    }

    public static Optional<ClassMapping> getClassMapping(MappingContext context, String qualifiedName) {
        return getClassMapping(context, qualifiedName, false);
    }

    /**
     * Gets the {@link ClassMapping} for the given qualified name, iteratively
     * creating mappings for both outer and inner classes as needed if they do
     * not exist.
     *
     * @param context The {@link MappingContext} to use
     * @param qualifiedName The fully-qualified name of the class to get a
     *     mapping for
     * @return The retrieved or created {@link ClassMapping}
     */
    public static ClassMapping getOrCreateClassMapping(MappingContext context, String qualifiedName) {
        return getClassMapping(context, qualifiedName, true).get();
    }



}