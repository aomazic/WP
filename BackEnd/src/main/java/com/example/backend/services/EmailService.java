package com.example.backend.services;

import com.example.backend.model.CartItem;
import com.example.backend.model.Order;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService{
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void send(String to,String name, String link) {
        try {
            MimeMessage  mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(buildEmail(name,link), true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("test@gmail.com");
            javaMailSender.send(mimeMessage);
            logger.info("email sent");

        }
        catch (MessagingException e) {
            logger.error("failed to send email", e);
            throw new IllegalStateException("failed to send email:");
        }
    }
    @Async
    public void sendOrder(Order order) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(buildOrderEmail(order), true);
            helper.setTo(order.getEmail());
            helper.setSubject("Order Confirmation");
            helper.setFrom("test@gmail.com");
            javaMailSender.send(mimeMessage);
            logger.info("email sent");
        } catch (MessagingException e) {
            logger.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }

    private String buildOrderEmail(Order order) {
        StringBuilder emailBuilder = new StringBuilder();

        emailBuilder.append("<div style=\"font-family: Helvetica, Arial, sans-serif; font-size: 16px; margin: 0; color: #0b0c0c\">\n");
        emailBuilder.append("\n");
        emailBuilder.append("<table role=\"presentation\" width=\"100%\" style=\"border-collapse: collapse; min-width: 100%; width: 100% !important;\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n");
        emailBuilder.append("  <tbody>\n");
        emailBuilder.append("    <tr>\n");
        emailBuilder.append("      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n");
        emailBuilder.append("        \n");
        emailBuilder.append("        <table role=\"presentation\" width=\"100%\" style=\"border-collapse: collapse; max-width: 580px;\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n");
        emailBuilder.append("          <tbody>\n");
        emailBuilder.append("            <tr>\n");
        emailBuilder.append("              <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n");
        emailBuilder.append("                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse;\">\n");
        emailBuilder.append("                  <tbody>\n");
        emailBuilder.append("                    <tr>\n");
        emailBuilder.append("                      <td style=\"padding-left: 10px\">\n");
        emailBuilder.append("                        \n");
        emailBuilder.append("                      </td>\n");
        emailBuilder.append("                      <td style=\"font-size: 28px; line-height: 1.315789474; Margin-top: 4px; padding-left: 10px\">\n");
        emailBuilder.append("                        <span style=\"font-family: Helvetica, Arial, sans-serif; font-weight: 700; color: #ffffff; text-decoration: none; vertical-align: top; display: inline-block\">Order Confirmation</span>\n");
        emailBuilder.append("                      </td>\n");
        emailBuilder.append("                    </tr>\n");
        emailBuilder.append("                  </tbody>\n");
        emailBuilder.append("                </table>\n");
        emailBuilder.append("              </td>\n");
        emailBuilder.append("            </tr>\n");
        emailBuilder.append("          </tbody>\n");
        emailBuilder.append("        </table>\n");
        emailBuilder.append("        \n");
        emailBuilder.append("      </td>\n");
        emailBuilder.append("    </tr>\n");
        emailBuilder.append("  </tbody>\n");
        emailBuilder.append("</table>\n");
        emailBuilder.append("\n");
        emailBuilder.append("<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; max-width: 580px; width: 100% !important;\" width=\"100%\">\n");
        emailBuilder.append("  <tbody>\n");
        emailBuilder.append("    <tr>\n");
        emailBuilder.append("      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n");
        emailBuilder.append("      <td>\n");
        emailBuilder.append("        \n");
        emailBuilder.append("        <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse;\">\n");
        emailBuilder.append("          <tbody>\n");
        emailBuilder.append("            <tr>\n");
        emailBuilder.append("              <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n");
        emailBuilder.append("            </tr>\n");
        emailBuilder.append("          </tbody>\n");
        emailBuilder.append("        </table>\n");
        emailBuilder.append("        \n");
        emailBuilder.append("      </td>\n");
        emailBuilder.append("      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n");
        emailBuilder.append("    </tr>\n");
        emailBuilder.append("  </tbody>\n");
        emailBuilder.append("</table>\n");
        emailBuilder.append("\n");
        emailBuilder.append("<table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse: collapse; max-width: 580px; width: 100% !important;\" width=\"100%\">\n");
        emailBuilder.append("  <tbody>\n");
        emailBuilder.append("    <tr>\n");
        emailBuilder.append("      <td height=\"30\"><br></td>\n");
        emailBuilder.append("    </tr>\n");
        emailBuilder.append("    <tr>\n");
        emailBuilder.append("      <td width=\"10\" valign=\"middle\"><br></td>\n");
        emailBuilder.append("      <td style=\"font-family: Helvetica, Arial, sans-serif; font-size: 19px; line-height: 1.315789474; max-width: 560px\">\n");
        emailBuilder.append("        \n");
        emailBuilder.append("        <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c\">Hello, <strong>" + order.getFirstName() + " " + order.getLastName() + "</strong>,</p>\n");
        emailBuilder.append("        <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c\">Thank you for placing an order with Aperture Science! Here are the details of your order:</p>\n");
        emailBuilder.append("        \n");
        emailBuilder.append("        <table style=\"border-collapse: collapse; width: 100%;\" cellpadding=\"0\" cellspacing=\"0\" border=\"1\">\n");
        emailBuilder.append("          <thead>\n");
        emailBuilder.append("            <tr>\n");
        emailBuilder.append("              <th style=\"padding: 10px; background-color: #1D70B8; color: #FFFFFF;\">Item</th>\n");
        emailBuilder.append("              <th style=\"padding: 10px; background-color: #1D70B8; color: #FFFFFF;\">Quantity</th>\n");
        emailBuilder.append("              <th style=\"padding: 10px; background-color: #1D70B8; color: #FFFFFF;\">Price</th>\n");
        emailBuilder.append("            </tr>\n");
        emailBuilder.append("          </thead>\n");
        emailBuilder.append("          <tbody>\n");

        for (CartItem item : order.getItems()) {
            emailBuilder.append("            <tr>\n");
            emailBuilder.append("              <td style=\"padding: 10px;\">" + item.getItem().getName() + "</td>\n");
            emailBuilder.append("              <td style=\"padding: 10px;\">" + item.getQuantity() + "</td>\n");
            emailBuilder.append("              <td style=\"padding: 10px;\">" + item.getItem().getPrice() + "€</td>\n");
            emailBuilder.append("            </tr>\n");
        }

        emailBuilder.append("          </tbody>\n");
        emailBuilder.append("          <tfoot>\n");
        emailBuilder.append("            <tr>\n");
        emailBuilder.append("              <td colspan=\"2\" style=\"padding: 10px; text-align: right; font-weight: bold;\">Total:</td>\n");
        emailBuilder.append("              <td style=\"padding: 10px; font-weight: bold;\">" + order.getTotalPrice() + "€</td>\n");
        emailBuilder.append("            </tr>\n");
        emailBuilder.append("          </tfoot>\n");
        emailBuilder.append("        </table>\n");

        emailBuilder.append("        <p style=\"Margin: 0 0 20px 0; font-size: 19px; line-height: 25px; color: #0b0c0c\">We appreciate your business and hope you enjoy your new items. Remember, the Enrichment Center is committed to the well-being of all participants. Cake and grief counseling will be available at the conclusion of the test. Thank you for your cooperation, and have a pleasant day!</p>\n");
        emailBuilder.append("        \n");
        emailBuilder.append("      </td>\n");
        emailBuilder.append("      <td width=\"10\" valign=\"middle\"><br></td>\n");
        emailBuilder.append("    </tr>\n");
        emailBuilder.append("    <tr>\n");
        emailBuilder.append("      <td height=\"30\"><br></td>\n");
        emailBuilder.append("    </tr>\n");
        emailBuilder.append("  </tbody>\n");
        emailBuilder.append("</table>\n");
        emailBuilder.append("<div class=\"yj6qo\"></div><div class=\"adL\">\n");
        emailBuilder.append("\n");
        emailBuilder.append("</div></div>");

        return emailBuilder.toString();
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email, " + name + "!</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hello " + name + ",</p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Congratulations on registering with Aperture Science! Please proceed to activate your account by clicking on the button below:</p>\n" +
                "            <table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"Margin:20px 0\">\n" +
                "              <tr>\n" +
                "                <td align=\"center\" style=\"border-radius:3px\" bgcolor=\"#1a82e2\">\n" +
                "                  <a href=\"" + link + "\" target=\"_blank\" style=\"display:inline-block;padding:16px 36px;text-decoration:none;background:#1a82e2;border-radius:3px;color:#ffffff;font-size:18px\">Activate Account</a>\n" +
                "                </td>\n" +
                "              </tr>\n" +
                "            </table>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">If the button above doesn't work, you can copy and paste the following link into your browser:</p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + link + "</p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Welcome to the Aperture family! We're looking forward to seeing you in action.</p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Best regards,</p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Cave Johnson<br>Founder and CEO<br>Aperture Science</p>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
