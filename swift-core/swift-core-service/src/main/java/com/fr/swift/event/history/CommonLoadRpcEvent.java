package com.fr.swift.event.history;

import com.fr.swift.event.base.AbstractHistoryRpcEvent;
import com.fr.swift.structure.Pair;

import java.util.List;
import java.util.Map;

/**
 * @author yee
 * @date 2018/9/12
 */
public abstract class CommonLoadRpcEvent extends AbstractHistoryRpcEvent<Pair<String, Map<String, List<String>>>> {
    private Pair<String, Map<String, List<String>>> content;

    public CommonLoadRpcEvent(Pair<String, Map<String, List<String>>> content) {
        this.content = content;
    }

    @Override
    public Pair<String, Map<String, List<String>>> getContent() {
        return content;
    }
}
