package com.singlefoldermusiccopy.service.util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class GetFileListTest {

    private static final String DIRECTORY_DELIMER = "/";
    public static final String FAKE_PATH = "fake_path";

    @Value("${tmp.directory}")
    String tmpDirectory;

    @Autowired
    FileListUtil fileListUtil;

    @Before
    public void setUp() {
        cleanupTmpDirectory();


    }

    @After
    public void thearDown(){
        cleanupTmpDirectory();
    }

    @Test
    public void testGetFileListUtilShouldReturnNotNullListFiles() throws Exception {
       //Given
        File directory = createTmpDirectoryWithRandomName();
       //When
       List<File> fileList = fileListUtil.getFileList(directory);
       //Then
       assertNotNull(fileList);
    }

    @Test
    public void testFileListUtilShouldReturnExpectedListFiles() throws Exception {
        File directory = createTmpDirectoryWithRandomName();
        List<File> expectedFileList = createTmpFiles(directory);
        List<File> actualFileList = fileListUtil.getFileList(directory);
        assertEquals(expectedFileList, actualFileList);
    }

    @Test
    public void testFileListUtilShouldReturnExpectedListFilesWithinDirectoryHierarchy() throws Exception {
        List<File> expectedAllFiles = new ArrayList<>();
        getAllExpectedFilesList(expectedAllFiles);

        Collections.sort(expectedAllFiles);
        List<File> actualFileList = fileListUtil.getFileList(new File(tmpDirectory));
        assertEquals(expectedAllFiles, actualFileList);
    }

    @Test
    public void testFileListUtilShouldReturnExpectedListFilesWithinDirectoryHierarchyAndFiles() throws Exception {
        List<File> expectedAllFiles = new ArrayList<>();
        getAllExpectedFilesList(expectedAllFiles);
        addFilesToRootDirectory(expectedAllFiles);

        Collections.sort(expectedAllFiles);
        List<File> actualFileList = fileListUtil.getFileList(new File(tmpDirectory));
        assertEquals(expectedAllFiles, actualFileList);
    }

    @Test
    public void testFileListUtilShouldReturnEmptyListWhenDirectoryEqualNull() throws Exception {
        List<File> actualFileList = fileListUtil.getFileList(null);
        assertNotNull(actualFileList);
        assertEquals(0, actualFileList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFileDirectoryIsNotDirectory() throws Exception {
        //Given
        String pathname = tmpDirectory + DIRECTORY_DELIMER + RandomStringUtils.randomAlphanumeric(20);
        File file = new File(pathname);
        FileUtils.touch(file);
        //When
        fileListUtil.getFileList(file);
    }

    @Test(expected = FileNotFoundException.class)
    public void testDirectoryDoesNotExist() throws Exception {
        fileListUtil.getFileList(new File(tmpDirectory + DIRECTORY_DELIMER + FAKE_PATH));
    }

    private void addFilesToRootDirectory(List<File> expectedAllFiles) throws IOException {
        List<File> tmpFiles = createTmpFiles(new File(tmpDirectory));
        expectedAllFiles.addAll(tmpFiles);
    }


    private void getAllExpectedFilesList(List<File> expectedAllFiles) throws IOException {
        int maxDirectories = Integer.parseInt(RandomStringUtils.randomNumeric(2));
        for(int i = 0; i <= maxDirectories; i++) {
              File directory = createTmpDirectoryWithRandomName();
              List<File> expectedFileList = createTmpFiles(directory);
            expectedAllFiles.addAll(expectedFileList);
        }
    }


    private List<File> createTmpFiles(File directory) throws IOException {
        List<File> expectedFileList = new ArrayList<>();
        Integer maxValue = Integer.parseInt(RandomStringUtils.randomNumeric(2));
        for(int i =0; i <= maxValue; i++) {
            String fileName = RandomStringUtils.randomAlphanumeric(20);
            File file = new File(directory.getAbsolutePath() + DIRECTORY_DELIMER + fileName);
            FileUtils.touch(file);
            expectedFileList.add(file);
        }
        Collections.sort(expectedFileList);
        return expectedFileList;
    }


    private File createTmpDirectoryWithRandomName() throws IOException {
        String directoryName = RandomStringUtils.randomAlphanumeric(20);
        String pathname = tmpDirectory + DIRECTORY_DELIMER + directoryName;
        File directory = new File(pathname);
        FileUtils.forceMkdir(directory);
        return directory;
    }

    private void cleanupTmpDirectory() {
        try {
            FileUtils.forceDelete(new File(tmpDirectory));
        } catch (IOException e) {
            //Do nothing when tmpDirectory doesn't exist
        }
    }


}
