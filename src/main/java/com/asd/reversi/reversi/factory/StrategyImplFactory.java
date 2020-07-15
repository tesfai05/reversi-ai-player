package com.asd.reversi.reversi.factory;

import com.asd.reversi.reversi.strategy.Strategy;
import com.asd.reversi.reversi.strategy.StrategyImplementation;

public class StrategyImplFactory implements StrategyFactory{

    @Override
    public Strategy createStrategy() {
     return new StrategyImplementation();
    }
}
