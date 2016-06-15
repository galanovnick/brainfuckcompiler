package brainfuck;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.fusesource.jansi.AnsiRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    final static Logger LOG = LoggerFactory.getLogger(CodeGenerator.class);

    public static void generateCode(File tempalteFile,
                                    String resultFilePath,
                                    String resultFileName,
                                    List<String> commands,
                                    PrintStream printStream) throws TemplateException {

        if (tempalteFile == null) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Template file for is null.");
            }
            throw new IllegalArgumentException("Template cannot be null.");
        }
        if (resultFilePath == null) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Result file path is null.");
            }
            throw new IllegalArgumentException("Result file path cannot be null.");
        }
        if (resultFileName == null) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Result file name is null.");
            }
            throw new IllegalArgumentException("Result file name cannot be null.");
        }
        if (commands == null || commands.size() == 0) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Commands list is null or empty.");
            }
            throw new IllegalArgumentException("Commands list cannot be null or empty.");
        }

        String templateFileExtension = getFileExtension(tempalteFile);

        Configuration cfg = new Configuration();

        Map<String, Object> data = new HashMap<>();

        if (templateFileExtension.equals("java")) {
            data.put("classname", resultFileName);
        }
        data.put("commands", commands);
        Template template = null;
        try {
            template = cfg.getTemplate(tempalteFile.getPath());
        } catch (IOException e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("No template file found.", e);
            }
            throw new IllegalStateException("Template file not found.", e);
        }
        try {
            if (printStream != null) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Showing generated code in additional stream...");
                }
                Writer writer = new OutputStreamWriter(printStream);
                template.process(data, writer);
                writer.close();
            }

            if (LOG.isInfoEnabled()) {
                LOG.info("Writing generated(" + templateFileExtension.substring(0) + ")"
                        + " code in file[\"" + resultFilePath + resultFileName
                        + "." + getFileExtension(tempalteFile) + "\"]...");
            }
            Writer fileWriter = new FileWriter(
                    new File(resultFilePath + resultFileName
                            + "." + getFileExtension(tempalteFile)));

            template.process(data, fileWriter);

            fileWriter.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        else return "";
    }
}
