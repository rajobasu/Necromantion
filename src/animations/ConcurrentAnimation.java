package animations;

public abstract class ConcurrentAnimation extends Animation {

	public ConcurrentAnimation(AnimationType animationType, int animationLength) {
		super(animationType, animationLength);
		// TODO Auto-generated constructor stub
	}

	public void updateAnimation() {

		sceneIndex += deltaIndex;

		if (sceneIndex >= animationLength) {
			if (animationType == AnimationType.LOOP_BACK_WHEN_END) {
				sceneIndex -= 2 * deltaIndex;
				deltaIndex = -deltaIndex;

			} else {
				sceneIndex = 0;
			}

		} else if (sceneIndex <= 0 && deltaIndex < 0) {
			if (animationType == AnimationType.LOOP_BACK_WHEN_END) {
				deltaIndex = -deltaIndex;

			} else {
				sceneIndex = animationLength - 1;
			}

		}
	}

	@Override
	public boolean hasEnded() {
		return false;
	}

}
