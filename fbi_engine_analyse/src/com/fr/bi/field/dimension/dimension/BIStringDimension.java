package com.fr.bi.field.dimension.dimension;

import com.fr.bi.conf.report.widget.BIDataColumn;
import com.fr.bi.field.dimension.calculator.StringDimensionCalculator;
import com.fr.bi.stable.constant.BIReportConstant;
import com.fr.bi.stable.operation.group.BIGroupFactory;
import com.fr.bi.stable.relation.BITableSourceRelation;
import com.fr.bi.stable.report.result.DimensionCalculator;
import com.fr.json.JSONObject;

import java.util.List;


public class BIStringDimension extends BIAbstractDimension {

    public BIStringDimension() {
    }

    @Override
    public void parseJSON(JSONObject jo) throws Exception {
        super.parseJSON(jo);
        JSONObject group = jo.optJSONObject("group");
        if(group == null || !group.has("type")){
            group = new JSONObject().put("type", BIReportConstant.GROUP.ID_GROUP);
        }
        this.group = BIGroupFactory.parseStringGroup(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BIStringDimension)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return true;
    }

    @Override
    public DimensionCalculator createCalculator(BIDataColumn column, List<BITableSourceRelation> relations) {
        return new StringDimensionCalculator(this, column, relations);
    }
}