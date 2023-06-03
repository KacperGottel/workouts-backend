package pl.kacperg.workoutsbackend.utils.parser;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortParser {

    private SortParser() {
        throw new IllegalStateException("Utility class");
    }

    public static Sort parseSort(String sort) {
        String[] sortArr = sort.split(",");
        String field = sortArr[0];
        Sort.Direction direction = Sort.Direction.fromString(sortArr[1].trim());
        return Sort.by(direction, field);
    }
}
