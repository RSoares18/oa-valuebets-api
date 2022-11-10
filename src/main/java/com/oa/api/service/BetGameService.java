package com.oa.api.service;

import com.oa.api.entity.BetGameDTO;
import com.oa.api.repository.BetGameRepository;
import com.oa.api.util.BetGameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class BetGameService {

    @Autowired
    private BetGameRepository betGameRepository;

    private BetGameConverter betGameConverter = new BetGameConverter();

    public BetGameService(){}

    public BetGameDTO createBetGame(HashMap<String, Object> betGame){
        return betGameRepository.save(betGameConverter.converModeltoDTO(betGame));
    }

    public List<BetGameDTO> getGamesByMarket(String market){
        return betGameRepository.findGameByMarket(market);
    }

    public List<BetGameDTO> getGamesByMarkets(List<String> markets){
        return betGameRepository.findGameByMarketIn(markets);
    }

    public int getTotalRecords(){
        return (int) betGameRepository.count();
    }


}
