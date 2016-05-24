package com.finebi.cube.tools;

import com.fr.bi.stable.constant.DBConstant;
import com.fr.bi.stable.data.db.BICubeFieldSource;
import com.fr.bi.stable.data.db.ICubeFieldSource;
import com.fr.bi.stable.data.source.ICubeTableSource;
import com.fr.bi.stable.utils.DateUtils;

import java.text.ParseException;
import java.util.*;

/**
 * This class created on 2016/4/10.
 *
 * @author Connery
 * @since 4.0
 */
public class BIMemoryDataSourceFactory {
    public static ICubeTableSource generateTableA() {
        BIMemoryDataSource memoryDataSource = new BIMemoryDataSource();
        List<ICubeFieldSource> columns = new ArrayList<ICubeFieldSource>();
        columns.add(new BICubeFieldSource("tableA", "id", DBConstant.CLASS.LONG, 2));
        columns.add(new BICubeFieldSource("tableA", "name", DBConstant.CLASS.STRING, 6));
        columns.add(new BICubeFieldSource("tableA", "gender", DBConstant.CLASS.STRING, 6));
        columns.add(new BICubeFieldSource("tableA", "age", DBConstant.CLASS.DOUBLE, 6));

        memoryDataSource.setFieldList(columns);
        memoryDataSource.setRowCount(5);
        Map<Integer, List> content = new HashMap<Integer, List>();
        List<Long> id = new ArrayList<Long>();
        id.add(1L);
        id.add(2L);
        id.add(3L);
        id.add(4L);
        id.add(5L);
        List<String> name = new ArrayList<String>();
        name.add("Parker");
        name.add("Jam");
        name.add("Blue");
        name.add("Sam");
        name.add("Eliza");
        List<String> gender = new ArrayList<String>();

        gender.add("girl");
        gender.add(".dr");
        gender.add("boy");
        gender.add("boy");
        gender.add("girl");
        List<Double> age = new ArrayList<Double>();

        age.add(Double.valueOf(1));
        age.add(Double.valueOf(2));
        age.add(Double.valueOf(3));
        age.add(Double.valueOf(4));
        age.add(Double.valueOf(5));

        content.put(0, id);
        content.put(1, name);
        content.put(2, gender);
        content.put(3, age);

        memoryDataSource.setContents(content);
        memoryDataSource.setSourceID("memoryAID");

        return memoryDataSource;
    }

    public static ICubeTableSource generateTableB() {
        BIMemoryDataSource memoryDataSource = new BIMemoryDataSource();
        List<ICubeFieldSource> columns = new ArrayList<ICubeFieldSource>();
        columns.add(new BICubeFieldSource("tableB", "id", DBConstant.CLASS.LONG, 2));
        columns.add(new BICubeFieldSource("tableB", "name", DBConstant.CLASS.STRING, 6));
        columns.add(new BICubeFieldSource("tableB", "lover", DBConstant.CLASS.STRING, 6));

        memoryDataSource.setFieldList(columns);
        memoryDataSource.setRowCount(7);
        Map<Integer, List> content = new HashMap<Integer, List>();
        List<Long> id = new ArrayList<Long>();
        id.add(1L);
        id.add(2L);
        id.add(3L);
        id.add(4L);
        id.add(5L);
        id.add(6L);
        id.add(7L);

        List<String> name = new ArrayList<String>();
        name.add("Abel");
        name.add("Brook");
        name.add("Adam");
        name.add("Calvin");
        name.add("Cash");
        name.add("Ban");
        name.add("Bush");


        List<String> lover = new ArrayList<String>();
        lover.add("Parker");
        lover.add("Donald");
        lover.add("Jam");
        lover.add("Sam");
        lover.add("Jam");
        lover.add("Dick");
        lover.add("Jam");


        content.put(0, id);
        content.put(1, name);
        content.put(2, lover);
        memoryDataSource.setContents(content);
        memoryDataSource.setSourceID("memoryBID");

        return memoryDataSource;
    }

    public static Set<ICubeTableSource> getDataSourceSet() {
        Set<ICubeTableSource> result = new HashSet<ICubeTableSource>();
        result.add(generateTableA());
        result.add(generateTableB());
        result.add(generateTableC());
        return result;
    }

    public static Set<List<Set<ICubeTableSource>>> getDataSourceSetMap() {
        Set<List<Set<ICubeTableSource>>> result = new HashSet<List<Set<ICubeTableSource>>>();
        result.add(generate(generateTableA()));
        result.add(generate(generateTableB()));
        result.add(generate(generateTableC()));
        return result;
    }

