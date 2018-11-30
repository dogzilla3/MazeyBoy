package mazebot.robot.mapmaker.mapmakersupport;

public class Direction {
	
    boolean wall;
    boolean visited;
    boolean arrivedFrom;
    
    // If the Direction is a wall it will also be marked as visited, 
    // so that dectection will overlook it on visit tests
    public Direction(boolean walledOff){
        wall = walledOff;
        visited = walledOff;
        arrivedFrom = false;
    }

    public Direction(boolean walledOff, boolean cameFrom){
    	this(walledOff);
        arrivedFrom = cameFrom;
    }
    
    public Direction(Direction d){
        wall = d.isWall();
        visited = d.hasVisited();
        arrivedFrom = d.hasLeftFrom();
    }
    
    public boolean hasVisited()				{return visited;}
    public boolean isWall()					{return wall;}
    public boolean hasLeftFrom()			{return arrivedFrom;}

    public void setVisited(boolean visit)	{visited = visit;}

    public String getDirectionInfo(){
    	if(wall)
			return "W";
		else if(visited)
			return "V";
		else if(arrivedFrom)
			return "A";
		else
			return "0";
	}
}