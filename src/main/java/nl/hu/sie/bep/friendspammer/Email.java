package nl.hu.sie.bep.friendspammer;

public class Email {

  private String to;
  private String from;
  private String subject;
  private String text;
  private boolean ashtml;

  public Email(String to, String from, String subject, String text, boolean ashtml) {
    this.to = to;
    this.from = from;
    this.subject = subject;
    this.text = text;
    this.ashtml = ashtml;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public boolean isAshtml() {
    return ashtml;
  }

  public void setAshtml(boolean ashtml) {
    this.ashtml = ashtml;
  }

  @Override
  public String toString() {
    return "Email{" +
      "to='" + to + '\'' +
      ", from='" + from + '\'' +
      ", subject='" + subject + '\'' +
      ", text='" + text + '\'' +
      ", ashtml=" + ashtml +
      '}';
  }


}