    public static Set<List<Set<ICubeTableSource>>> getDataSourceSetMap_Line() {
        Set<List<Set<ICubeTableSource>>> result = new HashSet<List<Set<ICubeTableSource>>>();
        List<Set<ICubeTableSource>> list = generate(generateTableA());
        Set<ICubeTableSource> set = new HashSet<ICubeTableSource>();
        set.add(generateTableB());
        list.add(set);
        Set<ICubeTableSource> set_two = new HashSet<ICubeTableSource>();
        set_two.add(generateTableC());
        list.add(set_two);
        result.add(list);
        return result;
    }

    public static List<Set<ICubeTableSource>> generate(ICubeTableSource tableSource) {
        List<Set<ICubeTableSource>> list = new ArrayList<Set<ICubeTableSource>>();
        Set<ICubeTableSource> set = new HashSet<ICubeTableSource>();
        set.add(tableSource);
        list.add(set);
        return list;
    }

    public static ICubeTableSource generateTableC() {
        BIMemoryDataSource memoryDataSource = new BIMemoryDataSource();
        List<ICubeFieldSource> columns = new ArrayList<ICubeFieldSource>();
        columns.add(new BICubeFieldSource("tableC", "id", DBConstant.CLASS.LONG, 2));
        columns.add(new BICubeFieldSource("tableC", "name", DBConstant.CLASS.STRING, 6));
        columns.add(new BICubeFieldSource("tableC", "lover", DBConstant.CLASS.STRING, 6));

        memoryDataSource.setFieldList(columns);
        memoryDataSource.setRowCount(8);
        Map<Integer, List> content = new HashMap<Integer, List>();

        List<String> name = new ArrayList<String>();
        name.add("Gavin");
        name.add("Jack");
        name.add("Thomas");
        name.add("Jordan");
        name.add("Carlos");
        name.add("Cooper");
        name.add("Jesse");
        name.add("Marcus");


        List<String> lover = new ArrayList<String>();
        lover.add("Blue");
        lover.add("Abel");
        lover.add("Jam");
        lover.add("Sam");
        lover.add("Cash");
        lover.add("Dick");
        lover.add("Bush");
        lover.add("Abel");


        List<Long> id = new ArrayList<Long>();
        id.add(1L);
        id.add(2L);
        id.add(3L);
        id.add(4L);
        id.add(5L);
        id.add(6L);
        id.add(7L);
        id.add(8L);
        content.put(0, id);
        content.put(1, name);
        content.put(2, lover);
        memoryDataSource.setContents(content);
        memoryDataSource.setSourceID("memoryCID");

        return memoryDataSource;
    }

    public static ICubeTableSource generateTableD() {
        BIMemoryDataSource memoryDataSource = new BIMemoryDataSource();
        List<ICubeFieldSource> columns = new ArrayList<ICubeFieldSource>();
        columns.add(new BICubeFieldSource("tableA", "id", DBConstant.CLASS.LONG, 2));

        memoryDataSource.setFieldList(columns);
        memoryDataSource.setRowCount(5);
        Map<Integer, List> content = new HashMap<Integer, List>();
        List<Long> id = new ArrayList<Long>();
        id.add(1L);
        id.add(2L);
        id.add(3L);
        id.add(4L);
        id.add(5L);
        content.put(0, id);

        memoryDataSource.setContents(content);
        memoryDataSource.setSourceID("memoryD");

        return memoryDataSource;
    }

    public static ICubeTableSource generateTableDate() {
        BIMemoryDataSource memoryDataSource = new BIMemoryDataSource();
        List<ICubeFieldSource> columns = new ArrayList<ICubeFieldSource>();
        columns.add(new BICubeFieldSource("tableDate", "date", DBConstant.CLASS.DATE, 10));


        memoryDataSource.setFieldList(columns);
        Map<Integer, List> content = new HashMap<Integer, List>();
        List<Long> date = new ArrayList<Long>();
        try {
            date.add(DateUtils.parse("1992-08-06").getTime());
            date.add(DateUtils.parse("1992-08-09").getTime());
            date.add(DateUtils.parse("1992-08-10").getTime());
            date.add(DateUtils.parse("1992-08-11").getTime());
            date.add(DateUtils.parse("1992-08-12").getTime());
            date.add(DateUtils.parse("1991-07-11").getTime());
            date.add(DateUtils.parse("1993-09-12").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        content.put(0, date);


        memoryDataSource.setContents(content);
        memoryDataSource.setSourceID("memoryDate");
        memoryDataSource.setRowCount(content.get(0).size());

        return memoryDataSource;
    }
}
