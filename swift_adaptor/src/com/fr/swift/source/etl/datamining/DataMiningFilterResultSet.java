package com.fr.swift.source.etl.datamining;

import com.finebi.conf.algorithm.*;
import com.finebi.conf.internalimp.analysis.bean.operator.datamining.AbstractFilterBean;
import com.finebi.conf.internalimp.analysis.bean.operator.datamining.AlgorithmBean;
import com.finebi.conf.structure.bean.filter.FilterBean;
import com.fr.swift.adaptor.transformer.FilterInfoFactory;
import com.fr.swift.log.SwiftLogger;
import com.fr.swift.log.SwiftLoggers;
import com.fr.swift.query.filter.info.FilterInfo;
import com.fr.swift.segment.Segment;
import com.fr.swift.segment.column.Column;
import com.fr.swift.segment.column.ColumnKey;
import com.fr.swift.segment.column.DictionaryEncodedColumn;
import com.fr.swift.source.*;
import com.fr.swift.source.etl.columnfilter.ColumnFilterOperatorResultSet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author qingj
 */
public class DataMiningFilterResultSet implements SwiftResultSet {
    private static final SwiftLogger LOGGER = SwiftLoggers.getLogger(DataMiningFilterResultSet.class);
    private AlgorithmBean algorithmBean;
    private Segment[] basedSegment;
    private ListBasedRow listBasedRow;
    private SwiftMetaData selfMetaData;
    private SwiftMetaData baseMetaData;
    private int rowCursor = 0;
    private List<List<Object>> predictTableData = new ArrayList<List<Object>>();
    private boolean isFirst = true;


    public DataMiningFilterResultSet(AlgorithmBean algorithmBean, SwiftMetaData selfMetaData, SwiftMetaData baseMetaData, Segment[] basedSegment) {
        this.algorithmBean = algorithmBean;
        this.basedSegment = basedSegment;
        this.selfMetaData = selfMetaData;
        this.baseMetaData = baseMetaData;
    }

    private Object getCellValueFromSegment(Segment segment, String columnName, int rowIndex) {
        Column column = segment.getColumn(new ColumnKey(columnName));
        DictionaryEncodedColumn dicColumn = column.getDictionaryEncodedColumn();
        return dicColumn.getValue(dicColumn.getIndexByRow(rowIndex));
    }

    private void init() throws Exception {
        Segment segment = basedSegment[0];
        DMRowMetaData inputMetaData = new DMRowMetaData();
        List<List<Object>> trainData = new ArrayList<List<Object>>();
        List<List<Object>> testData = new ArrayList<List<Object>>();
        for (int i = 0; i < segment.getRowCount(); i++) {
            List<Object> row = new ArrayList<Object>();
            for (int j = 0; j < baseMetaData.getColumnCount(); j++) {
                SwiftMetaDataColumn column = baseMetaData.getColumn(j + 1);
                if (i == 0) {
                    inputMetaData.addColMeta(new DMColMetaData(column.getName(), DMType.fromSwiftInt(column.getType())));
                }
                Object cellValue = getCellValueFromSegment(segment, column.getName(), i);
                row.add(cellValue);
            }
            testData.add(row);
        }

        AbstractFilterBean filterBean = (AbstractFilterBean) algorithmBean;
        List<FilterBean> filter = filterBean.getFilter();
        FilterInfo filterInfo = FilterInfoFactory.transformFilterBean(filter, Arrays.asList(basedSegment));
        ColumnFilterOperatorResultSet resultSet = new ColumnFilterOperatorResultSet(basedSegment, selfMetaData, filterInfo);
        while (resultSet.next()) {
            List<Object> row = new ArrayList<Object>();
            Row rowData = resultSet.getRowData();
            int rowDataSize = rowData.getSize();
            for (int i = 0; i < rowDataSize; i++) {
                Object value = rowData.getValue(i);
                row.add(value);
            }
            trainData.add(row);
        }

        // MetaData 相同
        DMDataModel trainDataModel = new DMDataModel(trainData, inputMetaData);
        DMDataModel testDataModel = new DMDataModel(testData, inputMetaData);
        DMDataModel[] dataModels = new DMDataModel[2];
        dataModels[0] = trainDataModel;
        dataModels[1] = testDataModel;
        DMAbstractAlgorithm algorithm = DMAlgorithmFactory.create(algorithmBean.getAlgorithmName());
        algorithm.init(algorithmBean, dataModels);
        DMDataModel outputData = algorithm.run();
        predictTableData = outputData.getData();
    }

    @Override
    public void close() {
    }

    @Override
    public boolean next() throws SQLException {
        try {
            if (isFirst) {
                isFirst = false;
                init();
            }
            List<Object> row;
            int rowCount = predictTableData.size();

            if (rowCursor < rowCount) {
                row = predictTableData.get(rowCursor);
                rowCursor++;
                setRowValue(new ListBasedRow(row));
                return true;
            }
            return false;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    private void setRowValue(ListBasedRow row) {
        this.listBasedRow = row;
    }

    @Override
    public SwiftMetaData getMetaData() {
        return selfMetaData;
    }

    @Override
    public Row getRowData() {
        return getRowValue();
    }

    private ListBasedRow getRowValue() {
        return this.listBasedRow;
    }
}
