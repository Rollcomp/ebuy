package org.ebuy.helper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Burak Köken on 22.4.2020.
 */
public class HelperMethods {

    public static Set<String> splitStringByComma(String value) {
        Optional<String> textOptional = Optional.ofNullable(value);
        if(textOptional.isPresent()) {
            String[] result = textOptional.get().split(",");
            return Arrays.stream(result).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
