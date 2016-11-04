package com.fr.bi.field.filtervalue.date.evenfilter;

import com.finebi.cube.api.ICubeDataLoader;
import com.finebi.cube.conf.table.BusinessTable;
import com.fr.bi.base.annotation.BICoreField;
import com.fr.bi.stable.gvi.GroupValueIndex;
import com.fr.bi.stable.report.result.DimensionCalculator;

/**
 * Created by 小灰灰 on 2014/6/2.
 */
public class DateNotEqualsTargetFilterValue extends DateKeyTargetFilterValue {

    @BICoreField
    private String CLASS_TYPE = "DateNotEqualsTargetFilterValue";

    /**
     * 获取过滤后的索引
     *
     * @param target
     * @param loader loader对象
     * @return 过滤索引
     */
    @Override
    public GroupValueIndex createFilterIndex(DimensionCalculator dimension, BusinessTable target, ICubeDataLoader loader, long userId) {
        GroupValueIndex gvi = super.createFilterIndex(dimension, target, loader, userId);
        if (gvi == null) {
            return loader.getTableIndex(target.getTableSource()).getAllShowIndex();
        } else {
            return gvi.NOT(loader.getTableIndex(target.getTableSource()).getRowCount()).AND(loader.getTableIndex(target.getTableSource()).getAllShowIndex());
        }
    }

}