package de.ksbrwsk.html;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendMailRunner implements ApplicationRunner {

    private static final Resource MAIL_TEMPLATE = new ClassPathResource("mail_template.mustache");

    private final JavaMailSender javaMailSender;

    public SendMailRunner(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String populatedTemplate = createMustacheContextAndPopulateTemplate();
        var mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom("mailpit@localhost");
        mimeMessageHelper.setTo("mailpit@localhost");
        mimeMessageHelper.setSubject("HTML Mail Example");
        mimeMessageHelper.setText(populatedTemplate, true);
        this.javaMailSender.send(mimeMessage);
    }

    private static String createMustacheContextAndPopulateTemplate() throws Exception {
        String contentsOfTestEmailMustacheFile = MAIL_TEMPLATE.getContentAsString(StandardCharsets.UTF_8);
        Map<String, Object> context = new HashMap<>();
        context.put("name", "John Doe");
        context.put("author", "Jane Doe");
        List<BlogEntry> blogEntries = new ArrayList<>();
        blogEntries.add(new BlogEntry("Blog Entry Subject 1", "Blog Body 1 ..."));
        blogEntries.add(new BlogEntry("Blog Entry Subject 2", "Blog Body 2 ..."));
        blogEntries.add(new BlogEntry("Blog Entry Subject 3", "Blog Body 3 ..."));
        context.put("blogEntries", blogEntries);
        context.put("blogEntriesExist", !blogEntries.isEmpty());
        Mustache.Compiler compiler = Mustache.compiler();
        return compiler.compile(contentsOfTestEmailMustacheFile).execute(context);
    }
}
