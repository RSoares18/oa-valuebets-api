package com.oa.api.repository;

import com.oa.api.entity.BetGameDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface BetGameRepository extends PagingAndSortingRepository<BetGameDTO, Long> {

    List<BetGameDTO> findGameByMarket(String market);
    List<BetGameDTO> findGameByMarketIn(List<String> markets);
    //List<BetGameDTO> findGameByMarketAndLatest1xbetOdds(String market, Double latest_1xbet_odds);
    Page findAll(Pageable pageable);
}
