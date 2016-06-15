package brainfuck;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    public static void generateCode(File tempalteFile,
                                    String resultFilePath,
                                    String resultFileName,
                                    List<String> commands,
                                    PrintStream printStream) throws TemplateException {
        if (tempalteFile == null)
            throw new IllegalArgumentException("Template cannot be null.");
        if (resultFilePath == null)
            throw new IllegalArgumentException("Result file path cannot be null.");
        if (resultFileName == null)
            throw new IllegalArgumentException("Result file name cannot be null.");
        if (commands == null || commands.size() == 0)
            throw new IllegalArgumentException("Commands list cannot be null or empty.");

        String templateFileExtension = getFileExtension(tempalteFile);
        Configuration cfg = new Configuration();

        Map<String, Object> data = new HashMap<>();

        if (templateFileExtension.equals("java"))
            data.put("classname", resultFileName);

        data.put("commands", commands);
        Template template = null;
        try {
            template = cfg.getTemplate(tempalteFile.getPath());
        } catch (IOException e) {
            throw new IllegalStateException("Template file not found.", e);
        }

        if (printStream != null) {
            Writer writer = new OutputStreamWriter(printStream);
            try {
                template.process(data, writer);
                writer.close();

                Writer fileWriter = new FileWriter(
                        new File(resultFilePath + resultFileName
                                + "." + getFileExtension(tempalteFile)));

                template.process(data, fileWriter);

                fileWriter.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
}
