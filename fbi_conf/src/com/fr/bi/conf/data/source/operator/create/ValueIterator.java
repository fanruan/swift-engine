package com.fr.bi.conf.data.source.operator.create;

import com.finebi.cube.api.ICubeColumnIndexReader;
import com.finebi.cube.api.ICubeTableService;
import com.finebi.cube.api.ICubeValueEntryGetter;
import com.finebi.cube.relation.BITableSourceRelation;
import com.fr.bi.base.key.BIKey;
import com.fr.bi.stable.constant.BIReportConstant;
import com.fr.bi.stable.engine.cal.DimensionIteratorCreator;
import com.fr.bi.stable.gvi.GVIUtils;
import com.fr.bi.stable.gvi.GroupValueIndex;
import com.fr.bi.stable.operation.group.IGroup;
import com.fr.bi.stable.structure.collection.CubeIndexGetterWithNullValue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 小灰灰 on 2016/11/23.
 */
class ValueIterator {
    private ICubeValueEntryGetter[] getters;
    private ICubeColumnIndexReader[] mapGetters;
    private ValuesAndGVI next;
    private GroupValueIndex allShowIndex;
    private Iterator<Map.Entry<Object, GroupValueIndex>>[] iterators;
    private ValuesAndGVI[] valuesAndGVIs;
    private ICubeTableService ti;
    private BIKey[] keys;
    private IGroup[] groups;

    public ValueIterator(ICubeTableService ti, BIKey[] keys) {
        this(ti, keys, null);
    }

    public ValueIterator(ICubeTableService ti, BIKey[] keys, IGroup[] groups) {
        this.ti = ti;
        this.keys = keys;
        allShowIndex = ti.getAllShowIndex();
        getters = new ICubeValueEntryGetter[keys.length];
        iterators = new Iterator[keys.length];
        valuesAndGVIs = new ValuesAndGVI[keys.length + 1];
        if (groups == null || groups.length != keys.length) {
            this.groups = new IGroup[keys.length];
        } else {
            this.groups = groups;
            this.mapGetters = new ICubeColumnIndexReader[keys.length];
        }
        valuesAndGVIs[0] = new ValuesAndGVI(new Object[0], allShowIndex);
        for (int i = 0; i < keys.length; i++) {
            getters[i] = ti.getValueEntryGetter(this.keys[i], new ArrayList<BITableSourceRelation>());
        }
        iterators[0] = getIter(0, allShowIndex);
        if (iterators[0].hasNext()) {
            move(0);
        }
    }

    public boolean hasNext() {
        return next != null;
    }

    public ValuesAndGVI next() {
        ValuesAndGVI temp = next;
        moveNext();
        return temp;
    }

    private void moveNext() {
        for (int i = iterators.length - 1; i >= 0; i--) {
            if (iterators[i].hasNext()) {
                move(i);
                return;
            }
        }
        next = null;
    }

    private void move(int index) {
        if (index < 0) {
            next = null;
            return;
        }
        for (int i = index; i < iterators.length; i++) {
            if (i != index) {
                iterators[i] = getIter(i, valuesAndGVIs[i].gvi);
            }
            Object[] values = new Object[i + 1];
            System.arraycopy(valuesAndGVIs[i].values, 0, values, 0, values.length - 1);
            if (iterators[i].hasNext()) {
                Map.Entry<Object, GroupValueIndex> entry = iterators[i].next();
                values[values.length - 1] = entry.getKey();
                valuesAndGVIs[i + 1] = new ValuesAndGVI(values, entry.getValue().AND(valuesAndGVIs[i].gvi));
            } else {
                move(i - 1);
                if (next == null) {
                    return;
                }
            }
        }
        next = valuesAndGVIs[valuesAndGVIs.length - 1];
    }

    private Iterator getIter(int index, GroupValueIndex gvi) {
        if (isCustomGroup(groups[index])) {
            return createMapIterator(index, gvi);
        }
        ICubeValueEntryGetter getter = getters[index];
        return DimensionIteratorCreator.createValueMapIterator(getter, gvi, true);
    }

    private Iterator createMapIterator(int index, GroupValueIndex gvi) {
        if (mapGetters[index] == null) {
            ICubeColumnIndexReader baseGroupMap = ti.loadGroup(keys[index], new ArrayList<BITableSourceRelation>());
            GroupValueIndex nullIndex = ti.getNullGroupValueIndex(keys[index]);
            if (!nullIndex.isAllEmpty()) {
                baseGroupMap = new CubeIndexGetterWithNullValue(baseGroupMap, getters[index], nullIndex);
            }
            mapGetters[index] = groups[index].createGroupedMap(baseGroupMap);
        }

        Iterator it = mapGetters[index].iterator();
        Map map = new LinkedHashMap();
        while (it.hasNext()) {
            Map.Entry<Object, GroupValueIndex> entry = (Map.Entry<Object, GroupValueIndex>) it.next();
            GroupValueIndex groupValueIndex = GVIUtils.AND(gvi, entry.getValue());
            if (groupValueIndex != null && !groupValueIndex.isAllEmpty()) {
                map.put(entry.getKey(), groupValueIndex);
            }
        }
        return map.entrySet().iterator();
    }

    private boolean isCustomGroup(IGroup group) {
        if (group == null) {
            return false;
        }
        int groupType = group.getType();
        return groupType != BIReportConstant.GROUP.NO_GROUP
                || groupType != BIReportConstant.GROUP.ID_GROUP;
    }

}
