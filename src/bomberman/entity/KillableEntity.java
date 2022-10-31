package bomberman.entity;

public interface KillableEntity extends Entity {

    void die();

    void reduceHealth(int damage);

    int getHealth();

}
