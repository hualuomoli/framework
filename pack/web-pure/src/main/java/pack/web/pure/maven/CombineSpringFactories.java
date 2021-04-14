package pack.web.pure.maven;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombineSpringFactories {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            return;
        }

        // 输出路径
        String output = args[0];
        // 输入路径
        String[] paths = args[1].split(",");
        // 编码集
        String charset = args.length >= 3 ? args[2] : DEFAULT_CHARSET;

        // read
        Map<String, List<String>> contents = new HashMap<String, List<String>>();
        for (String path : paths) {
            read(contents, path, charset);
        }

        // write
        flush(contents, output, charset);
    }

    private static void flush(Map<String, List<String>> contents, String path, String charset) {
        File file = new File(path, "target/classes/META-INF/spring.factories");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        System.out.println(file.getAbsolutePath());

        OutputStreamWriter writer = null;

        try {
            // writer
            writer = new OutputStreamWriter(new FileOutputStream(file), charset);

            // flush
            for (Map.Entry<String, List<String>> entry : contents.entrySet()) {

                // empty line
                writer.write("\n\n");

                // key
                writer.write(entry.getKey());
                writer.write("=\\\n");
                // value
                List<String> values = entry.getValue();
                for (int i = 0; i < values.size() - 1; i++) {
                    writer.write(values.get(i));
                    writer.write(",\\\n");
                }
                writer.write(values.get(values.size() - 1));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private static void read(Map<String, List<String>> contents, String path, String charset) {

        // 文件是否存在
        File file = new File(path.trim(), "src/main/resources/META-INF/spring.factories");
        if (!file.exists()) {
            return;
        }

        BufferedReader reader = null;
        String line = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));

            String key = null;
            String value = null;
            List<String> values = null;
            while ((line = reader.readLine()) != null) {

                // empty line
                if (line.length() == 0) {
                    continue;
                }

                // read key
                if (line.indexOf("=") != -1) {
                    key = line.substring(0, line.indexOf("="));
                    values = contents.get(key);
                    if (values == null) {
                        values = new ArrayList<String>();
                        contents.put(key, values);
                    }
                    continue;
                }

                // add value
                if (line.indexOf(",") != -1) {
                    values.add(line.substring(0, line.indexOf(",")));
                } else {
                    values.add(line);
                }

                // end
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // end
    }

}
