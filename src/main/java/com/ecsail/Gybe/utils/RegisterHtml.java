package com.ecsail.Gybe.utils;

public class RegisterHtml {
    static String buttonColor = "#7ba7fe";
    static String buttonBorderColor = "#000000";
    static String buttonTextColor = "#ffffff";
    static String backgroundColor = "#ffc365"; // last year's color
    static String imageURL = "https://eaglecreeksailing.com/images/2023thumb300.png";
    public static String createEmailWithHtml(String name, String link) {
        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en-US\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "<title></title>\n" +
                "<meta charset=\"utf-8\"/>\n" +
                "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                "<!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\n" +
                "<style>\n" +
                "\t\t* {\n" +
                "\t\t\tbox-sizing: border-box;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tbody {\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\ta[x-apple-data-detectors] {\n" +
                "\t\t\tcolor: inherit !important;\n" +
                "\t\t\ttext-decoration: inherit !important;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t#MessageViewBody a {\n" +
                "\t\t\tcolor: inherit;\n" +
                "\t\t\ttext-decoration: none;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\tp {\n" +
                "\t\t\tline-height: inherit;\n" +
                "\t\t}\n" +
                "\n" +
                "\t\t@media (max-width:520px) {\n" +
                "\t\t\t.icons-inner {\n" +
                "\t\t\t\ttext-align: center;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.icons-inner td {\n" +
                "\t\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.row-content {\n" +
                "\t\t\t\twidth: 100% !important;\n" +
                "\t\t\t}\n" +
                "\n" +
                "\t\t\t.stack .column {\n" +
                "\t\t\t\twidth: 100%;\n" +
                "\t\t\t\tdisplay: block;\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body style=\"background-color: "+backgroundColor+"; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: "+backgroundColor+";\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 500px;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<div class=\"spacer_block\" style=\"height:60px;line-height:60px;font-size:1px;\"> </div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 500px;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 10px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td style=\"padding-top:40px;padding-right:10px;padding-bottom:45px;padding-left:10px;\">\n" +
                "<div style=\"font-family: sans-serif\">\n" +
                "<div style=\"font-size: 14px; mso-line-height-alt: 16.8px; color: #555555; line-height: 1.2; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\">\n" +
                "<p style=\"margin: 0; font-size: 14px; text-align: center;\"><span style=\"font-size:22px;\">It is that time again!</span></p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "<div align=\"center\" style=\"line-height:10px\"><img src=\""+imageURL+"\" style=\"display: block; height: auto; border: 0; width: 300px; max-width: 100%;\" width=\"300\"/></div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td style=\"padding-top:25px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<div style=\"font-family: sans-serif\">\n" +
                "<div style=\"font-size: 14px; mso-line-height-alt: 16.8px; color: #555555; line-height: 1.2; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\">\n" +
                "<p style=\"margin: 0; font-size: 14px; text-align: center;\"><span style=\"font-size:18px;\">Hi "+name+",</span></p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td style=\"padding-top:10px;padding-right:10px;padding-bottom:25px;padding-left:10px;\">\n" +
                "<div style=\"font-family: Arial, sans-serif\">\n" +
                "<div style=\"font-size: 14px; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; mso-line-height-alt: 16.8px; color: #555555; line-height: 1.2;\">\n" +
                "<p style=\"margin: 0; font-size: 14px; text-align: center;\">Please click the link below to open your membership renewal form</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 500px;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"button_block\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tr>\n" +
                "<td style=\"text-align:center;padding-top:10px;padding-right:10px;padding-bottom:50px;padding-left:10px;\">\n" +
                "<div align=\"center\">\n" +
                "<!--[if mso]><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\""+link+"\" style=\"height:52px;width:120px;v-text-anchor:middle;\" arcsize=\"8%\" stroke=\"false\" fillcolor=\"#ffc365\"><w:anchorlock/><v:textbox inset=\"0px,0px,0px,0px\"><center style=\"color:#000000; font-family:Arial, sans-serif; font-size:16px\"><![endif]--><a href=\""+link+"\" style=\"text-decoration:none;display:inline-block;color:"+buttonTextColor+";background-color:"+buttonColor+";border-radius:4px;width:auto;border-top:1px solid "+buttonBorderColor+";border-right:1px solid "+buttonBorderColor+";border-bottom:1px solid "+buttonBorderColor+";border-left:1px solid "+buttonBorderColor+";padding-top:10px;padding-bottom:10px;font-family:'Helvetica Neue', Helvetica, Arial, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\" target=\"_blank\"><span style=\"padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;letter-spacing:normal;\"><span style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\">Open Form</span></span></a>\n" +
                "<!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-5\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 500px;\" width=\"500\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td class=\"column\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\" width=\"100%\">\n" +
                "<div class=\"spacer_block\" style=\"height:60px;line-height:60px;font-size:1px;\"> </div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table><!-- End -->\n" +
                "</body>\n" +
                "</html>";
    }
}
