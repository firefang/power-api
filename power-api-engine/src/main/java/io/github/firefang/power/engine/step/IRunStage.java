package io.github.firefang.power.engine.step;

/**
 * Stage of running steps
 * 
 * @author xinufo
 *
 */
public interface IRunStage {
    byte PROJECT_START_PLAIN = 1;
    byte PROJECT_START = 2;
    byte API_START_PLAIN = 3;
    byte API_START = 4;
    byte CASE_START_PLAIN = 5;
    byte CASE_START = 6;
    byte CASE_END_PLAIN = 7;
    byte CASE_END = 8;
    byte API_END_PLAIN = 9;
    byte API_END = 10;
    byte PROJECT_END_PLAIN = 11;
    byte PROJECT_END = 12;
}
