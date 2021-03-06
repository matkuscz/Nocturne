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

package blue.lapis.nocturne.gui.scene.control;

import blue.lapis.nocturne.Main;
import blue.lapis.nocturne.util.OperatingSystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Hyperlink;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Represents a {@link Hyperlink} to a web resource.
 */
public class WebLink extends Hyperlink {

    private final StringProperty addressProperty = new SimpleStringProperty(this, "url");

    public WebLink(String address) {
        this();
        this.setText(address);
        this.addressProperty.set(address);
    }

    public WebLink() {
        setOnAction(event -> {
            String url = getAddress();

            try { // First try the JavaFX way to open URLs (doesn't seem to be supported everywhere)
                Main.getInstance().getHostServices().showDocument(url);
                return;
            } catch (Throwable ignored) {
                // shut up, checkstyle
            }

            try {
                if (OperatingSystem.getOs() == OperatingSystem.LINUX
                        && (new File("/usr/bin/xdg-open").exists() || new File("/usr/local/bin/xdg-open").exists())) {
                    // Work-around to support non-GNOME Linux desktop environments with xdg-open installed
                    new ProcessBuilder("xdg-open", url).start();
                } else if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(url));
                } else {
                    Main.getLogger().warning("Could not open hyperlink (not supported)");
                }
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public final StringProperty addressProperty() {
        return addressProperty;
    }

    public final String getAddress() {
        return addressProperty.get();
    }

    public final void setAddress(String value) {
        addressProperty.set(value);
    }

}
