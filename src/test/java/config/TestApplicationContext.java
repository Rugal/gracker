package config;

import ga.rugal.gracker.core.entity.Issue;
import ga.rugal.gracker.core.entity.RawIssue;
import ga.rugal.gracker.util.StringUtil;

import com.google.gson.Gson;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Rugal Bernstein
 */
@Configuration
public class TestApplicationContext {

  @Bean
  @ConditionalOnMissingBean
  public Gson gson() {
    return new Gson();
  }

  @Bean
  public byte[] testData() {
    return StringUtil.getByte("Rugal Bernstein");
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
  public RawIssue rawIssue(final RawIssue.Content rawContent) {
    final RawIssue rawIssue = new RawIssue();
    rawIssue.setContent(rawContent);
    rawIssue.setCommit(Mockito.mock(ObjectId.class));
    rawIssue.setTree(Mockito.mock(ObjectId.class));
    return rawIssue;
  }
}
