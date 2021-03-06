package org.cheetah.commons.excel.client;

import org.cheetah.commons.excel.ExcelException;
import org.cheetah.commons.excel.ExcelProcessor;
import org.cheetah.commons.excel.processor.SimpleExcelProcessor;
import org.cheetah.commons.excel.processor.TemplateExcelProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * excel转换器
 * Created by Max on 2016/6/25.
 */
public class ExcelTranslator<T> {
    private SimpleExcelProcessor<T> readProcessor = new SimpleExcelProcessor<>();

    /**
     * 将excel转为数据
     *
     * @param inputStream
     * @param clz
     * @return
     */
    public List<T> translator(InputStream inputStream, Class<T> clz) {
        return translator(inputStream, clz, 0);
    }

    /**
     * 将excel转为数据
     *
     * @param inputStream
     * @param clz
     * @param sheetIndex
     * @return
     */
    public List<T> translator(InputStream inputStream, Class<T> clz, int sheetIndex) {
        return translator(inputStream, clz, sheetIndex, 0, 0);
    }

    /**
     * 将excel转为数据
     *
     * @param inputStream excel文件流
     * @param clz         对应的实体Class
     * @param sheetIndex  excel中sheet索引，起始为0
     * @param readLine    读的起始行,即标题行索引
     * @param tailLine    是否有标志行尾不需要读取
     * @return
     */
    public List<T> translator(InputStream inputStream, Class<T> clz, int sheetIndex,
                              int readLine, int tailLine) {
        return readProcessor.read(inputStream, clz, sheetIndex, readLine, tailLine);
    }

    /**
     * 将excel转为数据
     *
     * @param srcPath
     * @param clz
     * @return
     */
    public List<T> translator(String srcPath, Class<T> clz) {
        return translator(srcPath, clz, 0);
    }

    /**
     * 将excel转为数据
     *
     * @param srcPath
     * @param clz
     * @param sheetIndex
     * @return
     */
    public List<T> translator(String srcPath, Class<T> clz, int sheetIndex) {
        return translator(srcPath, clz, sheetIndex, 0, 0);
    }

    /**
     * 将excel转为数据
     *
     * @param srcPath   excel文件路径
     * @param clz        对应的实体Class
     * @param sheetIndex excel中sheet索引，起始为0
     * @param readLine   读的起始行,即标题行索引
     * @param tailLine   是否有标志行尾不需要读取
     * @return
     */
    public List<T> translator(String srcPath, Class<T> clz, int sheetIndex,
                              int readLine, int tailLine) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(srcPath);
            return readProcessor.read(fis, clz, sheetIndex, readLine, tailLine);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ExcelException("excel文件找不到！");
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 将数据转为excel文件流
     *
     * @param translation
     */
    public void translator(Translation<T> translation) {
        ExcelProcessor<T> processor;
        if (translation.hasTemplate()) {
            try {
                processor = new TemplateExcelProcessor<>(translation.templateStream(), translation.basicData());
            } catch (Exception e) {
                e.printStackTrace();
                throw new ExcelException("创建模板异常", e);
            }
        } else
            processor = new SimpleExcelProcessor<>(translation.xssf());
        processor.write(translation.data(), translation.entity());
        processor.export(translation.toStream());
    }
}
