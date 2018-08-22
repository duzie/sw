package com.f.sw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BUIPage {
    int start;
    int limit;
    int pageIndex;
    Sort sort;
    Map<String, String> param = new HashMap<>();

    public Pageable getPageable() {
        return PageRequest.of(pageIndex, limit, sort);
    }
}
