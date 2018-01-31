package increment.simulator.util;
/**
 * Methods for exception handling.
 * @author Xu Ke
 *
 */
public class ExceptionHandling {
	/**
	 * Throws an exception when panic.
	 * @param errmsg
	 * @throws IllegalStateException When called.
	 */
	public static void panic(String errmsg) {
		throw new IllegalStateException(errmsg);
	}
}
