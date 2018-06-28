package com.github.hualuomoli.http.util;

import com.github.hualuomoli.commons.collect.Maps;

import java.util.Map;

public class MimeUtils {

    private static final Map<String, String> MIME_MAP = Maps.newHashMap();
    private static final String DEFAULT_MIME = "*/*";

    static {
        //Video
        MIME_MAP.put(".3gp", "video/3gpp");
        MIME_MAP.put(".asf", "video/x-ms-asf");
        MIME_MAP.put(".avi", "video/x-msvideo");
        MIME_MAP.put(".m4u", "video/vnd.mpegurl");
        MIME_MAP.put(".m4v", "video/x-m4v");
        MIME_MAP.put(".mov", "video/quicktime");
        //mp4 统一使用mp4
        MIME_MAP.put(".mp4", "video/mp4");
        MIME_MAP.put(".mpg4", "video/mp4");

        MIME_MAP.put(".mpe", "video/x-mpeg");
        //mpeg 使用相应的默认程序打开，但不添加文件拓展名
        MIME_MAP.put(".mpeg", "video/mpg");
        MIME_MAP.put(".mpg", "video/mpg");

        //audio
        MIME_MAP.put(".m3u", "audio/x-mpegurl");
        //mp4a-latm 使用相应的默认程序打开，但不添加文件拓展名
        MIME_MAP.put(".m4a", "audio/mp4a-latm");
        MIME_MAP.put(".m4b", "audio/mp4a-latm");
        MIME_MAP.put(".m4p", "audio/mp4a-latm");

        //x-mpeg
        MIME_MAP.put(".mp2", "x-mpeg");
        MIME_MAP.put(".mp3", "audio/x-mpeg");

        MIME_MAP.put(".mpga", "audio/mpeg");
        MIME_MAP.put(".ogg", "audio/ogg");
        MIME_MAP.put(".rmvb", "audio/x-pn-realaudio");
        MIME_MAP.put(".wav", "audio/x-wav");
        MIME_MAP.put(".wma", "audio/x-ms-wma");
        MIME_MAP.put(".wmv", "audio/x-ms-wmv");

        //text
        //plain 使用相应的默认程序打开，但不添加文件拓展名
        MIME_MAP.put(".c", "text/plain");
        MIME_MAP.put(".java", "text/plain");
        MIME_MAP.put(".conf", "text/plain");
        MIME_MAP.put(".cpp", "text/plain");
        MIME_MAP.put(".h", "text/plain");
        MIME_MAP.put(".prop", "text/plain");
        MIME_MAP.put(".rc", "text/plain");
        MIME_MAP.put(".sh", "text/plain");
        MIME_MAP.put(".log", "text/plain");
        MIME_MAP.put(".txt", "text/plain");
        MIME_MAP.put(".xml", "text/plain");

        //统一使用html
        MIME_MAP.put(".html", "text/html");
        MIME_MAP.put(".htm", "text/html");

        MIME_MAP.put(".css", "text/css");

        //image
        MIME_MAP.put(".jpg", "image/jpeg");
        MIME_MAP.put(".jpeg", "image/jpeg");
        MIME_MAP.put(".bmp", "image/bmp");
        MIME_MAP.put(".gif", "image/gif");
        MIME_MAP.put(".png", "image/png");

        //application
        MIME_MAP.put(".bin", "application/octet-stream");
        MIME_MAP.put(".class", "application/octet-stream");
        MIME_MAP.put(".exe", "application/octet-stream");
        MIME_MAP.put("class", "application/octet-stream");

        MIME_MAP.put(".apk", "application/vnd.android.package-archive");
        MIME_MAP.put(".doc", "application/msword");
        MIME_MAP.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        MIME_MAP.put(".xls", "application/vnd.ms-excel");
        MIME_MAP.put(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        MIME_MAP.put(".gtar", "application/x-gtar");
        MIME_MAP.put(".gz", "application/x-gzip");
        MIME_MAP.put(".jar", "application/java-archive");
        MIME_MAP.put(".js", "application/x-javascript");
        MIME_MAP.put(".mpc", "application/vnd.mpohun.certificate");
        MIME_MAP.put(".msg", "application/vnd.ms-outlook");
        MIME_MAP.put(".pdf", "application/pdf");
        MIME_MAP.put(".pps", "application/vnd.ms-powerpoint");
        MIME_MAP.put(".ppt", "application/vnd.ms-powerpoint");

        MIME_MAP.put(".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        MIME_MAP.put(".rtf", "application/rtf");
        MIME_MAP.put(".tar", "application/x-tar");
        MIME_MAP.put(".tgz", "application/x-compressed");
        MIME_MAP.put(".wps", "application/vnd.ms-works");
        MIME_MAP.put(".z", "application/x-compress");
        MIME_MAP.put(".zip", "application/x-zip-compressed");
    }

    /**
     * 获取文件的Mime
     * @param filename 文件名
     * @return 文件的Mime
     */
    public static final String getMimeByFilename(String filename) {
        int index = filename.lastIndexOf(".");
        if(index <= 0) {
            return DEFAULT_MIME;
        }
        return MimeUtils.getMime(filename.substring(index));
    }

    /**
     * 获取文件的Mime
     * @param suffix 文件后缀(如.jpeg)
     * @return 文件的Mime
     */
    public static final String getMime(String suffix) {
        String mime = MIME_MAP.get(suffix);
        if(mime == null) {
            return DEFAULT_MIME;
        }
        return mime;
    }

}
