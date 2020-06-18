package com.kravel.server.api.article.dto.celebrity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CelebrityDetailDTO {
    private CelebrityDTO celebrity;
    private CelebrityArticleDTO articles;
}
