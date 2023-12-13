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

package top.charles7c.continew.starter.data.mybatis.plus.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.charles7c.continew.starter.core.exception.BadRequestException;
import top.charles7c.continew.starter.core.util.ReflectUtils;
import top.charles7c.continew.starter.core.util.validate.ValidationUtils;
import top.charles7c.continew.starter.data.mybatis.plus.annotation.Query;
import top.charles7c.continew.starter.data.mybatis.plus.enums.QueryType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询助手
 *
 * @author Zheng Jie（<a href="https://gitee.com/elunez/eladmin">ELADMIN</a>）
 * @author Charles7c
 * @since 1.0.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueryHelper {

    /**
     * 根据查询条件构建 MyBatis Plus 查询条件封装对象
     *
     * @param query 查询条件
     * @param <Q>   查询条件数据类型
     * @param <R>   查询数据类型
     * @return MyBatis Plus 查询条件封装对象
     */
    public static <Q, R> QueryWrapper<R> build(Q query) {
        QueryWrapper<R> queryWrapper = new QueryWrapper<>();
        // 没有查询条件，直接返回
        if (null == query) {
            return queryWrapper;
        }
        // 获取查询条件中所有的字段
        List<Field> fieldList = ReflectUtils.getNonStaticFields(query.getClass());
        fieldList.forEach(field -> buildQuery(query, field, queryWrapper));
        return queryWrapper;
    }

    /**
     * 构建 MyBatis Plus 查询条件封装对象
     *
     * @param query        查询条件
     * @param field        字段
     * @param queryWrapper MyBatis Plus 查询条件封装对象
     * @param <Q>          查询条件数据类型
     * @param <R>          查询数据类型
     */
    private static <Q, R> void buildQuery(Q query, Field field, QueryWrapper<R> queryWrapper) {
        boolean accessible = field.canAccess(query);
        try {
            field.setAccessible(true);
            // 没有 @Query，直接返回
            Query queryAnnotation = field.getAnnotation(Query.class);
            if (null == queryAnnotation) {
                return;
            }

            // 如果字段值为空，直接返回
            Object fieldValue = field.get(query);
            if (ObjectUtil.isEmpty(fieldValue)) {
                return;
            }

            // 解析查询条件
            parse(queryAnnotation, field.getName(), fieldValue, queryWrapper);
        } catch (BadRequestException e) {
            log.error("Build query occurred an validation error: {}. Query: {}, Field: {}.", e.getMessage(), query,
                    field, e);
            throw e;
        } catch (Exception e) {
            log.error("Build query occurred an error: {}. Query: {}, Field: {}.", e.getMessage(), query, field, e);
        } finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * 解析查询条件
     *
     * @param queryAnnotation 查询注解
     * @param fieldName       字段名
     * @param fieldValue      字段值
     * @param queryWrapper    MyBatis Plus 查询条件封装对象
     * @param <R>             查询数据类型
     */
    private static <R> void parse(Query queryAnnotation, String fieldName, Object fieldValue,
                                  QueryWrapper<R> queryWrapper) {
        // 解析多属性模糊查询
        // 如果设置了多属性模糊查询，分割属性进行条件拼接
        String[] blurryPropertyArr = queryAnnotation.blurry();
        if (ArrayUtil.isNotEmpty(blurryPropertyArr)) {
            queryWrapper.and(wrapper -> {
                for (String blurryProperty : blurryPropertyArr) {
                    wrapper.or().like(StrUtil.toUnderlineCase(blurryProperty), fieldValue);
                }
            });
            return;
        }

        // 解析单个属性查询
        // 如果没有单独指定属性名，就和使用该注解的属性的名称一致
        // 注意：数据库规范中列采用下划线连接法命名，程序规范中变量采用驼峰法命名
        String property = queryAnnotation.property();
        String columnName = StrUtil.toUnderlineCase(StrUtil.blankToDefault(property, fieldName));
        QueryType queryType = queryAnnotation.type();
        switch (queryType) {
            case EQUAL -> queryWrapper.eq(columnName, fieldValue);
            case NOT_EQUAL -> queryWrapper.ne(columnName, fieldValue);
            case GREATER_THAN -> queryWrapper.gt(columnName, fieldValue);
            case LESS_THAN -> queryWrapper.lt(columnName, fieldValue);
            case GREATER_THAN_OR_EQUAL -> queryWrapper.ge(columnName, fieldValue);
            case LESS_THAN_OR_EQUAL -> queryWrapper.le(columnName, fieldValue);
            case BETWEEN -> {
                List<Object> between = new ArrayList<>((List<Object>) fieldValue);
                ValidationUtils.throwIf(between.size() != 2, "[{}] 必须是一个范围", fieldName);
                queryWrapper.between(columnName, between.get(0), between.get(1));
            }
            case LEFT_LIKE -> queryWrapper.likeLeft(columnName, fieldValue);
            case INNER_LIKE -> queryWrapper.like(columnName, fieldValue);
            case RIGHT_LIKE -> queryWrapper.likeRight(columnName, fieldValue);
            case IN -> {
                ValidationUtils.throwIfEmpty(fieldValue, "[{}] 不能为空", fieldName);
                queryWrapper.in(columnName, (List<Object>) fieldValue);
            }
            case NOT_IN -> {
                ValidationUtils.throwIfEmpty(fieldValue, "[{}] 不能为空", fieldName);
                queryWrapper.notIn(columnName, (List<Object>) fieldValue);
            }
            case IS_NULL -> queryWrapper.isNull(columnName);
            case IS_NOT_NULL -> queryWrapper.isNotNull(columnName);
            default -> throw new IllegalArgumentException(String.format("暂不支持 [%s] 查询类型", queryType));
        }
    }
}
