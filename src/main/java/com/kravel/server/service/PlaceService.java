package com.kravel.server.service;

import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceMapDTO;
import com.kravel.server.dto.place.ScrapDTO;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.update.PlaceUpdateDTO;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.mapping.ScrapRepository;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.*;
import com.kravel.server.model.review.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceQueryRepository placeQueryRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;
    private final ReviewQueryRepository reviewQueryRepository;

    @Transactional(readOnly = true)
    public Page<PlaceMapDTO> findAllByLocation(double latitude, double longitude, double height, double width, String speech, Pageable pageable, boolean reviewCount) throws Exception {

        Page<Place> places = placeQueryRepository.findAllByLocation(latitude, longitude, height, width, speech, pageable);

        Page<PlaceMapDTO> placeMapDTOs = (Page<PlaceMapDTO>) places.map(new Function<Place, PlaceMapDTO>() {
            @Override
            public PlaceMapDTO apply(Place source) {
                return PlaceMapDTO.fromEntity(source);
            }
        });

        if (reviewCount) {
            placeMapDTOs.forEach(dto -> {
                try {
                    dto.setReviewCount(reviewQueryRepository.findCountByPlace(dto.getPlaceId()));
                } catch (Exception exception) {
                    throw new InvalidRequestException("🔥 error: " + exception.getMessage());
                }
            });
        }

        return placeMapDTOs;
    }

    public PlaceDTO findPlaceById(long placeId, String speech) throws Exception {

        Place place = placeQueryRepository.findById(placeId, speech).orElseThrow(() -> new NotFoundException("🔥 error: is not exist place"));
        return PlaceDTO.fromEntity(place);
    }

    public long handlePlaceScrap(long placeId, long memberId, ScrapDTO scrapDTO) throws Exception {
        Optional<Scrap> savedScrap = placeQueryRepository.checkScrapState(placeId, memberId);

        if (scrapDTO.isScrap()) {
            if (savedScrap.isPresent()) {
                throw new InvalidRequestException("🔥 error: scrap is already exist");
            }

            Scrap scrap = Scrap.builder()
                    .member(memberRepository.findById(memberId).get())
                    .place(placeRepository.findById(placeId).get())
                    .build();

            return scrapRepository.save(scrap).getId();

        } else if (!scrapDTO.isScrap()) {
            if (savedScrap.isEmpty()) {
                throw new InvalidRequestException("🔥 error: scrap is not exist");
            }
            scrapRepository.delete(savedScrap.get());
            return -1;

        } else {
            throw new InvalidRequestException("🔥 error: is not valid scarp state");
        }
    }

    public long savePlace(PlaceUpdateDTO placeUpdateDTO) throws Exception {
        Place place = new Place(placeUpdateDTO);
        return placeRepository.save(place).getId();
    }
}