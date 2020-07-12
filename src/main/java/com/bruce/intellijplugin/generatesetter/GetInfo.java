/*
 *  Copyright (c) 2017-2019, bruce.ge.
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU General Public License
 *    as published by the Free Software Foundation; version 2 of
 *    the License.
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *    GNU General Public License for more details.
 *    You should have received a copy of the GNU General Public License
 *    along with this program;
 */

package com.bruce.intellijplugin.generatesetter;

import com.bruce.intellijplugin.generatesetter.utils.StringUtils;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTypesUtil;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author bruce.ge
 * @Date 2017/1/19
 * @Description
 */
public class GetInfo {

    /**
     * 参数类型
     */
    private PsiType paramType;

    private String paramName;

    private List<PsiMethod> getMethods;

    private Map<String, PsiMethod> nameToMethodMap;

    public Map<String, PsiMethod> getNameToMethodMap() {
        return nameToMethodMap;
    }

    /**
     * 是否是 以类型为前缀的属性
     * 如果是则需要去除
     * 否直接查询
     */
    public PsiMethod getPsiMethodByParamName(String param) {
        // 当前类型是java类型的参数或参数为空时
        if (isJavaObject()||Objects.isNull(param) || param.isEmpty()) {
            return null;
        }
        String str = firstLowCaseParamType();
        if (param.startsWith(str) && param.length() > str.length()) {
            String realParamName = StringUtils.firstLowCase(param.substring(str.length()));
            return nameToMethodMap.get(realParamName);
        }
        return nameToMethodMap.get(param);
    }

    public void setNameToMethodMap(Map<String, PsiMethod> nameToMethodMap) {
        this.nameToMethodMap = nameToMethodMap;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public List<PsiMethod> getGetMethods() {
        return getMethods;
    }

    public void setGetMethods(List<PsiMethod> getMethods) {
        this.getMethods = getMethods;
    }

    public PsiType getParamType() {
        return paramType;
    }

    public void setParamType(PsiType paramType) {
        this.paramType = paramType;
    }

    public String getStrParamType() {
        return paramType.getPresentableText();
    }

    public String firstLowCaseParamType() {
        return StringUtils.firstLowCase(getStrParamType());
    }

    /**
     * 是java 对象类型或基本类型
     * @return
     */
    public boolean isJavaObject() {
        // 包括基本类型 & java 包下的对象
        PsiClass parameterClass = PsiTypesUtil.getPsiClass(paramType);
        if (parameterClass == null || parameterClass.getQualifiedName().startsWith("java.")) {
            return true;
        }
        return false;
    }
}
