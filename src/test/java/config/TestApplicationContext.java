package config;

import java.util.Arrays;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.util.StringUtil;

import com.google.gson.Gson;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.shell.ParameterResolver;
import org.springframework.shell.Shell;

/**
 *
 * @author Rugal Bernstein
 */
@Configuration
public class TestApplicationContext {

  @Bean
  @ConditionalOnMissingBean
  public ParameterResolver parameterResolver() {
    return Mockito.mock(ParameterResolver.class);
  }

  @Bean
  @ConditionalOnMissingBean
  public Shell shell() {
    return Mockito.mock(Shell.class);
  }

  @Bean
  @ConditionalOnMissingBean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public byte[] testData() {
    return StringUtil.getByte(TestConstant.NO_PARENT_COMMIT);
  }

  @Bean
  @ConditionalOnMissingBean
  public PersonIdent personIdent() {
    return new PersonIdent("Rugal Bernstein", "test@mail.com");
  }

  @Bean
  @Scope("prototype")
  public RawIssue.Content rawContent() {
    final RawIssue.Content content = new RawIssue.Content();
    content.setTitle(Mockito.mock(ObjectId.class));
    content.setBody(Mockito.mock(ObjectId.class));
    content.setLabel(Mockito.mock(ObjectId.class));
    return content;
  }

  @Bean
  @Scope("prototype")
  public RawIssue rawIssue(final RawIssue.Content rawContent) {
    final RawIssue rawIssue = new RawIssue();
    rawIssue.setContent(rawContent);
    rawIssue.setCommit(Mockito.mock(ObjectId.class));
    rawIssue.setTree(Mockito.mock(ObjectId.class));
    return rawIssue;
  }

  @Bean
  @Scope("prototype")
  public Issue.User user() {
    final Issue.User user = new Issue.User();
    user.setAuthor("Rugal Bernstein");
    user.setEmail("test@mail.com");
    return user;
  }

  @Bean
  @Scope("prototype")
  public Issue.Commit issueCommit(final Issue.User user) {
    final Issue.Commit commit = new Issue.Commit();
    commit.setAssignee(user);
    commit.setAssigner(user);
    return commit;
  }

  @Bean
  @Scope("prototype")
  public Issue.Content issueContent() {
    final Issue.Content content = new Issue.Content();
    content.setBody(Constant.BODY);
    content.setTitle(Constant.TITLE);
    content.setLabel(Arrays.asList(Constant.ASSIGNEE, Constant.ASSIGNER));
    return content;
  }

  @Bean
  @Scope("prototype")
  public Issue issue(final Issue.Commit commit, Issue.Content content) {
    return Issue.builder()
      .commit(commit)
      .content(content)
      .build();
  }

  @Bean
  @Scope("prototype")
  public ObjectLoader objectLoader(final byte[] testData) {
    return new ObjectLoader.SmallObject(Constants.OBJ_BLOB, testData);
  }
}
