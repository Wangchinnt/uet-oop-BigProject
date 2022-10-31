package bomberman.entity;

import bomberman.entity.boundedbox.RectBoundedBox;

public interface Entity {

    void update();

    boolean isColliding(Entity b);

    boolean isPlayerCollisionFriendly();

    void draw();

    void removeFromScene();

    double getPositionX();

    double getPositionY();

    RectBoundedBox getBoundingBox();

    int getLayer();

    double getScale();
}
