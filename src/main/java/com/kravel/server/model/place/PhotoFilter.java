package com.kravel.server.model.place;

import com.kravel.server.dto.update.PhotoFilterDTO;
import com.kravel.server.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class PhotoFilter extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "photo_filter_id")
    private long id;
    private String guideLine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public PhotoFilter(long id, String guideLine) {
        this.id = id;
        this.guideLine = guideLine;
    }

    public PhotoFilter(PhotoFilterDTO photoFilterDTO) {
        this.guideLine = photoFilterDTO.getGuideLine();
    }
}