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
