package com.singlefoldermusiccopy.service.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface FileListUtil {
    List<File> getFileList(File directory) throws FileNotFoundException;
}
