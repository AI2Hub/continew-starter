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

package top.continew.starter.extension.datapermission.model;

import top.continew.starter.extension.datapermission.enums.DataScope;

/**
 * 角色上下文
 *
 * @author Charles7c
 * @since 1.1.0
 */
public class RoleContext {

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 数据权限
     */
    private DataScope dataScope;

    public RoleContext() {
    }

    public RoleContext(String roleId, DataScope dataScope) {
        this.roleId = roleId;
        this.dataScope = dataScope;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public DataScope getDataScope() {
        return dataScope;
    }

    public void setDataScope(DataScope dataScope) {
        this.dataScope = dataScope;
    }
}
