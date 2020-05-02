package org.ebuy.helper;

import org.springframework.util.StringUtils;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public class CategoryUtil {

    private static final char[] turkishCharacters = new char[]{'ğ', 'ı', 'ö', 'ş', 'ü'};
    private static final char[] replacedCharactersForTurkish = new char[]{'g', 'i', 'o', 's', 'u'};

    public static String beautifyCategoryName(String categoryName) {
        if(StringUtils.isEmpty(categoryName)) {
            return null;
        }
        categoryName = categoryName.trim().replaceAll("\\s{2,}", " ")
                .toLowerCase();
        categoryName = categoryName.replaceAll("\\s*[&]\\s*", "--");
        categoryName = categoryName.replaceAll("\\s", "-");
        categoryName = replaceTurkishCharacters(categoryName);
        return categoryName;
    }


    private static String replaceTurkishCharacters(String categoryName) {
        String result = categoryName;
        for(int index = 0; index < turkishCharacters.length; index++) {
            result = result.replace(turkishCharacters[index], replacedCharactersForTurkish[index]);
        }
        return result;
    }

}
