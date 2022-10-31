package bomberman.entity.enermy.Astar;

import bomberman.entity.Entity;
import bomberman.entity.staticobject.Brick;
import bomberman.entity.staticobject.Wall;
import bomberman.view.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PathFinder {


  Node[][] node;
  List<Node> openList = new ArrayList<>();
  public List<Node> pathList = new ArrayList<>();
  Node startNode, goalNode, currentNode;
  boolean goalReached = false;
  int step = 0;
  int maxCol = Game.getMap().getCol();
  int maxRow = Game.getMap().getRow();

  public PathFinder() {
    instantiateNodes();
  }

  public void instantiateNodes() {

    System.out.println(maxCol + " " + maxRow + "");
    node = new Node[maxCol][maxRow + 1];
    for (int row = 1; row <= maxRow; row++) {
    for (int col = 0; col < maxCol; col++) {
        node[col][row] = new Node(col, row);
      }
    }
  }

  public void resetNodes() {
    for (int row = 1; row <= maxRow; row++) {
    for (int col = 0; col < maxCol; col++) {
        node[col][row].open = false;
        node[col][row].checked = false;
        node[col][row].checked = false;
      }
    }

    openList.clear();
    pathList.clear();
    goalReached = false;
    step = 0;
  }

  public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {

    Vector<Entity> e = Game.getEntities();
    resetNodes();

    startNode = node[startCol][startRow];
    currentNode = startNode;
    goalNode = node[goalCol][goalRow];
    openList.add(currentNode);

    for (int j = e.size() - 1; j >= 0; j--) {
      if (e.get(j) instanceof Wall || e.get(j) instanceof Brick) {
        node[(int) (e.get(j).getPositionX()) / 64][(int) (e.get(j).getPositionY()) / 64].solid = true;
      }
    }

    // SET COST
    for (int row = 1; row <= maxRow; row++) {
    for (int col = 0; col < maxCol; col++) {
        getCost(node[col][row]);
      }
    }
  }

  public void getCost(Node node) {

    // G cost
    int xDistance = Math.abs(node.col - startNode.col);
    int yDistance = Math.abs(node.row - startNode.row);
    node.gCost = xDistance + yDistance;
    // H cost
    xDistance = Math.abs(node.col - goalNode.col);
    yDistance = Math.abs(node.row - goalNode.row);
    node.hCost = xDistance + yDistance;
    // F cost
    node.fCost = node.gCost + node.hCost;
  }

  public boolean search() {


    while (!goalReached && step < 500) {

      int col = currentNode.col;
      int row = currentNode.row;

      currentNode.checked = true;
      openList.remove(currentNode);
      if (row - 1 > 0) {
        openNode(node[col][row - 1]);
      }
      if (col - 1 >= 0) {
        openNode(node[col - 1][row]);
      }
      if (row + 1 <= maxRow) {
        openNode(node[col][row + 1]);
      }
      if (col + 1 < maxCol) {
        openNode(node[col + 1][row]);
      }

      int bestNodeIndex = 0;
      int bestNodefCost = 999;

      for (int i = 0; i < openList.size(); i++) {

        if (openList.get(i).fCost < bestNodefCost) {
          bestNodeIndex = i;
          bestNodefCost = openList.get(i).fCost;
        } else if (openList.get(i).fCost == bestNodefCost) {
          if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
            bestNodeIndex = i;
          }
        }
      }

      if (openList.size() == 0) {
        break;
      }

      currentNode = openList.get(bestNodeIndex);

      if (currentNode == goalNode) {
        goalReached = true;
        trackThePath();
      }
      step++;
    }
    return goalReached;
  }

  public void openNode(Node node) {
    if (!node.open && !node.checked && !node.solid) {

      node.open = true;
      node.parent = currentNode;
      openList.add(node);
    }
  }
  public void trackThePath() {

    Node current = goalNode;

    while(current!= startNode) {
      pathList.add(0, current);
      current = current.parent;
    }
  }
}
