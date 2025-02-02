/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.continew.starter.extension.crud.handler;

import top.continew.starter.extension.crud.annotation.CrudApi;
import top.continew.starter.extension.crud.controller.BaseController;

import java.lang.reflect.Method;

/**
 * CRUD API 处理器
 *
 * @author Charles7c
 * @since 2.7.5
 */
public interface CrudApiHandler<T extends BaseController> {

    /**
     * 获取处理器控制器类
     *
     * @return 处理器控制器类
     */
    Class<T> getHandlerControllerClass();

    /**
     * 前置处理
     *
     * @param crudApi      CRUD API 注解
     * @param targetMethod 目标方法
     * @param targetClass  目标类
     * @throws Exception 处理异常
     */
    void preHandle(CrudApi crudApi, Method targetMethod, Class<?> targetClass) throws Exception;
}
