package animations;

public class ToxicGasAnimation extends ConcurrentAnimation {

	public ToxicGasAnimation() {
		super(AnimationType.LOOP_BACK_WHEN_END,5);
		deltaIndex=0.2;
	}

}
