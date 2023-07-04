package africa.vote.SmartVote.datas.enums;

import africa.vote.SmartVote.exeptions.GenericException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public enum Category {
    COHORT_I,
    COHORT_II,
    COHORT_III,
    COHORT_IV,
    COHORT_V;

    public static Category getCategory(String input){
        List<Category> categoryList = Arrays.asList(
                COHORT_I,
                COHORT_II,
                COHORT_III,
                COHORT_IV,
                COHORT_V);
        for (Category category : categoryList) {
            if(Objects.equals(category.name(), input.toUpperCase())) return category;

        }
        throw new GenericException("Plan does not Exist");
    }
}
