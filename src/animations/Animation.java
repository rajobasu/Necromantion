package animations;

import imageHandlers.ImageLoader;

public abstract class Animation {

	protected AnimationType animationType;
	protected ImageLoader imgldr;
	
	protected double sceneIndex;
	protected double deltaIndex;
	protected final int animationLength;
	
	public Animation(AnimationType animationType,int animationLength) {
		this.animationType=animationType;
		this.animationLength=animationLength;
		
		deltaIndex=1;
	}
	public abstract void updateAnimation();
	
	public int getSceneIndex(){
		return (int)sceneIndex;
	}
	public abstract boolean hasEnded();
}
