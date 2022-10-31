package bomberman.entity;

import bomberman.constants.Direction;

public interface MovingEntity extends Entity {

    void move(double steps, Direction direction);

}
