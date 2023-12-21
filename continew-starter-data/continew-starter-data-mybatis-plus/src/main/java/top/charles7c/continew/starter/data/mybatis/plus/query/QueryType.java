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

package top.charles7c.continew.starter.data.mybatis.plus.query;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 查询类型枚举
 *
 * @author Charles7c
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor
public enum QueryType {

    /**
     * 等值查询，例如：WHERE `age` = 18
     */
    EQUAL(1, "="),
    /**
     * 非等值查询，例如：WHERE `age` != 18
     */
    NOT_EQUAL(2, "!="),
    /**
     * 大于查询，例如：WHERE `age` > 18
     */
    GREATER_THAN(3, ">"),
    /**
     * 小于查询，例如：WHERE `age` < 18
     */
    LESS_THAN(4, "<"),
    /**
     * 大于等于查询，例如：WHERE `age` >= 18
     */
    GREATER_THAN_OR_EQUAL(5, ">="),
    /**
     * 小于等于查询，例如：WHERE `age` <= 18
     */
    LESS_THAN_OR_EQUAL(6, "<="),
    /**
     * 范围查询，例如：WHERE `age` BETWEEN 10 AND 18
     */
    BETWEEN(7, "BETWEEN"),
    /**
     * 左模糊查询，例如：WHERE `nickname` LIKE '%s'
     */
    LEFT_LIKE(8, "LIKE '%s'"),
    /**
     * 中模糊查询，例如：WHERE `nickname` LIKE '%s%'
     */
    INNER_LIKE(9, "LIKE '%s%'"),
    /**
     * 右模糊查询，例如：WHERE `nickname` LIKE 's%'
     */
    RIGHT_LIKE(10, "LIKE 's%'"),
    /**
     * 包含查询，例如：WHERE `age` IN (10, 20, 30)
     */
    IN(11, "IN"),
    /**
     * 不包含查询，例如：WHERE `age` NOT IN (20, 30)
     */
    NOT_IN(12, "NOT IN"),
    /**
     * 空查询，例如：WHERE `email` IS NULL
     */
    IS_NULL(13, "IS NULL"),
    /**
     * 非空查询，例如：WHERE `email` IS NOT NULL
     */
    IS_NOT_NULL(14, "IS NOT NULL"),
    ;

    private final Integer value;
    private final String description;
}
