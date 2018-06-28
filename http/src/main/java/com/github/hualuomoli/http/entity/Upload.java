package com.github.hualuomoli.http.entity;

import com.github.hualuomoli.http.util.MimeUtils;

import java.io.File;

/**
 * 上传信息
 */
public class Upload {

    /** 上传数据域名称 */
    private String fieldName;
    /** 文件名 */
    private String filename;
    /** 文件扩展类型 */
    private String mime;
    /** 文件 */
    private File file;

    private Upload() {
    }

    public static Upload newInstance(String fieldName, File file) {
        return Upload.newInstance(fieldName, file.getName(), MimeUtils.getMimeByFilename(file.getName()), file);
    }

    public static Upload newInstance(String fieldName, String filename, String mime, File file) {
        Upload upload = new Upload();
        upload.fieldName = fieldName;
        upload.filename = filename;
        upload.mime = mime;
        upload.file = file;
        return upload;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFilename() {
        return filename;
    }

    public String getMime() {
        return mime;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "fieldName='" + fieldName + '\'' +
                ", filename='" + filename + '\'' +
                ", mime='" + mime + '\'' +
                ", file=" + file.getName() +
                '}';
    }

}
