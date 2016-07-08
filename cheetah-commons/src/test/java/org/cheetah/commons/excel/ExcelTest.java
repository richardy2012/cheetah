package org.cheetah.commons.excel;

import com.google.common.collect.Lists;
import org.cheetah.commons.excel.client.ExcelTranslator;
import org.cheetah.commons.excel.client.Translation;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by maxhuang on 2016/6/24.
 */
@SuppressWarnings("unchecked")
public class ExcelTest {

    @Test
    public void test() throws FileNotFoundException {
//        List<FX> list =  ExcelBaseProcessor.Sample.readExcel2ObjsByPath("D:\\对接人旗下艺人账号汇总.xlsx", FX.class
//                , 0, 0, 0);
//        List<Anchor> source =  ExcelBaseProcessor.Sample.readExcel2ObjsByPath("D:\\2016-06至2016-06-24 16-17-15--手机直播素人主播表.xls", Anchor.class
//                , 0, 0, 0);
//
//        System.out.println(source.size());
//
//        List<Long> ls = list.stream().map(FX::getFxId).collect(Collectors.toList());
//
//        source = source.stream().filter(o ->
//            !ls.contains(o.getFxId())
//        ).collect(Collectors.toList());
//        System.out.println(source.size());
//
//
//        ExcelBaseProcessor.Sample.exportObj2Excel(new FileOutputStream("D:/test.xls"), source, Anchor.class, false);

    }

    /**
     * 复制文档
     * @throws FileNotFoundException
     */
    @Test
    public void test22() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        System.out.println(start);
        ExcelTranslator<Anchor> translator = new ExcelTranslator<>();
        List<Anchor> anchors = new ExcelTranslator<Anchor>().translator("D:\\test.xls", Anchor.class);
        for (Anchor anchor : anchors) {
            System.out.println(anchor);
        }

        Translation<Anchor> t = Translation.newBuilder()
                .toStream(new FileOutputStream("d:/test2.xls"))
                .data(anchors)
                .entity(Anchor.class)
                .build();

        translator.translator(t);
    }

    /**
     * 基于模板的导出
     * @throws FileNotFoundException
     */
    @Test
    public void templateTest() throws FileNotFoundException {
        InputStream stream = new FileInputStream("D:\\excel_template_v2.xlsx");
        ExcelTranslator<Anchor> translator = new ExcelTranslator<>();

        List<Anchor> anchors = Lists.newArrayList();
        Anchor anchor = new Anchor();
        anchor.setFxId(123);
        anchor.setLiveRoom(123);
        anchor.setName("name");
        anchors.add(anchor);

        translator.translator(Translation.newBuilder().toStream(new FileOutputStream("d:/test_template.xlsx"))
                .entity(Anchor.class)
                .templateStream(stream)
                .data(anchors)
                .build()
        );
    }
}
