package com.bruce.intellijplugin.generatesetter;

import com.bruce.intellijplugin.generatesetter.utils.StringUtils;
import com.intellij.psi.PsiMethod;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author xuzhao
 * @desc 推荐getInfo
 **/
public class RecommendGetInfo {

    public static final String SET = "set";

    private Map<String, List<GetInfo>> groupParameterMap;

    public RecommendGetInfo() {
    }

    public static RecommendGetInfo build(List<GetInfo> infoList, List<String> fieldNameList) {

        RecommendGetInfo recommendGetInfo = new RecommendGetInfo();

        Map<String, List<GetInfo>> map = fieldNameList.stream()
            .flatMap(e -> infoList.stream().filter(f -> isMeetRecommend(f, e)).map(g -> Pair.of(e, g)))
            .collect(
                Collectors.groupingBy(e -> e.getKey(), Collectors.mapping(e -> e.getValue(), Collectors.toList())));

        recommendGetInfo.groupParameterMap = map;

        return recommendGetInfo;
    }

    public GetInfo recommend(String fieldName){
        if(Objects.isNull(fieldName)||fieldName.trim().length()==0){
            return null;
        }
        List<GetInfo> list = groupParameterMap.getOrDefault(fieldName, Collections.EMPTY_LIST);
        if (list.size()==1) {
            return list.get(0);
        }
        return null;
    }

    public GetInfo recommendBySetMethod(PsiMethod method){
        if (method.getName().startsWith(SET)){
            return recommend(StringUtils.firstLowCase(method.getName().substring(3)));
        }
        return null;
    }

    private static boolean isMeetRecommend(GetInfo getInfo,String fieldName){

        // 类名首字母小写+字段属性名首字母大写。符合目标字段属性值时。做推荐匹配。
        // 形如：Teacher、id  ===>  teacherId
        return getInfo.getNameToMethodMap().keySet().stream().anyMatch(e->{
            if (fieldName.equals(e)){
                return true;
            }
            if (!fieldName.startsWith(getInfo.firstLowCaseParamType())){
                return false;
            } else {
                String str = getInfo.firstLowCaseParamType()+StringUtils.firstUpperCase(e);
                return str.equals(fieldName);
            }
        });
    }

}
