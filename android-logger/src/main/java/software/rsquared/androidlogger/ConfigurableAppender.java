package software.rsquared.androidlogger;

/**
 * @author Rafał Zajfert
 */
public interface ConfigurableAppender<E extends AppenderConfig> {

	E getConfig();

}
