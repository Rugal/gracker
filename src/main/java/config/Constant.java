package config;

/**
 * Store constant string.
 *
 * @author Rugal Bernstein
 */
public interface Constant {

  String TITLE = "title";

  String LABEL = "label";

  String BODY = "body";

  String ISSUE = "issue";

  String REFERENCE = "issue";

  String ASSIGNEE = "assignee";

  String CREATE = "create";

  String UPDATE = "update";

  String ASSIGNER = "assigner";

  String STATUS = "status";

  String ERROR = "ERROR";

  String NULL = "NULL";

  String DEBUG = "DEBUG";

  String TRACE = "TRACE";

  String AVAILABLE_LEVEL = "Available level: TRACE, DEBUG and ERROR";

  String ANY_FORMAT = "Any format of an issue id";

  String NO_ISSUE = "No issue found";

  String NO_ISSUE_FOR_ID = "No issue found with specified id";

  String NO_ID_ENTER = "Please enter id by either setting --id parameter or calling <use> command";

  String REMOTE_REPOSITORY = "The remote repository";
}
