package com.asd.reversi.factory;

import com.asd.reversi.reversi.strategy.Strategy;
import com.asd.reversi.reversi.strategy.StrategyImpl;

public class StrategyImplFactory implements StrategyFactory{

    @Override
    public Strategy createStrategy() {
     return new StrategyImpl();
    }
}
