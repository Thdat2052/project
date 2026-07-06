package com.example.fileservice.services.excels;

import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class BaseFileService<T> {
    protected abstract InputStreamResource writeFile(List<T> data) throws IOException;
}
