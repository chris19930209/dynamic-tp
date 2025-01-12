/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.dynamictp.core.support.task.wrapper;

import org.dromara.dynamictp.common.util.StringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * TaskWrapperHolder related
 *
 * @author yanhom
 * @since 1.0.4
 **/
public class TaskWrappers {

    private static final List<TaskWrapper> TASK_WRAPPERS = Lists.newArrayList();

    private TaskWrappers() {
        ServiceLoader<TaskWrapper> loader = ServiceLoader.load(TaskWrapper.class);
        for (TaskWrapper taskWrapper : loader) {
            TASK_WRAPPERS.add(taskWrapper);
        }

        TASK_WRAPPERS.add(new TtlTaskWrapper());
        TASK_WRAPPERS.add(new MdcTaskWrapper());
    }

    public List<TaskWrapper> getByNames(Set<String> names) {
        if (CollectionUtils.isEmpty(names)) {
            return Collections.emptyList();
        }

        return TASK_WRAPPERS.stream().filter(t -> StringUtil.containsIgnoreCase(t.name(), names)).collect(toList());
    }

    public static TaskWrappers getInstance() {
        return TaskWrappersHolder.INSTANCE;
    }

    private static class TaskWrappersHolder {
        private static final TaskWrappers INSTANCE = new TaskWrappers();
    }
}
