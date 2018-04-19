package com.fr.swift.cal.result.group;

import com.fr.swift.cal.Query;
import com.fr.swift.cal.result.AbstractResultQuery;
import com.fr.swift.query.adapter.target.GroupTarget;
import com.fr.swift.query.aggregator.Aggregator;
import com.fr.swift.result.NodeResultSet;

import java.util.List;

/**
 * Created by pony on 2017/12/18.
 */
public abstract class AbstractGroupResultQuery extends AbstractResultQuery<NodeResultSet> {

    protected List<Aggregator> aggregators;
    protected List<GroupTarget> targets;

    public AbstractGroupResultQuery(List<Query<NodeResultSet>> queries, List<Aggregator> aggregators, List<GroupTarget> targets) {
        super(queries);
        this.aggregators = aggregators;
        this.targets = targets;
    }
}
