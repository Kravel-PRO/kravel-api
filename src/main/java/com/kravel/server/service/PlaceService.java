package com.kravel.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.common.S3Uploader;
import com.kravel.server.dto.media.PlaceRelatedMediaDTO;
import com.kravel.server.dto.place.PlaceDetailDTO;
import com.kravel.server.dto.place.PlaceDTO;
import com.kravel.server.dto.place.PlaceMapDTO;
import com.kravel.server.dto.place.ScrapDTO;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.update.PlaceUpdateDTO;
import com.kravel.server.enums.Speech;
import com.kravel.server.model.mapping.PlaceCelebrity;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.mapping.ScrapRepository;
import com.kravel.server.model.media.Media;
import com.kravel.server.model.media.MediaRepository;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.*;
import com.kravel.server.model.review.ReviewQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceQueryRepository placeQueryRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final MediaRepository mediaRepository;
    private final ScrapRepository scrapRepository;
    private final ReviewQueryRepository reviewQueryRepository;
    private final S3Uploader s3Uploader;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public Page<PlaceDTO> findAllByLocation(double latitude, double longitude, double height, double width, Speech speech, Pageable pageable, boolean reviewCount) throws Exception {

        Page<Place> places = placeQueryRepository.findAllByLocation(latitude, longitude, height, width, speech, pageable);
        return places.map(new Function<Place, PlaceDTO>() {
            @Override
            public PlaceDTO apply(Place source) {
                try {
                    source.getPlaceCelebrities().forEach(placeCelebrity -> placeCelebrity.getCelebrity().findInfoSpeech(speech));
                    PlaceDTO placeDTO = PlaceDTO.fromEntity(source);
                    if (reviewCount) {
                        placeDTO.setReviewCount(reviewQueryRepository.findCountByPlace(placeDTO.getPlaceId()));
                    }
                    return placeDTO;

                } catch (Exception exception) {
                    throw new InvalidRequestException("🔥 error: " + "PlaceDTO convert error");
                }
            }
        });
    }

    public PlaceDetailDTO findPlaceById(long placeId, Speech speech, long memberId) throws Exception {

        Place place = placeQueryRepository
                .findById(placeId, speech)
                .orElseThrow(() -> new NotFoundException("🔥 error: is not exist place"));

        place.findInfoSpeech(speech);
        place.findTagSpeech(speech);
        Optional.ofNullable(place.getMedia())
                .orElse(new Media())
                .findInfoSpeech(speech);
        for (int i=0; i<place.getPlaceCelebrities().size(); i++) {
            place.getPlaceCelebrities().get(i).getCelebrity().findInfoSpeech(speech);
        }

        PlaceDetailDTO placeDetailDTO = PlaceDetailDTO.fromEntity(place);
        placeDetailDTO.setScrap(placeQueryRepository.checkScrapState(placeId, memberId));
        return placeDetailDTO;
    }

    public long handlePlaceScrap(long placeId, long memberId, ScrapDTO scrapDTO) throws Exception {
        Optional<Scrap> savedScrap = placeQueryRepository.findScrapByPlaceAndMember(placeId, memberId);

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
        Place place = new Place(placeUpdateDTO, s3Uploader, objectMapper);
        return placeRepository.save(place).getId();
    }

    public List<PlaceMapDTO> findMapByLocation(double latitude, double longitude, double height, double width) throws Exception {
        return placeQueryRepository.findMapByLocation(latitude, longitude, height, width).stream()
                .map(PlaceMapDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PlaceRelatedMediaDTO> findAllByMedia(long mediaId, Speech speech, Pageable pageable) throws Exception {

        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NotFoundException("🔥 error: iS not exist media"));

        List<Place> places = placeQueryRepository.findAllByMedia(mediaId, speech, pageable);
        places.forEach(place -> place.findTagSpeech(speech));

        return places.stream()
                .map(PlaceRelatedMediaDTO::fromEntity)
                .collect(Collectors.toList());
    }
}