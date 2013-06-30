package com.singlefoldermusiccopy.service.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileListUtilImpl implements FileListUtil {
    @Override
    public List<File> getFileList(File directory) throws FileNotFoundException {
        if(checkDirectoryForNull(directory)) return new ArrayList<>();
        checkDirectoryIsNotFile(directory);
        checkDirectoryIsExist(directory);
        List<File> fileList = new ArrayList<>();
        List<File> fileListInDirectory = getFilesList(directory);
        for (File currentFile : fileListInDirectory) {
            if (currentFile.isDirectory()) {
                List<File> fileListFromDirectory = getFileList(currentFile);
                fileList.addAll(fileListFromDirectory);
            } else {
                fileList.add(currentFile);
            }
        }
        return fileList;
    }

    private void checkDirectoryIsExist(File directory) throws FileNotFoundException {
        if(!directory.exists()) {
            throw new FileNotFoundException("Directory does not exist");
        }
    }

    private void checkDirectoryIsNotFile(File directory) {
        if(directory.isFile()) {
            throw new IllegalArgumentException("parameter should be directory, but it is file");
        }
    }

    private boolean checkDirectoryForNull(File directory) {
        if (directory == null) {
            return true;
        }
        return false;
    }

    private List<File> getFilesList(File directory) {
        File[] files = directory.listFiles();
        return Arrays.asList(files);
    }
}
