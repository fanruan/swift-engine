package com.fr.swift.file.system;

import com.fr.swift.file.exception.SwiftFileException;

import java.io.Closeable;
import java.io.InputStream;
import java.net.URI;

/**
 * @author yee
 * @date 2018/5/28
 */
public interface SwiftFileSystem extends Closeable {
    void write(URI remote, InputStream inputStream) throws SwiftFileException;

    void write(InputStream inputStream) throws SwiftFileException;

    SwiftFileSystem read(URI remote) throws SwiftFileException;

    SwiftFileSystem read() throws SwiftFileException;

    SwiftFileSystem parent();

    boolean remove(URI remote) throws SwiftFileException;

    boolean remove() throws SwiftFileException;

    boolean renameTo(URI src, URI dest) throws SwiftFileException;

    boolean renameTo(URI dest) throws SwiftFileException;

    boolean copy(URI src, URI dest) throws SwiftFileException;

    boolean copy(URI dest) throws SwiftFileException;

    SwiftFileSystem[] listFiles() throws SwiftFileException;

    boolean isExists();

    boolean isDirectory();

    InputStream toStream() throws SwiftFileException;

    URI getResourceURI();

    String getResourceName();

    void mkdirs();

    @Override
    void close() throws SwiftFileException;
}
