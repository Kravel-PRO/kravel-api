package com.kravel.server.service;

import com.kravel.server.dto.article.PlaceDTO;
import com.kravel.server.dto.article.PlaceMapDTO;
import com.kravel.server.dto.article.ScrapDTO;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.exception.NotFoundException;
import com.kravel.server.dto.update.PlaceUpdateDTO;
import com.kravel.server.model.mapping.Scrap;
import com.kravel.server.model.mapping.ScrapRepository;
import com.kravel.server.model.member.MemberRepository;
import com.kravel.server.model.place.Place;
import com.kravel.server.model.place.PlaceQueryRepository;
import com.kravel.server.model.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {

    private final PlaceQueryRepository placeQueryRepository;
    private final PlaceRepository placeRepository;
    private final MemberRepository memberRepository;
    private final ScrapRepository scrapRepository;


    public List<PlaceMapDTO> findAllPlacesByLocation(double latitude, double longitude, double height, double width, String speech, Pageable pageable) throws Exception {

        List<PlaceMapDTO> placeMapDTOs = placeQueryRepository.findAllPlacesByLocation(latitude, longitude, height, width, speech, pageable).stream()
                .map(PlaceMapDTO::fromEntity)
                .collect(Collectors.toList());
        if (placeMapDTOs.isEmpty()) {
            throw new NotFoundException("🔥 error: is not exist Article List");
        }

        return placeMapDTOs;
    }

    public PlaceDTO findPlaceById(long placeId, String speech) throws Exception {

        Place place = placeRepository.findById(placeId).orElseThrow(() -> new NotFoundException("🔥 error: is not exist place"));
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