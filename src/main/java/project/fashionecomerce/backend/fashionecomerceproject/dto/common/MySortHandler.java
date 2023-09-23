package project.fashionecomerce.backend.fashionecomerceproject.dto.common;

import org.springframework.data.domain.Sort;

public record MySortHandler(Sort sort) {
    public static Sort of(String sorter) {
        String[] sorterSplit = sorter.split("_");
        if(sorterSplit.length == 2) {
            if(sorterSplit[1].equals("ASC")) {
                return Sort.by(sorterSplit[0]).ascending();
            }
            if(sorterSplit[1].equals("DECS")) {
                return Sort.by(sorterSplit[0]).descending();
            }
        }
        return Sort.by("createdAt").descending();
    }
}
