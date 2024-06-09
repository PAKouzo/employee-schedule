package vju.finalexam.project.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateUtils {
    public static String formatZDT(Locale locale, ZonedDateTime time) {
        return time
                .format(
                    DateTimeFormatter
                    .ofLocalizedDateTime(
                        FormatStyle.SHORT
                    )
                    .withLocale( 
                        locale
                    )
                );
    }
}
