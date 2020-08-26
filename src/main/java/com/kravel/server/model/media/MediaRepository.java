package com.kravel.server.model.media;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {

//    public Page<Media> findAll(Pageable pagealbe);
}
