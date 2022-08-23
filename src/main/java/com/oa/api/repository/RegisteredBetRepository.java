package com.oa.api.repository;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.entity.RegisteredBetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface RegisteredBetRepository extends PagingAndSortingRepository<RegisteredBetDTO, Long> {

    Page findAll(Pageable pageable);
}
