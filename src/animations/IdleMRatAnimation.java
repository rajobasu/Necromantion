package animations;

public class IdleMRatAnimation extends ConcurrentAnimation {

	public IdleMRatAnimation() {
		super(AnimationType.LOOP_FRONT_WHEN_END, 10);
		deltaIndex=0.5;
	}

}
