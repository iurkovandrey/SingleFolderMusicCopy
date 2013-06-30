package com.singlefoldermusiccopy.service.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileListUtilImpl implements FileListUtil {
    @Override
    public List<File> getFileList(File directory) {
        List<File> fileList = new ArrayList<>();
        List<File> fileListInDirectory = getFilesList(directory);
        for (File currentFile : fileListInDirectory) {
            if (currentFile.isDirectory()) {
                List<File> fileListFromDirectory = getFileList(currentFile);
                fileList.addAll(fileListFromDirectory);
            }
            else {
              fileList.add(currentFile);
            }
        }
        return fileList;
    }

    private List<File> getFilesList(File directory) {
        File[] files = directory.listFiles();
        return Arrays.asList(files);
    }
}
