package com.kravel.server.api.article.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Celebrity {

    private long celebrityId;
    private long mediaId;
    private long creator;
    private String celebrityName;
    private Date createDe;
    private Date updateDe;
    private String useAt;
    private String imgUrl;

}
