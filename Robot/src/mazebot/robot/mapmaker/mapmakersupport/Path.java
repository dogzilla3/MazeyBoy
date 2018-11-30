package mazebot.robot.mapmaker.mapmakersupport;

public class Path {
    private DestinationPoint start;
    private DestinationPoint end;
    
    //Consttuctors
    public Path() {}
    
    public Path(DestinationPoint startPoint){
    	start = startPoint;
    	end = start;
    }
    
    public Path(DestinationPoint startPoint, DestinationPoint endPoint){
    	start = startPoint;
    	end = endPoint;
    }
    
    //Setters
    public DestinationPoint setStartPoint(DestinationPoint newStart) {
    	start = newStart;
    	end = start;
    	return newStart;
    }
    
    public DestinationPoint appendPath(DestinationPoint nextPoint) {
    	nextPoint.setPreviousDestination(end);
    	end = nextPoint;
    	return nextPoint;
    }
    
    //Getters
    public DestinationPoint getStartPoint() {
    	return start;
    }
    public DestinationPoint getEndPoint() {
    	return end;
    }
    
    //BackTrack
    public void backTrackPath() {
    	end = end.getPreviousDestination();
    }
}