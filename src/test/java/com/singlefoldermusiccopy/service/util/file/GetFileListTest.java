package com.singlefoldermusiccopy.service.util.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml"})
public class GetFileListTest {

    @Value("${tmp.directory}")
    String tmpDirectory;

    @Autowired
    FileListUtil fileListUtil;

    @After
    public void thearDown() throws Exception {
       FileUtils.forceDelete(new File(tmpDirectory));
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


    private File createTmpDirectoryWithRandomName() throws IOException {
        String directoryName = RandomStringUtils.randomAlphanumeric(20);
        String pathname = tmpDirectory + "/" + directoryName;
        File directory = new File(pathname);
        FileUtils.forceMkdir(directory);
        return directory;
    }


}
