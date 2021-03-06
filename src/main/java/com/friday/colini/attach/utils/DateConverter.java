package com.friday.colini.attach.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
public class DateConverter {
    public Optional<Date> localDateTimeToDate(@Nullable final LocalDateTime localDateTime) {
        return localDateTimeToDate(localDateTime, ZoneId.systemDefault());
    }

    public Optional<Date> localDateTimeToDate(
            @Nullable final LocalDateTime localDateTime,
            @NonNull final ZoneId zoneId
    ) {
        if (Objects.isNull(localDateTime)) {
            return Optional.empty();
        }

        return Optional.of(Date.from(localDateTime.atZone(zoneId).toInstant()));
    }

    public Optional<LocalDateTime> dateToLocalDateTime(@Nullable final Date date) {
        return dateToLocalDateTime(date, ZoneId.systemDefault());
    }

    public Optional<LocalDateTime> dateToLocalDateTime(
            @Nullable final Date date,
            @NonNull final ZoneId zoneId
    ) {
        if (Objects.isNull(date)) {
            return Optional.empty();
        }

        return Optional.of(LocalDateTime.ofInstant(millisToInstant(date.getTime()), zoneId));
    }

    public @NonNull Instant millisToInstant(final long millis) {
        return Instant.ofEpochMilli(millis);
    }
}
