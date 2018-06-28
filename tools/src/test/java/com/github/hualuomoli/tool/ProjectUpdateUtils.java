package com.github.hualuomoli.tool;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class ProjectUpdateUtils {

  private String searchPackageName = "com.github.hualuomoli"; // search package name
  private String searchProjectName = "hualuomoli"; // 查询的项目名,maven里会替换
  private String replacePackageName = "com.github.hualuomoli.sample"; // replace package name
  private String replaceProjectName = "sample"; // 替换成的项目名,maven里会替换

  private String projectPath = ""; // project path
  private String outputPath = "E:/p_1_2_3"; // out put path

  private String[] contentSearchList;
  private String[] contentReplacementList;
  private String pathSearch;
  private String pathReplace;

  @Before
  public void before() {
    // 初始化项目路径
    if (projectPath == null || projectPath.trim().length() == 0) {
      String path = ProjectUpdateUtils.class.getClassLoader().getResource(".").getPath();
      path = path.substring(0, path.lastIndexOf("/tools/target"));
      projectPath = path;
    }

    contentSearchList = new String[] { searchPackageName, "<artifactId>" + searchProjectName };
    contentReplacementList = new String[] { replacePackageName, "<artifactId>" + replaceProjectName };

    pathSearch = StringUtils.replace(searchPackageName, ".", File.separator);
    pathReplace = StringUtils.replace(replacePackageName, ".", File.separator);
  }

  @Test
  public void test() throws IOException {
    this.execute(new File(projectPath));
    System.out.println("success.");
  }

  private void execute(File path) throws IOException {
    File[] files = path.listFiles();

    for (File file : files) {
      String name = file.getName();
      if (name.equals(".git") || name.equals(".settings") || name.equals(".classpath") || name.equals(".project") || name.equals("target") || name.equals("pom.xml.versionsBackup")) {
        continue;
      }
      if (file.isFile()) {
        // 文件名替换
        String absolutePath = file.getAbsolutePath();
        String relativePath = absolutePath.substring(projectPath.length());
        relativePath = StringUtils.replace(relativePath, pathSearch, pathReplace);

        // 内容替换
        String content = FileUtils.readFileToString(file);
        content = StringUtils.replaceEach(content, contentSearchList, contentReplacementList);

        // 写内容
        File outFile = new File(outputPath, relativePath);
        FileUtils.writeStringToFile(outFile, content);
      } else if (file.isDirectory()) {
        this.execute(file);
      } else {
        throw new RuntimeException();
      }
    }
  }

}
