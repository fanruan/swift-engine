package com.fr.swift.cube.task.impl;

import com.fr.swift.cube.task.Task;
import com.fr.swift.cube.task.Task.Status;
import com.fr.swift.cube.task.TaskKey;
import com.fr.swift.cube.task.TaskPool;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author anchore
 * @date 2017/12/31
 */
abstract class BaseTaskPool<T extends Task> implements TaskPool<T> {
    Map<TaskKey, T> tasks;

    @Override
    public void add(T task) {
        tasks.put(task.key(), task);
    }

    @Override
    public boolean contains(TaskKey key) {
        return tasks.containsKey(key);
    }

    @Override
    public T get(TaskKey key) {
        return tasks.get(key);
    }

    @Override
    public Collection<TaskKey> allTasks() {
        return tasks.keySet();
    }

    @Override
    public Set<TaskKey> tasksOf(Status status) {
        Set<TaskKey> specTasks = new HashSet<TaskKey>();
        for (Entry<TaskKey, T> entry : tasks.entrySet()) {
            if (entry.getValue().status() == status) {
                specTasks.add(entry.getKey());
            }
        }
        return specTasks;
    }
}