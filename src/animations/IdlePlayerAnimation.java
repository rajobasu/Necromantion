package animations;

public class IdlePlayerAnimation extends ConcurrentAnimation {

	public IdlePlayerAnimation() {
		super(AnimationType.LOOP_BACK_WHEN_END, 10);
	}
}
