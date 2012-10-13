package student;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An enumeration of all possible binary condition operators.
 */
public enum BinaryBooleanOperator {

	OR,
	AND;

	/**
	 * The list of operators.
	 */
	public static final List<BinaryBooleanOperator> VALUES =
			Collections.unmodifiableList(Arrays.asList(values()));
	/**
	 * The number of operators.
	 */
	public static final int NUM_OPS = VALUES.size();

}
