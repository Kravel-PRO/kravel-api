package com.kravel.server.model.place;

import com.kravel.server.dto.update.PhotoFilterUpdateDTO;
import com.kravel.server.model.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor
public class PhotoFilter extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "photo_filter_id")
    private long id;

    @Lob
    private String guideLine;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @Builder
    public PhotoFilter(long id, String guideLine) {
        this.id = id;
        this.guideLine = guideLine;
    }

    public PhotoFilter(PhotoFilterUpdateDTO photoFilterUpdateDTO) {
        this.guideLine = photoFilterUpdateDTO.getGuideLine();
    }
}
