package config;

public interface SystemDefaultProperty {

  int ISSUE_NUMBER_LENGTH = 6;

  String REFERENCE_TEMPLATE = "refs/%s/%s";

  String DEFAULT_REMOTE = "origin";

  String ENCODE = "UTF-8";

  String DEFAULT_EDITOR = "vim";

  int ISSUE_LENGTH = 20;

  int TITLE_LENGTH = 50;

  int ASSIGNEE_LENGTH = 27;

  int ASSIGNER_LENGTH = ASSIGNEE_LENGTH;

  int STATUS_LENGTH = 21;
}
