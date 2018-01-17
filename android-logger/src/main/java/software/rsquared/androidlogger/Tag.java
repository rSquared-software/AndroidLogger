package software.rsquared.androidlogger;

/**
 * @author Rafał Zajfert
 */
public final class Tag {
	final String value;

	public Tag(String value) {
		this.value = value;
	}

	public static Tag create(String value) {
		return new Tag(value);
	}
}
