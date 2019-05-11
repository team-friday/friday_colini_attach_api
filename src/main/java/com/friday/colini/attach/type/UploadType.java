package com.friday.colini.attach.type;

import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

// TODO: upload and download file 
public enum UploadType {
    Local {
        @Override
        public @NonNull byte[] download(@NonNull final String filePath) {
            return new byte[0];
        }

        @Override
        public @NonNull String upload(@NonNull final MultipartFile file) {
            return "";
        }
    },
    S3 {
        @Override
        public @NonNull byte[] download(@NonNull final String filePath) {
            return new byte[0];
        }

        @Override
        public @NonNull String upload(@NonNull final MultipartFile file) {
            return "";
        }
    },

    ;

    //
    //
    //

    public abstract @NonNull byte[] download(@NonNull final String filePath);
    public abstract @NonNull String upload(@NonNull final MultipartFile file);
}
