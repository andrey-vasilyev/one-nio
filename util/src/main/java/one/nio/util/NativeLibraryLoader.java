/*
 * Copyright 2025 VK
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package one.nio.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;

public final class NativeLibraryLoader {
    private static final Logger log = LoggerFactory.getLogger(NativeLibraryLoader.class);

    public static boolean loadNativeLibrary(String libraryName) {
        String libraryFilename = toLibraryFilename(libraryName);
        return loadNativeLibrary(NativeLibraryLoader.class.getResourceAsStream("/" + libraryFilename), libraryFilename);
    }

    public static boolean loadNativeLibrary(InputStream in, String libraryFilename) {
        try {
            if (in == null) {
                log.error("Cannot find native IO library");
                return false;
            }

            ByteArrayBuilder libData = readStream(in);
            in.close();

            String tmpDir = System.getProperty("java.io.tmpdir", "/tmp");
            File dll = new File(tmpDir, nameOnly(libraryFilename) + "." + crc32(libData) + "." + extOnly(libraryFilename));
            if (!dll.exists() || dll.length() != libData.length() && dll.delete()) {
                FileOutputStream out = new FileOutputStream(dll);
                out.write(libData.buffer(), 0, libData.length());
                out.close();
            }

            String libraryPath = dll.getAbsolutePath();
            System.load(libraryPath);
            return true;
        } catch (Throwable e) {
            log.error("Cannot load native IO library", e);
            return false;
        }
    }

    private static ByteArrayBuilder readStream(InputStream in) throws IOException {
        byte[] buffer = new byte[64000];
        ByteArrayBuilder builder = new ByteArrayBuilder(buffer.length);
        for (int bytes; (bytes = in.read(buffer)) > 0; ) {
            builder.append(buffer, 0, bytes);
        }
        return builder;
    }

    private static String crc32(ByteArrayBuilder builder) {
        CRC32 crc32 = new CRC32();
        crc32.update(builder.buffer(), 0, builder.length());
        return Hex.toHex((int) crc32.getValue());
    }

    private static String toLibraryFilename(String libraryName) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return libraryName + ".dll";
        } else if (osName.contains("mac")) {
            return toUnixConvention(libraryName, ".dylib");
        }
        return toUnixConvention(libraryName, ".so");
    }

    private static String toUnixConvention(String libraryName, String suffix) {
        if (libraryName.endsWith(suffix)) {
            return libraryName;
        }
        return "lib" + libraryName + suffix;
    }

    private static String nameOnly(String filename) {
        int dotIdx = filename.lastIndexOf('.');
        if (dotIdx >= 0) {
            return filename.substring(0, dotIdx);
        }
        return filename;
    }

    private static String extOnly(String filename) {
        int dotIdx = filename.lastIndexOf('.');
        if (dotIdx >= 0) {
            return filename.substring(dotIdx + 1);
        }
        return "";
    }


    private NativeLibraryLoader() {
    }
}
