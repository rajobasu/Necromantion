package animations;

public abstract class ExclusiveAnimation extends Animation {

	protected boolean hasEnded;

	public ExclusiveAnimation(int animationLength) {
		super(AnimationType.EXIT_WHEN_END, animationLength);

	}

	@Override
	public void updateAnimation() {
		sceneIndex += deltaIndex;
		if (sceneIndex >= animationLength) {
			hasEnded = true;
			sceneIndex -= deltaIndex;
		}
		
	}

	public boolean hasEnded() {
		return hasEnded;
	}
}
