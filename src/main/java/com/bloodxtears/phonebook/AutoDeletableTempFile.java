package com.bloodxtears.phonebook;

import org.springframework.lang.NonNull;

import java.io.File;
import java.io.IOException;

public class AutoDeletableTempFile {
    private final File file;
    public AutoDeletableTempFile(@NonNull String prefix, @NonNull String suffix) throws IOException {
        System.gc();
        file = File.createTempFile(prefix,suffix);
        file.deleteOnExit();
    }

    public File getFile() {
        return file;
    }

    @Override
    protected void finalize() throws Throwable {
        if (file.exists()) {
            file.delete();
        }
        super.finalize();
    }
}
